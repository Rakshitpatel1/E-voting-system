import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.FileWriter;

public class main_page extends javax.swing.JFrame {
    public static int p1 = 0;
    public static int p2 = 0;
    public static int p3 = 0;
    public static int p4 = 0;
    public main_page() {
        initComponents();
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray
        connectToDatabase();
        readUserDetailsFromDatabase();
    }
    private void connectToDatabase() {
        try {
            // Provide your MySQL database URL, username, and password
            String url = "jdbc:mysql://localhost:3306/voting";
            String user = "root";
            String password = "Rakshit1";

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void readUserDetailsFromDatabase() {
        try {
            String query = "SELECT * FROM user_data";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String name = result.getString("username");
                String state = result.getString("state");
                String voterId = result.getString("voterId");

                jTextField1.setText(name);
                jTextField2.setText(voterId);
                jComboBoxStates.setSelectedItem(state);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertUserDetailsToDatabase(String name, String voterId, String state) {
        try {
            String query = "INSERT INTO user_data (username, voterId, state) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, voterId);
            statement.setString(3, state);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initComponents() {
        setBackgroundImage("src/images/bg1.png");
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        jTextField1 = new javax.swing.JTextField();
        JLabel jLabel3 = new JLabel();
        jTextField2 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton(); // New party
        JLabel jLabel4 = new JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBoxStates = new javax.swing.JComboBox<>();
        jComboBoxStates.setFont(new Font("Tahoma", Font.BOLD, 16));
        jComboBoxStates.setForeground(Color.BLACK);
        jComboBoxStates.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka",
                "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram",
                "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
                "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"
        }));
        Border customBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        ImageIcon profileImageIcon = new ImageIcon("src/images/profile.png");
        profileImageLabel = new JLabel(profileImageIcon);
        profileImageLabel.setBounds(10, 10, 150, 150);

        profileImageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                toggleMenu();
            }
        });

        getContentPane().add(profileImageLabel);
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton4.addActionListener(evt -> jButton4ActionPerformed(evt));

        // Create a sliding menu panel and add it to the layout
        menuPanel = new JPanel();
        menuPanel.setBounds(getWidth() - 100, 0, 300, getHeight());// Adjust the position and width as needed
        menuPanel.setBackground(Color.WHITE); // Customize the background color

        // Add components and options to the menu panel as needed
        // For example, you can add buttons or labels to represent menu items

        // Add the menu panel to the content pane
        getContentPane().add(menuPanel);

        jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 60));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel2.setBorder(BorderFactory.createEmptyBorder(10, 280, 5, 0));
        jLabel2.setFont(new Font("Tahoma", Font.BOLD, 20));
        jLabel3.setBorder(BorderFactory.createEmptyBorder(5, 280, 5, 0));
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 20));
        jLabel4.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jLabel4.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);

        UIManager.put("OptionPane.background", new Color(240, 240, 240)); // Light gray
        UIManager.put("Panel.background", new Color(240, 240, 240)); // Light gray
        UIManager.put("OptionPane.messageForeground", new Color(60, 60, 60)); // Dark gray

// Set the title font and foreground color for all dialogs
        UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 15));
        UIManager.put("OptionPane.titleText", "E-Voting System");

