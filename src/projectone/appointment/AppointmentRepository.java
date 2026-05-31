package projectone.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    // Inherits standard, thread-safe transactional methods:findById(), save(), deleteById(), etc.
}