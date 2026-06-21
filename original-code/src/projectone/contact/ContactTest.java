package projectone.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactTest {
    
    @Test
    void testValidContactCreation() {
        Contact contact = new Contact("1234567890", "First Name", "Last Name", "1234567890", "123 Main Street");
        Assertions.assertNotNull(contact);
    }

    @Test
    void testContactIdTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Contact("12345678901", "First", "Last", "1234567890", "Address");
        });
    }
    
    // ... (Other validation tests from original file) ...

    @Test
    void testSetFirstNameTooLong() {
        Contact contact = new Contact("1", "First", "Last", "1234567890", "Address");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contact.setFirstName("TooLongFirstName");
        });
    }
}