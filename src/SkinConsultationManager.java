import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface SkinConsultationManager {


    ArrayList<Doctor> sortDoctors();

    void removeDoctor();



    //implement SkinConsultationManger methods
    void addDoctor();

    void getaAllDoctors();


    void saveData() throws IOException;

    void loadData() throws IOException, ClassNotFoundException;

    Doctor getDoctor(String name);

    List<Doctor> getaAllDoctors(Date dateTime);

    Patient getPatient(int uniqueId);

    void addPatient(Patient patient);

    boolean addConsultation(Consultation consultation);

    boolean isUniqueIdTaken(String uniqueId);
}
