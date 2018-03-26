package Main;

import java.sql.*;

import DB.DBController;

public class Runner {

	private DBController dbc = new DBController();
	
	
	public void run(boolean createNewDatabase) {
		try {
			Class.forName("org.h2.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/helix_repo", "test", "test");
			System.out.println("Connection Successful...");

			if(createNewDatabase) {
				// if you want to delete the current database and start fresh (for testing)
				boolean databaseCreated = dbc.createDatabase(conn);
				if (!databaseCreated) {
					System.out.println("Database already exists, deleting it and starting over");
					dbc.deleteDatabase(conn);
					databaseCreated = dbc.createDatabase(conn);
				}
				System.out.println("Database creation required: " + databaseCreated);
			} else
				// if you want to keep the current database, if it exists
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
		runner.run(true);
		
	}

}
