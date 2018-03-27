package Main;

import java.sql.*;

import DB.DBController;
import Interface.CommandLineStart;
import Model.Hospital;

public class Runner {

	private DBController dbc = new DBController();
	
	
	public Connection run(boolean resetDatabase) {
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:h2:~/helix_repo", "test", "test");
			System.out.println("Connection successful.");
			if(resetDatabase) {
				System.out.println("Deleting database...");
				dbc.deleteDatabase(conn);
				System.out.println("Deletion successful.");
			}
			boolean creationSuccess = dbc.createDatabase(conn);
			if (!creationSuccess) {
				System.out.println("Error in initializing database.");
				return null;
			}
			System.out.println("Database successfully initialized.");
			System.out.println("Starting Application...\n");

			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	

	public static void main(String[] args) {
		System.out.println("Would you like to reset the database? (y/n)");
		java.util.Scanner in = new java.util.Scanner(System.in);
		boolean resetDatabase = false;
		boolean validCommand = false;
		while(!validCommand){
			String cmd = in.next();
			switch(cmd){
				case "y":
				case "Y":
				case "yes":
					resetDatabase = true;
					validCommand = true;
					break;
				case "n":
				case "N":
				case "no":
					resetDatabase = false;
					validCommand = true;
					break;
				default:
					System.out.println("That command was not recognized. Try again: (y/n)");
			}
		}
		Runner runner = new Runner();
		Connection conn = runner.run(resetDatabase);
		if (conn == null){
			System.out.println("Database connection could not be established.");
			return;
		}
		Hospital hospital = new Hospital(conn);
		CommandLineStart cl = new CommandLineStart(hospital);
		cl.run();

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
