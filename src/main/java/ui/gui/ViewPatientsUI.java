package ui.gui;

import model.Clinic;
import model.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// ViewPatientsUI displays the homepage with a table view of all patients in the clinic
public class ViewPatientsUI extends JPanel {
    private MainUI parent;
    private Clinic clinic;
    private JPanel contentPanel;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private NavigationBarUI navBar;

    // EFFECTS: Constructs a ViewPatientsUI JPanel with a navigation bar, displaying all patients in a table format
    public ViewPatientsUI(MainUI parent, Clinic clinic) {
        this.parent = parent;
        this.clinic = clinic;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        navBar = new NavigationBarUI(parent, clinic);
        contentPanel.add(navBar, BorderLayout.NORTH);
        createTable();
        add(contentPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a table to display all patients and loads patient data
    public void createTable() {
        String[] columnNames = {"First Name", "Last Name", "Date of Birth (DOB)", "Age", 
            "Personal Health Number (PHN)"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);
        adjustTableFormatting();
        adjustTableHeader();
        setColumnWidths();
        addDoubleClickListener();
        loadPatients();
    }

    // MODIFIES: this
    // EFFECTS: Adjusts patient table formatting
    public void adjustTableFormatting() {
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setSelectionBackground(new Color(230, 230, 230));
        patientTable.setSelectionForeground(Color.BLACK);
        patientTable.setRowHeight(30);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 18));
        patientTable.setShowHorizontalLines(false);
        patientTable.setShowVerticalLines(false);
        patientTable.setDefaultEditor(Object.class, null);
        patientTable.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(0, 80, 40, 80));
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(tableContainer, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Adjusts patient table header formatting
    public void adjustTableHeader() {
        JTableHeader tableHeader = patientTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        add(tableHeader, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: Sets patient table column widths
    public void setColumnWidths() {
        patientTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        patientTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        patientTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        patientTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        patientTable.getColumnModel().getColumn(4).setPreferredWidth(250);
    }

    // MODIFIES: this
    // EFFECTS: Loads patient information into the table
    public void loadPatients() {
        tableModel.setRowCount(0);
        for (Patient p: clinic.getPatients()) {
            Object[] rowData = { p.getFirstName(), p.getLastName(), p.getDateOfBirth().printDate(), 
                p.getAge(), p.getPersonalHealthNumber()};
            tableModel.addRow(rowData);
        }
    }

    // EFFECTS: Returns navigation bar so MainUI can update it
    public NavigationBarUI getNavBar() {
        return navBar;
    }

    // MODIFIES: this
    // EFFECTS: Adds double click event listener to the table rows to view a specific patient profile
    public void addDoubleClickListener() {
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = patientTable.getSelectedRow();
                    if (row >= 0) {
                        Patient selectedPatient = clinic.getPatients().get(row);
                        parent.viewPatientProfileScreen(selectedPatient);
                    }
                }
            }
        });
    }
}
