import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Doctor extends Person implements Serializable {
    //creating instance variables
    private int licenceNo;
    private String specialisation;
    private List<Consultation> schedule = new ArrayList<>();

    //constructor
    public Doctor(String name, String surname, String dateOfBirth, int mobileNo, int licenceNo, String specialisation) {
        super(name, surname, dateOfBirth, mobileNo);
        this.licenceNo = licenceNo;
        this.specialisation = specialisation;
    }


    //getters and setters

    public int getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(int licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public boolean isAvailable(Date dateTime) {
        if (schedule != null) {
            for (Consultation c : schedule) {
                if (c.getDateTime().equals(dateTime)) {
                    return false;
                }
            }
        }
        return true;
    }
}