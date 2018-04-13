package Interface;

import Model.*;

import java.util.List;
import java.util.Scanner;

public class CommandLineAdmin extends CommandLine {

    public CommandLineAdmin(Hospital hospital){
        super(hospital);
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);

        while(true) {

            System.out.println("\n=================================");
            System.out.println("Welcome admin!");
            printHelp();
            String cmd = in.next();

            // TODO add ability to modify employee
            switch(cmd){
                case "e":
                case "E":
                case "employees":
                    viewEmployees();
                    break;
                case "n":
                case "N":
                case "new":
                    if(!createEmployee()) System.out.println("Employee could not be created");
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
        System.out.println("Usage: employees/e/E | new/n/N | logout/l/L");
        System.out.println("employees/e/E\t\tView all employees");
        System.out.println("new/n/N\t\t\t\tAdd a new employee");
        System.out.println("logout/l/L\t\t\tLogout of the Hospital application");
    }

    public void viewEmployees(){
        List<Employee> employees = hospital.getAllEmployees();
        if(employees.size() < 1) System.out.println("There are no employees");
        else {
            while(true) {
                System.out.println("\n=================================");
                System.out.println(String.format("%30s%15s%15s", "Name", "Username", "Type"));
                for(Employee e : employees) {
                    System.out.println(e.toString());
                }

                System.out.println("\nEnter an employee's username to view their profile, or enter 'q' to go back to home");
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
                Employee selectedEmployee = null;
                for (Employee e : employees) {
                    if (e.getUsername().equals(cmdArgs[0])) {
                        selectedEmployee = e;
                    }
                }
                if (selectedEmployee != null) {
                    viewEmployeeInfo(selectedEmployee);
                }
                else {
                    System.out.println("That command was not recognized");
                }
            }
        }
    }

    private boolean createEmployee(){
        Scanner in = new Scanner(System.in);

        String firstName, middleInitial, lastName, username, ssn, phone, email, address;
        Employee.Type type;
        Employee.Gender gender;
        System.out.println();
        System.out.println("Create an Employee");
        firstName = firstNameValidation(in);
        middleInitial = middleInitialValidation(in);
        lastName = lastNameValidation(in);
        username = usernameValidation(in);
        phone = phoneValidation(in);
        gender = genderValidation(in);
        ssn = ssnValidation(in);
        email = emailValidation(in);
        address = addressValidation(in);
        type = typeValidation(in);

        if(type == Employee.Type.DOCTOR) {
            String special = specializationValidation(in);
            return hospital.addDoctor(username, firstName, middleInitial, lastName, phone,
                    gender, ssn, email, address, type, special);
        }

        return hospital.addEmployee(username, firstName, middleInitial, lastName, phone,
                gender, ssn, email, address, type);
    }

    void viewEmployeeInfo(Employee employee){
        Scanner in = new Scanner(System.in);
        while(true) {

            displayEmployeeInfo(employee);
            printEmployeeInfoHelp();
            String cmd = in.nextLine();
            String[] cmdArgs = cmd.split("\\s+");
            switch(cmdArgs[0]){
                case "e":
                case "E":
                case "edit":
                    editEmployeeInfo(employee);
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

    void displayEmployeeInfo(Employee e){

        System.out.println("\n=================================");

        if(e.getMiddleInit() == null) System.out.println("Name: " + e.getFirstName() + " " + e.getLastName());
        else System.out.println("Name: " + e.getFirstName() + " " + e.getMiddleInit() + " " + e.getLastName());

        System.out.println("Employee Type: " + Hospital.formatType(e.getType()));
        System.out.println("Username: " + e.getUsername());
        System.out.println("SSN: " + e.getSsn());
        System.out.println("Gender: " + Hospital.formatGender(e.getGender()));
        System.out.println("Address: " + e.getAddress());
        System.out.println("Phone Number: " + e.getPhoneNumber());
        System.out.println("Email Address: " + e.getEmail());
    }

    private void editEmployeeInfo(Employee employee){
        Scanner in = new Scanner(System.in);

        System.out.println();
        System.out.println("Edit an Employee");

        System.out.println("Current first name is " + employee.getFirstName());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setFirstName(firstNameValidation(in));

        System.out.println("Current middle intial is " + employee.getMiddleInit());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setMiddleInit(middleInitialValidation(in));

        System.out.println("Current last name is " + employee.getLastName());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setLastName(lastNameValidation(in));

        System.out.println("Current phone number is " + employee.getPhoneNumber());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setPhoneNumber(phoneValidation(in));

        System.out.println("Current gender is " + employee.getGender());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setGender(genderValidation(in));

        System.out.println("Current email is " + employee.getEmail());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setEmail(emailValidation(in));

        System.out.println("Current address is " + employee.getAddress());
        System.out.println("Do you want to change this? (y/n)");
        if(in.nextLine().equals("y"))
            employee.setAddress(addressValidation(in));

        hospital.updateEmployee(employee);
    }

    private void printEmployeeInfoHelp(){
        System.out.println();
        System.out.println("Usage: e/E/edit | quit/q/Q");
        System.out.println("edit/e/E\t\t\tEdit the employee info");
        System.out.println("quit/q/Q\t\t\tGo back to employee selection");
    }

    private String usernameValidation(Scanner in){
        while (true) {
            System.out.println("*Enter username: ");
            String username = in.nextLine();
            if (username.equals("admin") || hospital.getEmployee(username) != null) {
                System.out.println("Username '" + username + "' is unavailable");
            }
            else if (username.length() > 0 && username.length() <= 30 && username.matches("[A-Za-z0-9]+")) {
                return username;
            }
            else {
                System.out.println("Username must be alphanumeric with 1-30 characters");
            }
        }
    }

    private String ssnValidation(Scanner in){
        while (true) {
            System.out.println("*Enter the SSN (no dashes): ");
            String ssn = in.nextLine();
            if (hospital.getEmployeeBySSN(ssn) != null) {
                System.out.println("An employee with that SSN already exists");
            }
            else if (ssn.length() == 9 && ssn.matches("[0-9]+")) {
                return ssn;
            }
            else {
                System.out.println("SSN must be 9 characters long and only contain numbers");
            }
        }
    }

    private Employee.Type typeValidation(Scanner in){
        while(true) {
            System.out.println("*Enter the employee type (d for doctor, n for nurse, s for support): ");
            String t = in.nextLine();
            switch (t) {
                case "d":
                    return Employee.Type.DOCTOR;
                case "n":
                    return Employee.Type.NURSE;
                case "s":
                    return Employee.Type.SUPPORT;
                default:
                    System.out.println("Please enter a valid type");
            }
        }
    }

    private String specializationValidation(Scanner in){
        while (true) {
            System.out.println("Enter the doctor's specialization: ");
            String special = in.nextLine();
            if (special.length() <= 30) {
                return special;
            }
            else {
                System.out.println("Specialization must be 30 characters or less");
            }
        }
    }
}
