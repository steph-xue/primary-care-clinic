package ui.gui;

import model.Clinic;
import model.Patient;
import model.Event;
import model.EventLog;

import persistence.DataInitializer;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

// References
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html 
// https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
// https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html 
// https://docs.oracle.com/javase/8/docs/api/java/awt/event/MouseEvent.html
// https://docs.oracle.com/javase/8/docs/api/java/awt/event/MouseAdapter.html
// https://codehs.com/tutorial/david/java-swing-mouse-events
// https://www.tutorialspoint.com/swing/swing_mouseadapter.htm
// Java GUI: Full Course https://www.youtube.com/watch?v=Kmgo00avvEw
// Code referenced from SpaceInvaders, RobustTrafficLights, AlarmSystem, DrawingPlayer, SmartHome

// Image References
// JOptionPane Health Logo /images/health.jpg retrieved from https://www.freepik.com/premium-vector/
//basic-healthcare-icon-vector-image-can-be-used-home-services_157661598.html

// MainUI displays the main frame and primary contents of the primary care clinic application
public class MainUI extends JFrame implements WindowListener {
    private static final String JSON_STORE = System.getProperty("user.home") + File.separator
            + ".primarycareclinic" + File.separator + "clinic.json";
    private static final String SAMPLE_DATA_RESOURCE = "/data/sampleClinic.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Clinic clinic;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoadingScreenUI loadingScreen;
    private StartScreenUI startScreen;
    private ViewPatientsUI viewPatientsScreen;
    private AddPatientUI addPatientScreen;
    private SaveQuitUI saveQuitScreen;
    private ViewPatientProfileUI viewPatientProfileScreen;
    private AddClinicalNoteUI addClinicaNoteScreen;

    // EFFECTS: Constructs the MainUI JFrame for the main application
    public MainUI() {
        init();
        setupLayout();
        startApp();
    }

    // MODIFIES: this
    // EFFECTS: Initializes the primary care clinic application UI with specific settings;
    // enable data persistance with JSON reader and writer
    public void init() {
        setTitle("Primary Care Clinic Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        addWindowListener(this);

        try {
            DataInitializer.initializeIfMissing(JSON_STORE, SAMPLE_DATA_RESOURCE);
        } catch (IOException e) {
            System.err.println("Unable to initialize sample clinic data: " + e.getMessage());
        }

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        clinic = new Clinic("My Clinic");
    }

    // MODIFIES: this
    // EFFECTS: Sets up the card layout, main panel, loading screen, start screen, view patients screen, add 
    // patient screen, and the save/quit screen; all screens are added to the main panel in card layout
    public void setupLayout() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        loadingScreen = new LoadingScreenUI();
        startScreen = new StartScreenUI(this);
        viewPatientsScreen = new ViewPatientsUI(this, clinic);
        addPatientScreen = new AddPatientUI(this, clinic);
        saveQuitScreen = new SaveQuitUI(this);
        mainPanel.add(loadingScreen, "loading");
        mainPanel.add(startScreen, "start");
        mainPanel.add(viewPatientsScreen, "patients");
        mainPanel.add(addPatientScreen, "add patient");
        mainPanel.add(saveQuitScreen, "save and quit");

        add(mainPanel);
        setVisible(true);
    }
    
    // EFFECTS: Starts the application and shows the loading screen
    public void startApp() {
        showLoadingScreen();
    }

