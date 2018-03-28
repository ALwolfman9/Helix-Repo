package Model;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Hospital {
    private Connection conn;

    public Hospital(Connection conn){
        this.conn = conn;
    }

    public boolean addPatient(String firstName, String lastName, String middleInit, String roomNo,
                              String email, Employee.Gender gender, String insurance_ID,
                              String phoneNo, String status){

        Random random = new Random();
        String patient_ID = Integer.toString(random.nextInt());

        String sql = "INSERT INTO patient " +
                "VALUES (" + formatString(patient_ID) + ", " +
                formatString(roomNo) + ", " +
                formatString(firstName) + ", " +
                formatString(middleInit) + ", " +
                formatString(lastName) + ", " +
                formatString(email) + ", " +
                formatString(formatGender(gender)) + ", " +
                formatString(insurance_ID) + ", " +
                formatString(phoneNo) + ", " +
                formatString(status) + ");";
        try {
            Statement st = conn.createStatement();
            st.execute(sql);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
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

        String sql = "INSERT INTO doctor " +
                "VALUES (" + formatString(username) + ", " +
                formatString(special) + ");";

        try {
            Statement st = conn.createStatement();
            st.execute(sql);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addEmployee(String username, String firstName, String middleInit, String lastName,
                               String phoneNo, Employee.Gender gender, String ssn, String email, String address, Employee.Type type){

        String sql = "INSERT INTO employee " +
                "VALUES (" + formatString(username) + ", " +
                formatString(firstName) + ", " +
                formatString(middleInit) + ", " +
                formatString(lastName) + ", " +
                formatString(phoneNo) + ", " +
                formatString(formatGender(gender)) + ", " +
                formatString(ssn) + ", " +
                formatString(email) + ", " +
                formatString(address) + ", " +
                formatString(formatType(type)) + ");";
        try {
            Statement st = conn.createStatement();
            st.execute(sql);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

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
