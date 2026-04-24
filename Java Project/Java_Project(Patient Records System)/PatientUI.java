import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;

public class PatientUI extends JFrame {

    // ===== COLOR PALETTE =====
    private static final Color PRIMARY       = new Color(37, 99, 235);   // Blue
    private static final Color PRIMARY_DARK  = new Color(29, 78, 216);
    private static final Color PRIMARY_LIGHT = new Color(59, 130, 246);
    private static final Color ACCENT        = new Color(16, 185, 129);  // Green
    private static final Color ACCENT_DARK   = new Color(5, 150, 105);
    private static final Color DANGER        = new Color(239, 68, 68);   // Red
    private static final Color DANGER_DARK   = new Color(220, 38, 38);
    private static final Color BG_DARK       = new Color(15, 23, 42);    // Dark background
    private static final Color BG_CARD       = new Color(30, 41, 59);    // Card background
    private static final Color BG_INPUT      = new Color(51, 65, 85);    // Input background
    private static final Color TEXT_PRIMARY   = new Color(241, 245, 249);
    private static final Color TEXT_SECONDARY = new Color(148, 163, 184);
    private static final Color BORDER_COLOR  = new Color(71, 85, 105);
    private static final Color TABLE_ALT_ROW = new Color(26, 36, 52);

    // ===== FONTS =====
    private static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_LABEL   = new Font("Segoe UI Semibold", Font.PLAIN, 14);
    private static final Font FONT_INPUT   = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON  = new Font("Segoe UI Semibold", Font.PLAIN, 13);
    private static final Font FONT_TABLE_H = new Font("Segoe UI Semibold", Font.PLAIN, 13);
    private static final Font FONT_TABLE   = new Font("Segoe UI", Font.PLAIN, 13);

    JTextField nameField, ageField, diseaseField;
    JTable table;
    String selectedFile = "";
    JLabel fileLabel;

