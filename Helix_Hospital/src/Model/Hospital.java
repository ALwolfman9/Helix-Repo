package Model;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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

    public boolean addPatient(String firstName, String lastName, String middleInit, String roomNo, String email, Patient.Gender gender,
            String insurance_ID, String phoneNo, String status) {
    	String insertStatement = "insert into patient(room_number, first_name, middle_initial, last_name, email, sex, insurance_id, phone, status) "
    			+ "VALUES(?,?,?,?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(insertStatement);
    		preparedStmt.setString (1, roomNo);
    		preparedStmt.setString (2, firstName);
    		preparedStmt.setString (3, middleInit);
    		preparedStmt.setString (4, lastName);
    		preparedStmt.setString (5, email);
    		preparedStmt.setString (6, gender.name());
    		preparedStmt.setString (7, insurance_ID);
    		preparedStmt.setString (8, phoneNo);
    		preparedStmt.setString (9, status);

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

            Employee emp = new Employee();

            result.next();

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
    		preparedStmt.setString (6, gender.name());
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

//    public boolean addEmployee(String username, String firstName, String middleInit, String lastName,
//                               String phoneNo, Employee.Gender gender, String ssn, String email, String address, Employee.Type type){
//
//        String sql = "INSERT INTO employee " +
//                "VALUES (" + formatString(username) + ", " +
//                formatString(firstName) + ", " +
//                formatString(middleInit) + ", " +
//                formatString(lastName) + ", " +
//                formatString(phoneNo) + ", " +
//                formatString(formatGender(gender)) + ", " +
//                formatString(ssn) + ", " +
//                formatString(email) + ", " +
//                formatString(address) + ", " +
//                formatString(formatType(type)) + ");";
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

    public Iterator<Patient> getAllPatients(){
        String sql = "SELECT first_name, last_name, insurance_ID FROM PATIENT";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            ArrayList<Patient> patients = new ArrayList<>();

            while(set.next()){
                Patient patient = new Patient();
                patient.setFirstName( set.getString("first_name") );
                patient.setLastName( set.getString("last_name") );
                patient.setInsuranceID( set.getString("insurance_Id") );
                patients.add(patient);
            }
            return patients.iterator();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    public Iterator<Patient> getPatientsOfDoctor(Employee user){
        String sql = "SELECT "
                + "*"
                + "FROM "
                + "patient "
                + "WHERE "
                + ""; //<-- needs to be doctor's username user.getName
        try {
            Statement st = conn.createStatement();
            st.execute(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
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
