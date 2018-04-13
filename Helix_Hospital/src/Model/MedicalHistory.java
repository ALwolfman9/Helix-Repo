package Model;
public class MedicalHistory {
    //region fields
    private String patientID;
    private String bloodType;
    private String allergies;
    private String medications;
    private String pastConditions;
    private String familyHistory;
    //endregion

    //region constructors
    MedicalHistory(String patientID, String bloodType, String allergies, String medications, String pastConditions, String familyHistory){
        this.patientID = patientID;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.medications = medications;
        this.pastConditions = pastConditions;
        this.familyHistory = familyHistory;
    }
    MedicalHistory(){
        this.patientID = null;
        this.bloodType = null;
        this.allergies = null;
        this.medications = null;
        this.pastConditions = null;
        this.familyHistory = null;
    }
    //endregion

    //region properties
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getPastConditions() {
        return pastConditions;
    }

    public void setPastConditions(String pastConditions) {
        this.pastConditions = pastConditions;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }
    // endregion

    public String toString(){
        String view = "Blood Type: " + bloodType
                + "\nAllergies: " + allergies
                + "\nMedications: " + medications
                + "\nPast Conditions: " + pastConditions
                + "\nFamily History: " + familyHistory;
        return view;
    }
}
