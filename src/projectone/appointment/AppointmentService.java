package projectone.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final Clock clock; // Injecting a Clock provider decouples validation from systemic real-time anomalies

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, Clock clock) {
        this.appointmentRepository = appointmentRepository;
        this.clock = clock;
    }

    public void addAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment record cannot be null.");
        }
        String id = appointment.getAppointmentID();
        if (appointmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Appointment ID " + id + " already exists. Must be unique.");
        }
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(String appointmentID) {
        if (appointmentID == null || !appointmentRepository.existsById(appointmentID)) {
            throw new IllegalArgumentException("Appointment ID " + appointmentID + " not found for deletion.");
        }
        appointmentRepository.deleteById(appointmentID);
    }

    public void updateAppointment(String appointmentID, Instant newDate, String newDescription) {
        Appointment appointment = appointmentRepository.findById(appointmentID)
                .orElseThrow(() -> new IllegalArgumentException("Appointment ID " + appointmentID + " not found for update."));

        if (newDate != null) {
            // Evaluates date updates cleanly against an immutable execution timestamp
            appointment.setAppointmentDate(newDate, clock.instant()); 
        }

        if (newDescription != null) {
            appointment.setDescription(newDescription);
        }
        
        appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public Appointment getAppointment(String appointmentID) {
        if (appointmentID == null) {
            return null;
        }
        return appointmentRepository.findById(appointmentID).orElse(null);
    }
}