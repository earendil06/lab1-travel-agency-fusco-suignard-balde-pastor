package manager.data;

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
  static final String DATABASE_REFUSED = "refusedRequestDatabase";
  static final String DATABASE_VALIDATED = "validatedRequestDatabase";
  static final String DATABASE_PENDING = "pendingRequestDatabase";
  static final String COLLECTION = "travelRequests";
}
