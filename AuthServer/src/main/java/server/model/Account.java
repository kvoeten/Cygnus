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
package server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.hikari.Database;

/**
 *
 * @author kaz_v
 */
public class Account {

    private final int id;
    private final boolean verified;
    private final String username, password;
    private final Collection<Role> roles; //API access roles

    public Account(int id, String username, String password, boolean verified) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.verified = verified;
        this.roles = Role.getRoles(id);
    }

    public static Optional<Account> GetAccount(String name) {
        try (Connection connection = Database.GetConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE name = ?");) {
            ps.setString(1, name);
            
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.first()) {
                    Account account = new Account(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getBoolean("verified")
                    );
                    rs.close();
                    return Optional.of(account);
                }
            } catch (Exception ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassowrd() {
        return this.password;
    }

    public boolean getVerified() {
        return this.verified;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

}
