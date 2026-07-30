package ui.gui;

import model.Clinic;
import model.ClinicalNote;
import model.Patient;
import model.Date;

import javax.swing.*;
import javax.swing.border.Border;
import java.time.LocalDate;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Image References
// JOptionPane Health Logo /images/health.jpg retrieved from https://www.freepik.com/premium-vector/
//basic-healthcare-icon-vector-image-can-be-used-home-services_157661598.html

// AddClinicalNoteUI displays a form to add a new clinical note for a specific patient.
public class AddClinicalNoteUI extends JPanel {
    private MainUI parent;
    private Patient patient;
    private NavigationBarUI navBar;
    private JPanel mainContainerPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JTextField titleField;
    private JTextField providerField;
    private JTextArea bodyField;
    private JPanel addButtonPanel;

    // EFFECTS: Constructs an AddClinicalNoteUI JPanel with a navigation bar to make and add a new clinical note
    // for the selected patient based on the user's inputs.
    public AddClinicalNoteUI(MainUI parent, Clinic clinic, Patient patient) {
        this.parent = parent;
        this.patient = patient;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        navBar = new NavigationBarUI(parent, clinic);
        createMainContainerPanel();
        createContentPanel();
        addFormFields();
        createAddButton();
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(addButtonPanel);

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

        JLabel titleLabel = new JLabel("Create A New Clinical Note");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0)); // Vertical spacing
        contentPanel.add(titleLabel);
        mainContainerPanel.add(contentPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates and styles a scrolling pane for the create new clinical note screen.
    public void addScrollBar() {
        scrollPane = new JScrollPane(mainContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }

    // MODIFIES: this
    // EFFECTS: Makes and adds form fields for user input (title, provider, body).
    public void addFormFields() {
        titleField = createStyledTextFieldTitle();
        addFormField("Title:         ", titleField);
        providerField = createStyledTextFieldProvider();
        addFormField("Provider:  ", providerField);
        bodyField = createStyledTextAreaBody();
        addFormField("Body:       ", bodyField);
    }

    // EFFECTS: Creates styled text input field for title, returned as a JTextField.
    public JTextField createStyledTextFieldTitle() {
        JTextField textfield = new JTextField(33);
        textfield.setFont(new Font("Arial", Font.PLAIN, 18));
        Border outer = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        Border inner = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        textfield.setBorder(BorderFactory.createCompoundBorder(outer, inner));
        return textfield;
    }

    // EFFECTS: Creates styled text input field for provider, returned as a JTextField.
    public JTextField createStyledTextFieldProvider() {
        JTextField textfield = new JTextField(35);
        textfield.setFont(new Font("Arial", Font.PLAIN, 18));
        Border outer = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        Border inner = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        textfield.setBorder(BorderFactory.createCompoundBorder(outer, inner));
        return textfield;
    }

    // EFFECTS: Creates styled text area field for body, returned as a JTextArea.
    public JTextArea createStyledTextAreaBody() {
        JTextArea textArea = new JTextArea(15, 35);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Border outer = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        Border inner = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        textArea.setBorder(BorderFactory.createCompoundBorder(outer, inner));
        return textArea;
    }

    // MODIFIES: this
    // EFFECTS: Adds a label and the styled text field for each form field. 
    public void addFormField(String text, JComponent textField) {
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
    // EFFECTS: Adds create new clinical note button in a button panel.
    public void createAddButton() {
        JButton addButton = new JButton("Create New Clinical Note");
        styleButton(addButton);
        addButton.addActionListener(e -> createNewClinicalNote());

        addButtonPanel = new JPanel();
        addButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(Color.WHITE);
        addButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
        addButtonPanel.add(addButton);
    }

    // EFFECTS: Gets user input from the text fields from the form to create a new clinical note.
    public void createNewClinicalNote() {
        String title = titleField.getText().trim();
        String provider = providerField.getText().trim();
        String body = bodyField.getText().trim();

        if (title.isEmpty() || provider.isEmpty() || body.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dateString = LocalDate.now().toString();

        String[] parts = dateString.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        Date date = new Date(month, day, year);
        
        addNote(title, provider, body, date);
    }

    // MODIFIES: this
    // EFFECTS: Creates a new clinical note object and adds it for the specified patient. Clears
    // all fields once clinical note is successfully added and displays a success message.
    public void addNote(String title, String provider, String body, Date date) {

        title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
        provider = provider.substring(0, 1).toUpperCase() + provider.substring(1).toLowerCase();
        body = body.substring(0, 1).toUpperCase() + body.substring(1).toLowerCase();

        ClinicalNote note = new ClinicalNote(title, body, provider, date);
        this.patient.addClinicalNote(note);
        clearFields();

        JOptionPane.showMessageDialog(
                    this,
                    "New clinical note \"" + title + "\" added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/health.jpg")));

        parent.viewPatientProfileScreen(patient);
    }

    // MODIFIES: this
    // EFFECTS: Clears all user input fields. 
    public void clearFields() {
        titleField.setText("");
        providerField.setText("");
        bodyField.setText("");
    }

    // MODIFIES: button
    // EFFECTS: Adds styling to the button (add new note button).
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
    // EFFECTS: Adds hover effects to the button (add new note button).
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
