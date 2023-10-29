
import java.util.ArrayList;


public class AddressBook {

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


}



