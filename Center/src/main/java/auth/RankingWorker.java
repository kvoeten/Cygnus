/*
 * Copyright (C) 2018 Kaz Voeten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package auth;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import user.character.AvatarData;

/**
 * @author Kaz Voeten
 */
public class RankingWorker extends Thread {

    public static void Schedule() {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new RankingWorker(), 12, 12, TimeUnit.HOURS);
    }

    @Override
    public void run() {
        ProcessRanking();
    }

    public static void ProcessRanking() {
        try ( Connection con = Database.GetConnection();  PreparedStatement ps = con.prepareStatement("SELECT dwCharacterID, nJob, nExp64, nLevel, nPop FROM gw_characterstat ORDER BY nExp64 DESC")) {
            try ( ResultSet rs = ps.executeQuery()) {
                int nRank = 0;
                HashMap<Integer, Integer> mJobRank = new HashMap<>();
                while (rs.next()) {
                    int dwCharacterID = rs.getInt("dwCharacterID");
                    if (IsEligibleForRanking(con, dwCharacterID)) {
                        int nJob = rs.getInt("nJob");

                        //Track rank by job.
                        int nJobRank = 1;
                        if (mJobRank.containsKey(nJob)) {
                            nJobRank = mJobRank.get(nJob);
                        }
                        mJobRank.put(nJob, nJobRank + 1);

                        UpdateRanking(con, ++nRank, nJobRank, dwCharacterID);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RankingWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean IsEligibleForRanking(Connection con, int dwCharacterID) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("SELECT nOverAllRank FROM avatardata WHERE dwCharacterID = " + dwCharacterID);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                if (rs.getInt("nOverallRank") == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void UpdateRanking(Connection con, int nNewRank, int nNewJobRank, int dwCharacterID) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("SELECT nRank, nOverallRank FROM avatardata WHERE dwCharacterID = " + dwCharacterID);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int nOverallRank = rs.getInt("nOverallRank");
                int nOverallRankMove = nOverallRank - nNewRank;

                int nRank = rs.getInt("nRank");
                int nRankMove = nRank - nNewJobRank;

                try ( PreparedStatement update = con.prepareStatement("UPDATE avatardata SET nRank = ?, nRankMove = ?, nOverallRank = ?, nOverallRankMove = ? WHERE dwCharacterID = ?")) {
                    Database.Excecute(con, update, nRank, nRankMove, nOverallRank, nOverallRankMove, dwCharacterID);
                }
            }
        }
    }

    public static void SendWorldCharacterInfo() {
        try ( Connection con = Database.GetConnection();  PreparedStatement ps = con.prepareStatement("SELECT * FROM avatardata");  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AvatarData pAvatar = AvatarData.LoadAvatar(con, rs.getInt("dwCharacterID"));
                // TODO: HTTP Put ranking to auth API
            }
        } catch (SQLException ex) {
            Logger.getLogger(RankingWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
