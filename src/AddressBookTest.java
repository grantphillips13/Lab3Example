import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    @Test
    void testExportAndImportAddressBook() {
        AddressBook originalAddressBook = new AddressBook();
        originalAddressBook.addBuddy(new BuddyInfo("123 Maple Street", "Alice", "555-1234"));
        originalAddressBook.addBuddy(new BuddyInfo("456 Oak Street", "Bob", "555-5678"));

        // Step a: Export to a file
        String fileName = "testAddressBook.txt";
        originalAddressBook.save(fileName);

        // Step b: Import from the file
        AddressBook importedAddressBook = AddressBook.importAddressBook(fileName);

        // Step c: Assert both address books contain the same data
        assertEquals(originalAddressBook.getBuddy().size(), importedAddressBook.getBuddy().size(), "AddressBook sizes are not equal");

        for (int i = 0; i < originalAddressBook.getBuddy().size(); i++) {
            BuddyInfo originalBuddy = originalAddressBook.getBuddy().get(i);
            BuddyInfo importedBuddy = importedAddressBook.getBuddy().get(i);

            assertEquals(originalBuddy.getName().trim(), importedBuddy.getName().trim(), "Names do not match");
            assertEquals(originalBuddy.getAddress().trim(), importedBuddy.getAddress().trim(), "Addresses do not match");
            assertEquals(originalBuddy.getPhoneNumber().trim(), importedBuddy.getPhoneNumber().trim(), "Phone numbers do not match");
        }
    }
}
