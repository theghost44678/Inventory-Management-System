package projectone.contact;

public class Contact {
	private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID cannot be null and must be 10 characters or less.");
        }
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null and must be 10 characters or less.");
        }
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null and must be 10 characters or less.");
        }
        if (phone == null || phone.length() != 10) {
            throw new IllegalArgumentException("Phone number cannot be null and must be exactly 10 digits.");
        }
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null and must be 30 characters or less.");
        }

        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null and must be 10 characters or less.");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null and must be 10 characters or less.");
        }
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.length() != 10) {
            throw new IllegalArgumentException("Phone number cannot be null and must be exactly 10 digits.");
        }
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null and must be 30 characters or less.");
        }
        this.address = address;
    }
}
ContactService.java

Java

package projectone.contact;

import java.util.HashMap;
import java.util.Map;

public class ContactService {
    private final Map<String, Contact> contacts = new HashMap<>();

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists.");
        }
        contacts.put(contact.getContactId(), contact);
    }

    public void deleteContact(String contactId) {
        if (contactId == null || !contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID does not exist or is null.");
        }
        contacts.remove(contactId);
    }

    public void updateContact(String contactId, String newFirstName, String newLastName, String newPhone, String newAddress) {
        if (contactId == null || !contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID does not exist or is null.");
        }

        Contact contact = contacts.get(contactId);

        if (newFirstName != null) {
            contact.setFirstName(newFirstName);
        }
        if (newLastName != null) {
            contact.setLastName(newLastName);
        }
        if (newPhone != null) {
            contact.setPhone(newPhone);
        }
        if (newAddress != null) {
            contact.setAddress(newAddress);
        }
    }

    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }
}
ContactTest.java

Java

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
(Note: I've truncated the test classes for brevity, but they contain all the necessary assertions from the original uploaded files.)

ContactServiceTest.java

Java

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
}
