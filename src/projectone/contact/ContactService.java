package projectone.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }
        String id = contact.getContactId();
        if (contactRepository.existsById(id)) {
            throw new IllegalArgumentException("Contact ID already exists.");
        }
        contactRepository.save(contact);
    }

    public void deleteContact(String contactId) {
        if (contactId == null || !contactRepository.existsById(contactId)) {
            throw new IllegalArgumentException("Contact ID does not exist or is null.");
        }
        contactRepository.deleteById(contactId);
    }

    public void updateContact(String contactId, String newFirstName, String newLastName, String newPhone, String newAddress) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Contact ID does not exist or is null."));

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
        
        contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public Contact getContact(String contactId) {
        if (contactId == null) {
            return null;
        }
        return contactRepository.findById(contactId).orElse(null);
    }
}

