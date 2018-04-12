package Model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Hospital {
    private Connection conn;

    public Hospital(Connection conn){
        this.conn = conn;
    }

//    public boolean addPatient(String firstName, String lastName, String middleInit, String roomNo,
//                              String email, Employee.Gender gender, String insurance_ID,
//                              String phoneNo, String status){
//
//        Random random = new Random();
//        String patient_ID = Integer.toString(random.nextInt());
//
//        String sql = "INSERT INTO patient " +
//                "VALUES (" + formatString(patient_ID) + ", " +
//                formatString(roomNo) + ", " +
//                formatString(firstName) + ", " +
//                formatString(middleInit) + ", " +
//                formatString(lastName) + ", " +
//                formatString(email) + ", " +
//                formatString(formatGender(gender)) + ", " +
//                formatString(insurance_ID) + ", " +
//                formatString(phoneNo) + ", " +
//                formatString(status) + ");";
//        try {
//            Statement st = conn.createStatement();
//            st.execute(sql);
//            return true;
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//            return false;
//        }
//    }

    public boolean addPatient(String firstName, String lastName, String middleInit, String roomNo, String email,
                              Patient.Gender gender, String insurance_ID, String phoneNo, String status, String doctor) {
    	String insertStatement = "insert into patient(room_number, first_name, middle_initial, " +
                "last_name, email, sex, insurance_id, phone, status, doctor) "
    			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;

    	try {
    		preparedStmt = conn.prepareStatement(insertStatement);
    		preparedStmt.setString (1, roomNo);
    		preparedStmt.setString (2, firstName);
    		preparedStmt.setString (3, middleInit);
    		preparedStmt.setString (4, lastName);
    		preparedStmt.setString (5, email);
    		preparedStmt.setString (6, gender == null ? null : gender.name());
    		preparedStmt.setString (7, insurance_ID);
    		preparedStmt.setString (8, phoneNo);
    		preparedStmt.setString (9, status);
    		preparedStmt.setString(10, doctor);

    		// execute the preparedstatement
    		preparedStmt.execute();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;

    	}

    	return true;
    }

    public Employee getEmployee(String username){
        String sql = "SELECT first_name, last_name, username, type FROM employee WHERE username = '" + username + "';";
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(sql);
            if (!result.next()){
                return null;
            }
            Employee emp = new Employee();
            emp.setFirstName(result.getString("first_name"));
            emp.setLastName(result.getString("last_name"));
            emp.setUsername(result.getString("username"));
            emp.setType(Employee.Type.fromString(result.getString("type")));
            return emp;

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    public Employee getEmployeeBySSN(String ssn){
        String sql = "SELECT first_name, last_name, username, type FROM employee WHERE ssn = '" + ssn + "';";
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(sql);
            if (!result.next()){
                return null;
            }
            Employee emp = new Employee();
            emp.setFirstName(result.getString("first_name"));
            emp.setLastName(result.getString("last_name"));
            emp.setUsername(result.getString("username"));
            emp.setType(Employee.Type.fromString(result.getString("type")));
            return emp;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    public boolean addDoctor(String username, String firstName, String middleInit, String lastName,
                             String phoneNo, Employee.Gender gender, String ssn, String email, String address,
                             Employee.Type type, String special){

        addEmployee(username, firstName, middleInit, lastName, phoneNo, gender, ssn, email, address, type);

        String insertStatement = "INSERT INTO doctor(username, specialization) VALUES (?,?);";

        try {
			PreparedStatement preparedStmt = conn.prepareStatement(insertStatement);
			preparedStmt.setString (1, username);
    		preparedStmt.setString (2, special);
    		preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        
        return true;
    }
    
    public boolean addEmployee(String username, String firstName, String middleInit, String lastName,
            String phoneNo, Employee.Gender gender, String ssn, String email, String address, Employee.Type type) {
    	String insertStatement = "insert into employee(username, first_name, middle_initial, last_name, phone, sex, ssn, email, address, type) "
    			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(insertStatement);
    		preparedStmt.setString (1, username);
    		preparedStmt.setString (2, firstName);
    		preparedStmt.setString (3, middleInit);
    		preparedStmt.setString (4, lastName);
    		preparedStmt.setString (5, phoneNo);
    		preparedStmt.setString (6, gender == null ? null : gender.name());
    		preparedStmt.setString (7, ssn);
    		preparedStmt.setString (8, email);
    		preparedStmt.setString (9, address);
    		preparedStmt.setString (10, type.toString());

    		// execute the preparedstatement
    		preparedStmt.execute();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;

    	}

    	return true;
    }
    
    public boolean addAppointment(String username, String patientID, LocalDateTime dateTime, String reasonForVisit) {
    	String sql = "insert into appointment(username, patient_id, date, reason_for_visit) "
    			+ "+ VALUES(?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, username);
    		preparedStmt.setString(2, patientID);
    		Timestamp ts = Timestamp.valueOf(dateTime.toString());
    		preparedStmt.setTimestamp(3, ts);
    		preparedStmt.setString(4, reasonForVisit);
    		preparedStmt.execute();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;

    	}
    	return true;
    }
    
    public boolean addPrescription(String patientID, String drugName, String dosage, String duration) {
    	String sql = "insert into prescription(patient_ID, prescription_ID, drup_name, dosage, duration) "
    			+ "+ VALUES(?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, patientID);
    		preparedStmt.setString(2, drugName);
    		preparedStmt.setString(3, dosage);
    		preparedStmt.setString(4, duration);
    		preparedStmt.execute();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;

    	}
    	return true;
    }
    
    public boolean addMedicalHistory (String patientID, String bloodType, String allergies, 
    		String medications, String pastConditions, String familyHistory) {
    	String sql = "insert into medical_history(patient_ID, blood_type, family_history, past_Conditions, allergies, medications) "
    			+ "+ VALUES(?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, patientID);
    		preparedStmt.setString(2, bloodType);
    		preparedStmt.setString(3, familyHistory);
    		preparedStmt.setString(4, pastConditions);
    		preparedStmt.setString(5, allergies);
    		preparedStmt.setString(6, medications);
    		preparedStmt.execute();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;

    	}
    	
    	return true;
    }

    public boolean addMedicalRecord (String username, String patientID, LocalDateTime dateTime, String notes) {
    	String sql = "insert into medical_record(username, patient_id, date, notes) "
    			+ "+ VALUES(?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, username);
    		preparedStmt.setString(2, patientID);
    		Timestamp ts = Timestamp.valueOf(dateTime.toString());
    		preparedStmt.setTimestamp(3, ts);
    		preparedStmt.setString(4, notes);
    		preparedStmt.execute();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;

    	}
    	return true;
    }

    public Iterator<Employee> getAllDoctors(){
        String sql = "SELECT first_name, last_name, Doctor.username, type " +
                     "FROM Doctor, Employee " +
                     "WHERE Doctor.username = Employee.username";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            ArrayList<Employee> doctors = new ArrayList<>();

            while(set.next()){
                Employee doctor = new Employee();
                doctor.setFirstName( set.getString("first_name") );
                doctor.setLastName( set.getString("last_name") );
                doctor.setUsername( set.getString("username") );
                doctor.setType(Employee.Type.fromString(set.getString("type")));
                doctors.add(doctor);
            }
            return doctors.iterator();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean doctorExists(String username){
        String sql = "SELECT username FROM Doctor WHERE username = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString (1, username);
            ResultSet set = preparedStmt.executeQuery();
            return (set.next()) ? true : false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean doctorExists(){
        String sql = "SELECT username FROM Doctor";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet set = preparedStmt.executeQuery();
            return (set.next()) ? true : false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Patient> getAllPatients(){
        String sql = "SELECT patient_ID, first_name, last_name, insurance_ID, doctor FROM PATIENT";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Patient> patients = new ArrayList<>();

            while(set.next()){
                Patient patient = new Patient();
                patient.setPatientID( set.getString("patient_ID") );
                patient.setFirstName( set.getString("first_name") );
                patient.setLastName( set.getString("last_name") );
                patient.setInsuranceID( set.getString("insurance_Id") );
                patient.setDoctor( set.getString("doctor") );
                patients.add(patient);
            }
            return patients;

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Patient> getPatientsOfDoctor(Employee doctor){
        String sql = "SELECT patient_ID, first_name, last_name, insurance_ID, doctor "
                + "FROM Patient "
                + "WHERE doctor = '" + doctor.getUsername() + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Patient> patients = new ArrayList<>();

            while(set.next()){
                Patient patient = new Patient();
                patient.setPatientID( set.getString("patient_ID") );
                patient.setFirstName( set.getString("first_name") );
                patient.setLastName( set.getString("last_name") );
                patient.setInsuranceID( set.getString("insurance_Id") );
                patient.setDoctor( set.getString("doctor") );
                patients.add(patient);
            }
            return patients;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    // TODO actually make these methods
    public MedicalHistory getMedicalHistory(Patient patient) {
        return null;
    }

    public Iterator<Appointment> getAppointmentsOfPatient(String patientId){
        return null;
    }

    public Iterator<MedicalRecord> getRecordsOfPatient(String patientId){
        return null;
    }

    public Iterator<Prescription> getPrescriptionsOfPatient(String patientId) {
        return null;
    }

    private String formatString(String s){
        if(s == null) return null;
        else return "'" + s + "'";

    }

    private String formatGender(Employee.Gender g){
        if(g == null) return null;
        switch (g){
            case O:
                return "O";
            case F:
                return "F";
            case M:
                return "M";
            default:
                return "H";
        }

    }

    private String formatType(Employee.Type t){
        if(t == null) return null;
        switch (t){
            case DOCTOR:
                return "Doctor";
            case NURSE:
                return "Nurse";
            case SUPPORT:
                return "Support";
            default:
                return "H";
        }
    }

}
