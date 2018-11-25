package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Properties;

public class Configuration {
    private static Configuration pInstance;

    public int PORT = 8484;
    public int SERVER_TYPE = 8;
    public short MAPLE_VERSION = 188;
    public String BUILD_VERSION = "3";
    public int MAXIMUM_CONNECTIONS = 250;

    public int CENTER_SERVER_PORT = 8600;
    public String CENTER_SERVER_KEY = "MapleCygnus";

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
            MAXIMUM_CONNECTIONS = Integer.parseInt(pProperties.getProperty("MAXIMUM_CONNECTIONS", "250"));
            CENTER_SERVER_PORT = Integer.parseInt(pProperties.getProperty("CENTER_SERVER_PORT"));
            CENTER_SERVER_KEY = pProperties.getProperty("CENTER_SERVER_KEY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pProperties.clear();
    }
}
