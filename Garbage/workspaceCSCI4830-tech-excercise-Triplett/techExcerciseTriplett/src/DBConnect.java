
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	static Connection connection = null;
	
	static void getDBConnection() {
		try {
	         Class.forName("com.mysql.jdbc.Driver");
	      } catch (ClassNotFoundException e) {
	         System.out.println("Cannot Find MySQL JDBC Driver");
	         e.printStackTrace();
	         return;
	      }
		System.out.println("MySQL JDBC Driver Found");
		
		connection = null;
	      try {
	         UtilProp.loadProperty();
	         connection = DriverManager.getConnection(getURL(), getUserName(), getPassword());
	      } catch (Exception e) {
	         System.out.println("Connection Failed! Check output console");
	         e.printStackTrace();
	         return;
	      }

	      if (connection != null) {
	         System.out.println("You made it, take control your database now!");
	      } else {
	         System.out.println("Failed to make connection!");
	      }
	   }
	
	   static String getURL() {
		   String url = UtilProp.getProp("url");
		   System.out.println("[DBG] URL: " + url);
		   return url;
	   }

	   static String getUserName() {
		   String usr = UtilProp.getProp("user");
		   System.out.println("[DBG] URL: " + usr);
		   return usr;
	   }

	   static String getPassword() {
		   String pwd = UtilProp.getProp("password");
		   System.out.println("[DBG] URL: " + pwd);
		   return pwd;
	   }
}

