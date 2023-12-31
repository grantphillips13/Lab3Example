import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddressBookGUI {

    private JFrame frame;
    private JList<BuddyInfo> buddyList;
    private DefaultListModel<BuddyInfo> listModel;
    private AddressBook addressBook;

    private JMenuItem saveAddressBook;


    private ArrayList<BuddyInfo> buddys;

    public AddressBookGUI(){
        frame = new JFrame("Address Book");

        listModel = new DefaultListModel<>();

        buddyList = new JList<>(listModel);

        addressBook = new AddressBook();


        // Creating the menu
        JMenuBar menuBar = new JMenuBar();
        JMenu addressMenu = new JMenu("AddressBook");
        JMenu buddyMenu = new JMenu("BuddyInfo");

        JMenuItem createAddressBook = new JMenuItem("Create New AddressBook");
        createAddressBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addressBook = new AddressBook();
                listModel.clear();

                JOptionPane.showMessageDialog(frame, "New AddressBook has been created!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        addressMenu.add(createAddressBook);

        menuBar.add(addressMenu);
        menuBar.add(buddyMenu);

        frame.setJMenuBar(menuBar);
        frame.add(new JScrollPane(buddyList));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);

        JMenuItem addBuddy = new JMenuItem("Add Buddy");
        addBuddy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter Buddy's Name:");
                String address = JOptionPane.showInputDialog(frame, "Enter Buddy's Address:");
                String phoneNumber = JOptionPane.showInputDialog(frame, "Enter Buddy's Phone Number:");

                BuddyInfo newBuddy = new BuddyInfo(address, name, phoneNumber);
                addressBook.addBuddy(newBuddy);
                listModel.addElement(newBuddy);
            }
        });
        buddyMenu.add(addBuddy);

        JMenuItem removeBuddy = new JMenuItem("Remove Buddy");
        removeBuddy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected buddy from the JList
                BuddyInfo selectedBuddy = buddyList.getSelectedValue();

                // If a buddy is selected, remove it
                if (selectedBuddy != null) {
                    addressBook.removeBuddy(selectedBuddy);
                    listModel.removeElement(selectedBuddy);
                } else {
                    // If no buddy is selected, show a message to the user
                    JOptionPane.showMessageDialog(frame, "Please select a buddy from the list to remove.");
                }
            }
        });
        buddyMenu.add(removeBuddy);


        JMenuItem saveAddressBook = new JMenuItem("Save AddressBook");
        saveAddressBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name to save:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    addressBook.save(fileName);
                    JOptionPane.showMessageDialog(frame, "AddressBook saved to " + fileName, "Save Successful", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addressMenu.add(saveAddressBook);

        JMenuItem exportMenuItem = new JMenuItem("Export AddressBook");
        exportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportAddressBook();
            }
        });
        addressMenu.add(exportMenuItem);

        JMenuItem importMenuItem = new JMenuItem("Import AddressBook");
        importMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performImport();
            }
        });
        addressMenu.add(importMenuItem);

        // Menu item for serialization
        JMenuItem serializeItem = new JMenuItem("Serialize AddressBook");
        serializeItem.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(frame, "Enter file name to serialize to:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                addressBook.serializeToFile(fileName);
                JOptionPane.showMessageDialog(frame, "Serialized to " + fileName);
            }
        });
        addressMenu.add(serializeItem);


        JMenuItem deserializeItem = new JMenuItem("Deserialize AddressBook");
        deserializeItem.addActionListener(e -> performDeserialization());
        addressMenu.add(deserializeItem);

        JMenuItem exportXmlItem = new JMenuItem("Export to XML");
        exportXmlItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter the file name to export to XML:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    addressBook.exportToXmlFile(fileName);
                    JOptionPane.showMessageDialog(frame, "Address Book exported to XML in " + fileName);
                }
            }
        });
        addressMenu.add(exportXmlItem);

        JMenuItem importXmlItem = new JMenuItem("Import from XML");
        importXmlItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter the file name to import from XML:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    AddressBook importedAddressBook = AddressBook.importFromXmlFile(fileName);
                    if (importedAddressBook != null) {
                        addressBook = importedAddressBook;
                        listModel.clear();
                        for (BuddyInfo buddy : addressBook.getBuddy()) {
                            listModel.addElement(buddy);
                        }
                        JOptionPane.showMessageDialog(frame, "Address Book imported from " + fileName);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to import Address Book from XML", "Import Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        addressMenu.add(importXmlItem);


    }

    private void performDeserialization() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter the name of the file to deserialize from:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            AddressBook deserializedAddressBook = AddressBook.deserializeFromFile(fileName);
            if (deserializedAddressBook != null) {
                addressBook = deserializedAddressBook; // Update the main AddressBook reference
                listModel.clear(); // Clear existing data in the list model
                for (BuddyInfo buddy : addressBook.getBuddy()) {
                    listModel.addElement(buddy); // Add deserialized buddies to the list
                }
                JOptionPane.showMessageDialog(frame, "AddressBook deserialized from " + fileName, "Deserialization Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Deserialization failed", "Deserialization Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Deserialization cancelled or no file name provided", "Deserialization Cancelled", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void performImport() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter the name of the file to import from:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            AddressBook importedAddressBook = AddressBook.importAddressBook(fileName);
            listModel.clear(); // Clearing the existing list
            for (BuddyInfo buddy : importedAddressBook.getBuddy()) {
                listModel.addElement(buddy); // Adding imported buddies to the list
            }
            JOptionPane.showMessageDialog(frame, "AddressBook imported from " + fileName, "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Import cancelled or no file name provided", "Import Cancelled", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exportAddressBook() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter the name of the file to export to:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            addressBook.save(fileName);
            JOptionPane.showMessageDialog(frame, "AddressBook exported to " + fileName, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddressBookGUI();
            }
        });
    }

}

