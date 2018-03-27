package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicalRecord {
    //region fields
    private String username;
    private String patientID;
    private LocalDateTime dateTime;
    private String notes;
    //endregion

    //region constructors
    MedicalRecord(String username, String patientID, LocalDateTime dateTime, String notes){
        this.username = username;
        this.patientID = patientID;
        this.dateTime = dateTime;
        this.notes = notes;
    }
    MedicalRecord(String username, String patientID, String dateTime, String notes){
        this.username = username;
        this.patientID = patientID;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = java.time.LocalDateTime.from(f.parse(dateTime));
        this.notes = notes;
    }
    MedicalRecord(){
        this.username = null;
        this.patientID = null;
        this.dateTime = null;
        this.notes = null;
    }
    //endregion

    //region properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = java.time.LocalDateTime.from(f.parse(dateTime));
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    // endregion
}