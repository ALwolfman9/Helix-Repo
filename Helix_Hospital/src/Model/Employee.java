package Model;

public class Employee {
    private Type type;

    public enum Type{
        DOCTOR,
        NURSE,
        SUPPORT;
    }

    public enum Gender{
        M,
        F,
        O;
    }
    public Type getType() {
        return type;
    }
}
