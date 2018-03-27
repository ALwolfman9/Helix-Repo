package Interface;

import Model.Employee;
import Model.Hospital;

import java.util.Scanner;

public class CommandLineSupport extends CommandLineUser {

    public CommandLineSupport(Hospital hospital, Employee user){
        super(hospital, user);
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        //System.out.println(String.format("Welcome %s %s!", user.getFirstName(), user.getLastName()));

        while(true) {
            printHelp();
            String cmd = in.next();

            // TODO add ability to modify patients
            switch(cmd){
                case "n":
                case "N":
                case "new":
                    if(!createPatient()) System.out.println("Patient could not be created");
                    break;
                case "p":
                case "P":
                case"patients":
                    viewPatients();
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
        System.out.println("Usage: patients/p/P | logout/l/L");
        System.out.println("patient/p/P\t\t\tView all patients");
        System.out.println("new/n/N\t\t\tAdd a new patient");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    private boolean createPatient(){
        Scanner in = new Scanner(System.in);

        String firstName, lastName, username;
        int ssn;
        System.out.println("Create an Employee");
        System.out.println("Enter first name: ");
        firstName = in.next();
        System.out.println("Enter last name: ");
        lastName = in.next();

        return hospital.addPatient(firstName, lastName);
    }
}
