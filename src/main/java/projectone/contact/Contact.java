package projectone.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @Column(name = "contact_id", length = 10, nullable = false)
    private String contactId;

    @Column(name = "first_name", length = 10, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 10, nullable = false)
    private String lastName;

    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column(name = "address", length = 30, nullable = false)
    private String address;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int EXACT_PHONE_LENGTH = 10;
    private static final int MAX_ADDRESS_LENGTH = 30;

    protected Contact() {}

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.trim().isEmpty() || contactId.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("Contact ID cannot be null, empty, and must be " + MAX_ID_LENGTH + " characters or less.");
        }
        this.contactId = contactId.trim();

        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty() || firstName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("First name cannot be null, empty, and must be " + MAX_NAME_LENGTH + " characters or less.");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty() || lastName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Last name cannot be null, empty, and must be " + MAX_NAME_LENGTH + " characters or less.");
        }
        this.lastName = lastName.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || phone.length() != EXACT_PHONE_LENGTH || !phone.matches("\\d+")) {
            throw new IllegalArgumentException("Phone number cannot be null and must contain exactly " + EXACT_PHONE_LENGTH + " digits.");
        }
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty() || address.length() > MAX_ADDRESS_LENGTH) {
            throw new IllegalArgumentException("Address cannot be null, empty, and must be " + MAX_ADDRESS_LENGTH + " characters or less.");
        }
        this.address = address.trim();
    }
}