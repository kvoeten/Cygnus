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
package server.center.packet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.InPacket;
import net.OutPacket;
import server.hikari.Database;

/**
 *
 * @author Kaz Voeten
 */
public class Center {

    public static void OnAddBlock(InPacket iPacket) {
        int nAccountID = -1;
        String sIP = "", sMAC = "", sHWID = "";

        int nType = iPacket.DecodeInt();
        String sReason = iPacket.DecodeString();
        long uBanDuration = iPacket.DecodeLong();

        Date pNow = new java.sql.Date(System.currentTimeMillis());
        Date pEnd = new java.sql.Date(System.currentTimeMillis() + uBanDuration);

        switch (nType) {
            case 0:
                sIP = iPacket.DecodeString();
                IPBan(sIP, pNow, pEnd);
                break;
            case 1:
                nAccountID = iPacket.DecodeInt();
                AccountBan(nType, nAccountID, pNow);
                break;
            case 2:
                nAccountID = iPacket.DecodeInt();
                sIP = iPacket.DecodeString();
                AccountBan(nType, nAccountID, pNow);
                IPBan(sIP, pNow, pEnd);
                break;
            case 3:
                sIP = iPacket.DecodeString();
                sMAC = iPacket.DecodeString();
                sHWID = iPacket.DecodeString();
                IPBan(sIP, pNow, pEnd);
                MACBan(sMAC, pNow, pEnd);
                HWIDBan(sHWID, pNow, pEnd);
                break;
            case 4:
                nAccountID = iPacket.DecodeInt();
                sIP = iPacket.DecodeString();
                sMAC = iPacket.DecodeString();
                sHWID = iPacket.DecodeString();
                AccountBan(nType, nAccountID, pNow);
                IPBan(sIP, pNow, pEnd);
                MACBan(sMAC, pNow, pEnd);
                HWIDBan(sHWID, pNow, pEnd);
                break;
        }

        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO banlog (nType, sReason, pBanDate, pBanEndDate, nAccountID, sIP, sMAC, sHWID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, nType);
            ps.setString(2, sReason);
            ps.setDate(3, pNow);
            ps.setDate(4, pEnd);
            ps.setInt(5, nAccountID);
            ps.setString(6, sIP);
            ps.setString(7, sMAC);
            ps.setString(8, sHWID);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void AccountBan(int nType, int nAccountID, java.sql.Date pNow) {
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE account_cygnus SET nBanned = ?, pBanDate = ? WHERE nAccountID = ?;")) {
            ps.setInt(1, nType);
            ps.setDate(2, pNow);
            ps.setInt(3, nAccountID);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void IPBan(String sIP, java.sql.Date pNow, java.sql.Date pEnd) {
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO ipban (sIP, pBanDate, pBanEndDate) VALUES (?, ?, ?)")) {
            ps.setString(1, sIP);
            ps.setDate(2, pNow);
            ps.setDate(3, pEnd);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void MACBan(String sMAC, java.sql.Date pNow, java.sql.Date pEnd) {
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO macban (sMAC, pBanDate, pBanEndDate) VALUES (?, ?, ?)")) {
            ps.setString(1, sMAC);
            ps.setDate(2, pNow);
            ps.setDate(3, pEnd);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void HWIDBan(String sHWID, java.sql.Date pNow, java.sql.Date pEnd) {
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO hwidban (sHWID, pBanDate, pBanEndDate) VALUES (?, ?, ?)")) {
            ps.setString(1, sHWID);
            ps.setDate(2, pNow);
            ps.setDate(3, pEnd);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static OutPacket OnSetPIC(InPacket iPacket) {
        int nAccountID = iPacket.DecodeInt();
        String sSecondPassword = iPacket.DecodeString(); //Already encrypted :3

        boolean bSuccess = false;
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE account_cygnus SET sSecondPW = ? WHERE nAccountID = ?")) {
            ps.setString(1, sSecondPassword);
            ps.setInt(2, nAccountID);
            if (ps.executeUpdate() > 0) {
                bSuccess = true;
            }
            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        OutPacket oPacket = new OutPacket(LoopBackPacket.UpdateSPWResult);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeString(sSecondPassword);
        oPacket.EncodeBool(bSuccess);

        return oPacket;
    }

    public static OutPacket SetState(InPacket iPacket) {
        int nAccountID = iPacket.DecodeInt();
        byte nState = iPacket.DecodeByte();
        boolean bSuccess = false;
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE account_cygnus SET nState = ? WHERE nAccountID = ?")) {

            ps.setByte(1, nState);
            ps.setInt(2, nAccountID);
            if (ps.executeUpdate() > 0) {
                bSuccess = true;
            }

            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        OutPacket oPacket = new OutPacket(LoopBackPacket.SetState);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeBool(bSuccess);

        return oPacket;
    }

    public static void OnReceiveAvatarData(InPacket iPacket) {
        try (Connection con = Database.GetConnection()) {
            int i = 0, dwCharacterID = iPacket.DecodeInt();
            try (PreparedStatement ps = con.prepareStatement("REPLACE INTO avatar (dwCharacterID, nAccountID, nWorld, nOverallRank, nOverallRankMove, nRank, nRankMove, nLevel, nJob, nExp64, nPop, nGender, nSkin, nHair, nFace, sCharacterName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(++i, dwCharacterID);
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setLong(++i, iPacket.DecodeLong());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setInt(++i, iPacket.DecodeInt());
                ps.setString(++i, iPacket.DecodeString());
                ps.execute();
            }

            byte nPos = iPacket.DecodeByte();
            while (nPos != (byte) 0xFF) {
                try (PreparedStatement ps = con.prepareStatement("REPLACE INTO avatar_equip (dwCharacterID, nPos, nItemID) VALUES (?, ?, ?)")) {
                    ps.setInt(1, dwCharacterID);
                    ps.setByte(2, nPos);
                    ps.setInt(3, iPacket.DecodeInt());
                    ps.execute();
                }
                nPos = iPacket.DecodeByte();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
