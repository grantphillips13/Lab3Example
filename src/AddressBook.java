
import java.util.ArrayList;
public class AddressBook {
    private ArrayList<BuddyInfo> buddys;

    public AddressBook(String address, String name, String phone_number) {
        buddys = new ArrayList<BuddyInfo>();
    }


    public static void main(String[] args) {
        System.out.println("Address Book");
    }
    public void addBuddy(String address, String name, String phone_number){
        BuddyInfo x = new BuddyInfo(address, name, phone_number);
        buddys.add(x);
    }

    public void removeBuddy(String address, String name, String phone_number){
        BuddyInfo x = new BuddyInfo(address, name, phone_number);
        buddys.remove(x);
    }


}



