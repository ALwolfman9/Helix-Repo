package DB;

import java.sql.*;
public class DBController {

	/**
	 * Creates the database, with all tables and an initial admin user
	 * @param conn the connection to the database
	 * @return true if the database was created, false if the database already exists
	 */
	public boolean createDatabase(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.execute("CREATE TABLE EMPLOYEE (" +
							"	firstName VARCHAR(20) NOT NULL," +
							"	middleInitial CHAR(1)," +
							"	lastName VARCHAR(30) NOT NULL," +
							"	phone INTEGER(10)," +
							"	sex ENUM('M', 'F', 'O')," +
							"	ssn INTEGER(10) NOT NULL UNIQUE," +
							"	email VARCHAR(50)," +
							"	address VARCHAR(255)," +
							"	type VARCHAR(20) NOT NULL," +
							"	username VARCHAR(30) PRIMARY KEY FJD" +
							");");
			st.execute("CREATE TABLE DOCTOR (" +
							"	username VARCHAR(30) PRIMARY KEY FOREIGN KEY REFERENCES EMPLOYEE," +
							"	specialization VARCHAR(30)" +
							");");
			st.execute("CREATE TABLE PATIENT (" +
					"	patientID INTEGER(10) PRIMARY KEY," +
					"	roomNumber INTEGER(5)," +
					"	firstName VARCHAR(20 NOT NULL," +
					"	lastName VARCHAR(30)");

		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public void deleteDatabase(Connection conn){
		try{
			System.out.println("Deleting database");
			Statement st = conn.createStatement();
			st.execute("DROP TABLE EMPLOYEE;");
		}catch (SQLException e){

		}

	}
}
