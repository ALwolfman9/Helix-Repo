package Model;
// Patient class containing all of the information for the patient in the hospital

import java.io.File;
import java.util.HashMap;

public class Patient extends Person{
    //region fields
    private String patientID;
    private String insuranceID;
    private String firstName;
    private String middleInit;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private Gender gender;
    private String doctor;
    //endregion

    //region constructors
    Patient(String patientID, String insuranceID, String firstName, String middleInit, String lastName,
            String phoneNumber, String email, String address, Gender gender, String doctor){
        this.patientID = patientID;
        this.insuranceID = insuranceID;
        this.firstName = firstName;
        this.middleInit = middleInit;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.doctor = doctor;
    }

    Patient(){
        this.patientID = null;
        this.insuranceID = null;
        this.firstName = null;
        this.middleInit = null;
        this.lastName = null;
        this.phoneNumber = null;
        this.email = null;
        this.address = null;
        this.gender = null;
        this.doctor = null;
    }
    //endregion

    //region properties
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInit() {
        return middleInit;
    }

    public void setMiddleInit(String middleInit) {
        this.middleInit = middleInit;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDoctor(){
        return doctor;
    }

    public void setDoctor(String doctor){
        this.doctor = doctor;
    }
    // endregion


    @Override
    public String toString() {
        return String.format("%4s%30s%15s%30s", patientID, firstName + " " + lastName, insuranceID, doctor);
    }
}
