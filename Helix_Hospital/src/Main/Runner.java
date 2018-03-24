package Main;

import java.sql.*;

import DB.DBController;

public class Runner {

	DBController dbc = new DBController();
	
	
	public void run() {
		try {
			Class.forName("org.h2.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/helix_repo", "test", "test");
			System.out.println("Connection Successful...");
			System.out.println("Database creation required: " + dbc.createDatabase(conn));
			System.out.println("Starting Application...");
			
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		
		Runner runner = new Runner();
		runner.run();
		
	}

}
