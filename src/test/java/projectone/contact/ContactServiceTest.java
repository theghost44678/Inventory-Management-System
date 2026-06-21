package projectone.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectone.exception.DuplicateEntityException;
import projectone.exception.EntityNotFoundException;

import java.util.Optional;

public class ContactServiceTest {

    private ContactRepository repositoryMock;
    private ContactService service;

    @BeforeEach
    void setup() {
        repositoryMock = Mockito.mock(ContactRepository.class);
        service = new ContactService(repositoryMock);
        service.clearMemoryCache();
    }

    @Test
    void testAddContact() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Mockito.when(repositoryMock.existsById("12345")).thenReturn(false);

        service.addContact(contact);

        Assertions.assertEquals(contact, service.getContact("12345"));
        Mockito.verify(repositoryMock, Mockito.times(1)).save(contact);
    }

    @Test
    void testAddContactWithDuplicateId() {
        Contact contact1 = new Contact("1", "First", "Last", "1234567890", "Address");
        Mockito.when(repositoryMock.existsById("1")).thenReturn(true);

        // Milestone 4 Update
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.addContact(contact1));
    }

    @Test
    void testDeleteContactSuccess() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Mockito.when(repositoryMock.existsById("12345")).thenReturn(false);

        service.addContact(contact);
        service.deleteContact("12345");

        Assertions.assertNull(service.getContact("12345"));
        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById("12345");
    }

    @Test
    void testDeleteNonExistentContactThrows() {
        Mockito.when(repositoryMock.existsById("missing")).thenReturn(false);

        // Milestone 4 Update
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteContact("missing"));
    }

    @Test
    void testUpdateContact() {
        Contact contact = new Contact("1", "First", "Last", "1234567890", "Address");
        Mockito.when(repositoryMock.existsById("1")).thenReturn(false);
        service.addContact(contact);

        service.updateContact("1", "NewFirst", "NewLast", "9998887777", "New Address");

        Contact updated = service.getContact("1");
        Assertions.assertEquals("NewFirst", updated.getFirstName());
        Assertions.assertEquals("NewLast", updated.getLastName());
        Assertions.assertEquals("9998887777", updated.getPhone());
        Assertions.assertEquals("New Address", updated.getAddress());
    }

    @Test
    void testUpdateNonExistentContactThrows() {
        Mockito.when(repositoryMock.findById("missing")).thenReturn(Optional.empty());

        // Milestone 4 Update
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.updateContact("missing", "Bob", "Smith", "1234567890", "Addr");
        });
    }
}