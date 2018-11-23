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
import java.util.Properties;

/**
 * Configurations regarding the Login Server. Mirrors the way Invictus
 * used to load configurations.
 * 
 * @author Zygon
 */
public class Configuration {

    // [World Server Information]
    public static int WORLD_ID;
    public static String WORLD_NAME;
    public static int EXPERIENCE_MOD;
    public static int DROP_MOD;
    public static int EVENT_FLAG;
    public static String EVENT_MESSAGE;
    public static String SERVER_MESSAGE;
    public static int EVENT_EXP;
    public static int EVENT_DROP;
    public static boolean DISABLE_CHAR_CREATION;
    public static boolean SERVER_CHECK;

    // [Login Server Information]
    public static String LOGIN_SERVER_IP;
    public static short LOGIN_SERVER_PORT;
    public static String CENTER_SERVER_KEY;
    
    // [Auth Server Information]
    public static String AUTH_SERVER_IP;
    public static short AUTH_SERVER_PORT;

    // [Ranking Worker Information]
    public static boolean RANKING_WORKER;
    public static long RANKING_WORKER_INTERVAL;

    // [Database Information]
    public static String URL;
    public static String USER;
    public static String PASS;

    // [Game Server Details]
    public static int GAME_SERVER_PORT;

    // [AuthAPI Server Details]
    public static String AUTH_API_URL;

    static {
        File f = new File("config.ini");
        if (!f.exists()) {
            try (FileOutputStream fout = new FileOutputStream(f)) {
                PrintStream out = new PrintStream(fout);
                out.println("[Game Server Information]");
                out.println("WORLD_ID = ");
                out.println("WORLD_NAME = ");
                out.println("EXPERIENCE_MOD = ");
                out.println("DROP_MOD = ");
                out.println("EVENT_FLAG = ");
                out.println("EVENT_MESSAGE = ");
                out.println("SERVER_MESSAGE = ");
                out.println("EVENT_EXP = 100");
                out.println("EVENT_DROP = 100");
                out.println("DISABLE_CHAR_CREATION = ");
                out.println("SERVER_CHECK = ");
                out.println();
                out.println("[Login Server Information]");
                out.println("LOGIN_SERVER_IP = ");
                out.println("LOGIN_SERVER_PORT = ");
                out.println("CENTER_SERVER_KEY = ");
                out.println();
                out.println("[Auth Server Information]");
                out.println("AUTH_SERVER_IP = ");
                out.println("AUTH_SERVER_PORT = ");
                out.println();
                out.println("[Ranking Worker Information]");
                out.println("RANKING_WORKER = ");
                out.println("RANKING_WORKER_INTERVAL = ");
                out.println();
                out.println("[Database Information]");
                out.println("URL = ");
                out.println("USER = ");
                out.println("PASS = ");
                out.println();
                out.println("[Game Server Details]");
                out.println("GAME_SERVER_PORT = ");
                out.println();
                out.println("[AuthAPI Server Details]");
                out.println("AUTH_API_URL = ");
                fout.flush();
                fout.close();
            } catch (Exception e) {
            }
            System.out.println("Please configure 'config.ini' and relaunch the World Server.");
            System.exit(0);
        }
        Properties p = new Properties();
        try (FileReader fr = new FileReader(f)) {
            p.load(fr);
            WORLD_ID = Integer.parseInt(p.getProperty("WORLD_ID"));
            WORLD_NAME = p.getProperty("WORLD_NAME");
            EXPERIENCE_MOD = Integer.parseInt(p.getProperty("EXPERIENCE_MOD"));
            DROP_MOD = Integer.parseInt(p.getProperty("DROP_MOD"));
            EVENT_FLAG = Integer.parseInt(p.getProperty("EVENT_FLAG"));
            EVENT_MESSAGE = p.getProperty("EVENT_MESSAGE");
            SERVER_MESSAGE = p.getProperty("SERVER_MESSAGE");
            SERVER_CHECK = Boolean.parseBoolean(p.getProperty("SERVER_CHECK", "false"));

            EVENT_EXP = Integer.parseInt(p.getProperty("EVENT_EXP"));
            EVENT_DROP = Integer.parseInt(p.getProperty("EVENT_DROP"));
            RANKING_WORKER = Boolean.parseBoolean(p.getProperty("RANKING_WORKER", "false"));

            LOGIN_SERVER_IP = p.getProperty("LOGIN_SERVER_IP");
            LOGIN_SERVER_PORT = Short.parseShort(p.getProperty("LOGIN_SERVER_PORT"));
            CENTER_SERVER_KEY = p.getProperty("CENTER_SERVER_KEY");

            AUTH_SERVER_IP = p.getProperty("AUTH_SERVER_IP");
            AUTH_SERVER_PORT = Short.parseShort(p.getProperty("AUTH_SERVER_PORT"));

            RANKING_WORKER = Boolean.parseBoolean(p.getProperty("RANKING_WORKER", "false"));
            RANKING_WORKER_INTERVAL = Long.parseLong(p.getProperty("RANKING_WORKER_INTERVAL", "0"));

            URL = p.getProperty("URL");
            USER = p.getProperty("USER");
            PASS = p.getProperty("PASS");

            GAME_SERVER_PORT = Integer.parseInt(p.getProperty("GAME_SERVER_PORT"));
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
