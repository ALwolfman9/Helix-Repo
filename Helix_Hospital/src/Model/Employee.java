package Model;

public class Employee extends Person{
    //region fields
    private String username;
    private String firstName;
    private String middleInit;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private Gender gender;
    private String ssn;
    private Type type;
    //endregion

    //region constructors
    Employee(String username, String firstName, String middleInit, String lastName, String phoneNumber, String email, String address, Gender gender, String ssn, Type type){
        this.username = username;
        this.firstName = firstName;
        this.middleInit = middleInit;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.ssn = ssn;
        this.type = type;
    }

    Employee(){
        this.username = "";
        this.type = Type.ERROR;
        this.firstName = "";
        this.middleInit = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.email = "";
        this.address = "";
        this.gender = Gender.O;
        this.ssn = "";
    }
    //endregion

    //region enums
    public enum Type{
        DOCTOR("Doctor"),
        NURSE("Nurse"),
        SUPPORT("Support"),
        ERROR("Error");

        private String type;

        Type(String type){
            this.type = type;
        }

        public String toString(){
            return type;
        }

        public static Type fromString(String s){
            if(s.equals("Doctor")) return Type.DOCTOR;
            if(s.equals("Nurse")) return Type.NURSE;
            if(s.equals("Support")) return Type.SUPPORT;
            else return Type.ERROR;

        }
    }
    //endregion

    //region properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    // endregion


    @Override
    public String toString() {
        return String.format("%30s%15s%15s", firstName + " " + lastName, username, type.toString());
    }
}