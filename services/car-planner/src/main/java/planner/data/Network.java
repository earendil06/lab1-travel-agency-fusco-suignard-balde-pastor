package planner.data;

import java.io.IOException;
import java.util.Properties;

class Network {

  private static String readProperties(String key) {
    try {
      Properties prop = new Properties();
      prop.load(Network.class.getResourceAsStream("/network.properties"));
      return prop.getProperty(key);
    } catch(IOException e) { throw new RuntimeException(e); }
  }

  static final String HOST = readProperties("databaseHostName");
  static final int PORT = Integer.parseInt(readProperties("databasePort"));
  static final String DATABASE = "carplannersDatabase";
  static final String COLLECTION = "carplanners";
}
