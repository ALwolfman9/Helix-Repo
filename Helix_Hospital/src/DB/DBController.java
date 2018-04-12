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
			String createString = "";

			createString += "CREATE TABLE IF NOT EXISTS employee (" +
					"username varchar(30) not null primary key, " +			
					"first_name varchar(20) not null, " +
					"middle_initial varchar(1), " +
					"last_name varchar(30), " +
					"phone char(10), " +
					"sex enum('M', 'F', 'O'), " +
					"ssn char(10) not null unique, " +
					"email varchar(50), " +
					"address varchar(255), " +
					"type enum('Doctor', 'Nurse', 'Support') not null, " +
					")";
			st.execute(createString);

			createString = "CREATE TABLE IF NOT EXISTS doctor ("
					+ "username VARCHAR(30) NOT NULL PRIMARY KEY,"
					+ "specialization VARCHAR(30),"
					+ "FOREIGN KEY (username) REFERENCES EMPLOYEE(username)"
					+ ");";
			st.execute(createString);

			createString = "CREATE TABLE IF NOT EXISTS patient ("
					+ "patient_ID bigint auto_increment,"
					+ "room_number CHAR(5),"
					+ "first_name VARCHAR(20) NOT NULL,"
					+ "middle_initial VARCHAR(1),"
					+ "last_name VARCHAR(20) NOT NULL,"
					+ "email VARCHAR(50),"
					+ "sex ENUM('M', 'F', 'O'),"
					+ "insurance_ID CHAR(15),"
					+ "phone CHAR(10),"
					+ "status VARCHAR(20)," //enum?
					+ "doctor VARCHAR(30), "
					+ "FOREIGN KEY (doctor) REFERENCES DOCTOR(username)"
					+ ");";
			st.execute(createString);

			createString = "CREATE TABLE IF NOT EXISTS appointment("
					+ "username VARCHAR(30) NOT NULL, "
					+ "patient_ID bigint NOT NULL, "
					+ "date DATETIME NOT NULL, "
					+ "reason_for_visit VARCHAR(255), "
					+ "FOREIGN KEY (username) REFERENCES EMPLOYEE(username), "
					+ "FOREIGN KEY (patient_ID) REFERENCES PATIENT(patient_ID), "
					+ ");";
			st.execute(createString);
			String alterString = "ALTER TABLE appointment ADD CONSTRAINT IF NOT EXISTS appointmentPK PRIMARY KEY (username, patient_ID, date);";
			st.execute(alterString);

			createString = "CREATE TABLE IF NOT EXISTS prescription("
					+ "patient_ID bigint NOT NULL,"
					+ "prescription_ID bigint auto_increment,"
					+ "drug_name VARCHAR(30) NOT NULL,"
					+ "dosage VARCHAR(30) NOT NULL,"
					+ "duration VARCHAR(20) NOT NULL,"
					+ "FOREIGN KEY (patient_ID) REFERENCES PATIENT(patient_ID),"
					+ ");";
			st.execute(createString);
			alterString = "ALTER TABLE prescription ADD CONSTRAINT IF NOT EXISTS prescriptionPK PRIMARY KEY (patient_ID, prescription_ID);";
			st.execute(alterString);

			createString = "CREATE TABLE IF NOT EXISTS medical_record ("
					+ "username VARCHAR(30) NOT NULL,"
					+ "patient_ID bigint NOT NULL,"
					+ "date DATETIME NOT NULL,"
					+ "notes VARCHAR(255),"
					+ "FOREIGN KEY (username) REFERENCES EMPLOYEE(username),"
					+ "FOREIGN KEY (patient_ID) REFERENCES PATIENT(patient_ID),"
					+ ");";
			st.execute(createString);
			alterString = "ALTER TABLE medical_record ADD CONSTRAINT IF NOT EXISTS medical_recordPK PRIMARY KEY (username, patient_ID, date);";
			st.execute(alterString);

			createString = "CREATE TABLE IF NOT EXISTS medical_history("
					+ "patient_ID bigint PRIMARY KEY,"
					+ "blood_type VARCHAR(3),"
					+ "family_history VARCHAR(255),"
					+ "past_conditions VARCHAR(255),"
					+ "allergies VARCHAR(255),"
					+ "medications VARCHAR(255),"
					+ "FOREIGN KEY (patient_id) REFERENCES PATIENT(patient_ID),"
					+ ");";
			st.execute(createString);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void deleteDatabase(Connection conn){
		try{
			Statement st = conn.createStatement();
			st.execute("DROP TABLE MEDICAL_HISTORY;");
			st.execute("DROP TABLE MEDICAL_RECORD;");
			st.execute("DROP TABLE PRESCRIPTION;");
			st.execute("DROP TABLE APPOINTMENT;");
			st.execute("DROP TABLE DOCTOR;");
			st.execute("DROP TABLE PATIENT;");
			st.execute("DROP TABLE EMPLOYEE;");
		}catch (SQLException e){
			e.printStackTrace();
		}

	}
}
