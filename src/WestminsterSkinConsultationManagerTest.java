import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {

    @Before
    public void setUp() {
        WestminsterSkinConsultationManager westminsterSkinConsultationManager = new WestminsterSkinConsultationManager();
    }

    @Test
    @org.junit.jupiter.api.Test
    public void testAddDoctor_validInput(){
        // Set up test input
        String name = "John";
        String surname = "Doe";
        String licenceNo = "12345SP";
        String dob = "01/01/1970";
        String mobileNo = "0712345678";
        String specialisation = "Medical Dermatology";

        // Call the method under test
/*        WestminsterSkinConsultationManager.addDoctor(name, surname, licenceNo, dob, mobileNo, specialisation);

        // Call the method under test
        WestminsterSkinConsultationManager.addDoctor(name, surname, licenceNo, dob, mobileNo, specialisation);*/

        // Verify the results
/*        assertTrue(WestminsterSkinConsultationManager.docList().containsKey(12345));
        Doctor doctor = WestminsterSkinConsultationManager.docList().get(12345);
        assertEquals(name, doctor.getName());
        assertEquals(surname, doctor.getSurname());
        assertEquals(licenceNo, doctor.getLicenceNo());
        assertEquals(dob, doctor.getDateOfBirth());
        assertEquals(mobileNo, doctor.getMobileNo());
        assertEquals(specialisation, doctor.getSpecialisation());*/
    }



    @org.junit.jupiter.api.Test
    void getaAllDoctors() {
    }

    @org.junit.jupiter.api.Test
    void sortDoctors() {
    }

    @org.junit.jupiter.api.Test
    void sort() {
    }

    @org.junit.jupiter.api.Test
    void removeDoctor() {
    }

    @org.junit.jupiter.api.Test
    void displayMenu() {
    }
}