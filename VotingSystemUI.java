import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;

public class VotingSystemUI extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final Map<String, String> userDatabase = new HashMap<>();
    private final Map<String, String> userPhoneNumbers = new HashMap<>();
    private final JLabel passwordStrengthLabel;
    private String userPhoneNumber;
    private Color contentPaneBackgroundColor;
    public JLabel successLabel;
    public boolean hasVoted = false;
    public void setHasVoted(boolean voted) {
        hasVoted = voted;
    }
    public static class MySQLConnection {
        private static final String JDBC_URL = "jdbc:mysql://localhost:3306/voting";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "Rakshit1";

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static class UserData {
        private final String name;
        private final String state;
        private final String voterId;

        public UserData(String name, String state, String voterId) {
            this.name = name;
            this.state = state;
            this.voterId = voterId;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public String getVoterId() {
            return voterId;
        }
    }

    public VotingSystemUI() {
        setTitle("E-Voting System");
        setSize(1700, 1100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Border customBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBackgroundImage("src/images/bg1.png");

        ImageIcon logoIcon = new ImageIcon("src/images/insta.png");
        Image logoImage = logoIcon.getImage();
        int newWidth = 30;
        int newHeight = 30;
        Image resizedLogoImage = logoImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(resizedLogoImage));
        String externalLink = "https://www.instagram.com/notdhruvmore/";

        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(externalLink));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
        });
        ImageIcon fbIcon = new ImageIcon("src/images/fb.png");
        Image fbImage = fbIcon.getImage();
        int fbWidth = 50;
        int fbHeight = 30;
        Image resizedfbImage = fbImage.getScaledInstance(fbWidth, fbHeight, Image.SCALE_SMOOTH);
        JLabel fbLabel = new JLabel(new ImageIcon(resizedfbImage));
        String fbLink = "https://www.facebook.com/profile.php?id=100035724995456&mibextid=9R9pXO";

        fbLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(fbLink));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
        });

        JLabel titleLabel = new JLabel("WELCOME TO E-VOTING SYSTEM");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 54));
        JLabel usernameLabel = new JLabel("USERNAME  :");
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        JLabel passwordLabel = new JLabel("PASSWORD :");
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Tahoma", Font.BOLD, 16));
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Tahoma", Font.BOLD, 16));
        usernameField.setBorder(customBorder);
        usernameField.setForeground(Color.BLACK);
        usernameField.setOpaque(false);
        usernameField.setBackground(contentPaneBackgroundColor);
        passwordField.setBorder(customBorder);
        passwordField.setForeground(Color.BLACK);
        passwordField.setOpaque(false);
        passwordField.setBackground(contentPaneBackgroundColor);

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        passwordStrengthLabel = new JLabel("Password Strength: ");
        passwordStrengthLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordStrengthLabel.setForeground(Color.GRAY);
        passwordStrengthLabel.setVisible(false);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 22));
        JButton signUpButton = new JButton("SIGN UP");
        signUpButton.setFont(new Font("Tahoma", Font.BOLD, 22));
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setFont(new Font("Tahoma", Font.BOLD, 18));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(-50, 0, 100, 0);
        add(titleLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(100, 250, 10, 4);
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(100, 0, 10, 10);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 10, 10, 5);
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(7, 0, 9, 10);
        add(passwordStrengthLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, -40, 12, 0);
        add(forgotPasswordButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 0, 100, 220);
        add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(50, 0, 100, 200);
        add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 0, 10, -20);
        add(logoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 0, 10, 65);
        add(fbLabel, gbc);

        titleLabel.setForeground(new Color(30, 80, 150));
        usernameLabel.setForeground(new Color(60, 60, 60));
        passwordLabel.setForeground(new Color(60, 60, 60));

        loginButton.setBackground(new Color(30, 80, 150));
        loginButton.setForeground(Color.WHITE);
        signUpButton.setBackground(new Color(30, 80, 150));
        signUpButton.setForeground(Color.WHITE);
        forgotPasswordButton.setBackground(new Color(30, 80, 150));
        forgotPasswordButton.setForeground(Color.WHITE);

        UIManager.put("OptionPane.background", new Color(240, 240, 240));
        UIManager.put("Panel.background", new Color(240, 240, 240));
        UIManager.put("OptionPane.messageForeground", new Color(60, 60, 60));

        UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 15));
        UIManager.put("OptionPane.titleText", "E-Voting System");

        UIManager.put("Button.background", new Color(30, 80, 150));
        UIManager.put("Button.foreground", Color.WHITE);

        loadUserCredentials();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (hasVoted) {
                    JOptionPane.showMessageDialog(VotingSystemUI.this, "You have already voted. You cannot vote again.", "Already Voted", JOptionPane.WARNING_MESSAGE);
                    return; // Do not allow the user to log in
                }

                if (isValidLogin(enteredUsername, enteredPassword)) {
                    openMainPage();
                } else {
                    JOptionPane.showMessageDialog(
                            VotingSystemUI.this, "Incorrect Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignUpWindow();
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(
                        VotingSystemUI.this, "Enter your Username:", "Forgot Password", JOptionPane.INFORMATION_MESSAGE);

                if (username != null && !username.isEmpty()) {
                    if (userDatabase.containsKey(username)) {
                        if (userPhoneNumber != null && !userPhoneNumber.isEmpty()) {
                            //sendPasswordRecoveryOtp(username, userPhoneNumber);
                        } else {
                            JOptionPane.showMessageDialog(
                                    VotingSystemUI.this, "Please enter a valid phone number.",
                                    "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                VotingSystemUI.this, "Username not found. Please enter a valid Username.",
                                "Invalid Username", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        successLabel = new JLabel("Successfully Voted", SwingConstants.CENTER);
        successLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        successLabel.setForeground(new Color(125,0,0));
        successLabel.setBackground(new Color(210, 207, 207));
        successLabel.setOpaque(true);
        successLabel.setVisible(false); // Initially, make it invisible
        add(successLabel); // Add the label to the frame

        JLabel footerLabel = new JLabel("© Made By - DHRUV, RAKSHIT & ABHISHEK", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        footerLabel.setForeground(new Color(30,80,150));
        footerLabel.setBackground(new Color(210, 207, 207));
        footerLabel.setOpaque(true);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(-50, 0, 50, 0);
        add(successLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 2);
        add(footerLabel, gbc);
    }
    private int generateRandomOtp() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }

    private void updatePasswordStrength() {
        String password = new String(passwordField.getPassword());
        PasswordStrength strength = calculatePasswordStrength(password);

        if (password.isEmpty()) {
            passwordStrengthLabel.setText("Password Strength: ");
            passwordStrengthLabel.setVisible(false);
        } else {
            passwordStrengthLabel.setText("Password Strength: " + strength.toString());
            passwordStrengthLabel.setVisible(true);

            if (strength == PasswordStrength.WEAK) {
                passwordStrengthLabel.setForeground(Color.RED);
            } else if (strength == PasswordStrength.MEDIUM) {
                passwordStrengthLabel.setForeground(Color.ORANGE);
            } else {
                passwordStrengthLabel.setForeground(Color.GREEN);
            }
        }
    }

    private enum PasswordStrength {
        WEAK("Weak"), MEDIUM("Medium"), STRONG("Strong");

        private final String label;

        PasswordStrength(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
    private PasswordStrength calculatePasswordStrength(String password) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        int strengthPoints = 0;

        if (hasUppercase) {
            strengthPoints++;
        }
        if (hasLowercase) {
            strengthPoints++;
        }
        if (hasDigit) {
            strengthPoints++;
        }
        if (hasSpecialChar) {
            strengthPoints++;
        }

        int length = password.length();
        if (length >= 8) {
            strengthPoints++;
        }
        if (length >= 12) {
            strengthPoints++;
        }

        if (strengthPoints <= 2) {
            return PasswordStrength.WEAK;
        } else if (strengthPoints <= 4) {
            return PasswordStrength.MEDIUM;
        } else {
            return PasswordStrength.STRONG;
        }
    }
    private void setGradientBackground(Color color1, Color color2) {
        setLayout(new BorderLayout());
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });

        contentPaneBackgroundColor = color1;
    }
    private void loadUserCredentials() {
        String url = "jdbc:mysql://localhost:3306/voting";
        String user = "root";
        String password = "Rakshit1";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String selectQuery = "SELECT username, password, phone FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String passwrd = resultSet.getString("password");
                    String phone = resultSet.getString("phone");
                    userDatabase.put(username, passwrd);
                    userPhoneNumbers.put(username, phone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserCredentials() {
        String url = "jdbc:mysql://localhost:3306/voting";
        String user = "root";
        String password = "Rakshit1";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String insertQuery = "INSERT INTO users (username, password, name, phone, state, email, voter_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                    String username = entry.getKey();
                    String passwrd = entry.getValue();
                    String phone = userPhoneNumbers.get(username);

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, passwrd);
                    preparedStatement.setString(3, "Name Value");  // Replace with actual name
                    preparedStatement.setString(4, "phone");
                    preparedStatement.setString(5, "State Value");  // Replace with actual state
                    preparedStatement.setString(6, "Email Value");  // Replace with actual email
                    preparedStatement.setString(7, "Voter ID Value");  // Replace with actual voter ID
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void storeUserDataInDatabase(String username, UserData userData) {
        try (Connection connection = MySQLConnection.getConnection()) {
            if (connection != null) {
                String insertQuery = "INSERT INTO users (username, name, state, voter_id) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, userData.getName());
                preparedStatement.setString(3, userData.getState());
                preparedStatement.setString(4, userData.getVoterId());
                preparedStatement.executeUpdate();
            } else {
                // Handle the case when the database connection fails
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        }
    }

    private void showSignUpWindow() {
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(500, 500);
        signUpFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        signUpFrame.setLocationRelativeTo(null);

        Border customBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("USERNAME:");
        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Tahoma", Font.BOLD, 13));

        JLabel passwordLabel = new JLabel("PASSWORD:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Tahoma", Font.BOLD, 13));

        JLabel nameLabel = new JLabel("NAME:");
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Tahoma", Font.BOLD, 13));

        JLabel phoneLabel = new JLabel("PHONE:");
        JTextField phoneField = new JTextField(15);
        phoneField.setFont(new Font("Tahoma", Font.BOLD, 13));

        JLabel stateLabel = new JLabel("STATE:");
        String[] stateOptions = {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"}; // Add your state options here
        JComboBox<String> stateComboBox = new JComboBox<>(stateOptions);
        stateComboBox.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel emailLabel = new JLabel("EMAIL ID:");
        JTextField emailField = new JTextField(15);
        emailField.setFont(new Font("Tahoma", Font.BOLD, 13));

        JLabel voterIdLabel = new JLabel("VOTER ID:");
        JTextField voterIdField = new JTextField(15);
        voterIdField.setFont(new Font("Tahoma", Font.BOLD, 13));

        JButton signUpConfirmButton = new JButton("Sign Up");
        Font labelFont = new Font("Tahoma", Font.BOLD, 16);
        usernameField.setBorder(customBorder);
        usernameField.setForeground(Color.BLACK);
        usernameField.setOpaque(false);
        usernameField.setBackground(contentPaneBackgroundColor);
        passwordField.setBorder(customBorder);
        passwordField.setForeground(Color.BLACK);
        passwordField.setOpaque(false);
        passwordField.setBackground(contentPaneBackgroundColor);
        nameField.setBorder(customBorder);
        nameField.setForeground(Color.BLACK);
        nameField.setOpaque(false);
        nameField.setBackground(contentPaneBackgroundColor);
        phoneField.setBorder(customBorder);
        phoneField.setForeground(Color.BLACK);
        phoneField.setOpaque(false);
        phoneField.setBackground(contentPaneBackgroundColor);
        emailField.setBorder(customBorder);
        emailField.setForeground(Color.BLACK);
        emailField.setOpaque(false);
        emailField.setBackground(contentPaneBackgroundColor);
        voterIdField.setBorder(customBorder);
        voterIdField.setForeground(Color.BLACK);
        voterIdField.setOpaque(false);
        voterIdField.setBackground(contentPaneBackgroundColor);

        JLabel usernameWarningLabel = new JLabel("Username already taken");
        usernameWarningLabel.setForeground(Color.RED);
        usernameWarningLabel.setVisible(false);

        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        phoneLabel.setFont(labelFont);
        stateLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        voterIdLabel.setFont(labelFont);
        signUpConfirmButton.setFont(labelFont);

        signUpConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText().trim();
                char[] passwordChars = passwordField.getPassword();
                String newPassword = new String(passwordChars);
                String newName = nameField.getText().trim();
                String newPhone = phoneField.getText().trim();
                String newState = (String) stateComboBox.getSelectedItem();
                String newEmail = emailField.getText().trim();
                String newVoterId = voterIdField.getText().trim();

                if (userDatabase.containsKey(newUsername)) {
                    usernameWarningLabel.setVisible(true);
                    return;
                } else {
                    usernameWarningLabel.setVisible(false);
                }

                if (isPhoneNumberTaken(newPhone)) {
                    JOptionPane.showMessageDialog(
                            signUpFrame, "The Phone number you provided is already\nassociated with a Voter-ID", "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Validate the fields and perform user registration
                if (newUsername.isEmpty() || newPassword.isEmpty() || newName.isEmpty() || newPhone.isEmpty() || newVoterId.isEmpty() ||
                        newState.equals("Select State") || !newName.matches("[a-zA-Z]+") ||
                        !newPhone.matches("\\d+") ||
                        newEmail.isEmpty() || !newEmail.contains("@") || !newEmail.endsWith("srmist.edu.in")) {
                    JOptionPane.showMessageDialog(
                            signUpFrame, "Please enter valid credentials.", "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    String hashedPassword = hashPassword(newPassword);
                    userPhoneNumber = newPhone;
                    userDatabase.put(newUsername, hashedPassword);
                    UserData userData = new UserData(newName, newState, newVoterId);
                    storeUserDataInDatabase(newUsername, userData);
                    saveUserCredentials();

                    JOptionPane.showMessageDialog(
                            signUpFrame, "Sign-up successful. You can now log in with the same credentials.",
                            "Sign Up", JOptionPane.INFORMATION_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    voterIdField.setText("");
                    signUpFrame.dispose();

                }
            }
        });


        signUpPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        signUpPanel.add(usernameWarningLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(phoneField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        signUpPanel.add(stateLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(stateComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        signUpPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        signUpPanel.add(voterIdLabel, gbc);
        gbc.gridx = 1;
        signUpPanel.add(voterIdField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        signUpPanel.add(signUpConfirmButton, gbc);

        signUpFrame.add(signUpPanel);
        signUpFrame.setVisible(true);
    }
    private boolean isPhoneNumberTaken(String phoneNumber) {
        for (String storedPhoneNumber : userPhoneNumbers.values()) {
            if (storedPhoneNumber != null && storedPhoneNumber.equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
    public boolean isValidLogin(String username, String password) {
        try (Connection connection = MySQLConnection.getConnection()) {
            if (connection != null) {
                String selectQuery = "SELECT password FROM users WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("password");
                    String hashedEnteredPassword = hashPassword(password);
                    return storedHashedPassword.equals(hashedEnteredPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void setBackgroundImage(String imagePath) {
        setLayout(new BorderLayout());
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(imagePath).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
    }

    private void openMainPage() {
        setVisible(false);

        SwingUtilities.invokeLater(() -> {
            main_page mainPage = new main_page();
            mainPage.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VotingSystemUI votingSystemUI = new VotingSystemUI();
            votingSystemUI.setVisible(true);
        });
    }
}
