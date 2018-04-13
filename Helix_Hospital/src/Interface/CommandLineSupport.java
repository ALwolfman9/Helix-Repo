package Interface;

import Model.Employee;
import Model.Hospital;
import Model.Patient;

import java.util.Scanner;

public class CommandLineSupport extends CommandLineUser {

    public CommandLineSupport(Hospital hospital, Employee user){
        super(hospital, user);
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        while(true) {

            System.out.println("\n=================================");
            System.out.println(String.format("Welcome %s %s!", user.getFirstName(), user.getLastName()));
            printHelp();
            String cmd = in.next();

            switch(cmd){
                case "n":
                case "N":
                case "new":
                    if (hospital.doctorExists()) {
                        if (!createPatient()) System.out.println("Patient could not be created");
                    }
                    else {
                        System.out.println("Patient cannot be created; there are no doctors in the system");
                    }
                    break;
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
        System.out.println("new/n/N\t\t\t\tAdd a new patient");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    private boolean createPatient(){
        Scanner in = new Scanner(System.in);
        String firstName, middleInitial, lastName, insurance, phone, email, address, doctor;
        Patient.Gender gender;

        System.out.println();
        System.out.println("Create a Patient");
        firstName = firstNameValidation(in);
        middleInitial = middleInitialValidation(in);
        lastName = lastNameValidation(in);
        insurance = insuranceValidation(in);
        phone = phoneValidation(in);
        gender = genderValidation(in);
        email = emailValidation(in);
        address = addressValidation(in);
        doctor = doctorValidation(in);

        return hospital.addPatient(firstName, lastName, middleInitial, email, address,
                gender, insurance, phone, doctor);
    }
    void viewPatient(Patient patient) {
        Scanner in = new Scanner(System.in);

        while(true) {

            System.out.println("\n=================================");
            System.out.println(String.format("Profile of %s %s (Patient ID: %s)",
                    patient.getFirstName(), patient.getLastName(), patient.getPatientID()));
            printPatientViewHelp(patient);
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "i":
                case "I":
                case "info":
                    viewPatientInfo(patient);
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

    @Override
    void viewPatientInfo(Patient patient){
        Scanner in = new Scanner(System.in);
        while(true) {

            //TODO make this display all the patient's info
            displayPatientInfo(patient);
            printPatientInfoHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "e":
                case "E":
                case "edit":
                    editPatientInfo(patient);
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

    private void editPatientInfo(Patient patient){
        Scanner in = new Scanner(System.in);

        System.out.println();
        System.out.println("Edit a Patient");

        System.out.println("Current first name is " + patient.getFirstName());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setFirstName(firstNameValidation(in));

        System.out.println("Current middle intial is " + patient.getMiddleInit());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setMiddleInit(middleInitialValidation(in));

        System.out.println("Current last name is " + patient.getLastName());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setLastName(lastNameValidation(in));

        System.out.println("Current Insurance ID is " + patient.getInsuranceID());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setInsuranceID(insuranceValidation(in));

        System.out.println("Current phone number is " + patient.getPhoneNumber());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setPhoneNumber(phoneValidation(in));

        System.out.println("Current gender is " + patient.getGender());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setGender(genderValidation(in));

        System.out.println("Current email is " + patient.getEmail());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setEmail(emailValidation(in));

        System.out.println("Current address is " + patient.getAddress());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setAddress(addressValidation(in));

        System.out.println("Current doctor is " + patient.getDoctor());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            patient.setDoctor(doctorValidation(in));

        hospital.updatePatient(patient);
    }

    private void printPatientInfoHelp(){
        System.out.println();
        System.out.println("Usage: e/E/edit | quit/q/Q");
        System.out.println("edit/e/E\t\t\tEdit the patient info");
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    void printPatientViewHelp(Patient p){
        String name = p.getFirstName();
        System.out.println();
        System.out.println("Usage: info/i/I | appointments/a/A | quit/q/Q");
        System.out.println(String.format("info/i/I\t\t\tView, %s's Patient Info", name));
        System.out.println(String.format("appointments/a/A\tView, edit, and add to %s's Appointments", name));
        System.out.println("quit/q/Q\t\t\tGo back to patient selection");
    }

    private String insuranceValidation(Scanner in){
        while (true) {
            System.out.println("*Enter insurance ID: ");
            String insurance = in.nextLine();
            if (insurance.length() >= 10 && insurance.length() <= 15) {
                return insurance;
            }
            else {
                System.out.println("Insurance ID must be 10-15 characters");
            }
        }
    }

    private String doctorValidation(Scanner in){
        while(true) {
            System.out.println("*Enter the username of the patient's doctor: ");
            System.out.println("(Enter $list to list each doctor and their usernames)");
            String doctor = in.nextLine();
            if(doctor.equals("$list")) {
                viewDoctors();
            }
            else if (hospital.doctorExists(doctor)) {
                return doctor;
            }
            else{
                System.out.println("A doctor with the given username does not exist");
            }
        }
    }
}