    public PatientUI() {
        setTitle("Patient Record System");
        setSize(900, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        // Main container with dark background
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(BG_DARK);

        // ===== HEADER =====
        mainPanel.add(createHeader(), BorderLayout.NORTH);

        // ===== CONTENT =====
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(BG_DARK);
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        contentPanel.add(createFormCard(), BorderLayout.NORTH);
        contentPanel.add(createTableCard(), BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // ==================== HEADER ====================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, getWidth(), 0, new Color(109, 40, 217));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 100));
        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Icon + Title
        JLabel icon = new JLabel("\u2695");   // Medical symbol
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 46));
        icon.setForeground(Color.WHITE);

        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 0, 2));
        titleGroup.setOpaque(false);

        JLabel title = new JLabel("Patient Record System");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage patient records, reports and medical files");
        subtitle.setFont(FONT_SUBTITLE);
        subtitle.setForeground(new Color(255, 255, 255, 180));

        titleGroup.add(title);
        titleGroup.add(subtitle);

        JPanel left = new JPanel(new GridBagLayout());
        left.setOpaque(false);
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.gridx = 0;
        gbcH.gridy = 0;
        gbcH.insets = new Insets(0, 0, 0, 15);
        left.add(icon, gbcH);

        gbcH.gridx = 1;
        gbcH.insets = new Insets(0, 0, 0, 0);
        left.add(titleGroup, gbcH);

        header.add(left, BorderLayout.WEST);
        return header;
    }

    // ==================== FORM CARD ====================
    private JPanel createFormCard() {
        // Outer card with rounded border
        RoundedPanel card = new RoundedPanel(20, BG_CARD);
        card.setLayout(new BorderLayout(0, 18));
        card.setBorder(new EmptyBorder(22, 28, 22, 28));

        // Section title
        JLabel sectionTitle = new JLabel("\u2716  Add New Patient");
        sectionTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        sectionTitle.setForeground(TEXT_PRIMARY);
        card.add(sectionTitle, BorderLayout.NORTH);

        // Form grid
        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formGrid.add(createLabel("Name"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        nameField = createStyledTextField("Enter patient name");
        formGrid.add(nameField, gbc);

        // Age
        gbc.gridx = 2; gbc.weightx = 0;
        formGrid.add(createLabel("Age"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        ageField = createStyledTextField("Age");
        formGrid.add(ageField, gbc);

        // Disease
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formGrid.add(createLabel("Disease"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        diseaseField = createStyledTextField("Enter disease / condition");
        formGrid.add(diseaseField, gbc);

        // File upload
        gbc.gridx = 2; gbc.weightx = 0;
        formGrid.add(createLabel("Report"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;

        JPanel filePanel = new JPanel(new BorderLayout(8, 0));
        filePanel.setOpaque(false);

        fileLabel = new JLabel("No file chosen");
        fileLabel.setForeground(TEXT_SECONDARY);
        fileLabel.setFont(FONT_INPUT);

        JButton uploadBtn = createStyledButton("Browse", new Color(100, 116, 139), new Color(71, 85, 105));
        uploadBtn.setPreferredSize(new Dimension(90, 36));
        filePanel.add(fileLabel, BorderLayout.CENTER);
        filePanel.add(uploadBtn, BorderLayout.EAST);
        formGrid.add(filePanel, gbc);

        card.add(formGrid, BorderLayout.CENTER);

        // Buttons row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonRow.setOpaque(false);

        JButton addBtn    = createStyledButton("  \u2795  Add Patient  ", ACCENT, ACCENT_DARK);
        JButton viewBtn   = createStyledButton("  \uD83D\uDCCB  View All  ", PRIMARY, PRIMARY_DARK);
        JButton deleteBtn = createStyledButton("  \uD83D\uDDD1  Delete  ", DANGER, DANGER_DARK);

        buttonRow.add(addBtn);
        buttonRow.add(viewBtn);
        buttonRow.add(deleteBtn);
        card.add(buttonRow, BorderLayout.SOUTH);

        // ===== ACTIONS =====

        // Upload File
        uploadBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Select Report File");
            int result = fc.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fc.getSelectedFile().getAbsolutePath();
                fileLabel.setText(fc.getSelectedFile().getName());
                fileLabel.setForeground(ACCENT);
            }
        });

        // Add Patient
        addBtn.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || ageField.getText().trim().isEmpty()
                        || diseaseField.getText().trim().isEmpty()) {
                    showStyledMessage("Please fill in all fields.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Patient p = new Patient();
                p.name = nameField.getText().trim();
                p.age = Integer.parseInt(ageField.getText().trim());
                p.disease = diseaseField.getText().trim();

                if (!selectedFile.isEmpty()) {
                    p.filePath = FileHandler.saveFile(selectedFile);
                } else {
                    p.filePath = "";
                }

                PatientDAO.addPatient(p);

                showStyledMessage("Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                nameField.setText("");
                ageField.setText("");
                diseaseField.setText("");
                selectedFile = "";
                fileLabel.setText("No file chosen");
                fileLabel.setForeground(TEXT_SECONDARY);

            } catch (NumberFormatException ex) {
                showStyledMessage("Age must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                showStyledMessage("Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // View Patients
        viewBtn.addActionListener(e -> loadTable());

        // Delete Patient
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                String id = JOptionPane.showInputDialog(this, "Enter Patient ID to delete:");
                if (id != null && !id.trim().isEmpty()) {
                    PatientDAO.deletePatient(Integer.parseInt(id.trim()));
                    showStyledMessage("Patient deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadTable();
                }
            } else {
                int id = (int) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Delete patient ID " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    PatientDAO.deletePatient(id);
                    showStyledMessage("Patient deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadTable();
                }
            }
        });

        return card;
    }

    // ==================== TABLE CARD ====================
    private JPanel createTableCard() {
        RoundedPanel card = new RoundedPanel(20, BG_CARD);
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(22, 28, 22, 28));

        JLabel sectionTitle = new JLabel("\uD83D\uDCCB  Patient Records");
        sectionTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        sectionTitle.setForeground(TEXT_PRIMARY);
        card.add(sectionTitle, BorderLayout.NORTH);

        // Table setup
        String[] cols = {"ID", "Name", "Age", "Disease", "Report File"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? BG_CARD : TABLE_ALT_ROW);
                } else {
                    c.setBackground(PRIMARY.darker());
                }
                c.setForeground(TEXT_PRIMARY);
                return c;
            }
        };

        table.setFont(FONT_TABLE);
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setSelectionBackground(PRIMARY.darker());
        table.setSelectionForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setBorder(null);

        // Table header style
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_H);
        header.setBackground(BG_INPUT);
        header.setForeground(TEXT_PRIMARY);
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.LEFT);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(BG_CARD);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    // ==================== LOAD TABLE DATA ====================
    private void loadTable() {
        List<Patient> list = PatientDAO.getAllPatients();
        String[] cols = {"ID", "Name", "Age", "Disease", "Report File"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Patient p : list) {
            model.addRow(new Object[]{p.id, p.name, p.age, p.disease, p.filePath});
        }
        table.setModel(model);

        // Re-apply column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    // ==================== UI HELPERS ====================

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(FONT_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(BG_INPUT);
        field.setCaretColor(PRIMARY_LIGHT);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(12, BORDER_COLOR),
                new EmptyBorder(8, 14, 8, 14)
        ));

        // Focus effect
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(12, PRIMARY_LIGHT),
                        new EmptyBorder(8, 14, 8, 14)
                ));
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(12, BORDER_COLOR),
                        new EmptyBorder(8, 14, 8, 14)
                ));
                field.repaint();
            }
        });

        return field;
    }

    private JButton createStyledButton(String text, Color bg, Color hoverBg) {
        JButton button = new JButton(text) {
            private boolean hovering = false;

            {
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovering = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovering = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovering ? hoverBg : bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(FONT_BUTTON);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 38));
        return button;
    }

    private void showStyledMessage(String msg, String title, int type) {
        JOptionPane.showMessageDialog(this, msg, title, type);
    }

    // ==================== CUSTOM COMPONENTS ====================

    // Rounded panel with background
    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Rounded border for text fields
    static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
    }
}