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
package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Kaz Voeten
 */
public class Database {
    private static HikariDataSource pDataSource;

    private void Initialize() {
        File pFile = new File(this.getClass().getClassLoader().getResource("database.properties").getFile());
        if (!pFile.exists()) {
            System.out.println("Database configuration file could not be found!.");
            System.exit(0);
        }
        Properties pProperties = new Properties();
        try (FileReader reader = new FileReader(pFile)) {
            pProperties.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pDataSource = new HikariDataSource(new HikariConfig(pProperties));
    }

    public static Connection GetConnection() throws SQLException {
        if (pDataSource == null) {
            (new Database()).Initialize();
        }
        return pDataSource.getConnection();
    }

    private static int Bind(PreparedStatement ps, Object... aCommands) {
        for (int i = 1; i <= aCommands.length; i++) {
            Object pCommand = aCommands[i - 1];
            if (pCommand != null) {
                try {
                    if (pCommand instanceof Number) {
                        // Specific to only setByte calls, default Integer
                        if (pCommand instanceof Byte) {
                            ps.setByte(i, (Byte) pCommand);
                        } else if (pCommand instanceof Short) {
                            ps.setShort(i, (Short) pCommand);
                            // Specific to only setLong calls, default Integer
                        } else if (pCommand instanceof Long) {
                            ps.setLong(i, (Long) pCommand);
                        } else if (pCommand instanceof Double) {
                            ps.setDouble(i, (Double) pCommand);
                            // Almost all types are INT(11), so default to this
                        } else {
                            ps.setInt(i, (Integer) pCommand);
                        }
                        // If it is otherwise a String, we only require setString
                    } else if (pCommand instanceof String) {
                        ps.setString(i, (String) pCommand);
                    } else if (pCommand instanceof Boolean) {
                        ps.setBoolean(i, (Boolean) pCommand);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(System.err);
                }
            } else {
                return -4;
            }
        }
        return 1;
    }

    public static int Excecute(Connection con, PreparedStatement ps, Object... aCommands) throws SQLException {
        if (ps != null) {
            int nResult = Bind(ps, aCommands);

            if (nResult > 0) {
                int nAffectedRows = ps.executeUpdate();
                if (nAffectedRows == 0) {
                    String sQuery = ps.toString();
                    // The only valid DML statement for re-insertion is UPDATE.
                    if (!sQuery.contains("DELETE FROM") && !sQuery.contains("INSERT INTO")) {
                        // Substring based on if the query contains '?' IN params or not
                        if (sQuery.contains("', parameters")) {
                            sQuery = sQuery.substring(sQuery.indexOf("UPDATE"), sQuery.indexOf("', parameters"));
                        } else {
                            sQuery = sQuery.substring(sQuery.indexOf("UPDATE"));
                        }

                        // Begin the new query, starting by converting an update to an insert
                        String sNewQuery = sQuery.replaceAll("UPDATE", "INSERT INTO");

                        // Substring the FRONT rows (prior to WHERE condition)
                        String rows;
                        if (sNewQuery.contains("WHERE")) {
                            rows = sNewQuery.substring(sNewQuery.indexOf("SET ") + "SET ".length(), sNewQuery.indexOf("WHERE "));
                        } else {
                            rows = sNewQuery.substring(sNewQuery.indexOf("SET ") + "SET ".length());
                        }
                        // Construct an array of every front row
                        String[] frontRows = rows.replaceAll(" = \\?, ", ", ").replaceAll(" = \\? ", ", ").split(", ");
                        // Not all queries perform an UPDATE with a WHERE condition, allocate empty back rows
                        String[] backRows = {};
                        // If the query does contain a WHERE condition, parse the back rows (everything after WHERE)
                        if (sNewQuery.contains("WHERE")) {
                            rows = sNewQuery.substring(sNewQuery.indexOf("WHERE ") + "WHERE ".length());
                            backRows = rows.replaceAll(" = \\? AND ", ", ").replaceAll(" = \\?", ", ").split(", ");
                        }

                        // Merge the front and back rows into one table, these are all columns being inserted
                        String[] rowData = new String[frontRows.length + backRows.length];
                        System.arraycopy(frontRows, 0, rowData, 0, frontRows.length);
                        System.arraycopy(backRows, 0, rowData, frontRows.length, backRows.length);

                        // Begin transforming the query - clear the rest of the string, transform to (Col1, Col2, Col3)
                        sNewQuery = sNewQuery.substring(0, sNewQuery.indexOf("SET "));
                        sNewQuery += "(";
                        for (String row : rowData) {
                            sNewQuery += row + ", ";
                        }
                        // Trim the remaining , added at the end of the last column
                        sNewQuery = sNewQuery.substring(0, sNewQuery.length() - ", ".length());

                        // Begin appending the VALUES(?, ?) for the total size there is rows
                        sNewQuery += ") VALUES(";
                        for (String row : rowData) {
                            sNewQuery += "?, ";
                        }
                        // Trim the remaining , added at the end of the last column
                        sNewQuery = sNewQuery.substring(0, sNewQuery.length() - ", ".length());
                        sNewQuery += ")";

                        return Excecute(con, con.prepareStatement(sNewQuery), aCommands);
                    }
                }
                return nAffectedRows;
            }
            return nResult;
        }
        return -1;
    }
}
