import java.io.Serializable;

public abstract class Person implements Serializable {
    //creating instance variables
    private String name;
    private String surname;
    private String dateOfBirth;
    private int mobileNo;

    //creating constructor for Person class
    public Person(String name, String surname, String dateOfBirth, int mobileNo){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mobileNo = mobileNo;
    }

    //creating getters and setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(int mobileNo) {
        this.mobileNo = mobileNo;
    }
}
