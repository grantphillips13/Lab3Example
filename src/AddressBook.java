
import java.util.ArrayList;
public class AddressBook {
    private ArrayList<BuddyInfo> buddys;
    // This is me ftom git
    public AddressBook() {
        buddys = new ArrayList<BuddyInfo>();
    }


    public static void main(String[] args) {
        System.out.println("Address Book");
        BuddyInfo buddy1 = new BuddyInfo("123 street", "tom", "123-123-123");
        AddressBook buddys = new AddressBook();
        buddys.addBuddy(buddy1);
        buddys.removeBuddy(buddy1);

    }
    public void addBuddy(BuddyInfo bud3){
        BuddyInfo x = new BuddyInfo();
        buddys.add(x);
    }

    public void removeBuddy(BuddyInfo bud2){
        BuddyInfo x = new BuddyInfo();
        buddys.remove(x);
    }


}



