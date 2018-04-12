package Interface;

import Model.*;

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
                case "a":
                case "A":
                case "appointments":
                    //viewAppointments();
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
        System.out.println("patient/p/P\t\t\tView and select your patients ");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }


    @Override
    void viewPatients() {
        List<Patient> patients = hospital.getPatientsOfDoctor(user);
        if(patients == null) System.out.println("You have no patients.");
        else {
            System.out.println(String.format("%4s%30s%15s%15s", "ID", "Name", "InsuranceID", "Doctor"));
            for (Patient p : patients) {
                System.out.println(p.toString());
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
                for (Patient p : patients) {
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
        // support can do 'a' and 'i'
        // nurses can do 'a', 'h', and 'i'
        // doctors can do 'a', 'h', 'r', 'p', and 'i'
        while(true) {
            printPatientViewHelp(patient);
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "i":
                case "I":
                case "info":
                    viewPatientInfo(patient);
                    break;
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
                case "r":
                case "R":
                case "records":
                    viewPatientRecords(patient);
                    break;
                case "p":
                case "P":
                case "prescriptions":
                    viewPatientPrescriptions(patient);
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
        System.out.println("Usage: info/i/I | history/h/H | records/r/R | appointments/a/A | prescriptions/p/P | quit/q/Q");
        System.out.println(String.format("info/i/I\t\t\tView, %s's Patient Info", name));
        System.out.println(String.format("history/h/H\t\t\tView, edit, and add %s's Medical History", name));
        System.out.println(String.format("records/r/R\t\t\tView and add to %s's Medical Records", name));
        System.out.println(String.format("appointments/a/A\t\t\tView, edit, and add to %s's Appointments", name));
        System.out.println(String.format("prescriptions/p/P\t\t\tView, edit, and add to %s's Prescriptions",name));
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private void viewPatientRecords(Patient patient){
        Scanner in = new Scanner(System.in);

        Iterator<MedicalRecord> records = hospital.getRecordsOfPatient(patient.getPatientID());

        if(records == null) System.out.println("There are no appointments.");
        else //TODO Print out the records

            System.out.println("\n=================================");
        System.out.println(String.format(""));
        while(true) {
            printPatientRecordsHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "a":
                case"A":
                case "add":
                    addMedicalRecord(patient);
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

    private void printPatientRecordsHelp(){
        System.out.println();
        System.out.println("Usage: add/a/A | quit/q/Q");
        System.out.println("add/a/A\t\t\tAdd a new Medical Record");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private void addMedicalRecord(Patient patient){
        Scanner in = new Scanner(System.in);
        String datetime, comments;

        System.out.println();
        System.out.println("Add Medical Record");

        System.out.println("Enter the dateTime for this record:");
        datetime = in.nextLine();

        System.out.println("Enter comments on this record:");
        comments = in.nextLine();

        //TODO do the datetime
        hospital.addMedicalRecord(user.getUsername(), patient.getPatientID(), null, comments);

    }

    private void viewPatientPrescriptions(Patient patient){
        Scanner in = new Scanner(System.in);

        Iterator<Prescription> prescriptions = hospital.getPrescriptionsOfPatient(patient.getPatientID());

        if(prescriptions == null) System.out.println("There are no prescriptions.");
        else //TODO Print out the records

            System.out.println("\n=================================");
        System.out.println(String.format(""));
        while(true) {
            printPatientPrescriptionsHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "a":
                case "A":
                case "add":
                    addPrescription(patient);
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

    private void printPatientPrescriptionsHelp(){
        System.out.println();
        System.out.println("Usage: add/a/A | quit/q/Q");
        System.out.println("add/a/A\t\t\tAdd a new prescription");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private void addPrescription(Patient patient){
        Scanner in = new Scanner(System.in);
        String drug, dosage, duration, id;

        System.out.println();
        System.out.println("Add Prescription");

        System.out.println("Enter the name of the drug:");
        drug = in.nextLine();

        System.out.println("Enter the dosage:");
        dosage = in.nextLine();

        System.out.println("Enter the duration:");
        duration = in.nextLine();

        //TODO Is prescriptionID auto generated, like patientID?
        hospital.addPrescription(patient.getPatientID(), drug, dosage, duration);

    }

}
