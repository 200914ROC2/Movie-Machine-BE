package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class ConnectionUtil {

	// this class follows the singleton design pattern
		private static ConnectionUtil cu = null; 
		private static Properties properties;
		
		protected static Logger log; 
		
		private ConnectionUtil() {
			// use the class loader to get the properties file
			// then we don't have to rely on the file system
			//log = (Logger) LogManager.getLogger();  
			properties = new Properties();
			// use the class loader to get the properties file
			// then we don't have to rely on the file system
			try {
				InputStream dbProperties = ConnectionUtil.class.getClassLoader().
						getResourceAsStream("mm.properties");
				properties.load(dbProperties);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		public static synchronized ConnectionUtil getConnectionUtil() { 
			if (cu == null) {
				cu = new ConnectionUtil();
			}
			return cu;
		}
		
		public Connection getConnection() {
			Connection conn = null;			
			try {
				// registering the postgresql Driver class
				Class.forName(properties.getProperty("drv"));
				conn = DriverManager.getConnection(
							properties.getProperty("url"),
							properties.getProperty("usr"),
							properties.getProperty("psw")
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return conn;
		}  
		
		public static Logger getLog()
		{
			return log;
		}

}
