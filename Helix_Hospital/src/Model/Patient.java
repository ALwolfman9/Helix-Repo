package Model;
// Patient class containing all of the information for the patient in the hospital

import java.io.File;
import java.util.HashMap;

public class Patient {
    private String name;
    private String phoneNumber;
    private String eMail;
    private Gender gender;
    private String SSN;
    private HashMap<String, Prescription> prescriptions;

    enum Gender{
        M,
        F;
    }

    public Patient(String name, String phoneNumber, String eMail, Gender gender, String SSN){
        this.name = name;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.SSN = SSN;
    }

    public Patient(File file){

    }

    public void importPrescriptions(File file){
        //populate prescriptions
    }

    public String toString(){
        return name;
    }
}
