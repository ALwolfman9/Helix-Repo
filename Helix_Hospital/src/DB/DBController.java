package DB;

import java.sql.*;

public class DBController {



	public static void main(String[] a) throws Exception {
		Class.forName("org.h2.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection("jdbc:h2:~/helix_repo", "test", "test");
		Statement st = conn.createStatement();
		System.out.println("Connection Successful...");
		
		conn.close();
	}

}
