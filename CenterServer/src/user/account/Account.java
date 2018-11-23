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
package user.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.OutPacket;
import user.character.AvatarData;
import user.character.CharacterData;
import database.Database;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaz Voeten
 */
public class Account {

    public final int nAccountID;
    public long nSessionID;
    public int nNexonCash, nMaplePoint, nMileage, nLastWorldID, nCharSlots = 8;
    public String sAccountName, sIP, sSecondPW;
    public byte nState, nGender, nGradeCode;
    public Date dBirthDay, dLastLoggedIn, dCreateDate;
    private List<AvatarData> aAvatarData = new LinkedList<>(); //Charlist
    public CharacterData pCharacterData; //Where we add the logged in character for transitions.

    public Account(int nAccountID, long nSessionID, String sAccountName, String sIP, String sSecondPW,
            byte nState, byte nGender, Date dLastLoggedIn, Date dBirthDay, Date dCreateDate, byte nGradeCode,
            short nLastWorldID, int nNexonCash, int nMaplePoint, int nMileage) {
        this.nAccountID = nAccountID;
        this.nSessionID = nSessionID;
        this.sAccountName = sAccountName;
        this.sIP = sIP;
        this.sSecondPW = sSecondPW;
        this.nState = nState;
        this.nGender = nGender;
        this.dLastLoggedIn = dLastLoggedIn;
        this.dBirthDay = dBirthDay;
        this.dCreateDate = dCreateDate;
        this.nGradeCode = nGradeCode;
        this.nLastWorldID = nLastWorldID;
        this.nNexonCash = nNexonCash;
        this.nMaplePoint = nMaplePoint;
        this.nMileage = nMileage;
        LoadLocalInfo();
    }

    public void Encode(OutPacket oPacket) {
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeString(sAccountName);
        oPacket.EncodeString(sIP);
        oPacket.EncodeString(sSecondPW);
        oPacket.EncodeByte(nState);
        oPacket.EncodeByte(nGender);
        oPacket.EncodeByte(nGradeCode);
        oPacket.EncodeInt(nCharSlots);
    }
    
    private void LoadLocalInfo() {
        try (Connection con = Database.GetConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM account_info WHERE nAccountID = ?")){
            ps.setInt(0, nAccountID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.first()) {
                    nCharSlots = rs.getInt("nCharSlots");
                } else {
                    SetLocalInfo(con);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SetLocalInfo(Connection con) {
        try(PreparedStatement ps = con.prepareStatement("INSERT INTO account_info (nAccountID, nCharSlots) VALUES (? ,?)")) {
            Database.Excecute(con, ps, nAccountID, 8);
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public AvatarData GetAvatar(int dwCharacterID) {
        for (AvatarData pAvatar: aAvatarData) {
            if (pAvatar.pCharacterStat.dwCharacterID == dwCharacterID) {
                return pAvatar;
            }
        }
        return null;
    }

    public List<AvatarData> GetAvatars(int nAccountID, boolean bReload) {
        if (bReload) {
            aAvatarData.clear();
            try (Connection con = Database.GetConnection();
                    PreparedStatement ps = con.prepareStatement("SELECT dwCharacterID FROM avatardata WHERE nAccountID = ?");) {
                ps.setInt(1, nAccountID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        aAvatarData.add(AvatarData.LoadAvatar(con, rs.getInt("dwCharacterID")));
                    }
                    Collections.sort(aAvatarData, (AvatarData o1, AvatarData o2) -> o1.nCharlistPos - o2.nCharlistPos);
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                    return aAvatarData;
                }
                
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                return aAvatarData;
            }
        }
        return aAvatarData;
    }
}
