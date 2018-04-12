package Interface;

import Model.Employee;
import Model.Hospital;

import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class CommandLine {
    Hospital hospital;

    public CommandLine(Hospital hospital){
        this.hospital = hospital;
    }

    public abstract void run();

    public String firstNameValidation(Scanner in){
        while (true) {
            System.out.println("*Enter first name: ");
            String firstName = in.nextLine();
            if (firstName.length() > 0 && firstName.length() <= 20) {
                return firstName;
            }
            else {
                System.out.println("First name must be 1-20 characters");
            }
        }
    }

    public String middleInitialValidation(Scanner in){
        while (true) {
            System.out.println("Enter middle initial: ");
            String middleInitial = in.nextLine();
            if (middleInitial.length() == 0) {
                return null;
            }
            else if (middleInitial.length() == 1) {
                return middleInitial;
            }
            else {
                System.out.println("Middle initial cannot be more than 1 character");
            }
        }
    }

    public String lastNameValidation(Scanner in){
        while (true) {
            System.out.println("*Enter last name: ");
            String lastName = in.nextLine();
            if (lastName.length() > 0 && lastName.length() <= 30) {
                return lastName;
            }
            else {
                System.out.println("Last name must be 1-30 characters");
            }
        }
    }

    public String phoneValidation(Scanner in){
        while (true) {
            System.out.println("Enter the phone number (no dashes): ");
            String phone = in.nextLine();
            if (phone.length() == 0) {
                return null;
            }
            else if (phone.length() == 10 && phone.matches("[0-9]+")) {
                return phone;
            }
            else {
                System.out.println("Phone number must be 10 characters long and only contain numbers");
            }
        }
    }

    public Employee.Gender genderValidation(Scanner in){
        while(true) {
            System.out.println("*Enter the employee gender (m for male, f for female, o for other): ");
            String gender = in.nextLine();
            switch (gender) {
                case "m":
                    return Employee.Gender.M;
                case "f":
                    return Employee.Gender.F;
                case "o":
                    return Employee.Gender.O;
                default:
                    System.out.println("Please enter a valid gender");
            }
        }
    }

    public String emailValidation(Scanner in){
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            System.out.println("Enter the email: ");
            String email = in.nextLine();
            if (email.length() == 0) {
                return null;
            }
            else if (email.length() <= 50 && pattern.matcher(email).matches()) {
                return email;
            }
            else {
                System.out.println("Enter a valid email of 50 characters or less");
            }
        }
    }

    public String addressValidation(Scanner in){
        while (true) {
            System.out.println("Enter the address: ");
            String address = in.nextLine();
            if (address.length() == 0) {
                return null;
            }
            else if (address.length() <= 255) {
                return address;
            }
            else {
                System.out.println("Address must be 255 characters or less");
            }
        }
    }
}
