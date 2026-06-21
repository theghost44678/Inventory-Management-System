package projectone.appointment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

public class AppointmentTest {

    private Date getFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1); 
        return calendar.getTime();
    }

    private Date getPastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1); 
        return calendar.getTime();
    }

    @Test
    void testValidAppointmentCreation() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("1234567890", futureDate, "Valid Description.");
        Assertions.assertNotNull(appointment);
    }
    
    @Test
    void testAppointmentIdTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1234567890A", getFutureDate(), "Description");
        });
    }

    @Test
    void testAppointmentDateInPast() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", getPastDate(), "Description");
        });
    }

    @Test
    void testDescriptionTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", getFutureDate(), "A".repeat(51));
        });
    }

    @Test
    void testSetAppointmentDateInPastFails() {
        Appointment appointment = new Appointment("1", getFutureDate(), "Desc");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appointment.setAppointmentDate(getPastDate());
        });
    }
}