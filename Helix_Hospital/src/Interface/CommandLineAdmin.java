package Interface;

import Model.*;

import java.util.Scanner;

public class CommandLineAdmin extends CommandLine {

    public CommandLineAdmin(Hospital hospital){
        super(hospital);
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome admin!");

        while(true) {
            printHelp();
            String cmd = in.next();

            // TODO add ability to modify employee
            switch(cmd){
                case "n":
                case "N":
                case "new":
                    if(!createEmployee()) System.out.println("Employee could not be created");
                    break;
                case "q":
                case "Q":
                case "quit":
                    return;
                default:
                    System.out.println("That command was not recognized");
            }
        }
    }

    private void printHelp(){
        System.out.println("Usage: employee/e/E | logout/l/L");
        System.out.println("new/n/N\t\t\tAdd a new employee");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    private boolean createEmployee(){
        Scanner in = new Scanner(System.in);

        String firstName, lastName, username;
        int ssn;
        Employee.Type type;
        System.out.println("Create an Employee");
        System.out.println("Enter first name: ");
        firstName = in.next();
        System.out.println("Enter last name: ");
        lastName = in.next();
        System.out.println("Enter username: ");
        username = in.next();
        System.out.println("Enter the ssn: ");
        ssn = in.nextInt();
        type = getType();

        return hospital.addEmployee(firstName, lastName, username, ssn, type);
    }

    private Employee.Type getType(){
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the employee type (d for doctor, n for nurse, s for support): ");
            String t = in.next();
            switch (t) {
                case "d":
                    return Employee.Type.DOCTOR;
                    break;
                case "n":
                    return Employee.Type.NURSE;
                    break;
                case "s":
                    return Employee.Type.SUPPORT;
                    break;
                default:
                    System.out.println("Please enter a valid type");
            }
        }
    }
}
