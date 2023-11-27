import java.io.Serializable;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class AddressBook implements Serializable {

    private ArrayList<BuddyInfo> buddys;

    public AddressBook(){
        buddys = new ArrayList<>();
    }

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null && buddy.getName() != null && !buddy.getName().isEmpty()) {
            buddys.add(buddy);
        }
    }

    public void removeBuddy(BuddyInfo bud2){
        BuddyInfo x = new BuddyInfo();
        buddys.remove(x);
    }

    public ArrayList<BuddyInfo> getBuddy(){
        return new ArrayList<>(buddys);
    }

    public void save(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (BuddyInfo buddy : buddys) {
                writer.write(buddy.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AddressBook importAddressBook(String fileName) {
        AddressBook addressBook = new AddressBook();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                BuddyInfo buddy = BuddyInfo.importBuddyInfo(line);
                addressBook.addBuddy(buddy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressBook;
    }

    public void serializeToFile(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to deserialize (import) AddressBook
    public static AddressBook deserializeFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (AddressBook) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<AddressBook>\n");
        for (BuddyInfo buddy : buddys) {
            xml.append("\t<BuddyInfo>\n");
            xml.append("\t\t<Name>").append(buddy.getName()).append("</Name>\n");
            xml.append("\t\t<Address>").append(buddy.getAddress()).append("</Address>\n");
            xml.append("\t\t<Phone>").append(buddy.getPhoneNumber()).append("</Phone>\n");
            xml.append("\t</BuddyInfo>\n");
        }
        xml.append("</AddressBook>");
        return xml.toString();
    }

    public void exportToXmlFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(toXML());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Manual XML deserialization is more complex and error-prone.
    // It requires reading the file line by line and parsing the XML structure manually.
    public static AddressBook importFromXmlFile(String fileName) {
        AddressBook addressBook = new AddressBook();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            BuddyInfo currentBuddy = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("<BuddyInfo>")) {
                    currentBuddy = new BuddyInfo();
                } else if (line.trim().startsWith("</BuddyInfo>")) {
                    if (currentBuddy != null) {
                        addressBook.addBuddy(currentBuddy);
                    }
                    currentBuddy = null;
                } else if (line.trim().startsWith("<Name>")) {
                    String name = line.trim().replaceAll("<Name>|</Name>", "");
                    if (currentBuddy != null) currentBuddy.setName(name);
                } else if (line.trim().startsWith("<Address>")) {
                    String address = line.trim().replaceAll("<Address>|</Address>", "");
                    if (currentBuddy != null) currentBuddy.setAddress(address);
                } else if (line.trim().startsWith("<Phone>")) {
                    String phone = line.trim().replaceAll("<Phone>|</Phone>", "");
                    if (currentBuddy != null) currentBuddy.setPhone_number(phone);
                }
                // Add more cases as needed for other fields
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressBook;
    }





}



