package projectone.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectone.exception.DuplicateEntityException;
import projectone.exception.EntityNotFoundException;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final Clock clock;
    private final Map<String, Appointment> memoryCache = new ConcurrentHashMap<>();

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

        if (memoryCache.containsKey(id) || appointmentRepository.existsById(id)) {
            throw new DuplicateEntityException("Appointment ID " + id + " already exists. Must be unique.");
        }

        appointmentRepository.save(appointment);
        memoryCache.put(id, appointment);
    }

    public void deleteAppointment(String appointmentID) {
        if (appointmentID == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null.");
        }

        boolean exists = Optional.ofNullable(memoryCache.remove(appointmentID)).isPresent()
                || appointmentRepository.existsById(appointmentID);

        if (!exists) {
            throw new EntityNotFoundException("Appointment ID " + appointmentID + " not found for deletion.");
        }

        appointmentRepository.deleteById(appointmentID);
    }

    public void updateAppointment(String appointmentID, Instant newDate, String newDescription) {
        Appointment appointment = Optional.ofNullable(memoryCache.get(appointmentID))
                .or(() -> appointmentRepository.findById(appointmentID))
                .orElseThrow(() -> new EntityNotFoundException("Appointment ID " + appointmentID + " not found for update."));

        Optional.ofNullable(newDate).ifPresent(date -> appointment.setAppointmentDate(date, clock.instant()));
        Optional.ofNullable(newDescription).ifPresent(appointment::setDescription);

        appointmentRepository.save(appointment);
        memoryCache.put(appointmentID, appointment);
    }

    @Transactional(readOnly = true)
    public Appointment getAppointment(String appointmentID) {
        if (appointmentID == null) {
            return null;
        }
        return Optional.ofNullable(memoryCache.get(appointmentID))
                .or(() -> appointmentRepository.findById(appointmentID))
                .orElse(null);
    }

    public void clearMemoryCache() {
        this.memoryCache.clear();
    }
}