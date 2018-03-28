package Model;
// Patient class containing all of the information for the patient in the hospital

import java.io.File;
import java.util.HashMap;

public class Patient {
    //region fields
    private String patientID;
    private String insuranceID;
    private String firstName;
    private String middleInit;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String roomNumber;
    private String status;
    //endregion

    //region constructors
    Patient(String patientID, String insuranceID, String firstName, String middleInit, String lastName, String phoneNumber, String email, String address, Gender gender, String roomNumber, String status){
        this.patientID = patientID;
        this.insuranceID = insuranceID;
        this.firstName = firstName;
        this.middleInit = middleInit;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    Patient(){
        this.patientID = null;
        this.insuranceID = null;
        this.firstName = null;
        this.middleInit = null;
        this.lastName = null;
        this.phoneNumber = null;
        this.email = null;
        this.gender = null;
        this.roomNumber = null;
        this.status = null;
    }
    //endregion

    //region enums
    public enum Gender{
        M,
        F,
        O;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // endregion


    @Override
    public String toString() {
        return String.format("%30s%15s", firstName + " " + lastName, insuranceID);
    }
}
