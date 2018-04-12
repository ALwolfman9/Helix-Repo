package Interface;

import Model.*;
import static Model.Employee.Gender;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CommandLineAdmin extends CommandLine {

    public CommandLineAdmin(Hospital hospital){
        super(hospital);
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);

        System.out.println("\n=================================");
        System.out.println("Welcome admin!");

        while(true) {
            printHelp();
            String cmd = in.next();

            // TODO add ability to modify employee
            switch(cmd){
                case "e":
                case "E":
                case "employee":
                    if(!createEmployee()) System.out.println("Employee could not be created");
                    break;
                case "l":
                case "L":
                case "logout":
                    return;
                default:
                    System.out.println("That command was not recognized");
            }
        }
    }

    private void printHelp(){
        System.out.println();
        System.out.println("Usage: employee/e/E | logout/l/L");
        System.out.println("employee/e/E\t\tAdd a new employee");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    private boolean createEmployee(){
        Scanner in = new Scanner(System.in);

        String firstName, middleInitial, lastName, username, ssn, phone, email, address;
        Employee.Type type;
        Employee.Gender gender;
        System.out.println();
        System.out.println("Create an Employee");
        firstName = firstNameValidation(in);
        middleInitial = middleInitialValidation(in);
        lastName = lastNameValidation(in);
        username = usernameValidation(in);
        phone = phoneValidation(in);
        gender = genderValidation(in);
        ssn = ssnValidation(in);
        email = emailValidation(in);
        address = addressValidation(in);
        type = typeValidation(in);

        if(type == Employee.Type.DOCTOR) {
            String special = specializationValidation(in);
            return hospital.addDoctor(username, firstName, middleInitial, lastName, phone,
                    gender, ssn, email, address, type, special);
        }

        return hospital.addEmployee(username, firstName, middleInitial, lastName, phone,
                gender, ssn, email, address, type);
    }

    private String usernameValidation(Scanner in){
        while (true) {
            System.out.println("*Enter username: ");
            String username = in.nextLine();
            if (username.equals("admin") || hospital.getEmployee(username) != null) {
                System.out.println("Username '" + username + "' is unavailable");
            }
            else if (username.length() > 0 && username.length() <= 30 && username.matches("[A-Za-z0-9]+")) {
                return username;
            }
            else {
                System.out.println("Username must be alphanumeric with 1-30 characters");
            }
        }
    }

    private String ssnValidation(Scanner in){
        while (true) {
            System.out.println("*Enter the SSN (no dashes): ");
            String ssn = in.nextLine();
            if (hospital.getEmployeeBySSN(ssn) != null) {
                System.out.println("An employee with that SSN already exists");
            }
            else if (ssn.length() == 9 && ssn.matches("[0-9]+")) {
                return ssn;
            }
            else {
                System.out.println("SSN must be 9 characters long and only contain numbers");
            }
        }
    }

    private Employee.Type typeValidation(Scanner in){
        while(true) {
            System.out.println("*Enter the employee type (d for doctor, n for nurse, s for support): ");
            String t = in.nextLine();
            switch (t) {
                case "d":
                    return Employee.Type.DOCTOR;
                case "n":
                    return Employee.Type.NURSE;
                case "s":
                    return Employee.Type.SUPPORT;
                default:
                    System.out.println("Please enter a valid type");
            }
        }
    }

    private String specializationValidation(Scanner in){
        while (true) {
            System.out.println("Enter the doctor's specialization: ");
            String special = in.nextLine();
            if (special.length() <= 30) {
                return special;
            }
            else {
                System.out.println("Specialization must be 30 characters or less");
            }
        }
    }
}
