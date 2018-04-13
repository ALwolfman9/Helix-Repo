package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    static DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //region fields
    private String username;
    private String patientID;
    private LocalDateTime dateTime;
    private String reasonForVisit;
    //endregion

    //region constructors
    Appointment(String username, String patientID, LocalDateTime dateTime, String reasonForVisit){
        this.username = username;
        this.patientID = patientID;
        this.dateTime = dateTime;
        this.reasonForVisit = reasonForVisit;
    }
    Appointment(String username, String patientID, String dateTime, String reasonForVisit){
        this.username = username;
        this.patientID = patientID;
        this.dateTime = java.time.LocalDateTime.from(f.parse(dateTime));
        this.reasonForVisit = reasonForVisit;
    }
    Appointment(){
        this.username = null;
        this.patientID = null;
        this.dateTime = null;
        this.reasonForVisit = null;
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

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }
    // endregion

    public String patientView(boolean showReason){
        String view = "Doctor: " + username + " | Time " + dateTime.format(f);
        if(showReason){
            view = view.concat("\nReason: " + reasonForVisit);
        }
        return view;
    }

    public String doctorView(boolean showReason){
        String view = "PatientID: " + patientID + " | Time " + dateTime.format(f);
        if(showReason){
            view = view.concat("\nReason: " + reasonForVisit);
        }
        return view;
    }
}
