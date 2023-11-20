import java.util.Scanner;
public class BuddyInfo {

    public String getName() {
        return name;
    }

    public BuddyInfo(String address, String name, String phone_number) {
        this.address = address;
        this.name = name;
        this.phone_number = phone_number;
    }

    public BuddyInfo() {
        this.address = "";
        this.name = "";
        this.phone_number = "";
    }

    private String name;

    private String address;

    private String phone_number;

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("","Homer","");
        System.out.println("Hello "+buddy.getName());
    }

    @Override
    public String toString(){
        return name + " #" + address + " #" + phone_number;
    }

    public static BuddyInfo importBuddyInfo(String data) {
        Scanner scanner = new Scanner(data);
        scanner.useDelimiter("#");

        String name = "";
        String address = "";
        String phoneNumber = "";

        if (scanner.hasNext()) {
            name = scanner.next();
        }
        if (scanner.hasNext()) {
            address = scanner.next();
        }
        if (scanner.hasNext()) {
            phoneNumber = scanner.next();
        }

        scanner.close();
        return new BuddyInfo(address, name, phoneNumber);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BuddyInfo buddyInfo = (BuddyInfo) obj;

        if(!name.equals(buddyInfo.name)) return false;
        if(!address.equals(buddyInfo.address)) return false;
        return phone_number.equals(buddyInfo.phone_number);
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phone_number;
    }
}
