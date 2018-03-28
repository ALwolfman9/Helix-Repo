package Interface;

import Model.Employee;
import Model.Hospital;
import Model.Patient;

import java.util.Iterator;
import java.util.Scanner;

public class CommandLineDoctor extends CommandLineUser {

    public CommandLineDoctor(Hospital hospital, Employee user){
        super(hospital, user);
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        System.out.println("\n=======================");
        System.out.println(String.format("Welcome Dr. %s %s!", user.getFirstName(), user.getLastName()));

        while(true) {
            printHelp();
            String cmd = in.next();

            // TODO add ability to modify patients
            switch(cmd){
                case "p":
                case "P":
                case"patients":
                    viewPatients();
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
        System.out.println("Usage: patients/p/P | logout/l/L");
        System.out.println("patient/p/P\t\t\tView all of your patients");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    @Override
    void viewPatients() {
        Iterator<Patient> patients = hospital.getPatientsOfDoctor(user);
        if(patients == null) System.out.println("You have no patients.");
        else {
            System.out.println(String.format("          %30s%15s", "Name", "InsuranceID"));
            while (patients.hasNext()) {
                System.out.println("Patient:" + patients.next().toString());
            }
        }
    }
}
