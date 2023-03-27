import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Consultation {
    //instance variables
    private final Date dateTime;
    private double cost;
    private String notes;
    private Patient patient;
    private Doctor doctor;

    //constructor
    public Consultation(Date dateTime, double cost, String notes, Patient patient, Doctor doctor){
        this.dateTime =dateTime;
        this.cost = cost;
        this.notes = notes;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Consultation(Doctor selectedDoctor, Patient patient, Date dateTime) {
        this.doctor = selectedDoctor;
        this.patient = patient;
        this.dateTime = dateTime;
    }

    //getters and setters

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Object getDateTime() {
        return true;
    }
}
