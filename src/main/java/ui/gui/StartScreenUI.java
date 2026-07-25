package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// StartScreenUI displays the start screen with an option to load a save from file or start a new clinic
public class StartScreenUI extends JPanel {
    private MainUI parent;
    private JPanel contentPanel;
    private JLabel messageLabel;
    private JButton loadButton;
    private JButton newClinicButton;

    // EFFECTS: Constructs a StartScreenUI JPanel
    public StartScreenUI(MainUI parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        createMessageLabel();
        createLoadButton();
        createNewClinicButton();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 250)));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        contentPanel.add(loadButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(newClinicButton);

        add(contentPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a message label to load from file or create a new clinic
    public void createMessageLabel() {
        messageLabel = new JLabel();
        messageLabel.setText("Load from file or create a new clinic: ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setHorizontalTextPosition(JLabel.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: Creates a load from file button
    public void createLoadButton() {
        loadButton = new JButton("Load clinic data from file");
        styleButton(loadButton);
        loadButton.addActionListener(e -> parent.loadClinicData());
    }

    // MODIFIES: this
    // EFFECTS: Creates a create a new clinic button
    public void createNewClinicButton() {
        newClinicButton = new JButton("Create a new clinic");
        styleButton(newClinicButton);
        newClinicButton.addActionListener(e -> parent.createNewClinic());
    }

    // MODIFIES: button
    // EFFECTS: Add styling to the button (load from file/create a new clinic buttons)
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
    // EFFECTS: Add hover effects to the button (load from file/create a new clinic buttons)
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
