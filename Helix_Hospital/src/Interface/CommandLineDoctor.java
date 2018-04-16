package Interface;

import Model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CommandLineDoctor extends CommandLineUser {

    public CommandLineDoctor(Hospital hospital, Employee user) {
        super(hospital, user);
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        while (true) {

            System.out.println("\n=================================");
            System.out.println(String.format("Welcome Dr. %s %s!", user.getFirstName(), user.getLastName()));
            printHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch (cmdArgs[0]) {
                case "p":
                case "P":
                case "patients":
                    viewPatients();
                    break;
                case "a":
                case "A":
                case "appointments":
                    viewAppointments();
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

    private void printHelp() {
        System.out.println();
        System.out.println("Usage: patients/p/P | logout/l/L");
        System.out.println("patient/p/P\t\t\tView and select your patients ");
        System.out.println("appointments/a/A\t\t\tView your appointments");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }


    @Override
    void viewPatients() {
        List<Patient> patients = hospital.getPatientsOfDoctor(user);
        if (patients.size() < 1) System.out.println("You have no patients.");
        else {
            while (true) {
                System.out.println("\n=================================");
                System.out.println(String.format("%4s%30s%15s%15s", "ID", "Name", "InsuranceID", "Doctor"));
                for (Patient p : patients) {
                    System.out.println(p.toString());
                }
                System.out.println("\nEnter a patient ID to view their profile, or enter 'q' to go back to home");
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
                } else {
                    System.out.println("That command was not recognized");
                }
            }
        }
    }

    void viewPatient(Patient patient) {
        Scanner in = new Scanner(System.in);

        // support can do 'a' and 'i'
        // nurses can do 'a', 'h', and 'i'
        // doctors can do 'a', 'h', 'r', 'p', and 'i'
        while (true) {
            System.out.println("\n=================================");
            System.out.println(String.format("Profile of %s %s (Patient ID: %s)",
                    patient.getFirstName(), patient.getLastName(), patient.getPatientID()));
            printPatientViewHelp(patient);
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch (cmdArgs[0]) {
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

    void printPatientViewHelp(Patient p) {
        String name = p.getFirstName();
        System.out.println();
        System.out.println("Usage: info/i/I | history/h/H | records/r/R | appointments/a/A | prescriptions/p/P | quit/q/Q");
        System.out.println(String.format("info/i/I\t\t\tView %s's Patient Info", name));
        System.out.println(String.format("history/h/H\t\t\tView, edit, and add %s's Medical History", name));
        System.out.println(String.format("records/r/R\t\t\tView and add to %s's Medical Records", name));
        System.out.println(String.format("appointments/a/A\tView, edit, and add to %s's Appointments", name));
        System.out.println(String.format("prescriptions/p/P\tView, edit, and add to %s's Prescriptions", name));
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private void viewPatientRecords(Patient patient) {
        Scanner in = new Scanner(System.in);

        while (true) {

            List<MedicalRecord> records = hospital.getRecordsOfPatient(patient.getPatientID());

            if (records == null) System.out.println("There are no records.");
            else {
                System.out.println("\n=================================");
                System.out.println("Viewing medical records for: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("=================================");
                for (MedicalRecord record : records) {
                    System.out.println(record.toString());
                    System.out.println("---------------------------------");
                }
            }

            printPatientRecordsHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch (cmdArgs[0]) {
                case "a":
                case "A":
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

    private void printPatientRecordsHelp() {
        System.out.println();
        System.out.println("Usage: add/a/A | quit/q/Q");
        System.out.println("add/a/A\t\t\tAdd a new Medical Record");
        System.out.println("quit/q/Q\t\tGo back to patient selection");
    }

    private void addMedicalRecord(Patient patient) {
        Scanner in = new Scanner(System.in);
        LocalDateTime dateTime;
        String comments;

        System.out.println();
        System.out.println("Add Medical Record");

        dateTime = getDateTime(in, false);

        System.out.println("Enter comments on this record:");
        comments = in.nextLine();

        hospital.addMedicalRecord(user.getUsername(), patient.getPatientID(), dateTime, comments);

    }

    private void viewPatientPrescriptions(Patient patient) {
        Scanner in = new Scanner(System.in);
        while (true) {

            List<Prescription> prescriptions = hospital.getPrescriptionsOfPatient(patient.getPatientID());

            if (prescriptions == null) System.out.println("There are no prescriptions.");
            else {
                System.out.println("Viewing prescriptions for: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("=================================");
                for (Prescription prescription : prescriptions) {
                    System.out.println(prescription);
                    System.out.println("---------------------------------");
                }
            }

            printPatientPrescriptionsHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch (cmdArgs[0]) {
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

    private void printPatientPrescriptionsHelp() {
        System.out.println();
        System.out.println("Usage: add/a/A | quit/q/Q");
        System.out.println("add/a/A\t\t\tAdd a new prescription");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private void addPrescription(Patient patient) {
        Scanner in = new Scanner(System.in);
        String drug, dosage, duration, id;

        System.out.println();

        System.out.println("Add Prescription");

        drug = notNull("name of the drug", in);
        dosage = notNull("dosage", in);
        duration = notNull("duration", in);

        hospital.addPrescription(patient.getPatientID(), drug, dosage, duration);

    }

    private String notNull(String what, Scanner in) {
        String result;
        while (true) {
            System.out.println("Enter the " + what + ":");
            result = in.nextLine();
            if (!result.equals("")) return result;
            System.out.println("You must enter a " + what + ".");
        }
    }

    @Override
    void viewPatientAppointments(Patient patient) {
        Scanner in = new Scanner(System.in);
        while (true) {

            List<Appointment> appointments = hospital.getAppointmentsOfPatient(patient.getPatientID());

            if (appointments == null) System.out.println("There are no appointments.");
            else {
                System.out.println("\n=================================");
                System.out.println("Viewing appointments for: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("=================================");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment.patientView(true));
                }
            }

            System.out.println("=================================");
            printPatientAppointmentsHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");

            switch (cmdArgs[0]) {
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

    void viewAppointments() {
        List<Appointment> appointments = hospital.getAppointmentsOfEmployee(user.getUsername());

        if (appointments.size() == 0) System.out.println("There are no appointments.");
        else {
            System.out.println("\n=================================");
            System.out.println("Viewing appointments for: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("=================================");
            for (Appointment appointment : appointments) {
                System.out.println(appointment.doctorView(false));
                System.out.println("---------------------------------");
            }
        }
    }
}


