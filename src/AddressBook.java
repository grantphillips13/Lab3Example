import java.io.Serializable;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;


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




}



