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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.hikari.Database;

/**
 *
 * @author Kaz Voeten
 */
public class Role {

    private Long roleId;
    private String name, description;

    private Set<Privilege> privileges = new HashSet<>(0);

    public static Collection<Role> getRoles(int nAccountID) {
        List<Role> roles = new ArrayList<>();
        try (Connection con = Database.GetConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM account_roles WHERE account_id = ?")) {
            ps.setInt(1, nAccountID);

            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Role role = loadRoleByID(rs.getInt("role_id"), con);
                    roles.add(role);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }

    private static Role loadRoleByID(int role_id, Connection con) {
        Role role = new Role();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM roles WHERE id = ?")) {
            ps.setInt(1, role_id);

            try (ResultSet rs = ps.executeQuery();) {
                if (rs.first()) {
                    role.setRoleId((long) role_id);
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                    role.setPrivileges(loadPrivilegesByRoleID(role_id, con));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }

    private static Set<Privilege> loadPrivilegesByRoleID(int role_id, Connection con) {
        Set<Privilege> privileges = new HashSet<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM role_privileges WHERE role_id = ?")) {
            ps.setInt(1, role_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                privileges.add(Privilege.loadPrivilegeByID(rs.getInt("privilege_id"), con));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privileges;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
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

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(final Set<Privilege> privileges) {
        this.privileges = privileges;
    }
}
