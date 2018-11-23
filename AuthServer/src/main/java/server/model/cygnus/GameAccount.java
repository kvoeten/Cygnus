/*
    This file is part of AuthAPI by Kaz Voeten.
    AuthAPI is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    AuthAPI is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with AuthAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package server.model.cygnus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import server.hikari.Database;

/**
 *
 * @author kaz_v
 */
public class GameAccount {

    private final int nAccountID, nNexonCash, nMaplePoint, nMileage;
    private final boolean bVerified;
    private final String sAccountName, sEmail, sIP, sSecondPW;
    private final byte nState, nBanned, nGender, nGradeCode;
    private final Date pCreateDate, pLastLoadDate, pBirthDate;
    private final short nLastWorldID;

    public GameAccount(int nAccountID, int nNexonCash, int nMaplePoint, int nMileage,
            boolean bVerified, String sAccountName, String sEmail, String sIP,
            String sSecondPW, byte nGradeCode, byte nBanned, byte nState, byte nGender, Date pCreateDate, Date pLastLoadDate,
            Date pBirthDate, short nLastWorldID) {
        this.nAccountID = nAccountID;
        this.nNexonCash = nNexonCash;
        this.nMaplePoint = nMaplePoint;
        this.nMileage = nMileage;
        this.bVerified = bVerified;
        this.sAccountName = sAccountName;
        this.sEmail = sEmail;
        this.sIP = sIP;
        this.sSecondPW = sSecondPW;
        this.nGradeCode = nGradeCode;
        this.nBanned = nBanned;
        this.nState = nState;
        this.nGender = nGender;
        this.pCreateDate = pCreateDate;
        this.pLastLoadDate = pLastLoadDate;
        this.pBirthDate = pBirthDate;
        this.nLastWorldID = nLastWorldID;
    }

    public static GameAccount GetAccount(String sAccountName) {
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM account_cygnus WHERE sAccountName = ?");) {
            ps.setString(1, sAccountName);

            try (ResultSet rs = ps.executeQuery();) {
                if (rs.first()) {
                    GameAccount account = new GameAccount(
                            rs.getInt("nAccountID"),
                            rs.getInt("nNexonCash"),
                            rs.getInt("nMaplePoint"),
                            rs.getInt("nMileage"),
                            rs.getBoolean("bVerified"),
                            rs.getString("sAccountName"),
                            rs.getString("sEmail"),
                            rs.getString("sIP"),
                            rs.getString("sSecondPW"),
                            rs.getByte("nGradeCode"),
                            rs.getByte("nBanned"),
                            rs.getByte("nState"),
                            rs.getByte("nGender"),
                            rs.getDate("pCreateDate"),
                            rs.getDate("pLastLoadDate"),
                            rs.getDate("pBirthDate"),
                            rs.getShort("nLastWorldID")
                    );
                    rs.close();
                    return account;
                }
            } catch (Exception ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Autowired ObjectMapper mapper;
    public ObjectNode toObjectNode() {
        ObjectNode account = mapper.createObjectNode();
        account.put("nAccountID", nAccountID);
        account.put("nNexonCash", nNexonCash);
        account.put("nMaplePoint", nMaplePoint);
        account.put("nMileage", nMileage);
        account.put("bVerified", bVerified);
        account.put("sAccountName", sAccountName);
        account.put("sEmail", sEmail);
        account.put("sIP", sIP);
        account.put("sSecondPW", sSecondPW);
        account.put("nGradeCode", nGradeCode);
        account.put("nBanned", nBanned);
        account.put("nState", nState);
        account.put("nGender", nGender);
        account.put("pCreateDate", pCreateDate.toString());
        account.put("pLastLoadDate", pLastLoadDate.toString());
        account.put("pBirthDate", pBirthDate.toString());
        account.put("nLastWorldID", nLastWorldID);
        return account;
    }

}
