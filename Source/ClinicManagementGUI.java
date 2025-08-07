import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ClinicManagementGUI extends JFrame {
    private static ArrayList<String> doctorList = new ArrayList<>();
    private static ArrayList<Booking> bookingList = new ArrayList<>();

    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Main components
    private JPanel welcomePanel;
    private JPanel patientPanel;
    private JPanel doctorPanel;
    private JPanel adminPanel;
    private JPanel adminLoginPanel;

    public ClinicManagementGUI() {
        setTitle("Sushrut Clinic Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the card layout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Setup individual panels
        setupWelcomePanel();
        setupPatientPanel();
        setupDoctorPanel();
        setupAdminLoginPanel();
        setupAdminPanel();

        // Add panels to the main panel with card layout
        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(patientPanel, "Patient");
        mainPanel.add(doctorPanel, "Doctor");
        mainPanel.add(adminLoginPanel, "AdminLogin");
        mainPanel.add(adminPanel, "Admin");

        // Show welcome panel initially
        cardLayout.show(mainPanel, "Welcome");

        // Add the main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private void setupWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());

        // Create a title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Welcome to Sushrut Clinic!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton patientButton = new JButton("Patient");
        JButton doctorButton = new JButton("Doctor");
        JButton adminButton = new JButton("Admin");

        patientButton.setFont(new Font("Arial", Font.PLAIN, 18));
        doctorButton.setFont(new Font("Arial", Font.PLAIN, 18));
        adminButton.setFont(new Font("Arial", Font.PLAIN, 18));

        // Style the buttons
        patientButton.setBackground(new Color(70, 130, 180));
        patientButton.setForeground(Color.WHITE);
        doctorButton.setBackground(new Color(46, 139, 87));
        doctorButton.setForeground(Color.WHITE);
        adminButton.setBackground(new Color(178, 34, 34));
        adminButton.setForeground(Color.WHITE);

        // Add action listeners
        patientButton.addActionListener(e -> cardLayout.show(mainPanel, "Patient"));
        doctorButton.addActionListener(e -> cardLayout.show(mainPanel, "Doctor"));
        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminLogin"));

        // Add buttons to panel
        buttonsPanel.add(patientButton);
        buttonsPanel.add(doctorButton);
        buttonsPanel.add(adminButton);

        // Add components to welcome panel
        welcomePanel.add(titlePanel, BorderLayout.NORTH);
        welcomePanel.add(buttonsPanel, BorderLayout.CENTER);

        // Add a footer
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("© 2025 Sushrut Clinic - All Rights Reserved");
        footerPanel.add(footerLabel);
        welcomePanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void setupPatientPanel() {
        patientPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Patient Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);

        // Content
        JPanel contentPanel = new JPanel(new CardLayout());
        CardLayout patientCardLayout = (CardLayout) contentPanel.getLayout();

        // Patient menu panel
        JPanel patientMenuPanel = new JPanel(new GridLayout(2, 1, 10, 20));
        patientMenuPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton bookAppointmentButton = new JButton("Book an Appointment");
        JButton viewAppointmentsButton = new JButton("View Your Appointments");

        patientMenuPanel.add(bookAppointmentButton);
        patientMenuPanel.add(viewAppointmentsButton);

        // Book appointment panel
        JPanel bookAppointmentPanel = new JPanel();
        bookAppointmentPanel.setLayout(new BoxLayout(bookAppointmentPanel, BoxLayout.Y_AXIS));
        bookAppointmentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField patientNameField = new JTextField(20);
        JTextField dateField = new JTextField("YYYY-MM-DD", 20);
        JTextField timeField = new JTextField("HH:MM AM/PM", 20);
        JComboBox<String> doctorComboBox = new JComboBox<>();

        // Add sample doctors if list is empty
        if (doctorList.isEmpty()) {
            doctorList.add("Dr. Smith");
            doctorList.add("Dr. Johnson");
            doctorList.add("Dr. Williams");
        }

        for (String doctor : doctorList) {
            doctorComboBox.addItem(doctor);
        }

        JButton submitBookingButton = new JButton("Book Appointment");
        JButton backToPatientMenuButton = new JButton("Back to Patient Menu");

        // Add components with labels
        addLabelAndComponent(bookAppointmentPanel, "Your Name:", patientNameField);
        addLabelAndComponent(bookAppointmentPanel, "Date:", dateField);
        addLabelAndComponent(bookAppointmentPanel, "Time:", timeField);
        addLabelAndComponent(bookAppointmentPanel, "Select Doctor:", doctorComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(submitBookingButton);
        buttonPanel.add(backToPatientMenuButton);

        bookAppointmentPanel.add(Box.createVerticalStrut(20));
        bookAppointmentPanel.add(buttonPanel);

        // View appointments panel
        JPanel viewAppointmentsPanel = new JPanel(new BorderLayout());
        viewAppointmentsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Enter Your Name: ");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton backFromViewButton = new JButton("Back to Patient Menu");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(backFromViewButton);

        // Table for appointments
        String[] columnNames = {"Patient Name", "Doctor Name", "Date", "Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable appointmentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);

        viewAppointmentsPanel.add(searchPanel, BorderLayout.NORTH);
        viewAppointmentsPanel.add(scrollPane, BorderLayout.CENTER);

        // Add sub-panels to content panel
        contentPanel.add(patientMenuPanel, "PatientMenu");
        contentPanel.add(bookAppointmentPanel, "BookAppointment");
        contentPanel.add(viewAppointmentsPanel, "ViewAppointments");

        // Add content panel to main patient panel
        patientPanel.add(headerPanel, BorderLayout.NORTH);
        patientPanel.add(contentPanel, BorderLayout.CENTER);

        // Action listeners
        bookAppointmentButton.addActionListener(e -> patientCardLayout.show(contentPanel, "BookAppointment"));

        viewAppointmentsButton.addActionListener(e -> {
            patientCardLayout.show(contentPanel, "ViewAppointments");
            searchField.setText("");
            tableModel.setRowCount(0); // Clear table
        });

        backToPatientMenuButton.addActionListener(e -> patientCardLayout.show(contentPanel, "PatientMenu"));
        backFromViewButton.addActionListener(e -> patientCardLayout.show(contentPanel, "PatientMenu"));

        submitBookingButton.addActionListener(e -> {
            String patientName = patientNameField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String doctorName = (String) doctorComboBox.getSelectedItem();

            if (patientName.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Booking booking = new Booking(patientName, doctorName, date, time);
            bookingList.add(booking);

            JOptionPane.showMessageDialog(this, "Appointment booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            patientNameField.setText("");
            dateField.setText("YYYY-MM-DD");
            timeField.setText("HH:MM AM/PM");

            patientCardLayout.show(contentPanel, "PatientMenu");
        });

        searchButton.addActionListener(e -> {
            String patientName = searchField.getText().trim();

            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0); // Clear table
            boolean found = false;

            for (Booking booking : bookingList) {
                if (booking.patientName.equalsIgnoreCase(patientName)) {
                    tableModel.addRow(new Object[]{
                            booking.patientName,
                            booking.doctorName,
                            booking.date,
                            booking.time
                    });
                    found = true;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No appointments found for " + patientName, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void setupDoctorPanel() {
        doctorPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Doctor Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel nameLabel = new JLabel("Enter your name:");
        JTextField nameField = new JTextField(20);
        JButton loginButton = new JButton("Login");

        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setMaximumSize(new Dimension(300, 30));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginPanel.add(nameLabel);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(nameField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(loginButton);

        // Doctor Menu Panel
        JPanel doctorMenuPanel = new JPanel(new BorderLayout());

        // Doctor menu options
        JPanel menuOptionsPanel = new JPanel();
        menuOptionsPanel.setLayout(new BoxLayout(menuOptionsPanel, BoxLayout.Y_AXIS));
        menuOptionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel welcomeDoctorLabel = new JLabel("Welcome, Doctor");
        welcomeDoctorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeDoctorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton viewAllAppointmentsButton = new JButton("View All Appointments");
        JButton searchPatientButton = new JButton("Search for Patient");

        buttonPanel.add(viewAllAppointmentsButton);
        buttonPanel.add(searchPatientButton);

        menuOptionsPanel.add(welcomeDoctorLabel);
        menuOptionsPanel.add(Box.createVerticalStrut(30));
        menuOptionsPanel.add(buttonPanel);

        // Table panel for appointments
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Patient Name", "Date", "Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable appointmentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);

        JPanel searchPatientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchPatientLabel = new JLabel("Patient Name:");
        JTextField searchPatientField = new JTextField(20);
        JButton searchPatientButton2 = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        searchPatientPanel.add(searchPatientLabel);
        searchPatientPanel.add(searchPatientField);
        searchPatientPanel.add(searchPatientButton2);
        searchPatientPanel.add(clearButton);

        tablePanel.add(searchPatientPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create the card layout for doctor content
        JPanel doctorContentPanel = new JPanel(new CardLayout());
        CardLayout doctorCardLayout = (CardLayout) doctorContentPanel.getLayout();

        doctorContentPanel.add(menuOptionsPanel, "DoctorMenu");
        doctorContentPanel.add(tablePanel, "TableView");

        doctorMenuPanel.add(doctorContentPanel, BorderLayout.CENTER);

        // Main card layout for doctor panel
        CardLayout doctorMainLayout = new CardLayout();
        JPanel doctorMainPanel = new JPanel(doctorMainLayout);

        doctorMainPanel.add(loginPanel, "Login");
        doctorMainPanel.add(doctorMenuPanel, "Menu");

        doctorPanel.add(headerPanel, BorderLayout.NORTH);
        doctorPanel.add(doctorMainPanel, BorderLayout.CENTER);

        // Action listeners
        loginButton.addActionListener(e -> {
            String doctorName = nameField.getText().trim();

            if (doctorName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean authorized = false;
            for (String doctor : doctorList) {
                if (doctor.equalsIgnoreCase(doctorName)) {
                    authorized = true;
                    break;
                }
            }

            if (authorized) {
                welcomeDoctorLabel.setText("Welcome, Dr. " + doctorName);
                doctorMainLayout.show(doctorMainPanel, "Menu");

                // Store doctor name for search
                doctorMenuPanel.putClientProperty("currentDoctor", doctorName);
            } else {
                JOptionPane.showMessageDialog(this, "Access denied! Doctor not found in the system.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewAllAppointmentsButton.addActionListener(e -> {
            String doctorName = (String) doctorMenuPanel.getClientProperty("currentDoctor");

            tableModel.setRowCount(0); // Clear table

            for (Booking booking : bookingList) {
                if (booking.doctorName.equalsIgnoreCase(doctorName)) {
                    tableModel.addRow(new Object[]{
                            booking.patientName,
                            booking.date,
                            booking.time
                    });
                }
            }

            searchPatientField.setText("");
            doctorCardLayout.show(doctorContentPanel, "TableView");
        });

        searchPatientButton.addActionListener(e -> {
            searchPatientField.setText("");
            tableModel.setRowCount(0);
            doctorCardLayout.show(doctorContentPanel, "TableView");
        });

        searchPatientButton2.addActionListener(e -> {
            String doctorName = (String) doctorMenuPanel.getClientProperty("currentDoctor");
            String patientName = searchPatientField.getText().trim();

            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a patient name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0); // Clear table
            boolean found = false;

            for (Booking booking : bookingList) {
                if (booking.doctorName.equalsIgnoreCase(doctorName) &&
                        booking.patientName.equalsIgnoreCase(patientName)) {
                    tableModel.addRow(new Object[]{
                            booking.patientName,
                            booking.date,
                            booking.time
                    });
                    found = true;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No appointments found for patient: " + patientName, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            searchPatientField.setText("");
            String doctorName = (String) doctorMenuPanel.getClientProperty("currentDoctor");

            tableModel.setRowCount(0); // Clear table

            for (Booking booking : bookingList) {
                if (booking.doctorName.equalsIgnoreCase(doctorName)) {
                    tableModel.addRow(new Object[]{
                            booking.patientName,
                            booking.date,
                            booking.time
                    });
                }
            }
        });
    }

    private void setupAdminLoginPanel() {
        adminLoginPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);

        // Login Form
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new BoxLayout(loginFormPanel, BoxLayout.Y_AXIS));
        loginFormPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(300, 30));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(300, 30));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginFormPanel.add(usernameLabel);
        loginFormPanel.add(Box.createVerticalStrut(10));
        loginFormPanel.add(usernameField);
        loginFormPanel.add(Box.createVerticalStrut(20));
        loginFormPanel.add(passwordLabel);
        loginFormPanel.add(Box.createVerticalStrut(10));
        loginFormPanel.add(passwordField);
        loginFormPanel.add(Box.createVerticalStrut(30));
        loginFormPanel.add(loginButton);

        adminLoginPanel.add(headerPanel, BorderLayout.NORTH);
        adminLoginPanel.add(loginFormPanel, BorderLayout.CENTER);

        // Add action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check credentials (match the original code)
                if ((username.equals("abc") && password.equals("1234")) ||
                        (username.equals("xyz") && password.equals("45678"))) {

                    // Update welcome message in admin panel
                    JLabel welcomeLabel = (JLabel) adminPanel.getClientProperty("welcomeLabel");
                    if (welcomeLabel != null) {
                        welcomeLabel.setText("Welcome, " + username);
                    }

                    // Clear fields
                    usernameField.setText("");
                    passwordField.setText("");

                    cardLayout.show(mainPanel, "Admin");
                } else {
                    JOptionPane.showMessageDialog(ClinicManagementGUI.this,
                            "Invalid username or password",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupAdminPanel() {
        adminPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Welcome message
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomePanel.add(welcomeLabel);

        // Store reference to welcome label
        adminPanel.putClientProperty("welcomeLabel", welcomeLabel);

        // Main content panel with card layout
        JPanel contentPanel = new JPanel(new CardLayout());
        CardLayout adminCardLayout = (CardLayout) contentPanel.getLayout();

        // Admin menu panel
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton viewAllAppointmentsButton = new JButton("View All Appointments");
        JButton viewAllDoctorsButton = new JButton("View All Doctors");
        JButton deleteAppointmentButton = new JButton("Delete Appointment");
        JButton addDoctorButton = new JButton("Add Doctor");

        menuPanel.add(viewAllAppointmentsButton);
        menuPanel.add(viewAllDoctorsButton);
        menuPanel.add(deleteAppointmentButton);
        menuPanel.add(addDoctorButton);

        // Appointments panel
        JPanel appointmentsPanel = new JPanel(new BorderLayout());
        appointmentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel appointmentsTitle = new JLabel("All Appointments");
        appointmentsTitle.setFont(new Font("Arial", Font.BOLD, 16));

        String[] appointmentColumns = {"Patient Name", "Doctor Name", "Date", "Time"};
        DefaultTableModel appointmentsModel = new DefaultTableModel(appointmentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable appointmentsTable = new JTable(appointmentsModel);
        JScrollPane appointmentsScrollPane = new JScrollPane(appointmentsTable);

        JButton backFromAppointmentsButton = new JButton("Back to Admin Menu");

        appointmentsPanel.add(appointmentsTitle, BorderLayout.NORTH);
        appointmentsPanel.add(appointmentsScrollPane, BorderLayout.CENTER);
        appointmentsPanel.add(backFromAppointmentsButton, BorderLayout.SOUTH);

        // Doctors panel
        JPanel doctorsPanel = new JPanel(new BorderLayout());
        doctorsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel doctorsTitle = new JLabel("All Doctors");
        doctorsTitle.setFont(new Font("Arial", Font.BOLD, 16));

        DefaultListModel<String> doctorsListModel = new DefaultListModel<>();
        JList<String> doctorsList = new JList<>(doctorsListModel);
        JScrollPane doctorsScrollPane = new JScrollPane(doctorsList);

        JButton backFromDoctorsButton = new JButton("Back to Admin Menu");

        doctorsPanel.add(doctorsTitle, BorderLayout.NORTH);
        doctorsPanel.add(doctorsScrollPane, BorderLayout.CENTER);
        doctorsPanel.add(backFromDoctorsButton, BorderLayout.SOUTH);

        // Delete appointment panel
        JPanel deleteAppointmentPanel = new JPanel(new BorderLayout());
        deleteAppointmentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel deleteFormPanel = new JPanel();
        deleteFormPanel.setLayout(new BoxLayout(deleteFormPanel, BoxLayout.Y_AXIS));

        JLabel deleteTitleLabel = new JLabel("Delete Appointment");
        deleteTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        deleteTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel patientNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel patientNameLabel = new JLabel("Patient Name:");
        JTextField patientNameField = new JTextField(20);
        patientNamePanel.add(patientNameLabel);
        patientNamePanel.add(patientNameField);

        JPanel doctorNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        JTextField doctorNameField = new JTextField(20);
        doctorNamePanel.add(doctorNameLabel);
        doctorNamePanel.add(doctorNameField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteButton = new JButton("Delete");
        JButton backFromDeleteButton = new JButton("Back to Admin Menu");
        buttonPanel.add(deleteButton);
        buttonPanel.add(backFromDeleteButton);

        deleteFormPanel.add(deleteTitleLabel);
        deleteFormPanel.add(Box.createVerticalStrut(20));
        deleteFormPanel.add(patientNamePanel);
        deleteFormPanel.add(doctorNamePanel);
        deleteFormPanel.add(Box.createVerticalStrut(20));
        deleteFormPanel.add(buttonPanel);

        deleteAppointmentPanel.add(deleteFormPanel, BorderLayout.CENTER);

        // Add doctor panel
        JPanel addDoctorPanel = new JPanel(new BorderLayout());
        addDoctorPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel addDoctorFormPanel = new JPanel();
        addDoctorFormPanel.setLayout(new BoxLayout(addDoctorFormPanel, BoxLayout.Y_AXIS));

        JLabel addDoctorTitleLabel = new JLabel("Add Doctor");
        addDoctorTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addDoctorTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel addDoctorNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel addDoctorNameLabel = new JLabel("Doctor Name:");
        JTextField addDoctorNameField = new JTextField(20);
        addDoctorNamePanel.add(addDoctorNameLabel);
        addDoctorNamePanel.add(addDoctorNameField);

        JPanel addDoctorButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Doctor");
        JButton backFromAddButton = new JButton("Back to Admin Menu");
        addDoctorButtonPanel.add(addButton);
        addDoctorButtonPanel.add(backFromAddButton);
        addDoctorButtonPanel.add(backFromAddButton);
        
        addDoctorFormPanel.add(addDoctorTitleLabel);
        addDoctorFormPanel.add(Box.createVerticalStrut(20));
        addDoctorFormPanel.add(addDoctorNamePanel);
        addDoctorFormPanel.add(Box.createVerticalStrut(20));
        addDoctorFormPanel.add(addDoctorButtonPanel);
        
        addDoctorPanel.add(addDoctorFormPanel, BorderLayout.CENTER);

// Add panels to content panel
        contentPanel.add(menuPanel, "Menu");
        contentPanel.add(appointmentsPanel, "Appointments");
        contentPanel.add(doctorsPanel, "Doctors");
        contentPanel.add(deleteAppointmentPanel, "DeleteAppointment");
        contentPanel.add(addDoctorPanel, "AddDoctor");

// Add components to admin panel
JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(headerPanel, BorderLayout.NORTH);
        northPanel.add(welcomePanel, BorderLayout.CENTER);
        
        adminPanel.add(northPanel, BorderLayout.NORTH);
        adminPanel.add(contentPanel, BorderLayout.CENTER);

// Action listeners for admin panel
        viewAllAppointmentsButton.addActionListener(e -> {
        appointmentsModel.setRowCount(0); // Clear table
            
            for (Booking booking : bookingList) {
        appointmentsModel.addRow(new Object[]{
    booking.patientName,
            booking.doctorName,
            booking.date,
            booking.time
});
        }

        adminCardLayout.show(contentPanel, "Appointments");
        });

                viewAllDoctorsButton.addActionListener(e -> {
        doctorsListModel.clear(); // Clear list
            
            if (doctorList.isEmpty()) {
        doctorsListModel.addElement("No doctors available");
            } else {
                    for (String doctor : doctorList) {
        doctorsListModel.addElement(doctor);
                }
                        }

                        adminCardLayout.show(contentPanel, "Doctors");
        });

                deleteAppointmentButton.addActionListener(e -> {
        patientNameField.setText("");
            doctorNameField.setText("");
            adminCardLayout.show(contentPanel, "DeleteAppointment");
        });

                addDoctorButton.addActionListener(e -> {
        addDoctorNameField.setText("");
            adminCardLayout.show(contentPanel, "AddDoctor");
        });

                backFromAppointmentsButton.addActionListener(e -> adminCardLayout.show(contentPanel, "Menu"));
        backFromDoctorsButton.addActionListener(e -> adminCardLayout.show(contentPanel, "Menu"));
        backFromDeleteButton.addActionListener(e -> adminCardLayout.show(contentPanel, "Menu"));
        backFromAddButton.addActionListener(e -> adminCardLayout.show(contentPanel, "Menu"));

        deleteButton.addActionListener(e -> {
String patientName = patientNameField.getText().trim();
String doctorName = doctorNameField.getText().trim();
            
            if (patientName.isEmpty() || doctorName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
                        }

boolean found = false;
Iterator<Booking> iterator = bookingList.iterator();
            
            while (iterator.hasNext()) {
Booking booking = iterator.next();
                if (booking.patientName.equalsIgnoreCase(patientName) &&
        booking.doctorName.equalsIgnoreCase(doctorName)) {
        iterator.remove();
found = true;
        break;
        }
        }

        if (found) {
        JOptionPane.showMessageDialog(this, "Appointment removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                patientNameField.setText("");
                doctorNameField.setText("");
                adminCardLayout.show(contentPanel, "Menu");
            } else {
                    JOptionPane.showMessageDialog(this, "No matching appointment found", "Error", JOptionPane.ERROR_MESSAGE);
            }
                    });

                    addButton.addActionListener(e -> {
String doctorName = addDoctorNameField.getText().trim();
            
            if (doctorName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter doctor name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
                        }

                        doctorList.add(doctorName);
            JOptionPane.showMessageDialog(this, "Doctor added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            addDoctorNameField.setText("");
            adminCardLayout.show(contentPanel, "Menu");
        });
                }

private void addLabelAndComponent(JPanel panel, String labelText, JComponent component) {
    JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel label = new JLabel(labelText);
    label.setPreferredSize(new Dimension(120, 20));
    rowPanel.add(label);
    rowPanel.add(component);
    panel.add(rowPanel);
    panel.add(Box.createVerticalStrut(10));
}

// Booking class to store appointment data
private static class Booking {
    String patientName;
    String doctorName;
    String date;
    String time;

    public Booking(String patientName, String doctorName, String date, String time) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }
}

public static void main(String[] args) {
    // Add some sample data
    doctorList.add("Dr. Smith");
    doctorList.add("Dr. Johnson");
    doctorList.add("Dr. Williams");

    bookingList.add(new Booking("John Doe", "Dr. Smith", "2025-04-20", "10:30 AM"));
    bookingList.add(new Booking("Jane Smith", "Dr. Johnson", "2025-04-21", "2:15 PM"));

    // Set look and feel to system default
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Launch the application on EDT
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new ClinicManagementGUI();
        }
    });
}
}