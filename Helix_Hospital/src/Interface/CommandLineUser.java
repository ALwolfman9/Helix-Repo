package Interface;

import Model.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public abstract class CommandLineUser extends CommandLine{

    Employee user;

    public CommandLineUser(Hospital hospital, Employee user){
        super(hospital);
        this.user = user;
    }

    void viewDoctors(){
        Iterator<Employee> doctors = hospital.getAllDoctors();
        if(!doctors.hasNext()) System.out.println("There are no doctors.");
        else {
            System.out.println(String.format("%30s%15s%15s", "Name", "Username", "Type"));
            while (doctors.hasNext()) {
                System.out.println(doctors.next().toString());
            }
        }
    }

    void viewPatients() {
        List<Patient> patients = hospital.getAllPatients();
        if(patients.size() < 1) System.out.println("There are no patients.");
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

    abstract void viewPatient(Patient p);

    void viewPatientInfo(Patient patient){
        Scanner in = new Scanner(System.in);

        System.out.println("\n=================================");
        System.out.println(patient.toString());
        while(true) {
            printPatientInfoHelp();
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

    private void printPatientInfoHelp(){
        System.out.println();
        System.out.println("Usage: quit/q/Q");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    void viewPatientHistory(Patient patient){
        Scanner in = new Scanner(System.in);

        MedicalHistory medicalHistory = hospital.getMedicalHistory(patient);
        if(medicalHistory == null) System.out.println(patient.getFirstName() + " has no Medical History yet");
        else System.out.println(medicalHistory.toString());

        System.out.println("\n=================================");
        System.out.println();
        while(true) {
            printPatientHistoryHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "a":
                case "A":
                case "add":
                    addMedicalHistory(patient);
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

    private void addMedicalHistory(Patient patient){
        Scanner in = new Scanner(System.in);
        String bloodType, familyHistory, existingConditions, allergies, medications;

        System.out.println();
        System.out.println("Add Medical History");

        System.out.println("Enter blood type: ");
        bloodType = in.nextLine();

        System.out.println("Enter family history: ");
        familyHistory = in.nextLine();

        System.out.println("Enter past conditions and injuries: ");
        existingConditions = in.nextLine();

        System.out.println("Enter allergies: ");
        allergies = in.nextLine();

        System.out.println("Enter medications: ");
        medications = in.nextLine();

        hospital.addMedicalHistory(patient.getPatientID(), bloodType, allergies,
                medications, existingConditions, familyHistory);
    }

    private void printPatientHistoryHelp(){
        System.out.println();
        System.out.println("Usage: a/A/add | quit/q/Q");
        System.out.println("add/a/A\t\t\tAdd a new Medical History");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    void viewPatientAppointments(Patient patient){
        Scanner in = new Scanner(System.in);

        List<Appointment> appointments = hospital.getAppointmentsOfPatient(patient.getPatientID());

        if(appointments == null) System.out.println("There are no appointments.");
        else {
            for(Appointment appointment : appointments){
                //TODO create appointment toString for it to print correctly
                System.out.println(appointment);
            }
        }

        System.out.println("\n=================================");
        System.out.println(String.format(""));
        while(true) {
            printPatientAppointmentsHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "a":
                case "A":
                case "add":
                    addAppointment(patient);
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

    private void printPatientAppointmentsHelp(){
        System.out.println();
        System.out.println("Usage: add/a/A | quit/q/Q");
        System.out.println("add/a/A\t\t\tAdd a new appointment");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private void addAppointment(Patient patient){
        Scanner in = new Scanner(System.in);

        //TODO check that there are patients and doctors

        String doctor, patientId = patient.getPatientID(), reasonForVisit;

        System.out.println();
        System.out.println("Create an Appointment");
        System.out.println("Is this appointment with the patient's primary care doctor? (y or n)");
        String question = in.nextLine();

        if(question.equals("y")) doctor = patient.getDoctor();
        else {
            while (true) {
                if (!hospital.doctorExists()) {
                    System.out.println("Appointment cannot be created; there are no doctors in the system");
                    System.out.println("Quitting appointment creation");
                    return;
                }
                System.out.println("Enter the username of the doctor this appointment is with: ");
                System.out.println("(Enter $list to list each doctor and their usernames | " +
                        "Enter $quit to cancel appointment)");
                doctor = in.nextLine();
                if (doctor.equals("$1ist")) {
                    viewDoctors();
                } else if (!hospital.doctorExists(doctor)) {
                    System.out.println("A doctor with the given username does not exist");
                } else if (doctor.equals("$quit")){
                    //there are no doctors to choose from or canceling appointment creation
                    System.out.println("Quitting appointment creation");
                    return;
                }
                else{
                    //selected a valid doctor, breaking out of while loop
                    break;
                }
            }
        }
        System.out.println("Enter the reason for the appointment:");
        reasonForVisit = in.nextLine();
        // TODO add dateTime
        hospital.addAppointment(doctor, patientId, null, reasonForVisit);
    }


}
