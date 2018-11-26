package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Properties;

public class Configuration {
    private static Configuration pInstance;

    public static int PORT;
    public static int SERVER_TYPE;
    public static short MAPLE_VERSION;
    public static String BUILD_VERSION;
    public static int MAXIMUM_CONNECTIONS;
    public static int EXP_RATE;
    public static int DROP_RATE;
    public static int MESO_RATE;
    public static int PARTY_EXP_RATE;
    public static int CHANNEL_ID;
    public static InetAddress PUBLIC_IP;
    public static boolean AGE_RESTRICTED;
    public static String CENTER_SERVER_IP;
    public static int CENTER_SERVER_PORT;
    public static String CENTER_SERVER_KEY;

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
            PORT = Integer.parseInt(pProperties.getProperty("PORT"));
            SERVER_TYPE = Integer.parseInt(pProperties.getProperty("SERVER_TYPE"));
            MAPLE_VERSION = Short.parseShort(pProperties.getProperty("MAPLE_VERSION"));
            BUILD_VERSION = pProperties.getProperty("BUILD_VERSION");
            MAXIMUM_CONNECTIONS = Integer.parseInt(pProperties.getProperty("MAXIMUM_CONNECTIONS"));

            EXP_RATE = Integer.parseInt(pProperties.getProperty("EXP_RATE"));
            DROP_RATE = Integer.parseInt(pProperties.getProperty("DROP_RATE"));
            MESO_RATE = Integer.parseInt(pProperties.getProperty("MESO_RATE"));
            PARTY_EXP_RATE = Integer.parseInt(pProperties.getProperty("PARTY_EXP_RATE"));

            CHANNEL_ID = Integer.parseInt(pProperties.getProperty("CHANNEL_ID"));
            PUBLIC_IP = InetAddress.getByName(pProperties.getProperty("PUBLIC_IP"));
            AGE_RESTRICTED = Boolean.parseBoolean(pProperties.getProperty("AGE_RESTRICTED"));

            CENTER_SERVER_IP = pProperties.getProperty("CENTER_SERVER_IP");
            CENTER_SERVER_PORT = Integer.parseInt(pProperties.getProperty("CENTER_SERVER_PORT"));
            CENTER_SERVER_KEY = pProperties.getProperty("CENTER_SERVER_KEY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pProperties.clear();
    }
}
