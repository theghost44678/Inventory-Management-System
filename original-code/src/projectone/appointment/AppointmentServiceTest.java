package projectone.appointment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

public class AppointmentServiceTest {

    private AppointmentService service;
    
    private Date getFutureDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd); 
        return calendar.getTime();
    }

    @BeforeEach
    void setup() {
        service = new AppointmentService();
    }

    @Test
    void testAddAppointmentSuccess() {
        Appointment appointment = new Appointment("Appt1", getFutureDate(1), "Meeting");
        service.addAppointment(appointment);
        Assertions.assertEquals(appointment, service.getAppointment("Appt1"));
    }

    @Test
    void testAddAppointmentWithDuplicateIdFails() {
        Appointment appt1 = new Appointment("Appt1", getFutureDate(1), "First");
        Appointment appt2 = new Appointment("Appt1", getFutureDate(2), "Second");
        service.addAppointment(appt1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppointment(appt2));
    }

    @Test
    void testDeleteAppointmentSuccess() {
        Appointment appointment = new Appointment("Appt2", getFutureDate(1), "To Delete");
        service.addAppointment(appointment);
        service.deleteAppointment("Appt2");
        Assertions.assertNull(service.getAppointment("Appt2"));
    }

    @Test
    void testUpdateAppointmentDateAndDescriptionSuccess() {
        Appointment appointment = new Appointment("Appt3", getFutureDate(1), "Initial Desc");
        service.addAppointment(appointment);

        Date newDate = getFutureDate(2);
        String newDescription = "Updated Description";
        
        service.updateAppointment("Appt3", newDate, newDescription);

        Appointment updatedAppt = service.getAppointment("Appt3");
        Assertions.assertEquals(newDescription, updatedAppt.getDescription());
    }

    @Test
    void testUpdateNonExistentAppointmentFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateAppointment("nonexistent", getFutureDate(1), "Desc");
        });
    }
}