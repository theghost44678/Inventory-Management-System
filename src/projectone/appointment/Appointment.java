package projectone.appointment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(name = "appointment_id", length = 10, nullable = false)
    private String appointmentID;

    @Column(name = "appointment_date", nullable = false)
    private Instant appointmentDate;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_DESCRIPTION_LENGTH = 50;

    // Protected default constructor required by JPA/Hibernate proxying
    protected Appointment() {}

    public Appointment(String appointmentID, Instant appointmentDate, String description, Instant now) {
        if (appointmentID == null || appointmentID.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("Appointment ID must not be null and cannot exceed " + MAX_ID_LENGTH + " characters.");
        }
        this.appointmentID = appointmentID;
        
        // Pass a reference temporal anchor point ('now') to cleanly eliminate testing race conditions
        setAppointmentDate(appointmentDate, now);
        setDescription(description);
    }

    public String getAppointmentID() { return appointmentID; }
    public Instant getAppointmentDate() { return appointmentDate; }
    public String getDescription() { return description; }

    public void setAppointmentDate(Instant appointmentDate, Instant now) {
        if (appointmentDate == null) {
            throw new IllegalArgumentException("Appointment Date cannot be null.");
        }
        if (now == null) {
            throw new IllegalArgumentException("System temporal anchor 'now' cannot be null.");
        }
        if (appointmentDate.isBefore(now)) {
            throw new IllegalArgumentException("Appointment Date cannot be in the past.");
        }
        this.appointmentDate = appointmentDate;
    }

    public void setDescription(String description) {
        if (description == null || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Description must not be null and cannot exceed " + MAX_DESCRIPTION_LENGTH + " characters.");
        }
        this.description = description;
    }
}