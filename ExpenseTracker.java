import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;

class Expense {
    String date;
    String category;
    double amount;
    String description;

    public Expense(String date, String category, double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getMonth() {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(date);
            DateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
            return monthFormat.format(d);
        } catch (Exception e) {
            return "Invalid";
        }
    }

    public Object[] toRow() {
        return new Object[]{date, category, amount, description};
    }

    @Override
    public String toString() {
        return date + " | " + category + " | " + amount + " | " + description;
    }
}

public class ExpenseTracker extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private java.util.List<Expense> expenses;
    private HashMap<String, java.util.List<Expense>> monthMap;

    private JTextField tfDate, tfCategory, tfAmount, tfDescription;
    private JLabel statsLabel;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        monthMap = new HashMap<>();

        setTitle("Personal Expense Tracker");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        initUI();
    }

    private void initUI() {
        // Top Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 10, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Expense"));

        tfDate = new JTextField();
        tfCategory = new JTextField();
        tfAmount = new JTextField();
        tfDescription = new JTextField();

        inputPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(new JLabel());

        inputPanel.add(tfDate);
        inputPanel.add(tfCategory);
        inputPanel.add(tfAmount);
        inputPanel.add(tfDescription);

        JButton btnAdd = new JButton("Add Expense");
        btnAdd.setBackground(new Color(0, 150, 136));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(e -> addExpense());

        inputPanel.add(btnAdd);
        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Date", "Category", "Amount", "Description"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 5));

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteExpense());

        JButton btnExport = new JButton("Export to File");
        btnExport.setBackground(new Color(63, 81, 181));
        btnExport.setForeground(Color.WHITE);
        btnExport.addActionListener(e -> exportToFile());

        statsLabel = new JLabel("Monthly Stats: ");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(btnDelete);
        bottomPanel.add(btnExport);
        bottomPanel.add(statsLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addExpense() {
        try {
            String date = tfDate.getText().trim();
            String category = tfCategory.getText().trim();
            double amount = Double.parseDouble(tfAmount.getText().trim());
            String description = tfDescription.getText().trim();

            Expense exp = new Expense(date, category, amount, description);
            expenses.add(exp);
            tableModel.addRow(exp.toRow());

            String month = exp.getMonth();
            monthMap.putIfAbsent(month, new ArrayList<>());
            monthMap.get(month).add(exp);

            updateStats();

            tfDate.setText("");
            tfCategory.setText("");
            tfAmount.setText("");
            tfDescription.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields.");
        }
    }

    private void deleteExpense() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Expense exp = expenses.get(row);
            tableModel.removeRow(row);
            expenses.remove(row);

            monthMap.get(exp.getMonth()).remove(exp);
            updateStats();
        } else {
            JOptionPane.showMessageDialog(this, "Select an expense to delete.");
        }
    }

    private void updateStats() {
        StringBuilder sb = new StringBuilder("<html>Monthly Stats:<br>");
        for (String month : monthMap.keySet()) {
            double total = 0;
            for (Expense e : monthMap.get(month)) {
                total += e.amount;
            }
            sb.append(month).append(": ₹").append(String.format("%.2f", total)).append("<br>");
        }
        sb.append("</html>");
        statsLabel.setText(sb.toString());
    }

    private void exportToFile() {
        try {
            FileWriter fw = new FileWriter("expenses_export.txt");
            for (Expense e : expenses) {
                fw.write(e.toString() + "\n");
            }
            fw.close();
            JOptionPane.showMessageDialog(this, "Exported to expenses_export.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTracker().setVisible(true));
    }
}

