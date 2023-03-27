public class Patient extends Person{
    //instance variables
    private int uniqueId;

    //constructor
    public Patient(String name, String surname, String dateOfBirth, int mobileNo, int uniqueId) {
        super(name, surname, dateOfBirth, mobileNo);
        this.uniqueId = uniqueId;
    }

    //getters and setters
    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
}
