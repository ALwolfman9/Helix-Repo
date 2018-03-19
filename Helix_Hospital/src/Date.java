public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) throws IllegalArgumentException{
        if(isValid(day, month, year)){
            this.day = day;
            this.month = month;
            this.year = year;
        }
        else{
            throw new IllegalArgumentException("Date invalid, try again.");
        }
    }

    private boolean isValid(int day, int month, int year){
        if(day > 31){
            throw new IllegalArgumentException("Invalid number for days.");
        }
        else if(month > 12){
            throw new IllegalArgumentException("Invalid number for months.");
        }
        //TODO check for leap year / other stuff

        else {
            return true;
        }
    }
}
