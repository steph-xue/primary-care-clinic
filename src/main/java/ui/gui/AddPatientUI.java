package ui.gui;


import model.Clinic;
import model.Patient;
import model.Date;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Image References
// JOptionPane Health Logo /images/health.jpg retrieved from https://www.freepik.com/premium-vector/
//basic-healthcare-icon-vector-image-can-be-used-home-services_157661598.html

// AddPatientUI displays a form to add a new patient to the clinic.
public class AddPatientUI extends JPanel {
    private MainUI parent;
    private Clinic clinic;
    private NavigationBarUI navBar;
    private JPanel mainContainerPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private JTextField ageField;
    private JTextField phnField;
    private JList<String> allergiesListUI;
    private JList<String> medicationsListUI;
    private JList<String> conditionsListUI;
    private List<String> currentAllergiesList;
    private List<String> currentMedicationsList;
    private List<String> currentMedicalConditionsList;
    private JButton addButton;

    // EFFECTS: Constructs an AddPatientUI JPanel with a navigation bar to add a new patient to the clinic.
    public AddPatientUI(MainUI parent, Clinic clinic) {
        this.parent = parent;
        this.clinic = clinic;
        currentAllergiesList = new ArrayList<>();
        currentMedicationsList = new ArrayList<>();
        currentMedicalConditionsList = new ArrayList<>();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        navBar = new NavigationBarUI(parent, clinic);

        createMainContainerPanel();
        createContentPanel();
        addFormFields();
        addListFields();
        createAddButton();
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(addButton); 
        addScrollBar();

        add(navBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));
    }

    // MODIFIES: this
    // EFFECTS: Creates main container panel for overall centered layout.
    public void createMainContainerPanel() {
        mainContainerPanel = new JPanel();
        mainContainerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainContainerPanel.setBackground(Color.WHITE);
    }

    // MODIFIES: this
    // EFFECTS: Creates content panel for vertical box layout of form elements.
    public void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    
        JLabel titleLabel = new JLabel("Create A New Patient Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0)); 

        contentPanel.add(titleLabel);
        mainContainerPanel.add(contentPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates and styles a scrolling pane for the create new user screen.
    public void addScrollBar() {
        scrollPane = new JScrollPane(mainContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }

    // MODIFIES: this
    // EFFECTS: Adds form fields for user input (for demographic/personal information).
    public void addFormFields() {
        firstNameField = createStyledTextField();
        addFormField("First Name: ", firstNameField);
        lastNameField = createStyledTextField();
        addFormField("Last Name: ", lastNameField);
        dateOfBirthField = createStyledTextField();
        addFormField("Date of Birth (MM-DD-YYYY): ", dateOfBirthField);
        ageField = createStyledTextField();
        addFormField("Age: ", ageField);
        phnField = createStyledTextField();
        addFormField("Personal Health Number (PHN) (9-digits): ", phnField);
    }

    // EFFECTS: Creates styled text input field (for demographic/personal information).
    // Returns it as a JTextField.
    public JTextField createStyledTextField() {
        JTextField textfield = new JTextField(20);
        textfield.setFont(new Font("Arial", Font.PLAIN, 18));
        textfield.setPreferredSize(new Dimension(300, 35));
        return textfield;
    }

    // MODIFIES: this
    // EFFECTS: Adds a label and text field for each form field (for demographic/personal information).
    public void addFormField(String text, JTextField textField) {
        JPanel panel = new JPanel(); 
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(textField);
        contentPanel.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: Adds list fields for user input (for allergies, medications, medical conditions).
    public void addListFields() {
        allergiesListUI = createStyledList();
        addListField("Allergies:", allergiesListUI, currentAllergiesList);
    
        medicationsListUI = createStyledList();
        addListField("Medications:", medicationsListUI, currentMedicationsList);
    
        conditionsListUI = createStyledList();
        addListField("Medical Conditions:", conditionsListUI, currentMedicalConditionsList);
    }

    // EFFECTS: Creates styled list for displaying list items (for allergies, medications, medical conditions).
    // Returns it as a JList<String>.
    public JList<String> createStyledList() {
        JList<String> list = new JList<>(new DefaultListModel<>());
        list.setVisibleRowCount(3);
        list.setFixedCellHeight(25);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectionBackground(new Color(230, 230, 230));
        list.setSelectionForeground(Color.BLACK);
        return list;
    }

    // MODIFIES: this
    // EFFECTS: Adds the title, current list, and button to add/remove to the list for each list field
    // (allergies, medications, medical conditions).
    public void addListField(String title, JList<String> listUI, List<String> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    
        JScrollPane scrollPane = new JScrollPane(listUI);
        scrollPane.setPreferredSize(new Dimension(400, 75));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.setFocusable(false);
        listUI.setFocusable(false);
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setBackground(Color.WHITE);
    
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter new item:");
            if (input != null && !input.trim().isEmpty()) {
                String formatted = Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
                list.add(formatted);
                ((DefaultListModel<String>) listUI.getModel()).addElement(formatted);
            }
        });
    
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            int selected = listUI.getSelectedIndex();
            if (selected != -1) {
                list.remove(selected);
                ((DefaultListModel<String>) listUI.getModel()).remove(selected);
            }
        });
    
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
    
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        contentPanel.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: Creates add button to add a new patient to the clinic.
    public void createAddButton() {
        addButton = new JButton("Add New Patient");
        styleButton(addButton);
        addButton.addActionListener(e -> createNewPatient());
    }

    // MODIFIES: newPatient
    // EFFECTS: Adds allergies, medications, and medical condtions inputed by the user to the new patient.
    public void addPatientListData(Patient newPatient) {
        for (String allergy: currentAllergiesList) {
            newPatient.addAllergy(allergy);
        }

        for (String medication: currentMedicationsList) {
            newPatient.addMedication(medication);
        }

        for (String medicalCondition: currentMedicalConditionsList) {
            newPatient.addMedicalCondition(medicalCondition);
        }
    }

    // EFFECTS: Parses user inputed information to create a new patient to add to the clinic.
    public void createNewPatient() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dateOfBirth = dateOfBirthField.getText().trim();
        String age = ageField.getText().trim();
        String phn = phnField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() || age.isEmpty() || phn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int parsedAge = parseAge(age);
        long parsedPersonalHealthNumber = parsePersonalHealthNumber(phn);
        Date parsedDateOfBirth = parseDateOfBirth(dateOfBirth);

        if (parsedAge != 0 && parsedPersonalHealthNumber != 0 && parsedDateOfBirth != null) {
            addPatient(firstName, lastName, parsedDateOfBirth, parsedAge, parsedPersonalHealthNumber);
        } else {
            return;
        }
    }

    // EFFECTS: Parses age from string to integer. Shows error message if age is not in the right format.
    // Returns the age as an integer.
    public int parseAge(String age) {
        try {
            return Integer.parseInt(age);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age format!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    // EFFECTS: Parses phn from string to long. Shows error message if phn is not in the right format
    // or if it is not 9-digits long. Returns the phn as a long.
    public long parsePersonalHealthNumber(String phn) {
        try {
            if (phn.length() != 9) {
                JOptionPane.showMessageDialog(this, "Personal health number (PHN) must be 9-digits!", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return 0;
            }
            return Long.parseLong(phn);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid personal health number (PHN) format!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    // EFFECTS: Parses date of birth in (MM-DD-YYYY) string format to date object. Shows error message if
    // string date is not in the right format or invalid date. Returns date of birth as a date object.
    public Date parseDateOfBirth(String dateOfBirth) {
        try {
            String[] parts = dateOfBirth.split("-");
            if (parts.length != 3) {
                JOptionPane.showMessageDialog(this, "Invalid date format!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                return null;
            }

            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            
            if (month < 1 || month > 12) {
                JOptionPane.showMessageDialog(this, "Month must be between 1 and 12!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
    
            if (day < 1 || day > 31) {
                JOptionPane.showMessageDialog(this, "Day must be between 1 and 31!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
    
            if (year < 1900 || year > java.time.LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(this, "Year must be reasonable and not in the future!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
    
            java.time.LocalDate dob = java.time.LocalDate.of(year, month, day);
            if (dob.isAfter(java.time.LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Date of birth cannot be in the future!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
    
            return new Date(month, day, year);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a new patient object with the user inputed information to add to the clinic.
    // Clears all fields and lists once patient is successfully added with a success message and
    // reloads the view all patients screen.
    public void addPatient(String firstName, String lastName, Date parsedDateOfBirth, int parsedAge, 
            long parsedPersonalHealthNumber) {
                
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        Patient newPatient = new Patient(firstName, lastName, parsedDateOfBirth, parsedAge,
                parsedPersonalHealthNumber);
        addPatientListData(newPatient);
        clinic.addPatient(newPatient);
        clearFields();

        JOptionPane.showMessageDialog(
                    this,
                    "New patient \"" + firstName + " " + lastName + "\" added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/health.jpg")));

        parent.getViewPatientsScreen().loadPatients();
        parent.showViewPatientsScreen();
    }

    // MODIFIES: this
    // EFFECTS: Clears all user input fields and resets all lists.
    public void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setText("");
        ageField.setText("");
        phnField.setText("");
        currentAllergiesList.clear();
        currentMedicationsList.clear();
        currentMedicalConditionsList.clear();
        ((DefaultListModel<String>) allergiesListUI.getModel()).clear();
        ((DefaultListModel<String>) medicationsListUI.getModel()).clear();
        ((DefaultListModel<String>) conditionsListUI.getModel()).clear();

    }

    // MODIFIES: button
    // EFFECTS: Adds styling to the button (add new patient button).
    public void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(500, 50));
        button.setMaximumSize(new Dimension(500, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(70, 10, 70));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);

        addButtonEffects(button);
    }

    // MODIFIES: button
    // EFFECTS: Adds hover effects to the button (add new patient button).
    public void addButtonEffects(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(90, 40, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 10, 70));
            }
        });
    }
}
