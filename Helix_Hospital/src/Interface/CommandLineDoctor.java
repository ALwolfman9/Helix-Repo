package Interface;

import Model.Employee;
import Model.Hospital;
import Model.Patient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CommandLineDoctor extends CommandLineUser {

    public CommandLineDoctor(Hospital hospital, Employee user){
        super(hospital, user);
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        System.out.println("\n=================================");
        System.out.println(String.format("Welcome Dr. %s %s!", user.getFirstName(), user.getLastName()));

        while(true) {
            printHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
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
            List<Patient> patientList = new ArrayList<>();
            System.out.println(String.format("%4s%30s%15s%15s", "ID", "Name", "InsuranceID", "Doctor"));
            while (patients.hasNext()) {
                Patient p = patients.next();
                System.out.println(p.toString());
                patientList.add(p);
            }
            while (true) {
                System.out.println("Enter a patient ID to view their profile, or enter 'q' to go back to home");
                Scanner in = new Scanner(System.in);
                String cmd = in.nextLine();
                String[] cmdArgs = cmd.split("\\s+");
                switch (cmdArgs[0]) {
                    case "q":
                    case "Q":
                    case "quit":
                        return;
                    default:
                        break;
                }
                Patient selectedPatient = null;
                for (Patient p : patientList) {
                    if (p.getPatientID().equals(cmdArgs[0])) {
                        selectedPatient = p;
                    }
                }
                if (selectedPatient != null) {
                    viewPatient(selectedPatient);
                }
                else {
                    System.out.println("That command was not recognized");
                }
            }
        }
    }

    void viewPatient(Patient patient) {
        Scanner in = new Scanner(System.in);

        System.out.println("\n=================================");
        System.out.println(String.format("Profile of %s %s (Patient ID: %s)",
                patient.getFirstName(), patient.getLastName(), patient.getPatientID()));
        while(true) {
            printPatientViewHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "q":
                case "Q":
                case "quit":
                    return;
                default:
                    System.out.println("That command/ID was not recognized");
            }
        }
    }

    private void printPatientViewHelp(){
        System.out.println();
        System.out.println("Usage: quit/q/Q");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }
}
