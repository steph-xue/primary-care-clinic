package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// SaveQuitUI displays the save and quit screen with an option to save and quit or quit without saving
public class SaveQuitUI extends JPanel {
    private MainUI parent;
    private JPanel contentPanel;
    private JLabel messageLabel;
    private JButton saveQuitButton;
    private JButton quitButton;

    // EFFECTS: Constructs a SaveQuitUI JPanel
    public SaveQuitUI(MainUI parent) {
        this.parent = parent;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        createMessageLabel();
        createSaveQuitButton();
        createQuitButton();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 250)));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        contentPanel.add(saveQuitButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(quitButton);

        add(contentPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a message label to save and quit or quit without saving
    public void createMessageLabel() {
        messageLabel = new JLabel();
        messageLabel.setText("Would you like to save your progress before exiting? ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setHorizontalTextPosition(JLabel.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: Creates a save and quit button
    public void createSaveQuitButton() {
        saveQuitButton = new JButton("Save and quit");
        styleButton(saveQuitButton);
        saveQuitButton.addActionListener(e -> parent.saveQuit());
    }

    // MODIFIES: this
    // EFFECTS: Creates a quit without saving button
    public void createQuitButton() {
        quitButton = new JButton("Quit without saving");
        styleButton(quitButton);
        quitButton.addActionListener(e -> parent.quit());
    }

    // MODIFIES: button
    // EFFECTS: Add styling to the button (save and quit/quit without saving buttons)
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
    // EFFECTS: Add hover effects to button (save and quit/quit without saving buttons)
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
