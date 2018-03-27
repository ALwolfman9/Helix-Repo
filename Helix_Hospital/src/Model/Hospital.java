package Model;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Hospital {
    private Connection conn;

    public Hospital(Connection conn){
        this.conn = conn;
    }

    public boolean addPatient(String firstName, String lastName, String middleInit, int roomNo, String email, Patient.Gender gender,
                              int insurance_ID, int phoneNo, String status){
        Random random = new Random();
        int patient_ID = random.nextInt();
        String genderString;
        if(gender.equals(Patient.Gender.F)){
            genderString = "F";
        }
        else if(gender.equals(Patient.Gender.M)){
            genderString = "M";
        }
        else{
            genderString = "O";
        }
        String sql = "INSERT INTO patient " + "VALUES ('" + Integer.toString(patient_ID) + "', '" + Integer.toString(roomNo)
                + "', '" + firstName + "', '" + middleInit + "', '" + lastName + "', '" + email + "', '" + genderString
                + "', '" + Integer.toString(insurance_ID) + "', '" + Integer.toString(phoneNo) + "', '"
                + status + "');";
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
        String sql = "SELECT * FROM employee WHERE username = '" + username + "';";
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

    public boolean addDoctor(String username, String firstName, String middleInit, String lastName,
                             int phoneNo, Employee.Gender gender, int ssn, String email, String address,
                             Employee.Type type, String special){
        String sql = "INSERT INTO doctor " + "VALUES ('" + username + "', '" + special + "');";
        addEmployee(username, firstName, middleInit, lastName, phoneNo, gender, ssn, email, address, type);
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
                               int phoneNo, Employee.Gender gender, int ssn, String email, String address, Employee.Type type){
        String typeString;
        if (type == Employee.Type.DOCTOR){
            typeString = "doctor";
        }
        else if(type == Employee.Type.NURSE){
            typeString = "nurse";
        }
        else if(type == Employee.Type.SUPPORT){
            typeString = "support";
        }
        else{
            typeString = "unknown";
        }

        String genderString;
        if(gender.equals(Employee.Gender.F)){
            genderString = "F";
        }
        else if(gender.equals(Employee.Gender.M)){
            genderString = "M";
        }
        else{
            genderString = "O";
        }
        String sql = "INSERT INTO employee " + "VALUES ('" + username + "', '" + firstName + "', '" + middleInit
                + "', '" + lastName + "', '" + Integer.toString(phoneNo) + "', '" + genderString + "', '"
                + Integer.toString(ssn) + "', '" + email + "', '" + address + "', '" + typeString + "');";
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
        String sql = "SELECT "
                + "first_name, last_name, gender, patient_ID "
                + "FROM "
                + "patient;";
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

}