    // EFFECTS: Displays LoadingScreenUI for 10 seconds then switches to display the start screen
    public void showLoadingScreen() {
        cardLayout.show(mainPanel, "loading");

        Timer timer = new Timer(10000, e -> showStartScreen());
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: Displays StartScreenUI so user can load from file or create a new clinic
    public void showStartScreen() {
        cardLayout.show(mainPanel, "start");
    }

    // MODIFIES: this
    // EFFECTS: Loads previously saved clinic data to the application; prints out a success message if successful
    // or error message if unable to read from file; loads the view all patients screen
    public void loadClinicData() {
        try {
            Clinic loadedClinic = jsonReader.read();
            clinic.setClinicName(loadedClinic.getClinicName());
            clinic.setPatients(loadedClinic.getPatients());

            JOptionPane.showMessageDialog(
                    this,
                    "Clinic \"" + clinic.getClinicName() + "\" loaded successfully!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(getClass().getResource("/images/health.jpg")));

            viewPatientsScreen.getNavBar().updateClinicTitle();
            viewPatientsScreen.loadPatients();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE, "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        showViewPatientsScreen();
    }

    // MODIFIES: this
    // EFFECTS: Create a new clinic and allows user to input a clinic name; prints out a success message if clinic is
    // successfully named or prints an error message if clinic name is empty; loads the view all patients screen 
    public void createNewClinic() {
        String clinicName = (String) JOptionPane.showInputDialog(
                this, 
                "Enter new clinic name: ",
                "New Clinic",
                JOptionPane.DEFAULT_OPTION, 
                new ImageIcon(getClass().getResource("/images/health.jpg")), null, null);

        if (clinicName != null && !clinicName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "New clinic \"" + clinicName + "\" created successfully!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(getClass().getResource("/images/health.jpg")));
            clinic.setClinicName(clinicName);
            viewPatientsScreen.getNavBar().updateClinicTitle();
        } else {
            JOptionPane.showMessageDialog(this, "Clinic name cannot be empty!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        showViewPatientsScreen();
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to rename the clinic; prints out a success message if clinic is successfully 
    // named or prints an error message if clinic name is empty; loads the view all patients screen 
    public void renameClinic() {
        String clinicName = (String) JOptionPane.showInputDialog(
                this, 
                "Rename clinic: ",
                "Rename Clinic",
                JOptionPane.DEFAULT_OPTION, 
                new ImageIcon(getClass().getResource("/images/health.jpg")), null, null);

        if (clinicName != null && !clinicName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Clinic successfully renamed to \"" + clinicName + "\"!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(getClass().getResource("/images/health.jpg")));
            clinic.setClinicName(clinicName);
        } else {
            JOptionPane.showMessageDialog(this, "Clinic name cannot be empty!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        showViewPatientsScreen();
    }

    // MODIFIES: this
    // EFFECTS: Displays SaveQuitUI which allows the user to save and quit, or quit the application without saving
    public void showSaveQuitScreen() {
        cardLayout.show(mainPanel, "save and quit");
    }

    // MODIFIES: this
    // EFFECTS: Saves the clinic data to file and quits the application
    public void saveQuit() {
        try {
            jsonWriter.open();
            jsonWriter.write(clinic);
            jsonWriter.close();
            JOptionPane.showMessageDialog(
                    this,
                    "Saved \"" + clinic.getClinicName() + "\" to " + JSON_STORE,
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(getClass().getResource("/images/health.jpg")));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE, "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }

        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        System.exit(0);

    }

    // EFFECTS: Quits the application without saving
    public void quit() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: Displays ViewPatientsUI which allows the user to view details of all patients in the clinic
    public void showViewPatientsScreen() {
        getViewPatientsScreen().loadPatients();
        cardLayout.show(mainPanel, "patients");
    }

    // EFFECTS: Returns the ViewPatientsUI screen for reloading data in the patient table 
    public ViewPatientsUI getViewPatientsScreen() {
        return viewPatientsScreen;
    }

    // MODIFIES: this
    // EFFECTS: Displays AddPatientUI which allows the user to input information in a form to add a new patient
    public void showAddPatientScreen() {
        cardLayout.show(mainPanel, "add patient");
    }

    // MODIFIES: this
    // EFFECTS: Displays ViewPatientProfileUI which allows the user to view a specific patient profile
    public void viewPatientProfileScreen(Patient p) {
        viewPatientProfileScreen = new ViewPatientProfileUI(this, clinic, p);
        mainPanel.add(viewPatientProfileScreen, "patient");
        cardLayout.show(mainPanel, "patient");
    }

    // MODIFIES: this
    // EFFECTS: Displays AddClinicalNoteUI which allows the user to create and add a new clinical note
    // in a form for a specific patient
    public void addClinicalNote(Patient p) {
        addClinicaNoteScreen = new AddClinicalNoteUI(this, clinic, p);
        mainPanel.add(addClinicaNoteScreen, "note");
        cardLayout.show(mainPanel, "note");
    }
    
    // EFFECTS: Runs the main application
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Primary Care Clinic Application");
        new MainUI();
    }

    // EFFECTS: Prints the event log upon closing the window
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDate() + " - " + event.getDescription());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // No action
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // No action
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // No action
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // No action
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // No action
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // No action
    }
}
