package ui.gui;

import model.Clinic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// NavigationBarUI displays the navigation bar used to navigate the application and display the clinic name
public class NavigationBarUI extends JPanel {
    private MainUI parent;
    private Clinic clinic;
    private JPanel buttonPanel;
    private JButton viewPatientsButton;
    private JButton newPatientButton;
    private JButton renameClinicButton;
    private JButton saveQuitButton;
    private JLabel clinicTitleLabel;

    // EFFECTS: Constructs a NavigationBarUI JPanel with a panel containing buttons for different screens, 
    // as well as a label for displaying the clinic name
    public NavigationBarUI(MainUI parent, Clinic clinic) {
        this.parent = parent;
        this.clinic = clinic;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000, 150));

        buttonPanel = new JPanel(new GridLayout(1, 14, 10, 0));
        buttonPanel.setBackground(new Color(70, 10, 70));

        createButtons();
        addButtons();
        createClinicTitleLabel();

        add(buttonPanel, BorderLayout.NORTH);
        add(clinicTitleLabel, BorderLayout.SOUTH);
    }

    // EFFECTS: Creates all buttons for the navigation bar
    public void createButtons() {
        createViewPatientsButton();
        createNewPatientButton();
        createRenameClinicButton();
        createSaveQuitButton();
    }

    // MODIFIES: this
    // EFFECTS: Adds all buttons for the navigation bar to the buttonPanel
    public void addButtons() {
        buttonPanel.add(viewPatientsButton);
        buttonPanel.add(newPatientButton);
        buttonPanel.add(renameClinicButton);
        buttonPanel.add(saveQuitButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a view patients button that displays the view all patients screen
    public void createViewPatientsButton() {
        viewPatientsButton = new JButton("View Patients");
        styleButton(viewPatientsButton);
        viewPatientsButton.addActionListener(e -> parent.showViewPatientsScreen());
    }

    // MODIFIES: this
    // EFFECTS: Creates an add patient button that displays the form to add a new patient
    public void createNewPatientButton() {
        newPatientButton = new JButton("Add Patient");
        styleButton(newPatientButton);
        newPatientButton.addActionListener(e -> parent.showAddPatientScreen());
    }

    // MODIFIES: this
    // EFFECTS: Creates a rename clinic button that allows the user to updates the name of the clinic
    public void createRenameClinicButton() {
        renameClinicButton = new JButton("Rename Clinic");
        styleButton(renameClinicButton);
        renameClinicButton.addActionListener(e -> {
            parent.renameClinic();
            updateClinicTitle();
        });
    }

    // MODIFIES: this
    // EFFECTS: Updates clinic title in the navigation bar when renamed
    public void updateClinicTitle() {
        clinicTitleLabel.setText(clinic.getClinicName());
    }

    // MODIFIES: this
    // EFFECTS: Creates a save and quit button that displays the save and quit screen
    public void createSaveQuitButton() {
        saveQuitButton = new JButton("Save & Quit");
        styleButton(saveQuitButton);
        saveQuitButton.addActionListener(e -> parent.showSaveQuitScreen());
    }

    // MODIFIES: this
    // EFFECTS: Create clinic title label to display on the navigation bar
    public void createClinicTitleLabel() {
        clinicTitleLabel = new JLabel(clinic.getClinicName(), SwingConstants.CENTER);
        clinicTitleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        clinicTitleLabel.setForeground(Color.BLACK);
        clinicTitleLabel.setOpaque(true);
        clinicTitleLabel.setBackground(Color.WHITE);
        clinicTitleLabel.setPreferredSize(new Dimension(1000,100));
    }

    // MODIFIES: button
    // EFFECTS: Add styling and hover effects to the button (all navigation buttons)
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
    // EFFECTS: Add hover effects to the button (all navigation buttons)
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
