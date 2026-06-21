package projectone.appointment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectone.exception.DuplicateEntityException;
import projectone.exception.EntityNotFoundException;

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
        service.clearMemoryCache();
    }

    private Instant getFutureDate(int daysToAdd) {
        return fixedNow.plus(daysToAdd, ChronoUnit.DAYS);
    }

    @Test
    void testAddAppointmentSuccess() {
        Appointment appointment = new Appointment("Appt1", getFutureDate(1), "Meeting", fixedNow);
        Mockito.when(repositoryMock.existsById("Appt1")).thenReturn(false);

        service.addAppointment(appointment);

        Assertions.assertEquals(appointment, service.getAppointment("Appt1"));
        Mockito.verify(repositoryMock, Mockito.times(1)).save(appointment);
    }

    @Test
    void testAddAppointmentNullThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppointment(null));
    }

    @Test
    void testAddAppointmentWithDuplicateIdFails() {
        Appointment appt1 = new Appointment("Appt1", getFutureDate(1), "First", fixedNow);
        Mockito.when(repositoryMock.existsById("Appt1")).thenReturn(true);

        // Milestone 4: Verifying the custom domain exception response
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.addAppointment(appt1));
    }

    @Test
    void testDeleteAppointmentSuccess() {
        Appointment appointment = new Appointment("Appt2", getFutureDate(1), "Meeting", fixedNow);
        Mockito.when(repositoryMock.existsById("Appt2")).thenReturn(false);

        service.addAppointment(appointment);
        service.deleteAppointment("Appt2");

        Assertions.assertNull(service.getAppointment("Appt2"));
        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById("Appt2");
    }

    @Test
    void testDeleteNonExistentAppointmentThrows() {
        Mockito.when(repositoryMock.existsById("missingId")).thenReturn(false);

        // Milestone 4: Verifying exception mappings match structural deletion protections
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteAppointment("missingId"));
    }

    @Test
    void testUpdateAppointmentDateAndDescriptionSuccess() {
        Appointment appointment = new Appointment("Appt3", getFutureDate(1), "Initial Desc", fixedNow);
        Mockito.when(repositoryMock.existsById("Appt3")).thenReturn(false);
        service.addAppointment(appointment);

        Instant newDate = getFutureDate(2);
        String newDescription = "Updated Description";

        service.updateAppointment("Appt3", newDate, newDescription);

        Appointment updated = service.getAppointment("Appt3");
        Assertions.assertEquals(newDescription, updated.getDescription());
        Assertions.assertEquals(newDate, updated.getAppointmentDate());
    }

    @Test
    void testUpdateNonExistentAppointmentFails() {
        Mockito.when(repositoryMock.findById("nonexistent")).thenReturn(Optional.empty());

        // Milestone 4: Verifying exceptions match update safety targets
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.updateAppointment("nonexistent", getFutureDate(1), "Desc");
        });
    }
}