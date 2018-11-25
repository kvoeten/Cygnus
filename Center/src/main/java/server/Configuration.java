package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Properties;

public class Configuration {
    private static Configuration pInstance;

    public int WORLD_ID;
    public String WORLD_NAME;
    public int EXPERIENCE_MOD;
    public int DROP_MOD;
    public int EVENT_FLAG;
    public String EVENT_MESSAGE;
    public String SERVER_MESSAGE;
    public int EVENT_EXP;
    public int EVENT_DROP;
    public boolean DISABLE_CHAR_CREATION;
    public boolean SERVER_CHECK;

    public String LOGIN_SERVER_IP;
    public short LOGIN_SERVER_PORT;
    public String CENTER_SERVER_KEY;

    public String URL;
    public String USER;
    public String PASS;

    public int GAME_SERVER_PORT;

    public static Configuration GetConfig() {
        if (pInstance == null) {
            pInstance = new Configuration();
            pInstance.Load();
            }
        return pInstance;
    }

    private void Load() {
        File pFile = new File(this.getClass().getClassLoader().getResource("config.ini").getFile());
        if (!pFile.exists()) {
            System.out.println("Configuration file could not be found!.");
            System.exit(0);
        }
        Properties pProperties = new Properties();
        try (FileReader reader = new FileReader(pFile)) {
            pProperties.load(reader);
            WORLD_ID = Integer.parseInt(pProperties.getProperty("WORLD_ID"));
            WORLD_NAME = pProperties.getProperty("WORLD_NAME");
            EXPERIENCE_MOD = Integer.parseInt(pProperties.getProperty("EXPERIENCE_MOD"));
            DROP_MOD = Integer.parseInt(pProperties.getProperty("DROP_MOD"));
            EVENT_FLAG = Integer.parseInt(pProperties.getProperty("EVENT_FLAG"));
            EVENT_MESSAGE = pProperties.getProperty("EVENT_MESSAGE");
            SERVER_MESSAGE = pProperties.getProperty("SERVER_MESSAGE");
            SERVER_CHECK = Boolean.parseBoolean(pProperties.getProperty("SERVER_CHECK", "false"));

            EVENT_EXP = Integer.parseInt(pProperties.getProperty("EVENT_EXP"));
            EVENT_DROP = Integer.parseInt(pProperties.getProperty("EVENT_DROP"));

            LOGIN_SERVER_IP = pProperties.getProperty("LOGIN_SERVER_IP");
            LOGIN_SERVER_PORT = Short.parseShort(pProperties.getProperty("LOGIN_SERVER_PORT"));
            CENTER_SERVER_KEY = pProperties.getProperty("CENTER_SERVER_KEY");

            URL = pProperties.getProperty("URL");
            USER = pProperties.getProperty("USER");
            PASS = pProperties.getProperty("PASS");

            GAME_SERVER_PORT = Integer.parseInt(pProperties.getProperty("GAME_SERVER_PORT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pProperties.clear();
    }
}
