package projectone.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectone.exception.DuplicateEntityException;
import projectone.exception.EntityNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final Map<String, Contact> memoryCache = new ConcurrentHashMap<>();

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }

        String id = contact.getContactId();

        if (memoryCache.containsKey(id) || contactRepository.existsById(id)) {
            throw new DuplicateEntityException("Contact ID already exists.");
        }

        contactRepository.save(contact);
        memoryCache.put(id, contact);
    }

    public void deleteContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null.");
        }

        boolean exists = Optional.ofNullable(memoryCache.remove(contactId)).isPresent()
                || contactRepository.existsById(contactId);

        if (!exists) {
            throw new EntityNotFoundException("Contact ID does not exist or is null.");
        }

        contactRepository.deleteById(contactId);
    }

    public void updateContact(String contactId, String newFirstName, String newLastName, String newPhone, String newAddress) {
        Contact contact = Optional.ofNullable(memoryCache.get(contactId))
                .or(() -> contactRepository.findById(contactId))
                .orElseThrow(() -> new EntityNotFoundException("Contact ID does not exist or is null."));

        Optional.ofNullable(newFirstName).ifPresent(contact::setFirstName);
        Optional.ofNullable(newLastName).ifPresent(contact::setLastName);
        Optional.ofNullable(newPhone).ifPresent(contact::setPhone);
        Optional.ofNullable(newAddress).ifPresent(contact::setAddress);

        contactRepository.save(contact);
        memoryCache.put(contactId, contact);
    }

    @Transactional(readOnly = true)
    public Contact getContact(String contactId) {
        if (contactId == null) {
            return null;
        }
        return Optional.ofNullable(memoryCache.get(contactId))
                .or(() -> contactRepository.findById(contactId))
                .orElse(null);
    }

    public void clearMemoryCache() {
        this.memoryCache.clear();
    }
}