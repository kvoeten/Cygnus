/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaz Voeten
 */
public class Privilege {
    private long privilegeId;
    private String name, description;
    
    static Privilege loadPrivilegeByID(int id, Connection con) {
        Privilege priv = new Privilege();
        try(PreparedStatement ps = con.prepareStatement("SELECT * FROM privileges WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                priv.setPrivilegeId(id);
                priv.setName(rs.getString("name"));
                priv.setDescription(rs.getString("description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
        }
        return priv;
    }

    public long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(final long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
