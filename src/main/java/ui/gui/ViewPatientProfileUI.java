package ui.gui;

import model.Clinic;
import model.Patient;
import model.ClinicalNote;
import model.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Collections;

// Image References
// Patient image /images/patient.jpg retrieved from https://www.istockphoto.com/vector/patient-icon-vector-of-
// male-person-profile-avatar-symbol-for-medical-treatment-in-gm1147248235-309382448
// Edit image /images/edit.png retrieved from https://www.veryicon.com/icons/miscellaneous/linear-small-icon/
// edit-246.html

// ViewPatientProfileUI displays details of a specific patient profile.
public class ViewPatientProfileUI extends JPanel {
    private MainUI parent;
    private Clinic clinic;
    private Patient patient;
    private NavigationBarUI navBar;
    private JPanel mainContainerPanel;
    private JPanel contentPanel;
    private JLabel patientImageLabel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JButton newNoteButton;
    private JButton deletePatientButton;

    // EFFECTS: Constructs a ViewPatientProfileUI JPanel displaying details for a specific patient.
    public ViewPatientProfileUI(MainUI parent, Clinic clinic, Patient patient) {
        this.parent = parent;
        this.clinic = clinic;
        this.patient = patient;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        navBar = new NavigationBarUI(parent, clinic);

        createMainContainerPanel();
        createContentPanel();
        addPatientImage();
        addPatientInfo();
        addClinicalNotes();
        addButtonPanel();
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
    // EFFECTS: Creates content panel for vertical box layout of patient data.
    public void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        mainContainerPanel.add(contentPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates and styles a scrolling pane for the user profile.
    public void addScrollBar() {
        scrollPane = new JScrollPane(mainContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }

    // MODIFIES: this
    // EFFECTS: Creates and add image for patient profile.
    public void addPatientImage() {
        ImageIcon patientImage = new ImageIcon(getClass().getResource("/images/patient.jpg"));
        patientImage = new ImageIcon(patientImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));  

        patientImageLabel = new JLabel();
        patientImageLabel.setIcon(patientImage);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        imagePanel.add(patientImageLabel);

        contentPanel.add(imagePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // MODIFIES: this
    // EFFECTS: Creates and add panels for patient data to add to the contentPanel (demographic/personal and 
    // medical information).
    public void addPatientInfo() {
        JPanel firstNamePanel = createPanel("First Name: ", patient.getFirstName(), "firstName");
        JPanel lastNamePanel = createPanel("Last Name: ", patient.getLastName(), "lastName");
        JPanel dateOfBirthPanel = createPanel("Date of Birth (MM-DD-YYYY): ", patient.getDateOfBirth().printDate(),
                "dob");
        JPanel agePanel = createPanel("Age: ", String.valueOf(patient.getAge()), "age");
        JPanel phnPanel = createPanel("Personal Health Number (PHN): ", 
                String.valueOf(patient.getPersonalHealthNumber()), "phn");
        JPanel allergiesPanel = createListPanel("Allergies: ", patient.printAllergies(), "allergies");
        JPanel medicationsPanel = createListPanel("Medications: ", patient.printMedications(), "medications");
        JPanel medicalConditionsPanel = createListPanel("Medical Conditions: ", patient.printMedicalConditions(), 
                "medicalConditions");

        contentPanel.add(firstNamePanel);
        contentPanel.add(lastNamePanel);
        contentPanel.add(dateOfBirthPanel);
        contentPanel.add(agePanel);
        contentPanel.add(phnPanel);
        contentPanel.add(allergiesPanel);
        contentPanel.add(medicationsPanel);
        contentPanel.add(medicalConditionsPanel);
    }

    // EFFECTS: Creates and style a panel for a patient data field with the header, data, and edit button.
    // Returns it as a JPanel.
    public JPanel createPanel(String header, String data, String fieldName) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 250, 5, 0));
    
        JLabel headerLabel = new JLabel(header);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Wrap the data and edit button together
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.X_AXIS));
        valuePanel.setBackground(Color.WHITE);
        valuePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    
        JLabel dataLabel = new JLabel(data);
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dataLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));  // Small spacing
    
        JButton editButton = createEditButton(header, fieldName, dataLabel);
    
        valuePanel.add(dataLabel);
        valuePanel.add(editButton);
    
        panel.add(headerLabel);
        panel.add(valuePanel);
    
        return panel;
    }
    
    // MODIFIES: this
    // EFFECTS: Updates patient information with user inputed edits based on the field selected.
    public void updatePatientField(String key, String value, JComponent dataComponent) {
        switch (key) {
            case "firstName":
                patient.setFirstName(value);
                break;
            case "lastName":
                patient.setLastName(value);
                break;
            case "dob":
                parseAndSetDateOfBirth(value);
                break;
            case "age":
                parseAndSetAge(value);
                break;
            case "phn":
                parseAndSetPersonalHealthNumber(value);
                break;
        }
    
        if (dataComponent instanceof JLabel) {
            ((JLabel) dataComponent).setText(value);
        } else if (dataComponent instanceof JTextArea) {
            ((JTextArea) dataComponent).setText(value);
        }
    }

    // MODIFIES: this
    // EFFECTS: Parses date of birth in (MM-DD-YYYY) string format to date object to set for the selected patient.
    // Shows error message if string date is not in the right format.
    public void parseAndSetDateOfBirth(String dateOfBirth) {
        try {
            String[] parts = dateOfBirth.split("-");
            if (parts.length != 3) {
                JOptionPane.showMessageDialog(this, "Invalid date format!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                return;
            }

            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            if (month < 1 || month > 12) {
                JOptionPane.showMessageDialog(this, "Month must be between 1 and 12!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (day < 1 || day > 31) {
                JOptionPane.showMessageDialog(this, "Day must be between 1 and 31!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (year < 1900 || year > java.time.LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(this, "Year must be reasonable and not in the future!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            java.time.LocalDate dob = java.time.LocalDate.of(year, month, day);
            if (dob.isAfter(java.time.LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Date of birth cannot be in the future!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            patient.setDateOfBirth(new Date(month, day, year));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // MODIFIES: this
    // EFFECTS: Parses age from string to integer to set for the selected patient. Shows error message 
    // if age is not in the right format.
    public void parseAndSetAge(String age) {
        int parsedAge;

        try {
            parsedAge = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age format!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        patient.setAge(parsedAge);
    }

    // MODIFIES: this
    // EFFECTS: Parses phn from string to long to set for the selected patient. Shows error message
    // if phn is not in the right format or if it is not 9-digits long.
    public void parseAndSetPersonalHealthNumber(String phn) {
        long parsedPersonalHealthNumber;

        try {
            if (phn.length() != 9) {
                JOptionPane.showMessageDialog(this, "Personal health number (PHN) must be 9-digits!", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            parsedPersonalHealthNumber = Long.parseLong(phn);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid personal health number (PHN) format!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        patient.setPersonalHealthNumber(parsedPersonalHealthNumber);
    }

    // EFFECTS: Creates and style editing button that allows the user to edit the selected field.
    // Returns the editing button as a JButton.
    public JButton createEditButton(String header, String fieldName, JComponent dataComponent) {
        JButton editButton = new JButton();
        ImageIcon editIcon = new ImageIcon(getClass().getResource("/images/edit.png"));
        Image scaledImage = editIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        editButton.setIcon(new ImageIcon(scaledImage));
        editButton.setPreferredSize(new Dimension(15, 15));
        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        addEditActionListener(editButton, header, fieldName, dataComponent);
        return editButton;
    }
    

    // EFFECTS: Adds action listener to edit button to allow the user to update the selected field.
    public void addEditActionListener(JButton editButton, String header, String fieldName, JComponent dataComponent) {
        editButton.addActionListener(e -> {
            String newValue = (String) JOptionPane.showInputDialog(
                    this,
                    "Enter new " + header.substring(0, header.length() - 2) + ": ",
                    "Edit",
                    JOptionPane.DEFAULT_OPTION,
                    new ImageIcon(getClass().getResource("/images/health.jpg")), null, null
            );
    
            if (newValue != null && !newValue.trim().isEmpty()) {
                updatePatientField(fieldName, newValue.trim(), dataComponent);
            }
        });
    }
    

    // EFFECTS: Creates and style a panel to display patient list data with the header and data (allergies,
    // medications, medical conditions) and right-aligned add/remove buttons. Returns it as a JPanel.
    public JPanel createListPanel(String header, String data, String fieldName) {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        outerPanel.setBackground(Color.WHITE);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(5, 250, 5, 0)); 
    
        JPanel dataRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataRow.setBackground(Color.WHITE);
    
        JLabel headerLabel = new JLabel(header);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
    
        JTextArea dataArea = new JTextArea(data);
        dataArea.setFont(new Font("Arial", Font.PLAIN, 18));
        dataArea.setWrapStyleWord(true);    
        dataArea.setLineWrap(true);         
        dataArea.setOpaque(false);        
        dataArea.setEditable(false);        
        dataArea.setFocusable(false);      
        dataArea.setBorder(null);           
        dataArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        dataArea.setSize(new Dimension(500, Short.MAX_VALUE));
        Dimension preferredSize = dataArea.getPreferredSize();
        dataArea.setPreferredSize(new Dimension(500, preferredSize.height));

        dataRow.add(headerLabel);
        dataRow.add(dataArea);
    
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setBackground(Color.WHITE);
    
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
    
        styleSmallButton(addButton);
        styleSmallButton(removeButton);
    
        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter new item to add:");
            if (input != null && !input.trim().isEmpty()) {
                updateListField(fieldName, input.trim(), true);
                refreshView();
            }
        });
    
        removeButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter item to remove:");
            if (input != null && !input.trim().isEmpty()) {
                updateListField(fieldName, input.trim(), false);
                refreshView();
            }
        });
    
        buttonRow.add(addButton);
        buttonRow.add(removeButton);
    
        outerPanel.add(dataRow);
        outerPanel.add(buttonRow);
    
        return outerPanel;
    }    

    // MODIFIES: patient
    // EFFECTS: Adds or removes an item from a list based on the field name and shows error message
    // if trying to remove a non-existent item.
    public void updateListField(String fieldName, String value, boolean add) {
        boolean success = false;
        String formattedValue = capitalize(value);
    
        switch (fieldName) {
            case "allergies":
                if (add) {
                    patient.addAllergy(formattedValue);
                    success = true;
                } else {
                    success = removeCaseInsensitive(patient.getAllergies(), value, () -> patient.removeAllergy(formattedValue));
                }
                break;
            case "medications":
                if (add) {
                    patient.addMedication(formattedValue);
                    success = true;
                } else {
                    success = removeCaseInsensitive(patient.getMedications(), value, () -> patient.removeMedication(formattedValue));
                }
                break;
            case "medicalConditions":
                if (add) {
                    patient.addMedicalCondition(formattedValue);
                    success = true;
                } else {
                    success = removeCaseInsensitive(patient.getMedicalConditions(), value, () -> patient.removeMedicalCondition(formattedValue));
                }
                break;
        }
    
        if (!success && !add) {
            JOptionPane.showMessageDialog(this,
                    "Item not found in " + fieldName + ", unable to remove.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: Removes the first occurrence of a target string from a list, ignoring case.
    private boolean removeCaseInsensitive(List<String> list, String target, Runnable removeAction) {
        for (String item : list) {
            if (item.equalsIgnoreCase(target)) {
                removeAction.run(); 
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Capitalizes the first letter of the input string and lowercases the rest.
    private String capitalize(String input) {
        if (input.length() == 0) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    // MODIFIES: button
    // EFFECTS: Styles small "Add" and "Remove" buttons.
    public void styleSmallButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(new Color(200, 200, 255));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(75, 25)); 
        button.setMinimumSize(new Dimension(75, 25));
        button.setMaximumSize(new Dimension(75, 25));
        button.setMargin(new Insets(2, 6, 2, 6));
    }

    // MODIFIES: this
    // EFFECTS: Re-renders the patient profile screen to reflect changes.
    public void refreshView() {
        parent.viewPatientProfileScreen(patient);
    }
    
    // MODIFIES: this
    // EFFECTS: Adds clinical notes in individual boxes to contentPanel or displays no notes if
    // patient does not have any clinical notes.
    public void addClinicalNotes() {
        addClinicalNoteTitle();

        if (patient.getClinicalNotes().isEmpty() || patient.getClinicalNotes() == null) {
            JLabel noNotesLabel = createNoNotesLabel();
            contentPanel.add(noNotesLabel);
        } else {
            List<ClinicalNote> notes = patient.getClinicalNotes();
            Collections.reverse(notes);
            for (ClinicalNote note: notes) {
                contentPanel.add(createClinicalNoteBox(note));
                contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds clinical note section title to contentPanel.
    public void addClinicalNoteTitle() {
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        JLabel notesTitle = new JLabel("Clinical Notes");
        notesTitle.setFont(new Font("Arial", Font.BOLD, 25));
        notesTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        notesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(notesTitle);
    }

    // EFFECTS: Creates a no clinical notes label. Returns it as a JLabel.
    public JLabel createNoNotesLabel() {
        JLabel noNotesLabel = new JLabel("No clinical notes avaliable");
        noNotesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        noNotesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        noNotesLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 60, 10));
        return noNotesLabel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a clinical note box with all note data with editing buttons (title, provider, body) 
    // and a delete clinical note button. Returns it as a JPanel.
    public JPanel createClinicalNoteBox(ClinicalNote note) {
        JPanel notePanel = createNotePanel();

        JPanel titlePanel = createNoteTitlePanel(note);
        JPanel datePanel = createNoteDatePanel(note);
        JPanel providerPanel = createNoteProviderPanel(note);
        JPanel bodyPanel = createNoteBodyPanel(note);
        JPanel deleteNoteButtonPanel = createDeleteNoteButtonPanel(note);

        notePanel.add(titlePanel);
        notePanel.add(datePanel);
        notePanel.add(providerPanel);
        notePanel.add(bodyPanel);
        notePanel.add(deleteNoteButtonPanel);
        return notePanel;
    }

    // EFFECTS: Creates a clinical note panel for each seperate note with styling for the box.
    // Returns it as a JPanel.
    public JPanel createNotePanel() {
        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
        notePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        notePanel.setBackground(Color.WHITE);
        notePanel.setMinimumSize(new Dimension(1000, 200));
        notePanel.setMaximumSize(new Dimension(1000, 1000));
        notePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return notePanel;
    }

    // EFFECTS: Creates a clinical note title panel with styling and an editing button. 
    // Returns it as a JPanel.
    public JPanel createNoteTitlePanel(ClinicalNote note) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(note.getClinicalNoteTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton editButton = createEditNoteButton(note, "title", titleLabel);

        panel.add(titleLabel);
        panel.add(editButton);
        return panel;
    }

    // EFFECTS: Creates a clinical note date panel with styling and an editing button. 
    // Returns it as a JPanel.
    public JPanel createNoteDatePanel(ClinicalNote note) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JLabel dateLabel = new JLabel(note.getClinicalNoteDate().printDate());
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(dateLabel);
        return panel;
    }

    // EFFECTS: Creates a clinical note provider panel with styling and an editing button.
    // Returns it as a JPanel. 
    public JPanel createNoteProviderPanel(ClinicalNote note) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JLabel providerLabel = new JLabel(note.getClinicalNoteProvider());
        providerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        providerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton editButton = createEditNoteButton(note, "provider", providerLabel);

        panel.add(providerLabel);
        panel.add(editButton);
        return panel;
    }

    // EFFECTS: Creates a clinical note body panel with styling and an editing button.
    // Returns it as a JPanel. 
    public JPanel createNoteBodyPanel(ClinicalNote note) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTextArea bodyTextArea = createTextAreaBody(note);
        JButton editButton = createEditNoteButton(note, "body", bodyTextArea);

        panel.add(bodyTextArea, BorderLayout.CENTER);
        panel.add(editButton, BorderLayout.EAST);
        return panel;
    }

    // EFFECTS: Creates a JTextArea for the clinical note body. Returns it as a JTextArea.
    public JTextArea createTextAreaBody(ClinicalNote note) {
        JTextArea bodyTextArea = new JTextArea(note.getClinicalNoteBody());
        bodyTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        bodyTextArea.setWrapStyleWord(true);
        bodyTextArea.setLineWrap(true);
        bodyTextArea.setEditable(false);
        bodyTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        return bodyTextArea;
    }

    // MODIFIES: this, dataComponent
    // EFFECTS: Updates clinical note information with user inputed edits based on the field selected.
    public void updateNoteField(ClinicalNote note, String key, String value, JComponent dataComponent) {
        switch (key) {
            case "title":
                note.setClinicalNoteTitle(value);
                ((JLabel) dataComponent).setText(note.getClinicalNoteTitle());
                break;
            case "provider":
                note.setClinicalNoteProvider(value);
                ((JLabel) dataComponent).setText(note.getClinicalNoteProvider());
                break;
            case "body":
                note.setClinicalNoteBody(value);
                ((JTextArea) dataComponent).setText(note.getClinicalNoteBody());
                break;
        }   
    }

    // EFFECTS: Creates and style editing button for each of the clinical note fields that allows the user
    // to edit the selected field. Returns the editing button as a JButton.
    public JButton createEditNoteButton(ClinicalNote note, String fieldName, JComponent dataComponent) {
        JButton editButton = new JButton();
        ImageIcon editIcon = new ImageIcon(getClass().getResource("/images/edit.png"));
        Image scaledImage = editIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        editButton.setIcon(new ImageIcon(scaledImage));
        editButton.setPreferredSize(new Dimension(15, 15));
        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addEditNoteActionListener(note, editButton, fieldName, dataComponent);

        return editButton;
    }

    // MODIFIES: editButton
    // EFFECTS: Adds action listener to edit button to allow the user to edit the selected field.
    public void addEditNoteActionListener(ClinicalNote note, JButton editButton, String fieldName, 
            JComponent dataComponent) {
        editButton.addActionListener(e -> {
            String newValue = (String) JOptionPane.showInputDialog(
                    this, 
                    "Enter new " + fieldName + ": ",
                    "Edit",
                    JOptionPane.DEFAULT_OPTION, 
                    new ImageIcon(getClass().getResource("/images/health.jpg")), null, null
            );

            if (newValue != null && !newValue.trim().isEmpty()) {
                updateNoteField(note, fieldName, newValue.trim(), dataComponent);
            }
        });
    }

    // MODIFIES: this, note
    // EFFECTS: Creates a delete note button panel with a delete note button for a clinical note
    // with an action listener to delete the note from the clinic. Returns it as a JPanel.
    public JPanel createDeleteNoteButtonPanel(ClinicalNote note) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JButton deleteNoteButton = new JButton("Remove Note");
        styleButtonDeleteNote(deleteNoteButton);
        deleteNoteButton.addActionListener(e -> {
            patient.removeClinicalNote(note);
            parent.viewPatientProfileScreen(patient);
        });
        
        panel.add(deleteNoteButton);
        return panel;
    }

    // MODIFIES: button
    // EFFECTS: Adds styling to the button (delete clinical note button).
    public void styleButtonDeleteNote(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 30));
        button.setMaximumSize(new Dimension(150, 30));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);

        addButtonEffectsDeleteNote(button);
    }

    // MODIFIES: button
    // EFFECTS: Adds hover effects to the button (delete clinical note button).
    public void addButtonEffectsDeleteNote(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(170, 50, 60));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.GRAY);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Creates a button panel containing the delete patient and new clinical note buttons.
    private void addButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        newNoteButton = new JButton("New Clinical Note");
        styleButtonNewNote(newNoteButton);
        newNoteButton.addActionListener(e -> parent.addClinicalNote(patient));

        deletePatientButton = new JButton("Remove Patient");
        styleButtonDelete(deletePatientButton);
        deletePatientButton.addActionListener(e -> removePatient());

        buttonPanel.add(newNoteButton);
        buttonPanel.add(deletePatientButton);
        contentPanel.add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: Removes patient from the clinic and redirects user to the view all patients screen.
    public void removePatient() {
        clinic.removePatient(patient);
        parent.getViewPatientsScreen().loadPatients();
        parent.showViewPatientsScreen();
    }

    // MODIFIES: button
    // EFFECTS: Adds styling to the button (new clinical note button).
    public void styleButtonNewNote(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(500, 50));
        button.setMaximumSize(new Dimension(500, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(70, 10, 70));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);

        addButtonEffectsAdd(button);
    }

    // MODIFIES: button
    // EFFECTS: Adds styling to the button (delete clinical note button).
    public void styleButtonDelete(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(500, 50));
        button.setMaximumSize(new Dimension(500, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(170, 50, 60));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);

        addButtonEffectsDelete(button);
    }

    // MODIFIES: button
    // EFFECTS: Adds hover effects to the button (new clinical note button).
    public void addButtonEffectsAdd(JButton button) {
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

    // MODIFIES: button
    // EFFECTS: Adds hover effects to the button (delete clinical note button).
    public void addButtonEffectsDelete(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(170, 70, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(170, 50, 60));
            }
        });
    }
}
