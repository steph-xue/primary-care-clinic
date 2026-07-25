package ui.cli;

import model.Clinic;
import model.ClinicalNote;
import model.Date;
import model.Patient;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class PrimaryCareClinicApp {
    private static final String JSON_STORE = "./data/clinic.json";

    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Clinic clinic;
    private boolean isProgramRunning;
    private boolean isClinicRunning;


    // EFFECTS: starts the PrimaryCareClinic console ui application; initializes the application, welcomes 
    // the user, handles the loading from save file, and displays either the start menu or main menu depending
    // on if a new clinic has already been created/named
    public PrimaryCareClinicApp() {
        init();

        printDivider();
        System.out.println("Welcome to the Primary Care Clinic app!");
        printDivider();

        handleLoadingDataAtStart();

        while (this.isProgramRunning) {
            if (!isClinicRunning) {
                handleStartMenu();
            } else {
                handleMainMenu();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the clinic application with starting values where the program is running 
    // but the clinic is not yet running (new clinic not created/named yet)
    public void init() {
        this.scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.isProgramRunning = true;
        this.isClinicRunning = false;
    }

    // EFFECTS: displays and processes inputs for the start menu (no clinic created yet)
    public void handleStartMenu() {
        displayStartMenu();
        String input = this.scanner.nextLine();
        processStartMenuCommands(input);
    }

    // EFFECTS: displays a list of commands that can be used in the start menu (no clinic created yet)
    public void displayStartMenu() {
        System.out.println("Please select an option:");
        System.out.println("c: Create a new clinic");
        System.out.println("q: Exit the application");
        printDivider();
    }

    // EFFECTS: processes the user's input in the start menu (no clinic created yet)
    public void processStartMenuCommands(String input) {
        switch (input) {
            case "c":
                createNewClinic();
                break;
            case "q":
                quitApplication();
                break;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: creates new clinic with name from the user and sets the clinic as running;
    // prints out a confirmation and welcome message
    public void createNewClinic() {
        printDivider();
        System.out.println("Please enter a name for the clinic:");
        String input = this.scanner.nextLine();
        clinic = new Clinic(input);
        isClinicRunning = true;

        printDivider();
        System.out.print("New clinic \"");
        System.out.print(clinic.getClinicName());
        System.out.print("\" successfully created!\n");
        printDivider();
        System.out.print("*** Welcome to ");
        System.out.print(clinic.getClinicName());
        System.out.print("! *** \n");
    }

    // EFFECTS: displays and processes inputs for the main menu
    public void handleMainMenu() {
        displayMainMenu();
        String input = this.scanner.nextLine();
        processMainMenuCommands(input);
    }

    // EFFECTS: displays a list of commands that can be used in the main menu; the user can add
    // a new patient, view all patients, rename the clinic, or exit the application
    public void displayMainMenu() {
        System.out.println("Please select an option:");
        System.out.println("a: Add a new patient record");
        System.out.println("v: View all patient records");
        System.out.println("r: Rename the clinic");
        System.out.println("q: Exit the application");
        printDivider();
    }

    // EFFECTS: processes the user's input in the main menu
    public void processMainMenuCommands(String input) {
        switch (input) {
            case "a":
                addNewPatientRecord();
                break;
            case "v":
                viewAllPatientRecords();
                break;
            case "r":
                renameClinic();
                break;
            case "q":
                quitApplication();
                break;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: add new patient to patient record list using input from the user for first name, last name,
    // date of birth (DOB), age, personal health number (PHN) and additional information like allergies,
    // medications, and medical conditions; prints confirmation message for adding the patient
    public void addNewPatientRecord() {
        printDivider();
        System.out.println("Adding new patient");
        System.out.println("Please enter the first name: ");
        String firstName = this.scanner.nextLine();
        System.out.println("Please enter the last name: ");
        String lastName = this.scanner.nextLine();
        System.out.println("Please enter the date of birth (month) (e.g. 3 for March): ");
        int monthofBirth = this.scanner.nextInt();
        System.out.println("Please enter the date of birth (day): ");
        int dayofBirth = this.scanner.nextInt();
        System.out.println("Please enter the date of birth (year): ");
        int yearofBirth = this.scanner.nextInt();
        System.out.println("Please enter the age: ");
        int age = this.scanner.nextInt();
        System.out.println("Please enter the 9-digit personal health number (PHN): ");
        long personalHealthNumber = this.scanner.nextLong();
        scanner.nextLine(); 
        Date dateOfBirth = new Date(monthofBirth, dayofBirth, yearofBirth);
        Patient newPatient = new Patient(firstName, lastName, dateOfBirth, age, personalHealthNumber);
        clinic.addPatient(newPatient);
        newPatientConfirmationMessage(newPatient);
        addAdditionalInformation(newPatient);
    }

    // EFFECTS: prints out a new patient added confirmation message
    public void newPatientConfirmationMessage(Patient newPatient) {
        printDivider();
        System.out.print("New patient: ");
        System.out.print(newPatient.getFullName() + "\n");
        System.out.print("Date of birth (DOB): ");
        System.out.print(newPatient.getDateOfBirth().printDate() + "\n");
        System.out.print("Age: ");
        System.out.print(newPatient.getAge() + "\n");
        System.out.print("Personal health number (PHN): ");
        System.out.print(newPatient.getPersonalHealthNumber() + "\n");
        System.out.print("New patient has been successfully created!\n");
    }

    // EFFECTS: add additional information for the new the patient including allergies, medications,
    // and medical conditions 
    public void addAdditionalInformation(Patient newPatient) {
        printDivider();
        System.out.println("Adding allergy, medication, and medical condition information for new patient");

        addAllergies(newPatient);
        addMedications(newPatient);
        addMedicalConditions(newPatient);

        printDivider();
        System.out.print("Successfully added the following for new patient \"");
        System.out.print(newPatient.getFullName() + "\": " + "\n");
        System.out.print("Allergies: ");
        System.out.print(newPatient.printAllergies() + "\n");
        System.out.print("Medications: ");
        System.out.print(newPatient.printMedications() + "\n");
        System.out.print("Medical conditions: ");
        System.out.print(newPatient.printMedicalConditions() + "\n");
    }

    // EFFECTS: displays menu for adding additional allergies for new patient
    public void addAllergies(Patient newPatient) {
        boolean allergiesInputed = false;

        while (!allergiesInputed) {
            printDivider();
            System.out.println("Please select an option to input allergies:");
            System.out.println("a: Add an allergy");
            System.out.println("n: No more known allergies");

            printDivider();
            String input = this.scanner.nextLine();
            allergiesInputed = handleAddAllergies(input, newPatient);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles adding of allergies for new patient into list of allergies based on user input;
    // returns false if user is not done adding allergies and returns true if no more allergies to add
    public boolean handleAddAllergies(String input, Patient newPatient) {
        switch (input) {
            case "a":
                printDivider();
                System.out.println("Add allergy: ");
                String allergy = this.scanner.nextLine();
                newPatient.addAllergy(allergy);
                return false;
            case "n":
                return true;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return false;
        }
    }

    // EFFECTS: displays menu for adding additional medications for new patient
    public void addMedications(Patient newPatient) {
        boolean medicationsInputed = false;

        while (!medicationsInputed) {
            printDivider();
            System.out.println("Please select an option to input medications:");
            System.out.println("a: Add a medication");
            System.out.println("n: No more known medications");
            printDivider();

            String input = this.scanner.nextLine();
            medicationsInputed = handleAddMedications(input, newPatient);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles adding of medications for new patient into list of medications based on user input;
    // returns false if user is not done adding medications and returns true if no more medications to add
    public boolean handleAddMedications(String input, Patient newPatient) {
        switch (input) {
            case "a":
                printDivider();
                System.out.println("Add medication: ");
                String medication = this.scanner.nextLine();
                newPatient.addMedication(medication);
                return false;
            case "n":
                return true;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return false;
        }
    }

    // EFFECTS: displays menu for adding additional medical conditions for new patient
    public void addMedicalConditions(Patient newPatient) {
        boolean medicalConditionsInputed = false;

        while (!medicalConditionsInputed) {
            printDivider();
            System.out.println("Please select an option to input medical conditions:");
            System.out.println("a: Add an medical condition");
            System.out.println("n: No more known medical conditions");
            printDivider();

            String input = this.scanner.nextLine();
            medicalConditionsInputed = handleAddMedicalConditions(input, newPatient);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles adding of medical conditions for new patient into list of medical conditions based 
    // on user input; returns false if user is not done adding medical conditions and returns true if
    // no more medical conditions to add
    public boolean handleAddMedicalConditions(String input, Patient newPatient) {
        switch (input) {
            case "a":
                printDivider();
                System.out.println("Add medical condition: ");
                String medicalCondition = this.scanner.nextLine();
                newPatient.addMedicalCondition(medicalCondition);
                return false;
            case "n":
                return true;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return false;
        }
    }

    // EFFECTS: view all current patient records and handles the view all patients menu
    public void viewAllPatientRecords() {
        printDivider();
        System.out.println("Viewing all patient records (sorted by last name then first name)");

        if (!clinic.getPatients().isEmpty()) {
            handleViewAllPatientsMenu();
        } else {
            System.out.println(clinic.printPatientRecords());
        }
    }

    // EFFECTS: displays and processes inputs for the view all patients menu
    public void handleViewAllPatientsMenu() {
        boolean viewAllPatientsMenu = true;

        while (viewAllPatientsMenu) {
            System.out.println(clinic.printPatientRecords());
            displayViewAllPatientsMenu();
            String input = this.scanner.nextLine();
            viewAllPatientsMenu = processViewPatientMenuCommands(input);
        }
    }

    // EFFECTS: displays a list of commands that can be used in the view all patients menu; the user can
    // type in a number to select to view/edit a specific patient record or go back to the main menu
    public void displayViewAllPatientsMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("# (Index number): View/edit a patient record at this index");
        System.out.println("b: Go back to main menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in the view all patients menu; returns true if
    // user is to continue viewing the view all patients menu and false if going back to 
    // the main menu
    public Boolean processViewPatientMenuCommands(String input) {
        // Check if input is an index number that can be retrieved from the list of patients
        if (input.matches("\\d+") && (Integer.parseInt(input) <= clinic.getPatients().size())) { 
            int index = Integer.parseInt(input);
            Patient patient = clinic.getPatients().get(index - 1);
            handlePatientMenu(patient);
            return true;
        } else {
            switch (input) {
                case "b":
                    return false;
                default:
                    printDivider();
                    System.out.println("Invalid option inputted. Please try again.");
                    return true;
            }
        }
    }

    // EFFECTS: displays and processes inputs for the patient menu
    public void handlePatientMenu(Patient patient) {
        boolean viewPatientMenu = true;

        while (viewPatientMenu) {
            printRetrievedPatientRecord(patient);
            displayPatientMenu();
            String input = this.scanner.nextLine();
            viewPatientMenu = processPatientMenuCommands(input, patient);
        }
    }

    // EFFECTS: prints out details of the selected patient record 
    public void printRetrievedPatientRecord(Patient patient) {
        printDivider();
        System.out.println("Viewing patient record");
        printDivider();
        System.out.print("Patient name: ");
        System.out.print(patient.getFullName() + "\n");
        System.out.print("Date of birth (DOB): ");
        System.out.print(patient.getDateOfBirth().printDate() + "\n");
        System.out.print("Age: ");
        System.out.print(patient.getAge() + "\n");
        System.out.print("Personal health number (PHN): ");
        System.out.print(patient.getPersonalHealthNumber() + "\n");
        System.out.print("Allergies: ");
        System.out.print(patient.printAllergies() + "\n");
        System.out.print("Medications: ");
        System.out.print(patient.printMedications() + "\n");
        System.out.print("Medical conditions: ");
        System.out.print(patient.printMedicalConditions() + "\n");
        System.out.print("Number of clinical notes: ");
        System.out.print(patient.getClinicalNotes().size() + "\n");
    }

    // EFFECTS: displays a list of commands that can be used in the patient menu; the user can
    // remove the patient, edit patient information, add a clinical note, view/edit clinical
    // notes, and go back to the view all patients menu
    public void displayPatientMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("r: Remove patient");
        System.out.println("e: Edit patient information");
        System.out.println("a: Add new clinical note");
        System.out.println("v: View/edit clinical notes");
        System.out.println("b: Go back to view all patients menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in the patient menu; returns true if user is
    // to continue viewing the patient menu and false if going back to the view all 
    // patients menu
    public Boolean processPatientMenuCommands(String input, Patient patient) {
        switch (input) {
            case "r":
                removePatient(patient);
                return false;
            case "e":
                editPatientInformation(patient);
                return true;
            case "a":
                addNewClinicalNote(patient);
                return true;
            case "v":
                viewClinicalNotes(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes patient from clinic's list of patient records
    public void removePatient(Patient patient) {
        if (clinic.removePatient(patient)) {
            printDivider();
            System.out.println("Patient successfully removed!");
        } else {
            printDivider();
            System.out.println("Patient not found in records.");
        }
    }

    // EFFECTS: displays and processes inputs for the editing patient information
    public void editPatientInformation(Patient patient) {
        boolean editPatientMenu = true;

        while (editPatientMenu) {
            displayEditPatientMenu();
            String input = this.scanner.nextLine();
            editPatientMenu = processEditPatientMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used to edit the patient's record; user
    // can edit personal information, edit medical information, or go back to the patient menu
    public void displayEditPatientMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("p: Edit personal information");
        System.out.println("o: Edit allergies/medications/medical conditions");
        System.out.println("b: Go back to view patient menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in the patient menu; returns true if user is
    // to continue viewing the patient editing menu and false if going back to the 
    // view patient menu
    public Boolean processEditPatientMenuCommands(String input, Patient patient) {
        switch (input) {
            case "p":
                editPersonalInformation(patient);
                return true;
            case "o":
                editMedicalInformation(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // EFFECTS: displays and processes inputs for editing the patient's personal information
    public void editPersonalInformation(Patient patient) {
        boolean editPersonalMenu = true;

        while (editPersonalMenu) {
            displayEditPersonalMenu();
            String input = this.scanner.nextLine();
            editPersonalMenu = processEditPersonalMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used to edit the patient's personal information;
    // user can edit the patient's name, date of birth (DOB) and age, personal health number (PHN), or 
    // go back to the patient editing menu
    public void displayEditPersonalMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("n: Edit name");
        System.out.println("d: Edit date of birth (DOB) and age");
        System.out.println("p: Edit personal health number (PHN)");
        System.out.println("b: Go back to view patient editing menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in patient's personal information menu; returns true if user
    // is to continue viewing patient's personal information menu and false if going back to view the 
    // patient editing menu
    public Boolean processEditPersonalMenuCommands(String input, Patient patient) {
        switch (input) {
            case "n":
                editName(patient);
                return true;
            case "d":
                editDateOfBirthAge(patient);
                return true;
            case "p":
                editPersonalHealthNumber(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit the patient's name
    public void editName(Patient patient) {
        printDivider();
        System.out.println("Enter in a new first name: ");
        String firstName = this.scanner.nextLine();
        System.out.println("Enter in a new last name: ");
        String lastName = this.scanner.nextLine();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);

        printDivider();
        System.out.print("Patient's name successfully changed to \"");
        System.out.print(patient.getFullName());
        System.out.print("\"! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit the patient's date of birth and age
    public void editDateOfBirthAge(Patient patient) {
        printDivider();
        System.out.println("Enter in a new date of birth (month) (e.g. 3 for March): ");
        int monthofBirth = this.scanner.nextInt();
        System.out.println("Enter in a new date of birth (day): ");
        int dayofBirth = this.scanner.nextInt();
        System.out.println("Enter in a new date of birth (year): ");
        int yearofBirth = this.scanner.nextInt();
        System.out.println("Enter in a new age: ");
        int age = this.scanner.nextInt();
        scanner.nextLine(); 
        Date dateOfBirth = new Date(monthofBirth, dayofBirth, yearofBirth);
        patient.setDateOfBirth(dateOfBirth);
        patient.setAge(age);
        
        printDivider();

        System.out.print("Patient's date of birth (DOB) and age successfully changed to \"");
        System.out.print(patient.getDateOfBirth().printDate() + " and ");
        System.out.print(patient.getAge());
        System.out.print("\"! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit the patient's personal health number (PHN)
    public void editPersonalHealthNumber(Patient patient) {
        printDivider();
        System.out.println("Enter in a new 9-digit personal health number (PHN): ");
        long input = this.scanner.nextLong();
        scanner.nextLine(); 
        printDivider();

        patient.setPersonalHealthNumber(input);
        System.out.print("Patient's personal health number (PHN) successfully changed to \"");
        System.out.print(patient.getPersonalHealthNumber());
        System.out.print("\"! \n");
    }

    // EFFECTS: displays and processes inputs for editing the patient's medical information
    // including allergies, medications, and medical conditions
    public void editMedicalInformation(Patient patient) {
        boolean editMedicalMenu = true;

        while (editMedicalMenu) {
            displayEditMedicalMenu();
            String input = this.scanner.nextLine();
            editMedicalMenu = processEditMedicalMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used to edit the patient's medical information;
    // user can edit the patient's allergies, medications, and medical conditions or go back to the 
    // patient editing menu
    public void displayEditMedicalMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("a: Edit allergies");
        System.out.println("m: Edit medications");
        System.out.println("c: Edit medical conditions");
        System.out.println("b: Go back to view patient editing menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in patient's medical information menu; returns true if user
    // is to continue viewing patient's medical information menu and false if going back to view the 
    // patient editing menu
    public Boolean processEditMedicalMenuCommands(String input, Patient patient) {
        switch (input) {
            case "a":
                editAllergyInformation(patient);
                return true;
            case "m":
                editMedicationInformation(patient);
                return true;
            case "c":
                editMedicalConditionInformation(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // EFFECTS: displays and processes inputs for editing the patient's allergy information
    public void editAllergyInformation(Patient patient) {
        boolean editAllergyMenu = true;

        while (editAllergyMenu) {
            displayEditAllergyMenu();
            String input = this.scanner.nextLine();
            editAllergyMenu = processEditAllergyMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used to edit the patient's allergy information;
    // user can add, remove, or edit (replace) an existing allergy
    public void displayEditAllergyMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("a: Add allergy");
        System.out.println("r: Remove allergy");
        System.out.println("e: Edit (replace) existing allergy");
        System.out.println("b: Go back to view patient medical information menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in patient's allergy information menu; returns true if user
    // is to continue viewing patient's allergy information menu and false if going back to view the 
    // patient medical information menu
    public Boolean processEditAllergyMenuCommands(String input, Patient patient) {
        switch (input) {
            case "a":
                addAllergy(patient);
                return true;
            case "r":
                removeAllergy(patient);
                return true;
            case "e":
                replaceAllergy(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a new allergy
    public void addAllergy(Patient patient) {
        printDivider();
        System.out.println("Add a new allergy: ");
        String input = this.scanner.nextLine();
        patient.addAllergy(input);

        printDivider();
        String allergy = patient.getAllergies().get(patient.getAllergies().size() - 1);
        System.out.print("New allergy \"");
        System.out.print(allergy);
        System.out.print("\" added! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove an allergy if in list of allergies;
    // allergy to remove should be in lowercase
    public void removeAllergy(Patient patient) {
        printDivider();
        System.out.print("Current allergies: ");
        System.out.print(patient.printAllergies() + "\n");

        printDivider();
        System.out.println("Remove an allergy (lowercase): ");
        String input = this.scanner.nextLine();
        printDivider();

        if (patient.removeAllergy(input)) {
            System.out.println("Allergy removed!");
        } else {
            System.out.println("Allergy not found in list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to replace an allergy with a new allergy if in list of allergies
    // allergy to replace should be in lowercase
    public void replaceAllergy(Patient patient) {
        printDivider();
        System.out.print("Current allergies: ");
        System.out.print(patient.printAllergies() + "\n");

        printDivider();
        System.out.println("Replace this allergy (lowercase): ");
        String oldAllergy = this.scanner.nextLine();
        System.out.println("New allergy: ");
        String newAllergy = this.scanner.nextLine();
        printDivider();

        if (patient.editAllergy(oldAllergy, newAllergy)) {
            System.out.println("Allergy replaced!");
        } else {
            System.out.println("Allergy not found in list!");
        }
    }

    // EFFECTS: displays and processes inputs for editing the patient's medication information
    public void editMedicationInformation(Patient patient) {
        boolean editMedicationMenu = true;

        while (editMedicationMenu) {
            displayEditMedicationMenu();
            String input = this.scanner.nextLine();
            editMedicationMenu = processEditMedicationMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used to edit the patient's medication information;
    // user can add, remove, or edit (replace) an existing medication
    public void displayEditMedicationMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("a: Add medication");
        System.out.println("r: Remove medication");
        System.out.println("e: Edit (replace) existing medication");
        System.out.println("b: Go back to view patient medical information menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in patient's medication information menu; returns true if user
    // is to continue viewing patient's medication information menu and false if going back to view the 
    // patient medical information menu
    public Boolean processEditMedicationMenuCommands(String input, Patient patient) {
        switch (input) {
            case "a":
                addMedication(patient);
                return true;
            case "r":
                removeMedication(patient);
                return true;
            case "e":
                replaceMedication(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a new medication
    public void addMedication(Patient patient) {
        printDivider();
        System.out.println("Add a new medication: ");
        String input = this.scanner.nextLine();
        patient.addMedication(input);

        printDivider();
        String medication = patient.getMedications().get(patient.getMedications().size() - 1);
        System.out.print("New medication \"");
        System.out.print(medication);
        System.out.print("\" added! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove a medication if in list of medications
    // medication to remove should be in lowercase
    public void removeMedication(Patient patient) {
        printDivider();
        System.out.print("Current medications: ");
        System.out.print(patient.printMedications() + "\n");

        printDivider();
        System.out.println("Remove a medication (lowercase): ");
        String input = this.scanner.nextLine();
        printDivider();

        if (patient.removeMedication(input)) {
            System.out.println("Medication removed!");
        } else {
            System.out.println("Medication not found in list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to replace an medication with a new medication if in list of medications
    // medication to replace should be in lowercase
    public void replaceMedication(Patient patient) {
        printDivider();
        System.out.print("Current medications: ");
        System.out.print(patient.printMedications() + "\n");

        printDivider();
        System.out.println("Replace this medication (lowercase): ");
        String oldMedication = this.scanner.nextLine();
        System.out.println("New medication: ");
        String newMedication = this.scanner.nextLine();
        printDivider();

        if (patient.editMedication(oldMedication, newMedication)) {
            System.out.println("Medication replaced!");
        } else {
            System.out.println("Medication not found in list!");
        }
    }

    // EFFECTS: displays and processes inputs for editing the patient's medical condition information
    public void editMedicalConditionInformation(Patient patient) {
        boolean editMedicalConditionMenu = true;

        while (editMedicalConditionMenu) {
            displayEditMedicalConditionMenu();
            String input = this.scanner.nextLine();
            editMedicalConditionMenu = processEditMedicalConditionMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used to edit the patient's medical condition information;
    // user can add, remove, or edit (replace) an existing medical condition
    public void displayEditMedicalConditionMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("a: Add medical condition");
        System.out.println("r: Remove medical condition");
        System.out.println("e: Edit (replace) existing medical condition");
        System.out.println("b: Go back to view patient medical information menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in patient's medical condition information menu; returns true if user
    // is to continue viewing patient's medical condition information menu and false if going back to view the 
    // patient medical information menu
    public Boolean processEditMedicalConditionMenuCommands(String input, Patient patient) {
        switch (input) {
            case "a":
                addMedicalCondition(patient);
                return true;
            case "r":
                removeMedicalCondition(patient);
                return true;
            case "e":
                replaceMedicalCondition(patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a new medical condition
    public void addMedicalCondition(Patient patient) {
        printDivider();
        System.out.println("Add a new medical condition: ");
        String input = this.scanner.nextLine();
        patient.addMedicalCondition(input);

        printDivider();
        String medicalCondition = patient.getMedicalConditions().get(patient.getMedicalConditions().size() - 1);
        System.out.print("New medical condition \"");
        System.out.print(medicalCondition);
        System.out.print("\" added! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove an medical condition if in list of medical conditions
    // medical condition to remove should be in lowercase
    public void removeMedicalCondition(Patient patient) {
        printDivider();
        System.out.print("Current medical conditions: ");
        System.out.print(patient.printMedicalConditions() + "\n");

        printDivider();
        System.out.println("Remove an medical condition (lowercase): ");
        String input = this.scanner.nextLine();
        printDivider();

        if (patient.removeMedicalCondition(input)) {
            System.out.println("Medical condition removed!");
        } else {
            System.out.println("Medical condition not found in list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to replace an medical condition with a new medical condition if in 
    // list of medical conditions; medical condition to replace should be in lowercase
    public void replaceMedicalCondition(Patient patient) {
        printDivider();
        System.out.print("Current medical conditions: ");
        System.out.print(patient.printMedicalConditions() + "\n");

        printDivider();
        System.out.println("Replace this medical condition (lowercase): ");
        String oldMedicalCondition = this.scanner.nextLine();
        System.out.println("New medical condition: ");
        String newMedicalCondition = this.scanner.nextLine();
        printDivider();

        if (patient.editMedicalCondition(oldMedicalCondition, newMedicalCondition)) {
            System.out.println("Medical condition replaced!");
        } else {
            System.out.println("Medical condition not found in list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds new clinical note to patient's list of clinical notes based on the user's input
    // including the title, body, healthcare provider's name, and current date details
    public void addNewClinicalNote(Patient patient) {
        printDivider();
        System.out.print("Adding new clinical note for patient \"");
        System.out.print(patient.getFullName());
        System.out.print("\" \n");
        printDivider();

        System.out.println("Enter a title: ");
        String title = this.scanner.nextLine();
        System.out.println("Enter clinical note details: ");
        String body = this.scanner.nextLine();
        System.out.println("Enter healthcare provider's name: ");
        String name = this.scanner.nextLine();

        LocalDate today = LocalDate.now();
        int year = today.getYear();  
        int month = today.getMonthValue();
        int day = today.getDayOfMonth(); 

        Date date = new Date(month, day, year);
        ClinicalNote note = new ClinicalNote(title, body, name, date);
        patient.addClinicalNote(note);
        newClinicalNoteConfirmationMessage(patient, note);
    }

    // EFFECTS: prints out confirmation message for new clinical note added
    public void newClinicalNoteConfirmationMessage(Patient patient, ClinicalNote note) {
        printDivider();
        System.out.println("Clinical note added succesfully with the following details");
        System.out.print("for patient \"");
        System.out.print(patient.getFullName());
        System.out.print("\": \n");

        System.out.print("Title: ");
        System.out.print(note.getClinicalNoteTitle() + "\n");
        System.out.print("Body: ");
        System.out.print(note.getClinicalNoteBody() + "\n");
        System.out.print("Healthcare provider: ");
        System.out.print(note.getClinicalNoteProvider() + "\n");
        System.out.print("Date: ");
        System.out.print(note.getClinicalNoteDate().printDate() + "\n");
    }

    // EFFECTS: view all clinical notes for selected patient and handles the view all 
    // clinical notes menu options
    public void viewClinicalNotes(Patient patient) {
        printDivider();
        System.out.print("Viewing all clinical notes for patient \"");
        System.out.print(patient.getFullName());
        System.out.print("\": \n");

        if (!patient.getClinicalNotes().isEmpty()) {
            handleViewAllClinicalNotesMenu(patient);
        } else {
            System.out.println(patient.printClinicalNotes());
        }
    }

    // EFFECTS: displays and processes inputs for the view all clinical notes menu
    public void handleViewAllClinicalNotesMenu(Patient patient) {
        boolean viewAllNotesMenu = true;

        while (viewAllNotesMenu) {
            System.out.println(patient.printClinicalNotes());
            displayViewAllClinicalNotesMenu();
            String input = this.scanner.nextLine();
            viewAllNotesMenu = processViewClinicalNotesMenuCommands(input, patient);
        }
    }

    // EFFECTS: displays a list of commands that can be used in the view all clinical notes menu; the user
    // can type in a number to select to view/edit a specific clinical note or go back to the patient menu
    public void displayViewAllClinicalNotesMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("# (Index number): View/edit a clinical note at this index");
        System.out.println("b: Go back to patient menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in the view all clinical notes menu; returns true if
    // user is to continue viewing the view all clinical nnotes menu and false if going back to 
    // the patient menu
    public Boolean processViewClinicalNotesMenuCommands(String input, Patient patient) {
        // Check if input is an index number that can be retrieved from the list of clinical notes
        if (input.matches("\\d+") && (Integer.parseInt(input) <= patient.getClinicalNotes().size())) { 
            int index = Integer.parseInt(input);
            ClinicalNote note = patient.getClinicalNotes().get(index - 1);
            handleClinicalNoteMenu(note, patient);
            return true;
        } else {
            switch (input) {
                case "b":
                    return false;
                default:
                    printDivider();
                    System.out.println("Invalid option inputted. Please try again.");
                    return true;
            }
        }
    }

    // EFFECTS: displays and processes inputs for the clinical note menu
    public void handleClinicalNoteMenu(ClinicalNote note, Patient patient) {
        boolean viewClinicalNoteMenu = true;

        while (viewClinicalNoteMenu) {
            printRetrievedClinicalNote(note, patient);
            displayClinicalNoteMenu();
            String input = this.scanner.nextLine();
            viewClinicalNoteMenu = processClinicalNoteMenuCommands(input, note, patient);
        }
    }

    // EFFECTS: prints out details of the selected patient clinical note
    public void printRetrievedClinicalNote(ClinicalNote note, Patient patient) {
        printDivider();
        System.out.print("Viewing selected clinical note for patient \"");
        System.out.print(patient.getFullName());
        System.out.print("\" \n");
        printDivider();

        System.out.print("Title: ");
        System.out.print(note.getClinicalNoteTitle() + "\n");
        System.out.print("Body: ");
        System.out.print(note.getClinicalNoteBody() + "\n");
        System.out.print("Healthcare provider: ");
        System.out.print(note.getClinicalNoteProvider() + "\n");
        System.out.print("Date: ");
        System.out.print(note.getClinicalNoteDate().printDate() + "\n");
    }

    // EFFECTS: displays a list of commands that can be used in the clinical note menu; the user can
    // remove the note, or edit the title, body, and healthcare provider of the clinical note
    public void displayClinicalNoteMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("r: Remove clinical note");
        System.out.println("t: Edit title");
        System.out.println("i: Edit body information");
        System.out.println("h: Edit healthcare provider");
        System.out.println("b: Go back to view all clinical notes menu");
        printDivider();
    }

    // EFFECTS: processes the user's input in the clinical note menu; returns true if user is
    // to continue viewing the clinical note menu and false if going back to the view all 
    // clinical notes menu
    public Boolean processClinicalNoteMenuCommands(String input, ClinicalNote note, Patient patient) {
        switch (input) {
            case "r":
                removeClinicalNote(note, patient);
                return false;
            case "t":
                editTitle(note, patient);
                return true;
            case "i":
                editBody(note, patient);
                return true;
            case "h":
                editHealthCareProvider(note, patient);
                return true;
            case "b":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes clinical note from patient's list of clinical notes 
    public void removeClinicalNote(ClinicalNote note, Patient patient) {
        if (patient.removeClinicalNote(note)) {
            printDivider();
            System.out.println("Clinical note successfully removed!");
        } else {
            printDivider();
            System.out.println("Clinical note not found in patient record.");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit (replace) the title of the clinical note
    public void editTitle(ClinicalNote note, Patient patient) {
        printDivider();
        System.out.println("Enter in a new title: ");
        String input = this.scanner.nextLine();
        note.setClinicalNoteTitle(input);

        printDivider();
        System.out.print("Clinical note title successfully changed to \"");
        System.out.print(note.getClinicalNoteTitle());
        System.out.print("\"! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit (replace) the body of the clinical note
    public void editBody(ClinicalNote note, Patient patient) {
        printDivider();
        System.out.println("Enter in new body details: ");
        String input = this.scanner.nextLine();
        note.setClinicalNoteBody(input);

        printDivider();
        System.out.print("Clinical note body successfully changed to \"");
        System.out.print(note.getClinicalNoteBody());
        System.out.print("\"! \n");
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit (replace) the healthcare provider of the clinical note
    public void editHealthCareProvider(ClinicalNote note, Patient patient) {
        printDivider();
        System.out.println("Enter in a new healthcare provider: ");
        String input = this.scanner.nextLine();
        note.setClinicalNoteProvider(input);

        printDivider();
        System.out.print("Clinical note healthcare provider successfully changed to \"");
        System.out.print(note.getClinicalNoteProvider());
        System.out.print("\"! \n");
    }

    // MODIFIES: this
    // EFFECTS: rename the clinic using input from the user and print out a success message
    public void renameClinic() {
        printDivider();
        System.out.println("Please enter a new name for the clinic:");
        String input = this.scanner.nextLine();
        clinic.setClinicName(input);

        printDivider();
        System.out.print("Clinic renamed to \"");
        System.out.print(clinic.getClinicName());
        System.out.print("\" successfully!\n");
        printDivider();
        System.out.print("*** Welcome to ");
        System.out.print(clinic.getClinicName());
        System.out.print("! *** \n");
    }

    // EFFECTS: handles loading of saved data upon opening clinic application; allows the user to choose if 
    // they want to load existing saved clinic data or start a new application file
    public void handleLoadingDataAtStart() {
        displayLoadingMenu();
        String input = this.scanner.nextLine();
        processLoadingMenu(input);
    }

    // EFFECTS: displays a list of commands that can be used in the loading menu so the user can choose if 
    // they want to load existing saved clinic data or start a new application file
    public void displayLoadingMenu() {
        System.out.println("Please select an option:");
        System.out.println("l: Load previous existing saved clinic data");
        System.out.println("n: Start a new clinic application");
        printDivider();
    }

    // EFFECTS: processes the user's input for if they want to load existing saved clinic data or start
    // a new application file
    public void processLoadingMenu(String input) {
        switch (input) {
            case "l":
                loadClinic();
                printWelcomeMessage();
                isClinicRunning = true;
                break;
            case "n":
                break;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: loads clinic from file
    private void loadClinic() {
        try {
            clinic = jsonReader.read();
            printDivider();
            System.out.println("Loaded \"" + clinic.getClinicName() + "\" from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void printWelcomeMessage() {
        printDivider();
        System.out.print("*** Welcome to ");
        System.out.print(clinic.getClinicName());
        System.out.print("! *** \n");
    }

    // MODIFIES: this
    // EFFECTS: handles quitting of the clinic application; allows the user to choose if they want to save the existing
    // clinic data, prints a quitting application message, and marks the program and clinic as not running
    public void quitApplication() {

        boolean showQuitSaveMenu = true;

        while (showQuitSaveMenu) {
            displayQuitSaveMenu();
            String input = this.scanner.nextLine();
            showQuitSaveMenu = processQuitSaveMenu(input);
        }

        printDivider();
        System.out.println("Thanks for using the Primary Care Clinic app!");
        this.isProgramRunning = false;
        this.isClinicRunning = false;
    }

    // EFFECTS: displays a list of commands that can be used in the quitting menu so the user can choose if 
    // they want to save the existing clinic data before quitting the application
    public void displayQuitSaveMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("s: Save existing clinic data");
        System.out.println("n: Exit the application without saving");
        printDivider();
    }

    // EFFECTS: processes the user's input for if they want to save clinic data before quitting the application
    public Boolean processQuitSaveMenu(String input) {
        switch (input) {
            case "s":
                saveClinic();
                return false;
            case "n":
                return false;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // EFFECTS: saves the clinic data to file
    private void saveClinic() {
        try {
            jsonWriter.open();
            jsonWriter.write(clinic);
            jsonWriter.close();
            printDivider();
            System.out.println("Saved " + clinic.getClinicName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("---------------------------------------------------------------------------------");
    }
}
