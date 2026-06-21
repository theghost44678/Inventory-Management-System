package projectone.appointment;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class AppointmentService {
    private final Map<String, Appointment> appointments = new HashMap<>();

    public void addAppointment(Appointment appointment) {
        String id = appointment.getAppointmentID();
        if (appointments.containsKey(id)) {
            throw new IllegalArgumentException("Appointment ID " + id + " already exists. Must be unique.");
        }
        appointments.put(id, appointment);
    }

    public void deleteAppointment(String appointmentID) {
        if (!appointments.containsKey(appointmentID)) {
            throw new IllegalArgumentException("Appointment ID " + appointmentID + " not found for deletion.");
        }
        appointments.remove(appointmentID);
    }

    public void updateAppointment(String appointmentID, Date newDate, String newDescription) {
        Appointment appointment = appointments.get(appointmentID);

        if (appointment == null) {
            throw new IllegalArgumentException("Appointment ID " + appointmentID + " not found for update.");
        }

        if (newDate != null) {
            appointment.setAppointmentDate(newDate); 
        }

        if (newDescription != null) {
            appointment.setDescription(newDescription);
        }
    }

    public Appointment getAppointment(String appointmentID) {
        return appointments.get(appointmentID);
    }
}
