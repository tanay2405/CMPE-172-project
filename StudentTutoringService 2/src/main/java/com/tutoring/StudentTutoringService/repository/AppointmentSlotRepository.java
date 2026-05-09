package com.tutoring.StudentTutoringService.repository;

import com.tutoring.StudentTutoringService.model.AvailabilityTimeSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AppointmentSlotRepository {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentSlotRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public AppointmentSlotRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AvailabilityTimeSlot> findAllSlots() {
        String sql = "SELECT * FROM AvailabilityTimeSlot";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AvailabilityTimeSlot slot = new AvailabilityTimeSlot();
            slot.setSlotID(rs.getInt("slotID"));
            slot.setTutorID(rs.getInt("tutorID"));
            slot.setTutorName(rs.getString("tutorName"));
            slot.setSubject(rs.getString("subject"));
            slot.setStartTime(rs.getString("startTime"));
            slot.setEndTime(rs.getString("endTime"));
            slot.setCapacity(rs.getInt("capacity"));
            slot.setBookedCount(rs.getInt("bookedCount"));
            slot.setStatus(rs.getString("status"));
            return slot;
        });
    }

    public AvailabilityTimeSlot findByIdForUpdate(int slotID) {
        String sql = "SELECT * FROM AvailabilityTimeSlot WHERE slotID = ? FOR UPDATE";
        logger.info("Acquiring lock on slotID: {}", slotID);
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            AvailabilityTimeSlot slot = new AvailabilityTimeSlot();
            slot.setSlotID(rs.getInt("slotID"));
            slot.setTutorID(rs.getInt("tutorID"));
            slot.setTutorName(rs.getString("tutorName"));
            slot.setSubject(rs.getString("subject"));
            slot.setStartTime(rs.getString("startTime"));
            slot.setEndTime(rs.getString("endTime"));
            slot.setCapacity(rs.getInt("capacity"));
            slot.setBookedCount(rs.getInt("bookedCount"));
            slot.setStatus(rs.getString("status"));
            return slot;
        }, slotID);
    }

    public void updateSlotBooking(int slotID, int newBookedCount, String status) {
        String sql = "UPDATE AvailabilityTimeSlot SET bookedCount = ?, status = ? WHERE slotID = ?";
        jdbcTemplate.update(sql, newBookedCount, status, slotID);
        logger.info("Slot updated — slotID: {}, bookedCount: {}, status: {}", slotID, newBookedCount, status);
    }

    public void insertSlot(int tutorID, String tutorName, String subject,
                           String startTime, String endTime, int capacity) {
        String sql = "INSERT INTO AvailabilityTimeSlot (tutorID, tutorName, subject, startTime, endTime, capacity, bookedCount, status) VALUES (?, ?, ?, ?, ?, ?, 0, 'AVAILABLE')";
        jdbcTemplate.update(sql, tutorID, tutorName, subject, startTime, endTime, capacity);
    }

    public void deleteSlot(int slotID) {
        String deleteAppointments = "DELETE FROM Appointment WHERE slotID = ?";
        jdbcTemplate.update(deleteAppointments, slotID);
        String deleteSlot = "DELETE FROM AvailabilityTimeSlot WHERE slotID = ?";
        jdbcTemplate.update(deleteSlot, slotID);
        logger.info("Slot and linked appointments deleted — slotID: {}", slotID);
    }

    public List<AvailabilityTimeSlot> findByTutorID(int tutorID) {
        String sql = "SELECT * FROM AvailabilityTimeSlot WHERE tutorID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AvailabilityTimeSlot slot = new AvailabilityTimeSlot();
            slot.setSlotID(rs.getInt("slotID"));
            slot.setTutorID(rs.getInt("tutorID"));
            slot.setTutorName(rs.getString("tutorName"));
            slot.setSubject(rs.getString("subject"));
            slot.setStartTime(rs.getString("startTime"));
            slot.setEndTime(rs.getString("endTime"));
            slot.setCapacity(rs.getInt("capacity"));
            slot.setBookedCount(rs.getInt("bookedCount"));
            slot.setStatus(rs.getString("status"));
            return slot;
        }, tutorID);
    }

    public List<AvailabilityTimeSlot> searchSlots(String search) {
        String sql = "SELECT * FROM AvailabilityTimeSlot WHERE " +
                "LOWER(tutorName) LIKE LOWER(?) OR " +
                "LOWER(subject) LIKE LOWER(?) OR " +
                "LOWER(startTime) LIKE LOWER(?) OR " +
                "LOWER(endTime) LIKE LOWER(?)";
        String keyword = "%" + search + "%";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AvailabilityTimeSlot slot = new AvailabilityTimeSlot();
            slot.setSlotID(rs.getInt("slotID"));
            slot.setTutorID(rs.getInt("tutorID"));
            slot.setTutorName(rs.getString("tutorName"));
            slot.setSubject(rs.getString("subject"));
            slot.setStartTime(rs.getString("startTime"));
            slot.setEndTime(rs.getString("endTime"));
            slot.setCapacity(rs.getInt("capacity"));
            slot.setBookedCount(rs.getInt("bookedCount"));
            slot.setStatus(rs.getString("status"));
            return slot;
        }, keyword, keyword, keyword, keyword);
    }
}