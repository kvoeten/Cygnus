/*
    This file is part of Desu: MapleStory v62 Server Emulator
    Copyright (C) 2014  Zygon <watchmystarz@hotmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Properties;

/**
 * Configurations regarding the Login Server. Mirrors the way Invictus
 * used to load configurations.
 *
 * @author Zygon
 */
public class Configuration {

    // [Game Server Information]
    public static int PORT;
    public static int SERVER_TYPE;
    public static short MAPLE_VERSION;
    public static String BUILD_VERSION;
    public static int MAXIMUM_CONNECTIONS;
    public static boolean SERVER_CHECK;

    // [Game Server Events]
    public static int EVENT_EXP_MOD;
    public static int EVENT_DROP_MOD;
    public static int PARTY_EXP_MOD;

    // [Game Server Information]
    public static int CHANNEL_ID;
    public static InetAddress PUBLIC_IP;
    public static boolean AGE_RESTRICTED;

    // [Center Server Information]
    public static String CENTER_SERVER;
    public static int CENTER_SERVER_PORT;
    public static String CENTER_SERVER_KEY;

    // [World Database Information]
    public static String URL;
    public static String USER;
    public static String PASS;

    // [Auth API Details]
    static String AUTH_API_URL;

    static {
        File f = new File("config.ini");
        if (!f.exists()) {
            try (FileOutputStream fout = new FileOutputStream(f)) {
                PrintStream out = new PrintStream(fout);
                out.println("[Game Service Information]");
                out.println("PORT = ");
                out.println("SERVER_TYPE = ");
                out.println("MAPLE_VERSION = ");
                out.println("FILE_VERSION = ");
                out.println("BUILD_VERSION = ");
                out.println("WZ_DIRECTORY = ");
                out.println("MAXIMUM_CONNECTIONS = ");
                out.println("SERVER_CHECK = ");
                out.println();
                out.println("[Channel Server Events]");
                out.println("EVENT_EXP_MOD = ");
                out.println("EVENT_DROP_MOD = ");
                out.println("PARTY_EXP_MOD = ");
                out.println("THIRD_KILL_EVENT = ");
                out.println();
                out.println("[Channel Server Information]");
                out.println("CHANNEL_ID = ");
                out.println("PUBLIC_IP = ");
                out.println("AGE_RESTRICTED = ");
                out.println();
                out.println("[Center Server Information]");
                out.println("CENTER_SERVER = ");
                out.println("CENTER_SERVER_PORT = ");
                out.println("CENTER_SERVER_KEY = ");
                out.println();
                out.println("[Database Information]");
                out.println("URL = ");
                out.println("USER = ");
                out.println("PASS = ");
                out.println();
                out.println("[Auth API Details]");
                out.println("AUTH_API_URL = ");
                out.println();
                fout.flush();
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Please configure 'config.ini' and relaunch the Channel Server.");
            System.exit(0);
        }
        Properties p = new Properties();
        try (FileReader fr = new FileReader(f)) {
            p.load(fr);
            PORT = Integer.parseInt(p.getProperty("PORT"));
            SERVER_TYPE = Integer.parseInt(p.getProperty("SERVER_TYPE"));
            MAPLE_VERSION = Short.parseShort(p.getProperty("MAPLE_VERSION"));
            BUILD_VERSION = p.getProperty("BUILD_VERSION");
            MAXIMUM_CONNECTIONS = Integer.parseInt(p.getProperty("MAXIMUM_CONNECTIONS"));
            SERVER_CHECK = Boolean.parseBoolean(p.getProperty("SERVER_CHECK"));

            EVENT_EXP_MOD = Integer.parseInt(p.getProperty("EVENT_EXP_MOD"));
            EVENT_DROP_MOD = Integer.parseInt(p.getProperty("EVENT_DROP_MOD"));
            PARTY_EXP_MOD = Integer.parseInt(p.getProperty("PARTY_EXP_MOD"));

            CHANNEL_ID = Integer.parseInt(p.getProperty("CHANNEL_ID"));
            PUBLIC_IP = InetAddress.getByName(p.getProperty("PUBLIC_IP"));
            AGE_RESTRICTED = Boolean.parseBoolean(p.getProperty("AGE_RESTRICTED"));

            CENTER_SERVER = p.getProperty("CENTER_SERVER");
            CENTER_SERVER_PORT = Integer.parseInt(p.getProperty("CENTER_SERVER_PORT"));
            CENTER_SERVER_KEY = p.getProperty("CENTER_SERVER_KEY");

            URL = p.getProperty("URL");
            USER = p.getProperty("USER");
            PASS = p.getProperty("PASS");

            AUTH_API_URL = p.getProperty("AUTH_API_URL");
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.clear();
    }

    private Configuration() {
    }
}
