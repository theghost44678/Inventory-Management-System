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

