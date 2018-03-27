package Interface;

import Model.*;

import java.util.Scanner;

public class CommandLineStart extends CommandLine {

    public CommandLineStart(Hospital hospital){
        super(hospital);
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the Hospital!");
        while(true){
            printHelp();
            String cmd = in.next();

            switch(cmd){
                case "l":
                case "L":
                case "login":
                    login();
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
        System.out.println("Usage: login/l/L | quit/q/Q");
        System.out.println("login/l/L\t\t\tLog in to the Hospital Application");
        System.out.println("quit/q/Q\t\t\tQuit the Hospital Application");
    }

    /**
     * Logs a user into the system.
     */
    private void login(){
        Scanner in = new Scanner(System.in);

        CommandLine cmdLine;

        System.out.println("Please log in: ");
        String username = in.next();
        if(username.equals("admin")) cmdLine = new CommandLineAdmin(hospital);
        Employee emp = hospital.getEmployee(username);

        while(emp == null){
            System.out.println("Username was incorrect. Please try again: ");
            username = in.next();
            if(username.equals("admin")) cmdLine = new CommandLineAdmin(hospital);
            emp = hospital.getEmployee(username);
        }
        switch(emp.type){
            case DOCTOR:
                cmdLine = new CommandLineDoctor(hospital, emp);
                break;
            case NURSE:
                cmdLine = new CommandLineNurse(hospital, emp);
                break;
            case SUPPORT:
                cmdLine = new CommandLineSupport(hospital, emp);
                break;
            default:
                System.out.println("Username wasn't recognized. Please try again.");
                return;
        }
        cmdLine.run();
    }

}
