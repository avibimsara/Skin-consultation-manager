import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class WestminsterGUI {
    private final WestminsterSkinConsultationManager westminsterSkinConsultationManager;
    private final JFrame frame;
    private final JTable table;
    private final DefaultTableModel model;
    private List<Doctor> doctors;


    public WestminsterGUI(WestminsterSkinConsultationManager westminsterSkinConsultationManager){
        this.westminsterSkinConsultationManager = westminsterSkinConsultationManager;
        this.frame = new JFrame("Westminster Skin Consultation");
        this.model = new DefaultTableModel(new String[] {"Name", "Surname", "LicenceNo", "Date of Birth", "Mobile No", "Specialisation"}, 0 );
        this.table = new JTable(model);
        this.doctors = new ArrayList<>();
    }

    public void show() throws IndexOutOfBoundsException{


        //Creating buttons
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());

        JButton showDoctorsButton = new JButton("Show Doctors");
        showDoctorsButton.addActionListener(e -> showDoctors());

        //creating panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(refreshButton);
        buttonPanel.add(showDoctorsButton);

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        buttonPanel.add(scrollPane);

        //button to book consultation
        JButton bookButton = new JButton("Book Consultation");
        bookButton.addActionListener(e -> {

            // Get the selected doctor
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a doctor to add a consultation.");
                return;
            }
            if (doctors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are no doctors in the system.");
                return;
            }
            Doctor selectedDoctor = doctors.get(selectedRow);
            //entering patient data
            String name;
            while (true) {
                name = JOptionPane.showInputDialog(frame, "Enter patient name");
                if (name != null && name.matches("[a-zA-Z]+")) {
                    //name is a string
                    break;
                }
                JOptionPane.showMessageDialog(null, "Invalid patient name.");
            }
            String surname;
            while (true) {
                surname = JOptionPane.showInputDialog(frame, "Enter patient surname");
                if (surname != null && surname.matches("[a-zA-Z]+")) {
                    //name is a string
                    break;
                }
                JOptionPane.showMessageDialog(null, "Invalid patient surname.");
            }
            String dob;
            while (true) {
                dob = JOptionPane.showInputDialog(frame, "Enter date of birth in format (dd/MM/yyyy");
                if (dob!= null && dob.matches("^\\d{2}/\\d{2}/\\d{4}$")) {  //to validate correct date format
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        sdf.setLenient(false);
                        Date date = sdf.parse(dob);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        if(year >= 1960 && year <= 2000){
                            break;
                        } else{
                            JOptionPane.showMessageDialog(null, "Invalid year, please enter a date of birth between 1960 and 2000.");
                        }
                    } catch (ParseException er) {
                        JOptionPane.showMessageDialog(null, "Invalid date. Please enter a valid date in format dd/MM/yyyy");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Invalid format. Please enter the date in the format dd/mm/yyyy.");
                }
            }
            int mobileNo;
            while (true) {
                String StrMobileNo = JOptionPane.showInputDialog(frame, "Enter patient mobile number in the format 07XXXXXXXX");
                if(StrMobileNo.matches("^07\\d{8}$")){
                    try {
                        mobileNo = Integer.parseInt(StrMobileNo);
                        break;
                    }catch (NumberFormatException er){
                        JOptionPane.showMessageDialog(null, "Invalid mobile number. Please enter a number starting with 07 and 8 more digits.");
                    }
                }
                JOptionPane.showMessageDialog(null, "Invalid mobile number. Please enter a number starting with 07 and 8 more digits.");
            }
            int uniqueId = 0;
            while (true) {
                String strUniqueId = JOptionPane.showInputDialog(frame, "Enter patient unique ID (5 digits)");
                if (strUniqueId != null && strUniqueId.matches("^\\d{5}$")) {  //to validate correct unique ID format
                    // Check if the unique ID is already taken by another patient
                    if (westminsterSkinConsultationManager.isUniqueIdTaken(strUniqueId)) {
                        JOptionPane.showMessageDialog(null, "The entered unique ID is already taken. Please enter a different unique ID.");
                    } else {
                        break;
                    }
                    uniqueId = Integer.parseInt(strUniqueId);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid unique ID. Please enter a number that is exactly 5 digits long.");
                }
            }

            String dateTimeString = JOptionPane.showInputDialog("Enter consultation date and time:");
            if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid date and time.");
                return;
            }
            Date dateTime = null;
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try{
                dateTime = df.parse(dateTimeString);
            } catch (ParseException er) {
                JOptionPane.showMessageDialog(null, "Invalid date and time format.");
            }
            Patient patient = westminsterSkinConsultationManager.getPatient(uniqueId);
            if (patient == null) {
                patient = new Patient(name, surname, dob, mobileNo, uniqueId);
                westminsterSkinConsultationManager.addPatient(patient);
            }
            if(selectedDoctor.isAvailable(dateTime)){
                if(westminsterSkinConsultationManager.getPatient(uniqueId) == null){
                    JOptionPane.showMessageDialog(null, "No patients available.");
                    return;
                }
                Consultation consultation = new Consultation(selectedDoctor, patient, dateTime);
                if(westminsterSkinConsultationManager.addConsultation(consultation)){
                    JOptionPane.showMessageDialog(frame, "Consultation booked successfully with doctor " + selectedDoctor.getName() + " " + selectedDoctor.getSurname());
                }else{
                    JOptionPane.showMessageDialog(frame, "Consultation already exists.");
                }
            }else {
                List<Doctor> availableDoctors = westminsterSkinConsultationManager.getaAllDoctors(dateTime);
                if(availableDoctors.isEmpty()){ // to check if there are no available doctors
                    JOptionPane.showMessageDialog(frame, "No doctors available at given time");
                }else{      //if doctors available, select random doctor and add that doctor to consultation
                    Random random = new Random();
                    int index = random.nextInt(availableDoctors.size());
                    Doctor availableDoctor = availableDoctors.get(index);
                    Consultation consultation = new Consultation(availableDoctor, patient, dateTime);
                    if(westminsterSkinConsultationManager.addConsultation(consultation)) {
                        JOptionPane.showMessageDialog(frame, "Consultation booked successfully with doctor " + availableDoctor.getName() + " " + availableDoctor.getSurname());
                    }else{
                        JOptionPane.showMessageDialog(frame, "Consultation already exists.");
                    }

                }
            }

        });
        buttonPanel.add(bookButton);

        //adding table and button panel to frame
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //set frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null); //center on screen
        frame.setVisible(true);
    }

    private void showDoctors() {
        model.setRowCount(0);
        doctors = westminsterSkinConsultationManager.sortDoctors();
        for (Doctor doctor : doctors) {
            model.addRow(new Object[] {doctor.getName(), doctor.getSurname(), doctor.getLicenceNo(), doctor.getDateOfBirth(), doctor.getMobileNo(), doctor.getSpecialisation()});
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
    }

}
