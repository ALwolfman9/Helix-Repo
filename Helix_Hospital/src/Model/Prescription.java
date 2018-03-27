package Model;
public class Prescription {
    //region fields
    private String patientID;
    private String prescriptionID;
    private String drugName;
    private String dosage;
    private String duration;
    //endregion

    //region constructors
    Prescription(String patientID, String prescriptionID, String drugName, String dosage, String duration){
        this.patientID = patientID;
        this.prescriptionID = prescriptionID;
        this.drugName = drugName;
        this.dosage = dosage;
        this.duration = duration;
    }
    Prescription(){
        this.patientID = null;
        this.prescriptionID = null;
        this.drugName = null;
        this.dosage = null;
        this.duration = null;
    }
    //endregion

    //region properties
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    // endregion
}
