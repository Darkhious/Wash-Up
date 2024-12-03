/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package casestudy;

/**
 *
 * @author Christian
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*; 
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.table.DefaultTableModel;

public class CaseStudy {

    /**
     * @param args the command line arguments
     */
    
    private static int ctr = 0;
    
    public static void displayError(String msg, String errorCode) {
        JOptionPane.showMessageDialog(null, msg, errorCode, JOptionPane.ERROR_MESSAGE);
    }
    
    public static String dasher(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return "--------------------";
        } else {
            return text;
        }
    }
    
    public static String generateCode() {
        String code = "";
        int codeLength = String.valueOf(ctr).length();
        
        ctr++;
        
        if (codeLength == 1) {
            code = "000" + ctr;
        } else if (codeLength == 2) {
            code = "00" + ctr;
        } else if (codeLength == 3) {
            code = "0" + ctr;
        } else {
            code = "" + ctr;
        }
        
        return code;
    }
    
    public static void produceReceipt(String name, String contact, String address, String date, String note, int regKG, int blaKG, int comKG, double due, double payment, double change, boolean toDeliver) {
        String filename, code;
        
        JFrame receiptFrame = new JFrame("Receipt");
        receiptFrame.setSize(300, 400);
        receiptFrame.setLocationRelativeTo(null); // Sets the frame to appear at the center of the screen
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.setResizable(false); // Set to false so that users can't resize the window
        
        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new GridLayout(18, 0));
        receiptPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder("RECEIPT"), new EmptyBorder(10, 10, 10, 10)));
        
        JLabel title = new JLabel("WASH-UP");
        JLabel subTitle = new JLabel("Laundry Lounge");
        JLabel nameLabel = new JLabel("Name: " + dasher(name));
        JLabel contactLabel = new JLabel("Contact No.: " + dasher(contact));
        JLabel addressLabel = new JLabel("Address: " + dasher(address));
        
        JLabel noteLabel = new JLabel("Note: " + note);
        if (note == "") {
            noteLabel.setText("Notes: N/A");
        }
        JLabel regLabel = new JLabel("Regular: " + regKG + "Kg");
        JLabel blaLabel = new JLabel("Blanket: " + blaKG + "x");
        JLabel comLabel = new JLabel("Comforter: " + comKG + "x");
        JLabel dueLabel = new JLabel("AMOUNT DUE: " + due + " PHP");
        JLabel payLabel = new JLabel("CASH: " + payment + " PHP");
        JLabel changeLabel = new JLabel("CHANGE: " + change + " PHP");
        
        JLabel deliverLabel = new JLabel("To Deliver: NO");
        if (toDeliver) {
            deliverLabel.setText("To Deliver: YES");
        }
        
        receiptPanel.add(title);
        receiptPanel.add(subTitle);
        receiptPanel.add(new JLabel("==============================")); // Blank Spaces
        receiptPanel.add(new JLabel("")); // Blank Spaces
        receiptPanel.add(nameLabel);
        receiptPanel.add(contactLabel);
        receiptPanel.add(deliverLabel);
        receiptPanel.add(addressLabel);
        receiptPanel.add(noteLabel);
        receiptPanel.add(new JLabel("")); // Blank Spaces
        receiptPanel.add(regLabel);
        receiptPanel.add(blaLabel);
        receiptPanel.add(comLabel);
        receiptPanel.add(new JLabel("")); // Blank Spaces
        receiptPanel.add(new JLabel("==============================")); // Blank Spaces
        receiptPanel.add(dueLabel);
        receiptPanel.add(payLabel);
        receiptPanel.add(changeLabel);
        
        receiptFrame.add(receiptPanel);
        
        // Creates a softcopy of the receipt
        code = generateCode();
        filename = "receipt" + code + ".txt";
        try {
            File receiptFile = new File(filename);
            if (receiptFile.createNewFile()) {
                FileWriter receiptWriter = new FileWriter(filename);
                
                receiptWriter.write("WASH-UP\nLaundry Lounge\n");
                receiptWriter.write("==============================\n\n");
                receiptWriter.write("Name: " + name + "\n");
                receiptWriter.write("Contact No.: " + contact + "\n");
                
                if (toDeliver) {
                    receiptWriter.write("To Deliver: YES\n");
                } else {
                    receiptWriter.write("To Deliver: NO\n");
                }
                
                receiptWriter.write("Address: " + address + "\n");
                
                if (note == "") {
                    receiptWriter.write("Note: N/A\n");
                } else {
                    receiptWriter.write("Note: " + note + "\n\n");
                }
                
                receiptWriter.write("Regular Clothes: " + regKG + "Kg\n");
                receiptWriter.write("Blanket: " + blaKG + "x\n");
                receiptWriter.write("Comforter: " + comKG + "x\n\n");
                receiptWriter.write("==============================\n");
                receiptWriter.write("AMOUNT DUE: " + due + " PHP\n");
                receiptWriter.write("CASH: " + payment + " PHP\n");
                receiptWriter.write("CHANGE: " + change + " PHP\n");
                
                receiptWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        receiptFrame.setVisible(true);
    }
    
    public static double getExpense(String expense) {
        double value;
        String userInput;
        boolean correctData;
        
        value = 0;
        correctData = false;
        do {
            userInput = JOptionPane.showInputDialog(null, "Enter " + expense + " expense: ", "Setting Up", JOptionPane.QUESTION_MESSAGE);
            
            if (userInput == null) {
                correctData = false;
            } else {
                try {
                    value = Double.parseDouble(userInput);

                    correctData = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "You have entered an Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);

                    correctData = false;
                }
            }
        } while (!correctData);
        
        return value;
    }
    
    public static double computeRevenue(double averageFee) {
        double revenue = averageFee * ctr;
        
        return revenue;
    }
    
    public static void main(String[] args) {
        final double BASE_FEE = 200.0d;
        final double ADD_FEE = 50.0d;
        final int MAX_LOAD = 7;
        
        // Expenses
        double electric, water, supplies, rent;
        
        // Creates the frame
        JFrame mainFrame = new JFrame("Dashboard");
        mainFrame.setSize(650, 475);
        mainFrame.setLocationRelativeTo(null); // Sets the frame to appear at the center of the screen
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false); // Set to false so that users can't resize the window
        
        // MAIN PANEL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 0));
        mainPanel.setBackground(Color.WHITE);
        
        // TOP PANEL
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.GRAY);
        
        // TOP PANEL COMPONENTS
        
        // Report Panel
        
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BorderLayout());
        
        JTabbedPane tabbedReport = new JTabbedPane();
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Components of Form Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel("Name:");
        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField nameField = new JTextField(20);
        JTextField contactField = new JTextField(20);
        
        // Adds the component to the deliver panel
        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(contactLabel);
        infoPanel.add(contactField);
        
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new GridLayout(2, 4));
        
        // Check box will be used to let the program know what kind of clothes is in the order
        JLabel typeLabel = new JLabel("Type");
        JLabel kgLabel = new JLabel("Kg");
        JCheckBox regularCB = new JCheckBox("Regular");
        JCheckBox blanketCB = new JCheckBox("Blanket");
        JCheckBox comforterCB = new JCheckBox("Comforter");
        
        SpinnerModel value1 = new SpinnerNumberModel(0, 0, 999, 1);
        SpinnerModel value2 = new SpinnerNumberModel(0, 0, 999, 1);
        SpinnerModel value3 = new SpinnerNumberModel(0, 0, 999, 1);
        JSpinner regularKG = new JSpinner(value1);
        JSpinner blanketKG = new JSpinner(value2);
        JSpinner comforterKG = new JSpinner(value3);
        
        // Set to false as default
        regularKG.setEnabled(false);
        blanketKG.setEnabled(false);
        comforterKG.setEnabled(false);

        orderPanel.add(typeLabel);
        orderPanel.add(regularCB);
        orderPanel.add(blanketCB);
        orderPanel.add(comforterCB);
        orderPanel.add(kgLabel);
        orderPanel.add(regularKG);
        orderPanel.add(blanketKG);
        orderPanel.add(comforterKG);
        
        
        JPanel collectPanel = new JPanel();
        collectPanel.setLayout(new FlowLayout());
        collectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JRadioButton pickupRB = new JRadioButton("To Pick-up", true);
        JRadioButton deliverRB = new JRadioButton("To Deliver");
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(pickupRB);
        bg.add(deliverRB);
        
        collectPanel.add(pickupRB);
        collectPanel.add(deliverRB);
        
        formPanel.add(infoPanel);
        formPanel.add(orderPanel);
        formPanel.add(collectPanel);
        
        JPanel salesPanel = new JPanel();
        salesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
        salesPanel.setLayout(new GridLayout(9, 2));
        salesPanel.setBackground(Color.WHITE);
        
        JLabel orderLabel = new JLabel("Total no. of Orders:");
        JLabel salesLabel = new JLabel("Estimated Revenue:");
        JLabel rentLabel = new JLabel("Rent Expense:");
        JLabel supplyLabel = new JLabel("Supply Expense:");
        JLabel electricLabel = new JLabel("Average Electricity Expense:");
        JLabel waterLabel = new JLabel("Average Water Expense:");
        JLabel profitLabel = new JLabel("Net Income:");
        JTextField orderField = new JTextField();
        JTextField revenueField = new JTextField();
        JTextField rentField = new JTextField();
        JTextField supplyField = new JTextField();
        JTextField electricField = new JTextField();
        JTextField waterField = new JTextField();
        JTextField profitField = new JTextField();
        
        orderField.setEditable(false);
        revenueField.setEditable(false);
        rentField.setEditable(false);
        supplyField.setEditable(false);
        electricField.setEditable(false);
        waterField.setEditable(false);
        profitField.setEditable(false);
        
        salesPanel.add(orderLabel);
        salesPanel.add(orderField);
        salesPanel.add(salesLabel);
        salesPanel.add(revenueField);
        
        salesPanel.add(new JLabel());
        salesPanel.add(new JLabel());
        
        salesPanel.add(rentLabel);
        salesPanel.add(rentField);
        
        salesPanel.add(supplyLabel);
        salesPanel.add(supplyField);
        
        salesPanel.add(electricLabel);
        salesPanel.add(electricField);
        
        salesPanel.add(waterLabel);
        salesPanel.add(waterField);
        
        salesPanel.add(new JLabel());
        salesPanel.add(new JLabel());
        
        salesPanel.add(profitLabel);
        salesPanel.add(profitField);
        
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new FlowLayout());
        inventoryPanel.setBackground(Color.WHITE);
        
        DefaultTableModel recordModel = new DefaultTableModel();
        JTable records = new JTable(recordModel);
        recordModel.addColumn("ORDER");
        recordModel.addColumn("AMOUNT PAID");
        recordModel.addColumn("NAME");
        recordModel.addColumn("DATE");
        
        // Sets the size of the columns
        records.getColumnModel().getColumn(0).setPreferredWidth(100);
        records.getColumnModel().getColumn(1).setPreferredWidth(70);
        records.getColumnModel().getColumn(3).setPreferredWidth(50);
        
        JScrollPane tableContainer = new JScrollPane(records);
        records.setFillsViewportHeight(true);
        
        tabbedReport.addTab("Order Form", formPanel);
        tabbedReport.addTab("Sales", salesPanel);
        tabbedReport.addTab("Inventory", inventoryPanel);
    
        // ADD TO TOP PANEL
        reportPanel.add(tabbedReport, BorderLayout.CENTER);
        inventoryPanel.add(tableContainer);
        topPanel.add(reportPanel, BorderLayout.CENTER);
        

        
        // BOTTOM PANEL
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BorderLayout());
        
        CardLayout cards = new CardLayout();
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(cards);
        
        // COMPONENTS OF BOTTOM PANEL
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel deliverPanel = new JPanel();
        deliverPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        deliverPanel.setBorder(BorderFactory.createTitledBorder("Delivery"));
        
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel noteLabel = new JLabel("Notes:");
        JTextArea noteArea = new JTextArea(3, 10);
        JScrollPane areaScroll = new JScrollPane(noteArea);
        addressField.setEnabled(false);
        noteArea.setEnabled(false);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        deliverPanel.add(addressLabel, gbc);
        gbc.ipadx = 200;
        gbc.gridx = 1;
        deliverPanel.add(addressField, gbc);
        gbc.ipadx = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        deliverPanel.add(noteLabel, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        deliverPanel.add(areaScroll, gbc);
        
        JPanel paymentForm = new JPanel();
        paymentForm.setLayout(new GridLayout(3, 2));
        paymentForm.setBorder(BorderFactory.createCompoundBorder(new TitledBorder("Payment Form"), new EmptyBorder(0, 10, 0, 10)));
        
        JLabel costLabel = new JLabel("TOTAL AMOUNT DUE: ");
        JLabel payLabel = new JLabel("Received Amount: ");
        JLabel changeLabel = new JLabel("Change:");
        JTextField costField = new JTextField("0.00 PHP", 20);
        JTextField payField = new JTextField("0.00", 20);
        JTextField changeField = new JTextField("0.00 PHP", 20);
        costField.setEditable(false);
        changeField.setEditable(false);
        
        paymentForm.add(costLabel);
        paymentForm.add(costField);
        paymentForm.add(payLabel);
        paymentForm.add(payField);
        paymentForm.add(changeLabel);
        paymentForm.add(changeField);
        
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout());

        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon(new ImageIcon("rcs/logo.png").getImage().getScaledInstance(450, 210, Image.SCALE_SMOOTH)));
        
        logoPanel.add(logo);
        
        detailPanel.add(deliverPanel, BorderLayout.NORTH);
        detailPanel.add(paymentForm, BorderLayout.CENTER);
        
        // ADD TO CARD
        cardPanel.add(detailPanel, "detailPane");
        cardPanel.add(logoPanel, "logoPane");
       
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        
        JButton submitBtn = new JButton("Submit");
        JButton clearBtn = new JButton("Clear");
        
        // Settings Panel
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new GridLayout(5, 0));
        settingPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder("Control Panel"), new EmptyBorder(10, 10, 10, 10)));
        
        // Components of Settings Panel
        JButton excelBtn = new JButton("Produce Excel File");
        
        settingPanel.add(submitBtn);
        settingPanel.add(new JLabel(""));
        settingPanel.add(clearBtn);
        settingPanel.add(new JLabel(""));
        settingPanel.add(excelBtn);
        
        
        // ADD TO BOTTOM PANEL
        botPanel.add(cardPanel, BorderLayout.CENTER);
        botPanel.add(settingPanel, BorderLayout.EAST);
        
        // ADD TO MAIN PANEL
        mainPanel.add(topPanel);
        mainPanel.add(botPanel);
        
        // FINISH UP THE GUI
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        
    
    // PROCESSES BACK-END (MESSY STUFF)
        // get rent
        rent = getExpense("RENT");
        // get supply
        supplies = getExpense("SUPPLIES");

        // get electricity
        electric = getExpense("ELECTRIC");
        // get water
        water = getExpense("WATER");
        // Total Expenses
        final double totalExpenses = rent + supplies + electric + water;
        
        rentField.setText(rent + " PHP");
        supplyField.setText(supplies + " PHP");
        electricField.setText(electric + " PHP");
        waterField.setText(water + " PHP");
        
        
    
    // LISTENERS
    
        tabbedReport.addChangeListener(e -> {
            if (formPanel.isShowing()) {
                cards.show(cardPanel, "detailPane");
            } else {
                cards.show(cardPanel, "logoPane");
            }
        });
    
    // EVENT LISTENERS FOR CHECKBOXES: When checkbox is true, enable the spinners
        regularCB.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == 1) {
                regularKG.setEnabled(true);
            } else {
                regularKG.setEnabled(false);
            }
        });
        
        blanketCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    blanketKG.setEnabled(true);
                } else {
                    blanketKG.setEnabled(false);
                }
            }
        });
        
        comforterCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    comforterKG.setEnabled(true);
                } else {
                    comforterKG.setEnabled(false);
                }
            }
        });
        
        deliverRB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    addressField.setEnabled(true);
                    noteArea.setEnabled(true);
                } else {
                    addressField.setEnabled(false);
                    noteArea.setEnabled(false);
                }
            }
        });
        
    // EVENT LISTENERS FOR BUTTONS
        // Submit Button
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {             
                double regCost, blaCost, comCost, totalDue, payment, change, averageFee, revenue;
                int load, regKG, blaKG, comKG, number;
                boolean emptyName, emptyContact, emptyAddress;
                
                String address, note, order, userInput;
                String name = nameField.getText();
                String contact = contactField.getText();
                boolean toDeliver = false;
                averageFee = (BASE_FEE + (BASE_FEE + ADD_FEE)) / 2;
                
                LocalDate date = LocalDate.now();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateNow = date.format(dateFormat);
                
                address = "";
                note = "";
                if (deliverRB.isSelected()) {
                    address = addressField.getText();
                    note = noteArea.getText();
                    
                    emptyContact = contact.isBlank() || contact.isEmpty();
                    emptyAddress = address.isBlank() || address.isEmpty();
                    toDeliver = true;
                } else {
                    emptyContact = false;
                    emptyAddress = false;
                }
                
                try {
                    Integer.valueOf(contact);
                } catch (NumberFormatException ex) {
                    emptyContact = true;
                }
                
                emptyName = name.isBlank() || name.isEmpty();
                if (!emptyContact && !emptyAddress && !emptyName) {
                    order = "";
                    payment = 0;
                    regCost = 0;
                    blaCost = 0;
                    comCost = 0;
                    regKG = 0;
                    blaKG = 0;
                    comKG = 0;

                    try {
                        payment = Double.parseDouble(payField.getText());
                    } catch (NumberFormatException ex) {
                        displayError("Invalid Input of payment!", "Input Error");
                    }

                    if (regularCB.isSelected()) {
                        regKG = (int) regularKG.getValue();

                        regCost = (regKG / MAX_LOAD) * BASE_FEE;
                        load = regKG % MAX_LOAD;
                        if (load == 1 && regKG > 7) {
                            regCost += ADD_FEE;
                        } else if (load >= 1) {
                            regCost += BASE_FEE;
                        }

                        order += "Regular (" + regKG + " Kg)";
                    }
                    if (blanketCB.isSelected()) {
                        blaKG = (int) blanketKG.getValue();

                        if (!(blaKG == 1 && regKG > 0)) {
                            blaCost = blaKG * BASE_FEE;
                        }

                        if (order.length() > 0) {
                            order += " - Blanket (" + blaKG + "x)";
                        } else {
                            order += "Blanket (" + blaKG + "x)";
                        }
                    }
                    if (comforterCB.isSelected()) {
                        comKG = (int) comforterKG.getValue();

                        blaCost = comKG * BASE_FEE;

                        if (order.length() > 0) {
                            order += " - Comforter (" + comKG + "x)";
                        } else {
                            order += "Comforter (" + comKG + "x)";
                        }
                    }

                    if (payment <= 0 && (regKG > 0 || blaKG > 0 || comKG > 0)) {
                        userInput = JOptionPane.showInputDialog(mainFrame, "Input payment:", "Payment Officer", JOptionPane.QUESTION_MESSAGE);

                        try {
                            payment = Double.parseDouble(userInput);

                            payField.setText(payment + "");
                        } catch (NumberFormatException ex) {
                            displayError("Invalid Input of payment!", "Input Error");
                        }
                    }

                    totalDue = regCost + blaCost + comCost;
                    change = payment - totalDue;

                    if (change >= 0 && totalDue > 0) {
                        recordModel.addRow(new Object[] {
                            order, payment, name, dateNow
                        });
                    }

                    costField.setText(totalDue + " PHP");
                    changeField.setText(change + " PHP");

                    if ((regKG > 0 || blaKG > 0 || comKG > 0) && payment >= totalDue) {
                        produceReceipt(name, contact, address, dateNow, note, regKG, blaKG, comKG, totalDue, payment, change, toDeliver);
                        orderField.setText(ctr + "");

                        revenue = computeRevenue(averageFee);
                        revenueField.setText(revenue + " PHP");
                        profitField.setText((revenue - totalExpenses) + " PHP");
                        
                        
                        // CLEARS THE INPUT AREA
                        nameField.setText("");
                        contactField.setText("");
                        regularKG.setValue(0);
                        blanketKG.setValue(0);
                        comforterKG.setValue(0);
                        addressField.setText("");
                        noteArea.setText("");
                        costField.setText("0.00 PHP");
                        payField.setText("0.00");
                        changeField.setText("0.00 PHP");
                    }
                } else {
                    if (emptyName) {
                        displayError("NAME is left blank", "Missing Input");
                    } else if (emptyContact) {
                        displayError("CONTACT is left blank", "Missing Input");
                    } else {
                        displayError("ADDRESS is left blank", "Missing Input");
                    }
                    
                }
            }
        });
        
        
        // Clear Button
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                contactField.setText("");
                regularKG.setValue(0);
                blanketKG.setValue(0);
                comforterKG.setValue(0);
                addressField.setText("");
                noteArea.setText("");
                costField.setText("0.00 PHP");
                payField.setText("0.00");
                changeField.setText("0.00 PHP");
            }
        });
        
        
        excelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintWriter out = null;
                try {
                    String orderData, amountData, nameData, dateData;
                    
                    File csvFile = new File("salesReport.csv"); // Filename
                    out = new PrintWriter(csvFile); // The writer of csv files
                    
                    out.printf("%s, %s, %s, %s\n", "ORDER DETAILS", "AMOUNT PAID", "NAME", "DATE"); // Produces the Table Headers
                    for (int i = 0; i < records.getRowCount(); i++) { // Loop through the rows
                        
                        // Retrieves the data from the table
                        orderData = String.valueOf(records.getValueAt(i, 0));
                        amountData = String.valueOf(records.getValueAt(i, 1));
                        nameData = String.valueOf(records.getValueAt(i, 2));
                        dateData = String.valueOf(records.getValueAt(i, 3));
                        
                        out.printf("%s, %s, %s, %s\n", orderData, amountData + " PHP", nameData, dateData); // Writes each rows into the csv file
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CaseStudy.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    out.close(); // Closes and saves the csv file
                }
            }
        });
    } 
}

// EVENT LISTENERS
