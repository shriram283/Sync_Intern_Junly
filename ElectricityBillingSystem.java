import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElectricityBillingSystem extends JFrame {
    private JTextField meterReadingField;
    private JTextField billAmountField;
    private JComboBox<String> monthComboBox;

    public ElectricityBillingSystem() {
        setTitle("Electricity Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create a panel with a background image
        ImagePanel panel = new ImagePanel("electric.jpg");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel monthLabel = new JLabel("Select Month:");
        monthComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});

        JLabel meterReadingLabel = new JLabel("Meter Reading:");
        meterReadingField = new JTextField(10);

        JButton calculateButton = new JButton("Calculate Bill");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBill();
            }
        });

        JLabel billAmountLabel = new JLabel("Bill Amount:");
        billAmountField = new JTextField(10);
        billAmountField.setEditable(false);

        JButton makePaymentButton = new JButton("Make Payment");
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makePayment();
            }
        });

        // Set custom font and foreground color for labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        monthLabel.setFont(labelFont);
        monthLabel.setForeground(Color.WHITE);
        meterReadingLabel.setFont(labelFont);
        meterReadingLabel.setForeground(Color.WHITE);
        billAmountLabel.setFont(labelFont);
        billAmountLabel.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(monthLabel, gbc);

        gbc.gridx = 1;
        panel.add(monthComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(meterReadingLabel, gbc);

        gbc.gridx = 1;
        panel.add(meterReadingField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(calculateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(billAmountLabel, gbc);

        gbc.gridx = 1;
        panel.add(billAmountField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(makePaymentButton, gbc);

        add(panel);
        setVisible(true);
    }

    private void calculateBill() {
        try {
            int meterReading = Integer.parseInt(meterReadingField.getText());
            int unitsConsumed = meterReading; // Assuming units consumed is equal to the meter reading for simplicity
            double billAmount = unitsConsumed * 5.0; // Assuming the rate per unit is $5.0

            // Display the calculated bill amount
            billAmountField.setText(String.format("$%.2f", billAmount));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid meter reading. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makePayment() {
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        double billAmount;
        try {
            billAmount = Double.parseDouble(billAmountField.getText().substring(1)); // Remove the dollar sign from the bill amount
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid bill amount. Please calculate the bill first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform the payment processing logic here
        // For this example, let's just display a message box with the payment information
        String paymentMessage = String.format("Payment successful!\nMonth: %s\nBill Amount: $%.2f", selectedMonth, billAmount);
        JOptionPane.showMessageDialog(this, paymentMessage, "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Set the look and feel of the GUI to Nimbus for a modern appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ElectricityBillingSystem();
            }
        });
    }

    // Custom JPanel class to display an image as the background
    static class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
