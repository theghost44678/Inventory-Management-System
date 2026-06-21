package projectone.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {

	private ContactService service;

    @BeforeEach
    void setup() {
        service = new ContactService();
    }

    @Test
    void testAddContact() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);
        Assertions.assertEquals(contact, service.getContact("12345"));
    }

    @Test
    void testAddContactWithDuplicateId() {
        Contact contact1 = new Contact("1", "First", "Last", "1234567890", "Address");
        Contact contact2 = new Contact("1", "First2", "Last2", "0987654321", "Address2");
        service.addContact(contact1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(contact2));
    }

    // ... (Other service tests from original file) ...
    
    @Test
    void testUpdateContact() {
        Contact contact = new Contact("1", "First", "Last", "1234567890", "Address");
        service.addContact(contact);
        
        service.updateContact("1", "NewFirst", "NewLast", "9998887777", "New Address");

        Contact updatedContact = service.getContact("1");
        Assertions.assertEquals("NewFirst", updatedContact.getFirstName());
    }
}