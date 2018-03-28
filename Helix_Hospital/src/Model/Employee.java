package Model;

public class Employee {
    //region fields
    private String username;
    private String firstName;
    private char middleInit;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private Gender gender;
    private String ssn;
    private Type type;
    //endregion

    //region constructors
    Employee(String username, String firstName, char middleInit, String lastName, String phoneNumber, String email, String address, Gender gender, String ssn, Type type){
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
        this.username = null;
        this.type = null;
        this.firstName = null;
        this.middleInit = '\u0000';
        this.lastName = null;
        this.email = null;
        this.address = null;
        this.gender = null;
        this.ssn = null;
        this.type = null;
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

    public enum Gender{
        M,
        F,
        O;
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

    public char getMiddleInit() {
        return middleInit;
    }

    public void setMiddleInit(char middleInit) {
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
}