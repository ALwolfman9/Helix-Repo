package Interface;

import Model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            while (true) {
                System.out.println("\n=================================");
                System.out.println(String.format("%4s%30s%15s%15s", "ID", "Name", "InsuranceID", "Doctor"));
                for (Patient p : patients) {
                    System.out.println(p.toString());
                }
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

    void displayPatientInfo(Patient p){

        System.out.println("\n=================================");

        if(p.getMiddleInit() == null) System.out.println("Name: " + p.getFirstName() + " " + p.getLastName());
        else System.out.println("Name: " + p.getFirstName() + " " + p.getMiddleInit() + " " + p.getLastName());

        System.out.println("Doctor's username : " + p.getDoctor());
        System.out.println("InsuranceID: " + p.getInsuranceID());
        System.out.println("Gender: " + Hospital.formatGender(p.getGender()));
        System.out.println("Address: " + p.getAddress());
        System.out.println("Phone Number: " + p.getPhoneNumber());
        System.out.println("Email Address: " + p.getEmail());
    }

    void viewPatientInfo(Patient patient){
        Scanner in = new Scanner(System.in);

        while(true) {

            displayPatientInfo(patient);
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
        if(medicalHistory == null) {
            System.out.println("\n" + patient.getFirstName() + " has no Medical History yet. Do you want to add one? (y/n)");
            String question = in.nextLine();
            if(question.equals("y")) addMedicalHistory(patient);
            else return;
        }
        medicalHistory = hospital.getMedicalHistory(patient);

        while(true) {
            System.out.println("\n=================================");
            System.out.println(medicalHistory.toString());
            printPatientHistoryHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "e":
                case "E":
                case "edit":
                    editMedicalHistory(patient, medicalHistory);
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
        familyHistory = "\n" + in.nextLine();

        System.out.println("Enter past conditions and injuries: ");
        existingConditions = "\n" + in.nextLine();

        System.out.println("Enter allergies: ");
        allergies = "\n" + in.nextLine();

        System.out.println("Enter medications: ");
        medications = "\n" + in.nextLine();

        hospital.addMedicalHistory(patient.getPatientID(), bloodType, allergies,
                medications, existingConditions, familyHistory);
    }

    private void editMedicalHistory(Patient patient, MedicalHistory medicalHistory){
        Scanner in = new Scanner(System.in);
        String familyHistory, existingConditions, allergies, medications;

        System.out.println();
        System.out.println("Edit Medical History");

        System.out.println("Current family history: "
                + "\n-----------------------"
                + medicalHistory.getFamilyHistory());

        System.out.println("\nAdd to family history: (If no changes, hit enter)");
        familyHistory = in.nextLine();
        if(!familyHistory.equals(""))
            medicalHistory.setFamilyHistory(medicalHistory.getFamilyHistory() + "\n" +  familyHistory);

        System.out.println("\nPast conditions and injuries: "
                + "\n-----------------------"
                + medicalHistory.getPastConditions());

        System.out.println("\nAdd to past conditions and injuries: (If no changes, hit enter)");
        existingConditions = in.nextLine();
        if(!existingConditions.equals(""))
            medicalHistory.setPastConditions(medicalHistory.getPastConditions() + "\n" + existingConditions);

        System.out.println("\nCurrent allergies: \n"
                + "\n-----------------------"
                + medicalHistory.getAllergies());
        loop:
        while(true) {
            System.out.println("\n\tTo add to this list, enter 'a'.\n" +
                    "\tTo delete and replace the list, enter 'r'.\n" +
                    "\tIf there are no changes, hit enter.");
            allergies = in.nextLine();

            switch (allergies) {
                case "a":
                    System.out.println("Enter the allergies to add to the list: ");
                    allergies = in.nextLine();
                    medicalHistory.setAllergies(medicalHistory.getAllergies() + "\n" + allergies);
                    break loop;
                case "r":
                    System.out.println("The current allergies list will be deleted. Enter the allergies to replace it: ");
                    allergies = in.nextLine();
                    medicalHistory.setAllergies(allergies);
                    break loop;
                case "":
                    break loop;
                default:
                    System.out.println("Command was not recognized.");

            }
        }

        System.out.println("\nCurrent medications: "
                + "\n-----------------------"
                + medicalHistory.getMedications());
        loop:
        while(true) {
            System.out.println("\n\tTo add to this list, enter 'a'.\n" +
                    "\tTo delete and replace the list, enter 'r'.\n" +
                    "\tIf there are no changes, hit enter.");
            medications = in.nextLine();

            switch (medications) {
                case "a":
                    System.out.println("Enter the medications to add to the list: ");
                    medications = in.nextLine();
                    medicalHistory.setMedications(medicalHistory.getAllergies() + "\n" + medications);
                    break loop;
                case "r":
                    System.out.println("The current medications list will be deleted. " +
                            "Enter the medication(s) to replace it: ");
                    medications = in.nextLine();
                    medicalHistory.setMedications(medications);
                    break loop;
                case "":
                    break loop;
                default:
                    System.out.println("Command was not recognized.");

            }
        }

        hospital.updateMedicalHistory(medicalHistory);
    }

    private void printPatientHistoryHelp(){
        System.out.println();
        System.out.println("Usage: e/E/edit | quit/q/Q");
        System.out.println("edit/e/E\t\t\tEdit the patient's medical history");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    void viewPatientAppointments(Patient patient){
        Scanner in = new Scanner(System.in);
        while(true) {

            List<Appointment> appointments = hospital.getAppointmentsOfPatient(patient.getPatientID());

            if(appointments == null) System.out.println("There are no appointments.");
            else {
                System.out.println("\n=================================");
                System.out.println("Viewing appointments for: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("=================================");
                for(Appointment appointment : appointments){
                    System.out.println(appointment.patientView(false));
                }
            }

            System.out.println("=================================");
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

    void printPatientAppointmentsHelp(){
        System.out.println();
        System.out.println("Usage: add/a/A | quit/q/Q");
        System.out.println("add/a/A\t\tAdd a new appointment");
        System.out.println("quit/q/Q\tGo back to patient selection");
    }

    void addAppointment(Patient patient){
        Scanner in = new Scanner(System.in);

        //TODO check that there are patients and doctors

        String doctor, patientId = patient.getPatientID(), reasonForVisit;

        System.out.println();
        System.out.println("Create an Appointment");
        System.out.println("Is this appointment with the patient's primary care doctor? (y/n)");
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

        LocalDateTime dateTime = getDateTime(in, true);
        hospital.addAppointment(doctor, patientId, dateTime, reasonForVisit);
    }

    LocalDateTime getDateTime(Scanner in, boolean appointment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        LocalDateTime dateTime;

        System.out.println(String.format("Enter the date and time %s in the format \"yyyy-mm-dd hh:mm AM/PM",
                appointment ? "of the appointment" : "for the record"));
        while(true) {
            String date = in.nextLine();

            try {
                dateTime = LocalDateTime.parse(date, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("The date was not entered in the correct format. Try again:");
            }
        }
        return dateTime;
    }

}
