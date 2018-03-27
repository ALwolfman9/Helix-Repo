package Main;

import Model.*;

import java.util.Scanner;

public class CommandLineAdmin extends CommandLine{

    public CommandLineAdmin(Hospital hospital){
        super(hospital);
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome admin!");

        while(true) {
            printHelp();
            String cmd = in.next();

            switch(cmd){
                case "e":
                case "E":
                case "employee":
                    if(!createEmployee()) System.out.println("Employee could not be created");
                    break;
                case "q":
                case "Q":
                case "quit":
                    return;
                default:
                    System.out.println("That command was not recognized");
            }
        }
    }

    private void printHelp(){
        System.out.println("Usage: employee/e/E | logout/l/L");
        System.out.println("employee/e/E\t\t\tAdd a new employee");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    private void
}
