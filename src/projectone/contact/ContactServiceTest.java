package projectone.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

public class ContactServiceTest {

    private ContactRepository repositoryMock;
    private ContactService service;

    @BeforeEach
    void setup() {
        repositoryMock = Mockito.mock(ContactRepository.class);
        service = new ContactService(repositoryMock);
    }

    @Test
    void testAddContact() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Mockito.when(repositoryMock.existsById("12345")).thenReturn(false);

        service.addContact(contact);
        Mockito.verify(repositoryMock, Mockito.times(1)).save(contact);
    }

    @Test
    void testAddContactWithDuplicateId() {
        Contact contact1 = new Contact("1", "First", "Last", "1234567890", "Address");
        Mockito.when(repositoryMock.existsById("1")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(contact1));
    }
    
    @Test
    void testDeleteContactSuccess() {
        Mockito.when(repositoryMock.existsById("12345")).thenReturn(true);

        service.deleteContact("12345");
        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById("12345");
    }

    @Test
    void testUpdateContact() {
        Contact contact = new Contact("1", "First", "Last", "1234567890", "Address");
        Mockito.when(repositoryMock.findById("1")).thenReturn(Optional.of(contact));
        
        service.updateContact("1", "NewFirst", "NewLast", "9998887777", "New Address");

        Assertions.assertEquals("NewFirst", contact.getFirstName());
        Assertions.assertEquals("NewLast", contact.getLastName());
        Assertions.assertEquals("9998887777", contact.getPhone());
        Assertions.assertEquals("New Address", contact.getAddress());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(contact);
    }
}
}