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
package server.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import javafx.util.Pair;

/**
 *
 * @author kaz_v
 */
public class Database {

    private static final HikariConfig pConfig; //Hikari database config.
    private static final HikariDataSource pDataSource; //Hikari datasource based on config.
    private static HashMap<String, Pair<String, Date>> authCodes = new HashMap<>(); //Map of account verification codes sorted by email.

    static {
        //Check if file exists, if not: create and use default file.
        File properties = new File("database.properties");
        if (!properties.exists()) {
            try (FileOutputStream fout = new FileOutputStream(properties)) {
                PrintStream out = new PrintStream(fout);
                out.println("dataSourceClassName=org.mariadb.jdbc.MariaDbDataSource");
                out.println("dataSource.user=root");
                out.println("dataSource.password=");
                out.println("dataSource.databaseName=service");
                out.println("dataSource.portNumber=3306");
                out.println("dataSource.serverName=localhost");
                fout.flush();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("No database.properties file found. A default one has been generated.");
        }
        pConfig = new HikariConfig("database.properties");
        pDataSource = new HikariDataSource(pConfig);
    }

    public static Connection GetConnection() throws SQLException {
        return pDataSource.getConnection();
    }
}
