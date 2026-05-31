package projectone.appointment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class AppointmentServiceTest {

    private AppointmentRepository repositoryMock;
    private AppointmentService service;
    private Clock fixedClock;
    private Instant fixedNow;

    @BeforeEach
    void setup() {
        fixedNow = Instant.parse("2026-05-24T12:00:00Z");
        fixedClock = Clock.fixed(fixedNow, ZoneId.of("UTC"));
        repositoryMock = Mockito.mock(AppointmentRepository.class);
        service = new AppointmentService(repositoryMock, fixedClock);
    }

    private Instant getFutureDate(int daysToAdd) {
        return fixedNow.plus(daysToAdd, ChronoUnit.DAYS);
    }

    @Test
    void testAddAppointmentSuccess() {
        Appointment appointment = new Appointment("Appt1", getFutureDate(1), "Meeting", fixedNow);
        Mockito.when(repositoryMock.existsById("Appt1")).thenReturn(false);

        service.addAppointment(appointment);
        Mockito.verify(repositoryMock, Mockito.times(1)).save(appointment);
    }

    @Test
    void testAddAppointmentWithDuplicateIdFails() {
        Appointment appt1 = new Appointment("Appt1", getFutureDate(1), "First", fixedNow);
        Mockito.when(repositoryMock.existsById("Appt1")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppointment(appt1));
    }

    @Test
    void testDeleteAppointmentSuccess() {
        Mockito.when(repositoryMock.existsById("Appt2")).thenReturn(true);

        service.deleteAppointment("Appt2");
        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById("Appt2");
    }

    @Test
    void testUpdateAppointmentDateAndDescriptionSuccess() {
        Appointment appointment = new Appointment("Appt3", getFutureDate(1), "Initial Desc", fixedNow);
        Mockito.when(repositoryMock.findById("Appt3")).thenReturn(Optional.of(appointment));

        Instant newDate = getFutureDate(2);
        String newDescription = "Updated Description";
        
        service.updateAppointment("Appt3", newDate, newDescription);

        Assertions.assertEquals(newDescription, appointment.getDescription());
        Assertions.assertEquals(newDate, appointment.getAppointmentDate());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(appointment);
    }

    @Test
    void testUpdateNonExistentAppointmentFails() {
        Mockito.when(repositoryMock.findById("nonexistent")).thenReturn(Optional.empty());
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateAppointment("nonexistent", getFutureDate(1), "Desc");
        });
    }
}