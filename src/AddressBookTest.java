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



        @Test
        void testSerialization() {
            AddressBook original = new AddressBook();
            original.addBuddy(new BuddyInfo("123 Maple St", "Alice", "555-1234"));
            original.addBuddy(new BuddyInfo("456 Oak St", "Bob", "555-5678"));

            String fileName = "serializedAddressBook.dat";
            original.serializeToFile(fileName);

            AddressBook deserialized = AddressBook.deserializeFromFile(fileName);
            assertNotNull(deserialized, "Deserialized object is null");
            assertEquals(original.getBuddy().size(), deserialized.getBuddy().size(), "Sizes do not match");

            for (int i = 0; i < original.getBuddy().size(); i++) {
                BuddyInfo originalBuddy = original.getBuddy().get(i);
                BuddyInfo deserializedBuddy = deserialized.getBuddy().get(i);

                assertEquals(originalBuddy.getName(), deserializedBuddy.getName(), "Names do not match for Buddy at index " + i);
                assertEquals(originalBuddy.getAddress(), deserializedBuddy.getAddress(), "Addresses do not match for Buddy at index " + i);
                assertEquals(originalBuddy.getPhoneNumber(), deserializedBuddy.getPhoneNumber(), "Phone numbers do not match for Buddy at index " + i);
            }
        }

}
