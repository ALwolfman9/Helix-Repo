package Interface;

import Model.Employee;
import Model.Hospital;
import Model.Patient;

import java.util.Iterator;

public abstract class CommandLineUser extends CommandLine{

    Employee user;

    public CommandLineUser(Hospital hospital, Employee user){
        super(hospital);
        this.user = user;
    }

    void viewPatients(){
        Iterator<Patient> patients = hospital.getAllPatients();
        System.out.println(String.format("          %30s%15s", "Name", "InsuranceID"));
        while(patients.hasNext()){
            System.out.println("Patient:" + patients.next().toString());
        }
    }

}
