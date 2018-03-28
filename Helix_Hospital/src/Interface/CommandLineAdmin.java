package Interface;

import Model.*;
import Model.Employee.Gender;

import java.util.Scanner;

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

        String firstName, lastName, username, ssn;
        Employee.Type type;
        System.out.println();
        System.out.println("Create an Employee");
        System.out.println("Enter first name: ");
        firstName = in.nextLine();
        System.out.println("Enter last name: ");
        lastName = in.nextLine();
        System.out.println("Enter username: ");
        username = in.nextLine();
        System.out.println("Enter the ssn: ");
        ssn = in.nextLine();
        type = getType();

        if(type == Employee.Type.DOCTOR) {
            System.out.println("Enter the doctor's specialization: ");
            String special = in.next();
            return hospital.addDoctor(username, firstName, null, lastName, null,
                    Gender.O, ssn, null, null, type, special);
        }

        return hospital.addEmployee(username, firstName, null, lastName, null,
                null, ssn, null, null, type);
    }

    private Employee.Type getType(){
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the employee type (d for doctor, n for nurse, s for support): ");
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
}
