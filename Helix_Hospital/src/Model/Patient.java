package Model;
// Patient class containing all of the information for the patient in the hospital

import java.io.File;
import java.util.HashMap;

public class Patient {
    private String firstName;
    private String lastName;
    private Gender gender;
    private int id;

    enum Gender{
        M,
        F,
        O;
    }

    public Patient(String firstName, String lastName, Gender gender, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.id = id;
    }


    public String toString(){
        return firstName + lastName;
    }
}
