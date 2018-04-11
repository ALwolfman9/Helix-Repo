package Interface;

import Model.Employee;
import Model.Hospital;
import Model.Patient;

import java.util.Scanner;

public class CommandLineNurse extends CommandLineUser {

    public CommandLineNurse(Hospital hospital, Employee user){
        super(hospital, user);
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        System.out.println("\n=================================");
        System.out.println(String.format("Welcome Nurse %s %s!", user.getFirstName(), user.getLastName()));

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
        System.out.println("patient/p/P\t\t\tView all patients");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    void viewPatient(Patient patient) {
        Scanner in = new Scanner(System.in);

        System.out.println("\n=================================");
        System.out.println(String.format("Profile of %s %s (Patient ID: %s)",
                patient.getFirstName(), patient.getLastName(), patient.getPatientID()));
        // support can do 'a' and 'i'
        // nurses can do 'a', 'h', and 'i'
        while(true) {
            printPatientViewHelp(patient);
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "i":
                case "I":
                case "info":
                    viewPatientInfo(patient);
                case "h":
                case "H":
                case "history":
                    viewPatientHistory(patient);
                    break;
                case "a":
                case "A":
                case "appointments":
                    viewPatientAppointments(patient);
                    break;
                case "q":
                case "Q":
                case "quit":
                    return;
                default:
                    System.out.println("That command/ID was not recognized");
            }
        }
    }
    void printPatientViewHelp(Patient p){
        String name = p.getFirstName();
        System.out.println();
        System.out.println("Usage: info/i/I | history/h/H| appointments/a/A| quit/q/Q");
        System.out.println(String.format("info/i/I\t\t\tView, %s's Patient Info", name));
        System.out.println(String.format("history/h/H\t\t\tView, edit, and add %s's Medical History", name));
        System.out.println(String.format("appointments/a/A\t\t\tView, edit, and add to %s's Appointments", name));
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }
}
