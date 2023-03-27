import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    //instance variables
    private static final int MAX_DOCTORS = 10;

    private Map<Integer, Doctor> docList;

    private List<Patient> patients;

    private List<Consultation> consultations;

    private Set<String> takenUniqueIds;
    //constructor
    public WestminsterSkinConsultationManager(){
        this.docList = new HashMap<>();
        this.patients = new ArrayList<>();
        this.consultations = new ArrayList<>();
        this.takenUniqueIds = new HashSet<>();
    }


    //implement SkinConsultationManger methods
    @Override
    public void addDoctor() { //method to add doctor to hashmap
        if (docList.size() < MAX_DOCTORS) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Doctor name : ");
            String name = sc.nextLine();
            while (!name.matches("[a-zA-Z]+")) {   //to validate input as alphabet character
                System.out.println();
                name = sc.nextLine();
            }
            System.out.println("Enter doctor surname : ");
            String surname = sc.nextLine();
            while (!surname.matches("[a-zA-Z]+")) {   //to validate input as alphabet character
                System.out.println("Please enter a valid surname : ");
                surname = sc.nextLine();
            }
            int licenceNo;
            while (true) {
                System.out.println("Enter doctors' licence no: ");
                String stringLicence = sc.nextLine();
                if(stringLicence.matches("^\\d{5}SP$")){ //To validate correct format
                    try {
                        licenceNo = Integer.parseInt(stringLicence.substring(0,5));
                        if(docList.containsKey(licenceNo)){
                            System.out.println("There already exists a doctor with that licence number, please enter a valid license number.");
                        }else{
                            break;
                        }

                    }catch (NumberFormatException e){
                        System.out.println("Error, Invalid Licence no. Enter a valid Licence no.");
                    }
                }
                else {
                    System.out.println("Error, Invalid Licence no. Enter a valid Licence no in the format #####SP.");
                }
            }
            String dob;
            while (true) {
                System.out.print("Enter date of birth (dd/mm/yyyy): ");
                dob = sc.nextLine();
                if (dob.matches("^\\d{2}/\\d{2}/\\d{4}$")) {  //to validate correct date format
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
                            System.out.println("Invalid year, please enter a date of birth between 1960 and 2000.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Invalid date. Please enter a valid date in format dd/MM/yyyy");
                    }
                } else {
                    System.out.println("Error: Invalid format. Please enter the date in the format dd/mm/yyyy.");
                }
            }
            int mobileNo;
            while (true) {
                System.out.println("Enter doctor's mobile no: ");
                String stringMobileNo = sc.nextLine();
                if(stringMobileNo.matches("^07\\d{8}$")) {
                    try {
                        mobileNo = Integer.parseInt(stringMobileNo);
                        break;
                    } catch (NumberFormatException ne) {
                        System.out.println("This is not a valid phone number.");
                    }
                }else System.out.println("This is not a valid phone number. Please enter a ten digit phone number staring with 07.");
            }
            //option to select specialisation
            System.out.println("Please select the doctor's Specialisation from following options;");
            System.out.println("1: Cosmetic Dermatology");
            System.out.println("2: Medical Dermatology");
            System.out.println("3: Paediatric Dermatology");
            int specialisationOption;
            while (true){
                System.out.println("Enter option no: ");
                String specialisationOpStr = sc.nextLine();
                try {
                    specialisationOption = Integer.parseInt(specialisationOpStr);
                    if(specialisationOption >= 1 && specialisationOption <= 3){
                        break;
                    }else {
                        System.out.println("Please enter a valid option.");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Please enter a valid option");
                }
            }
            String specialisation = "";
            switch (specialisationOption){
                case 1-> specialisation = "Cosmetic Dermatology";
                case 2-> specialisation = "Medical Dermatology";
                case 3-> specialisation = "Paediatric Dermatology";
            }
            Doctor d = new Doctor(name, surname, dob, mobileNo, licenceNo, specialisation);
            docList.put(d.getLicenceNo(), d);
            System.out.println("Doctor added successfully.");
        }
        else{
            System.out.println("Maximum number of doctors reached.");
        }
    }


    @Override
    public void getaAllDoctors(){
        ArrayList<Doctor> doctors = sortDoctors();
        if(doctors.size() >= 1){
            for (Doctor d : doctors){
                System.out.println("Surname: " + d.getSurname() + "\nname : " + d.getName() + "\nLicence No.: " +  d.getLicenceNo() + "SP" + "\ndate of Birth: " + d.getDateOfBirth() + "\nmobileNo: " + d.getMobileNo() + "\nSpecialisation : " + d.getSpecialisation() + "\n");
            }
        }
        else System.out.println("There are no available Doctors.");
    }

    @Override
    public ArrayList<Doctor> sortDoctors(){
        ArrayList<Doctor> doctors = new ArrayList<>(); // creating new arraylist to store doctor data
        Collection<Doctor> values = docList.values();  // to get values of hashmap into arraylist
        ArrayList<Doctor> listOfValues = new ArrayList<>(values);
        sort(listOfValues); //to sort alphabetically
        return listOfValues;
    }


    public static void sort(ArrayList<Doctor> list) {

        list.sort(Comparator.comparing(Person::getSurname));
    }




    @Override
    public void removeDoctor() { //method to remove doctor by selecting the key which is the licence number
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter doctors licence no: ");
        String licenceNoStr = sc.nextLine();
        int licenceNo = Integer.parseInt(licenceNoStr.substring(0, 5));
        if (docList.containsKey(licenceNo)) {
            docList.remove(licenceNo);
            System.out.println("Doctor with licence number " + licenceNoStr + " is removed.");
        } else {
            System.out.println("Sorry, doctor with that licence is not found.");
        }
    }



    //methods for console menu
    public static void displayMenu() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        WestminsterSkinConsultationManager westminsterSkinConsultationManager = new WestminsterSkinConsultationManager();
        westminsterSkinConsultationManager.loadData();
        int input = 0;
        do{
            System.out.println("Welcome to Westminster Skin Consultation Manager");
            System.out.println("=======================================================\n");
            System.out.println("Please select from following options: ");
            System.out.println("1 : Add a new doctor");
            System.out.println("2 : Remove a doctor");
            System.out.println("3 : Print list of available doctors");
            System.out.println("4 : Save in a file and exit");
            System.out.println("5 : Open Westminster GUI Application ");
            try{
                input = sc.nextInt();
                switch(input){
                    case 1 -> westminsterSkinConsultationManager.addDoctor();
                    case 2 -> westminsterSkinConsultationManager.removeDoctor();
                    case 3 -> westminsterSkinConsultationManager.getaAllDoctors();
                    case 4 -> {
                        System.out.println("Thank you for using the system!");
                        westminsterSkinConsultationManager.saveData();
                    }
                    case 5 ->{
                        WestminsterGUI gui = new WestminsterGUI(westminsterSkinConsultationManager);
                        gui.show();

                    }

                    default -> System.out.println("invalid option");
                }

            }
            catch (InputMismatchException e){
                System.out.println("invalid option");
                sc.nextLine();
            }
        }while (input != 4);
    }

    @Override
    public void saveData() throws IOException {  //To serialize objects and save in a file
        try(FileOutputStream fos = new FileOutputStream("data.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(docList);
        }
        catch (IOException e) {
            System.out.println("Error, Cannot write to file");
        }
    }

    @Override
    public void loadData() throws IOException, ClassNotFoundException { //To deserialize objects in the file and load data into the console
        try (FileInputStream fis = new FileInputStream("data.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            docList = (Map<Integer, Doctor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error, cannot read form file");
        }
    }

    @Override
    public Doctor getDoctor(String name){
        if(docList.containsValue(name)){
            docList.get(name);
        }
        return null;
    }

    @Override
    public List<Doctor> getaAllDoctors(Date dateTime) {
        return null;
    }

    @Override
    public Patient getPatient(int uniqueId) {
        if (patients != null) {
            for (Patient patient : patients) {
                if (patient.getUniqueId() == uniqueId) {
                    return patient;
                }
            }
        }
        return null;
    }

    @Override
    public void addPatient(Patient patient){
        patients.add(patient);
    }
    @Override
    public boolean addConsultation(Consultation consultation) {
        for (Consultation existingConsultation : consultations) {
            if (existingConsultation.equals(consultation)) {
                return false;
            }
        }
        consultations.add(consultation);
        return true;
    }

    @Override
    public boolean isUniqueIdTaken(String uniqueId) {
        return takenUniqueIds.contains(uniqueId);
    }

}




