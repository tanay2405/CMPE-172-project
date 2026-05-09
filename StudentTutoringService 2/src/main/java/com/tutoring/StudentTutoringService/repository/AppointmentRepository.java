package com.tutoring.StudentTutoringService.repository;

import com.tutoring.StudentTutoringService.model.Appointment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AppointmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppointmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertAppointment(int userID, int slotID, int tutorID) {
        String sql = "INSERT INTO Appointment (userID, slotID, tutorID, status) VALUES (?, ?, ?, 'CONFIRMED')";
        jdbcTemplate.update(sql, userID, slotID, tutorID);
    }

    public List<Appointment> findByUserID(int userID) {
        String sql = "SELECT a.appointmentID, a.userID, a.slotID, a.tutorID, a.status, " +
                "s.tutorName, s.subject, s.startTime, s.endTime " +
                "FROM Appointment a " +
                "JOIN AvailabilityTimeSlot s ON a.slotID = s.slotID " +
                "WHERE a.userID = ? AND a.status = 'CONFIRMED'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Appointment a = new Appointment();
            a.setAppointmentID(rs.getInt("appointmentID"));
            a.setUserID(rs.getInt("userID"));
            a.setSlotID(rs.getInt("slotID"));
            a.setTutorID(rs.getInt("tutorID"));
            a.setStatus(rs.getString("status"));
            a.setTutorName(rs.getString("tutorName"));
            a.setSubject(rs.getString("subject"));
            a.setStartTime(rs.getString("startTime"));
            a.setEndTime(rs.getString("endTime"));
            return a;
        }, userID);
    }

    public List<String> findStudentsBySlotID(int slotID) {
        String sql = "SELECT u.name FROM Appointment a " +
                "JOIN UserInfo u ON a.userID = u.userID " +
                "WHERE a.slotID = ? AND a.status = 'CONFIRMED'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"), slotID);
    }

    public List<Integer> findUserIDsBySlotID(int slotID) {
        String sql = "SELECT userID FROM Appointment WHERE slotID = ? AND status = 'CONFIRMED'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("userID"), slotID);
    }

    public void cancelAppointment(int appointmentID, int userID) {
        String sql = "UPDATE Appointment SET status = 'CANCELLED' WHERE appointmentID = ? AND userID = ?";
        jdbcTemplate.update(sql, appointmentID, userID);
    }

    public boolean hasExistingBooking(int userID, int slotID) {
        String sql = "SELECT COUNT(*) FROM Appointment WHERE userID = ? AND slotID = ? AND status = 'CONFIRMED'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userID, slotID);
        return count != null && count > 0;
    }
}