// Set the button colors for all dialogs
        UIManager.put("Button.background", new Color(30, 80, 150)); // Dark blue
        UIManager.put("Button.foreground", Color.WHITE);

        UIManager.put("ComboBox.background", contentPaneBackgroundColor);
        UIManager.put("ComboBox.selectionBackground", contentPaneBackgroundColor);

        jTextField1.setBorder(customBorder);
        jTextField1.setFont(new Font("Tahoma", Font.BOLD, 15));
        jTextField1.setForeground(Color.BLACK);
        jTextField1.setOpaque(false);
        jTextField1.setBackground(contentPaneBackgroundColor);
        jTextField2.setBorder(customBorder);
        jTextField2.setFont(new Font("Tahoma", Font.BOLD, 15));
        jTextField2.setForeground(Color.BLACK);
        jTextField2.setOpaque(false);
        jTextField2.setBackground(contentPaneBackgroundColor);

        jRadioButton1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jRadioButton2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jRadioButton3.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jRadioButton4.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // New party

        jButton1.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        jButton1.setFont(new Font("Tahoma", Font.BOLD, 19));
        jButton1.setForeground(Color.WHITE);

        jButton4.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        jButton4.setFont(new Font("Tahoma", Font.BOLD, 19));
        jButton4.setForeground(Color.WHITE);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jLabel1.setText("E-VOTING SYSTEM");
        jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 230));
        jLabel1.setForeground(new Color(30, 80, 150)); // Dark blue
        jLabel2.setText("YOUR NAME");
        jLabel2.setForeground(new Color(60, 60, 60)); // Dark gray
        jLabel3.setText("YOUR VOTER-ID");
        jLabel3.setForeground(new Color(60, 60, 60)); // Dark gray
        JLabel jLabel5 = new JLabel();
        jLabel5.setBorder(BorderFactory.createEmptyBorder(5, 280, 5, 0));
        jLabel5.setFont(new Font("Tahoma", Font.BOLD, 20));
        jLabel5.setText("YOUR STATE");
        jLabel5.setForeground(new Color(60, 60, 60));

        jRadioButton1.setText("BJP");
        jRadioButton1.addActionListener(this::jRadioButton1ActionPerformed);
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseExited(evt);
            }
        });
        jRadioButton2.setText("CONGRESS");
        jRadioButton2.addActionListener(evt -> jRadioButton2ActionPerformed(evt));
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseExited(evt);
            }
        });
        jRadioButton3.setText("AAP");
        jRadioButton3.addActionListener(evt -> jRadioButton3ActionPerformed(evt));
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseExited(evt);
            }
        });
        jRadioButton4.setText("BSP"); // New party
        jRadioButton4.addActionListener(evt -> jRadioButton4ActionPerformed(evt));
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jRadioButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jRadioButton4MouseExited(evt);
            }
        });
        jButton1.setText("SUBMIT YOUR VOTE");
        jButton1.setBackground(new Color(30, 80, 150));
        jButton1.setForeground(Color.WHITE);
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton4.setText("CHECK RESULTS");
        jButton4.setBackground(new Color(30, 80, 150));
        jButton4.setForeground(Color.WHITE);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited();
            }
        });

        Image originalImage1 = new ImageIcon("src/images/BJP.png").getImage();
        Image originalImage2 = new ImageIcon("src/images/CONGRESS.png").getImage();
        Image originalImage3 = new ImageIcon("src/images/AAP.png").getImage();
        Image originalImage4 = new ImageIcon("src/images/BSP.png").getImage();

        jRadioButton1.setContentAreaFilled(false);
        jRadioButton1.setBorder(null);
        jRadioButton2.setContentAreaFilled(false);
        jRadioButton2.setBorder(null);
        jRadioButton3.setContentAreaFilled(false);
        jRadioButton3.setBorder(null);
        jRadioButton4.setContentAreaFilled(false);
        jRadioButton4.setBorder(null);

        int scaledWidth = 150;
        int scaledHeight = 150;

        Image scaledImage1 = originalImage1.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        Image scaledImage2 = originalImage2.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        Image scaledImage3 = originalImage3.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        Image scaledImage4 = originalImage4.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);
        ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);

        JLabel partyLabel1 = new JLabel(scaledIcon1);
        JLabel partyLabel2 = new JLabel(scaledIcon2);
        JLabel partyLabel3 = new JLabel(scaledIcon3);
        JLabel partyLabel4 = new JLabel(scaledIcon4);

        jRadioButton1.setFont(new Font("Tahoma", Font.BOLD, 16));
        jRadioButton2.setFont(new Font("Tahoma", Font.BOLD, 16));
        jRadioButton3.setFont(new Font("Tahoma", Font.BOLD, 16));
        jRadioButton4.setFont(new Font("Tahoma", Font.BOLD, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(150, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                                                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // New "Enter State" label
                                                                .addGap(0, 0, 0)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jComboBoxStates, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jLabel4))
                                                                .addGap(220, 220, 220))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addGap(255, 255, 255))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jRadioButton1)
                                                                                        .addComponent(partyLabel1))
                                                                                .addGap(150, 150, 150)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jRadioButton2)
                                                                                        .addComponent(partyLabel2))
                                                                                .addGap(150, 150, 150)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jRadioButton3)
                                                                                        .addComponent(partyLabel3))
                                                                                .addGap(150, 150, 150)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jRadioButton4)
                                                                                        .addComponent(partyLabel4))) // New party
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(230, 230, 230)
                                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(100, 100, 100)
                                                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(150, Short.MAX_VALUE))))))
                        .addComponent(profileImageLabel, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBoxStates, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(partyLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(partyLabel2))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(partyLabel3))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(partyLabel4)))
                                .addGap(130, 130, 130)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1)
                                        .addComponent(jButton4))
                                .addContainerGap(50, Short.MAX_VALUE))
                        .addComponent(profileImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        if (jRadioButton1.isSelected()){
            jRadioButton2.setSelected(false);
            jRadioButton3.setSelected(false);
            jRadioButton4.setSelected(false);
        }
    }

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jRadioButton2.isSelected()){
            jRadioButton1.setSelected(false);
            jRadioButton3.setSelected(false);
            jRadioButton4.setSelected(false);
        }
    }

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jRadioButton3.isSelected()){
            jRadioButton2.setSelected(false);
            jRadioButton1.setSelected(false);
            jRadioButton4.setSelected(false);
        }
    }

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent ignoredEvt) {
        if (jRadioButton4.isSelected()){
            jRadioButton1.setSelected(false);
            jRadioButton2.setSelected(false);
            jRadioButton3.setSelected(false);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String name = jTextField1.getText().trim();
        String voterId = jTextField2.getText().trim();
        String state = (String) jComboBoxStates.getSelectedItem();
        final String[] votedParty = {""};

        if (name.isEmpty() || voterId.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter all the details");
            return;
        }

        JSONObject voteData = new JSONObject();
        voteData.put("username", name);
        voteData.put("voterId", voterId);
        voteData.put("state", state);
        voteData.put("votedParty", votedParty[0]);

        try {
            FileWriter fileWriter = new FileWriter("src/votes.json", true); // Append mode
            fileWriter.write(voteData.toString() + System.lineSeparator());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(600);

                if (jRadioButton1.isSelected()) {
                    votedParty[0] = jRadioButton1.getText();
                    p1++;
                } else if (jRadioButton2.isSelected()) {
                    votedParty[0] = jRadioButton2.getText();
                    p2++;
                } else if (jRadioButton3.isSelected()) {
                    votedParty[0] = jRadioButton3.getText();
                    p3++;
                } else if (jRadioButton4.isSelected()) {
                    votedParty[0] = jRadioButton4.getText();
                    p4++;
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Select a Party");
                }
                VotingSystemUI loginPage = new VotingSystemUI();
                loginPage.setHasVoted(true);
                loginPage.setVisible(true); // Show the login/signup page
                loginPage.successLabel.setVisible(true);
                dispose();

                return null;
            }


            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);

                jTextField1.setText("");
                jTextField2.setText("");
                jRadioButton1.setSelected(false);
                jRadioButton2.setSelected(false);
                jRadioButton3.setSelected(false);
                jRadioButton4.setSelected(false);
            }
        };

        worker.execute();

        int confirm = JOptionPane.showConfirmDialog(rootPane, progressBar, "Processing Vote", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (confirm == JOptionPane.CLOSED_OPTION) {
            worker.cancel(true);
        }
    }
    private void toggleMenu() {
        if (isMenuVisible) {
            // Slide the menu out of view
            menuPanel.setBounds(-300, 0, 300, getHeight()); // Adjust the position and width as needed
            isMenuVisible = false;
        } else {
            // Slide the menu into view
            menuPanel.setBounds(1200, 50, 300, 600); // Adjust the position and width as needed
            isMenuVisible = true;
        }
    }
    private void setBackgroundImage(String imagePath) {
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(imagePath).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent ignoredEvt) {
        int confirm = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to check the results?", "Confirm Check Results", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Check results and display message
            List<String> partyNames = new ArrayList<>();
            List<Integer> partyVotes = new ArrayList<>();

            partyNames.add("BJP");
            partyNames.add("CONGRESS");
            partyNames.add("AAP");
            partyNames.add("BSP");

            partyVotes.add(p1);
            partyVotes.add(p2);
            partyVotes.add(p3);
            partyVotes.add(p4);

            int maxVotes = -1;
            int winningPartyIndex = -1;
            boolean tie = false;

            for (int i = 0; i < partyVotes.size(); i++) {
                if (partyVotes.get(i) > maxVotes) {
                    maxVotes = partyVotes.get(i);
                    winningPartyIndex = i;
                    tie = false;
                } else if (partyVotes.get(i) == maxVotes) {
                    tie = true;
                }
            }

            DefaultPieDataset dataset = new DefaultPieDataset();
            for (int i = 0; i < partyNames.size(); i++) {
                dataset.setValue(partyNames.get(i), partyVotes.get(i));
            }

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Election Results",
                    dataset,
                    true,
                    true,
                    false
            );

            PiePlot plot = (PiePlot) pieChart.getPlot();
            plot.setSectionPaint("BJP", Color.BLUE);
            plot.setSectionPaint("CONGRESS", Color.RED);
            plot.setSectionPaint("AAP", Color.GREEN);
            plot.setSectionPaint("BSP", Color.ORANGE);

            String resultMessage;

            if (!tie) {
                String winningParty = partyNames.get(winningPartyIndex);
                resultMessage = winningParty + " has won the election with " + maxVotes + " votes!";
            } else {
                resultMessage = "It is difficult to determine the winner,\n" +
                        "As more than one party has the same number of votes";
            }

            JPanel chartPanel = new ChartPanel(pieChart);
            JOptionPane.showMessageDialog(rootPane, chartPanel, "Election Results", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(rootPane, resultMessage, "Election Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void jRadioButton1MouseEntered(java.awt.event.MouseEvent evt) {
        jRadioButton1.setForeground(new Color(30, 80, 150));
    }

    private void jRadioButton1MouseExited(MouseEvent ignoredEvt) {
        if (!jRadioButton1.isSelected()) {
            jRadioButton1.setForeground(Color.BLACK);
        }
    }

    private void jRadioButton2MouseEntered(java.awt.event.MouseEvent evt) {
        jRadioButton2.setForeground(new Color(30, 80, 150));
    }

    private void jRadioButton2MouseExited(java.awt.event.MouseEvent ignoredEvt) {
        if (!jRadioButton2.isSelected()) {
            jRadioButton2.setForeground(Color.BLACK);
        }
    }

    private void jRadioButton3MouseEntered(java.awt.event.MouseEvent ignoredEvt) {
        jRadioButton3.setForeground(new Color(30, 80, 150));
    }

    private void jRadioButton3MouseExited(java.awt.event.MouseEvent ignoredEvt) {
        if (!jRadioButton3.isSelected()) {
            jRadioButton3.setForeground(Color.BLACK);
        }
    }

    private void jRadioButton4MouseEntered(java.awt.event.MouseEvent evt) {
        jRadioButton4.setForeground(new Color(30, 80, 150)); // Dark blue
    }

    private void jRadioButton4MouseExited(java.awt.event.MouseEvent evt) {
        if (!jRadioButton4.isSelected()) {
            jRadioButton4.setForeground(Color.BLACK);
        }
    }

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {
        jButton1.setBackground(new Color(60, 100, 170)); // Slightly lighter blue
    }

    private void jButton1MouseExited(java.awt.event.MouseEvent ignoredEvt) {
        jButton1.setBackground(new Color(30, 80, 150)); // Dark blue
    }

    private void jButton4MouseEntered(java.awt.event.MouseEvent ignoredEvt) {
        jButton4.setBackground(new Color(60, 100, 170)); // Slightly lighter blue
    }

    private void jButton4MouseExited() {
        jButton4.setBackground(new Color(30, 80, 150)); // Dark blue
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            main_page mainPage = new main_page();
            mainPage.setVisible(true);
        });
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JComboBox<String> jComboBoxStates;
    private Color contentPaneBackgroundColor;
    private JLabel profileImageLabel;
    private JPanel menuPanel;
    private boolean isMenuVisible = false;
    private static Connection conn;
}
