import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Hospital {
    private HashSet<Patient> patients;

    public Hospital(){

    }

    public static void printHelp(){
        System.out.println("Usage: patient/p/P | quit/q/Q | list/l/L | help/h/H");
        System.out.println("patient/p/P:        Add a patient");
        System.out.println("quit/q/Q:           Quits program.");
        System.out.println("list/l/L:           Lists patients.");
        System.out.println("help/h/H:        Displays this help message.");
    }

    public void listPatients(){
        Iterator<Patient> patientIterator = patients.iterator();
        while(patientIterator.hasNext()){
            System.out.println("Patient: " + patientIterator.next());
        }
    }

    public static void main(String[] args){
        System.out.println("Welcome to the Hospital!");
        Hospital hospital = new Hospital();


        Scanner scanner = new Scanner(System.in);
        String command;

        while(true){
            printHelp();
            command = scanner.next();
            if (command.equals("Q") || command.equals("q") || command.equals("quit")) {
                System.out.println("Exiting program...");
                break;
            } else if (command.equals("L") || command.equals("l") || command.equals("list")) {
                //display list of urls
                hospital.listPatients();
            }
            else if(command.equals("H") || command.equals("h") || command.equals("help")){
                printHelp();
            }
            else if(command.equals("P") || command.equals("p") || command.equals("patient")) {
                System.out.println("Input Patient Data File (CSV)");

                String filename = scanner.next();
                File file = new File(filename);
                Patient patient = new Patient(file);
            }
            break;
        }
    }
}
