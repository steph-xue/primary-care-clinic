package ui.gui;

import javax.swing.*;
import java.awt.*;

// Image References
// EHR image /images/ehr.jpg retrieved from https://www.vecteezy.com/
// Loading gif image /images/loading.gif retrieved from https://icons8.com/icons/set/dots-loading--animated

// LoadingScreenUI displays loading screen with an image and welcome message to the user with a
// loading gif for 10 seconds.
public class LoadingScreenUI extends JPanel {
    private JPanel contentPanel;
    private JLabel welcomeLabel;
    private JPanel loadingPanel;

    // EFFECTS: Constructs a LoadingScreenUI JPanel.
    public LoadingScreenUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        createWelcomeLabel();
        createLoadingPanel();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int topPadding = screenHeight / 8; 
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, topPadding)));
        contentPanel.add(welcomeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50))); 
        contentPanel.add(loadingPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a welcome label with an image and welcome message.
    public void createWelcomeLabel() {
        ImageIcon ehrImage = new ImageIcon(getClass().getResource("/images/ehr.jpg"));
        ehrImage = new ImageIcon(ehrImage.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH));  

        welcomeLabel = new JLabel();
        welcomeLabel.setIcon(ehrImage);
        welcomeLabel.setText("Welcome to the Primary Care Clinic Application!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
        welcomeLabel.setVerticalTextPosition(JLabel.BOTTOM);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: Creates a loading panel with a loading gif and message.
    public void createLoadingPanel() {
        JLabel imageLabel = new JLabel();
        ImageIcon loadingImage = new ImageIcon(getClass().getResource("/images/loading.gif"));
        imageLabel.setIcon(loadingImage);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel textLabel = new JLabel("Loading...");
        textLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        textLabel.setForeground(Color.GRAY);

        loadingPanel = new JPanel();
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
        loadingPanel.setBackground(getBackground());
        loadingPanel.add(imageLabel);
        loadingPanel.add(textLabel);
        loadingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
