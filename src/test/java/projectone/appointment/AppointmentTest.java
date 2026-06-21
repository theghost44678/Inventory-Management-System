package projectone.appointment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AppointmentTest {

    private final Instant fixedNow = Instant.parse("2026-05-24T12:00:00Z");

    private Instant getFutureDate() {
        return fixedNow.plus(1, ChronoUnit.DAYS);
    }

    private Instant getPastDate() {
        return fixedNow.minus(1, ChronoUnit.DAYS);
    }

    @Test
    void testValidAppointmentCreation() {
        Appointment appointment = new Appointment("1234567890", getFutureDate(), "Valid Description.", fixedNow);
        Assertions.assertNotNull(appointment);
    }

    @Test
    void testAppointmentIdEmptyThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("   ", getFutureDate(), "Description", fixedNow);
        });
    }

    @Test
    void testAppointmentIdTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1234567890A", getFutureDate(), "Description", fixedNow);
        });
    }

    @Test
    void testAppointmentDateInPast() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", getPastDate(), "Description", fixedNow);
        });
    }

    @Test
    void testDescriptionEmptyThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", getFutureDate(), "   ", fixedNow);
        });
    }

    @Test
    void testDescriptionTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", getFutureDate(), "A".repeat(51), fixedNow);
        });
    }
}