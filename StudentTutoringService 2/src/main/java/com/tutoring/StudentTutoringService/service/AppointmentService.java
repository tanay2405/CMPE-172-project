package com.tutoring.StudentTutoringService.service;

import com.tutoring.StudentTutoringService.model.AvailabilityTimeSlot;
import com.tutoring.StudentTutoringService.repository.AppointmentRepository;
import com.tutoring.StudentTutoringService.repository.AppointmentSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AtomicInteger totalBookings = new AtomicInteger(0);
    private final AtomicInteger failedBookings = new AtomicInteger(0);
    private final AtomicInteger totalCancellations = new AtomicInteger(0);

    private final AppointmentSlotRepository slotRepository;
    private final AppointmentRepository appointmentRepository;
    private final NotificationService notificationService;

    public AppointmentService(AppointmentSlotRepository slotRepository,
                              AppointmentRepository appointmentRepository,
                              NotificationService notificationService) {
        this.slotRepository = slotRepository;
        this.appointmentRepository = appointmentRepository;
        this.notificationService = notificationService;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void bookSlot(int slotID, int userID) {

        long startTime = System.currentTimeMillis();
        logger.info("Booking attempt started — userID: {}, slotID: {}", userID, slotID);

        if (appointmentRepository.hasExistingBooking(userID, slotID)) {
            failedBookings.incrementAndGet();
            logger.warn("Booking failed — already booked. userID: {}, slotID: {}", userID, slotID);
            throw new IllegalStateException("You have already booked this slot.");
        }

        AvailabilityTimeSlot slot = slotRepository.findByIdForUpdate(slotID);

        if (!"AVAILABLE".equals(slot.getStatus()) ||
                slot.getBookedCount() >= slot.getCapacity()) {
            failedBookings.incrementAndGet();
            logger.warn("Booking failed — slot unavailable. userID: {}, slotID: {}", userID, slotID);
            throw new IllegalStateException("Slot is no longer available.");
        }

        int newCount = slot.getBookedCount() + 1;
        String newStatus = (newCount >= slot.getCapacity()) ? "BOOKED" : "AVAILABLE";
        slotRepository.updateSlotBooking(slotID, newCount, newStatus);
        appointmentRepository.insertAppointment(userID, slotID, slot.getTutorID());

        totalBookings.incrementAndGet();
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Booking confirmed — userID: {}, slotID: {}, duration: {}ms", userID, slotID, duration);

        notificationService.sendBookingConfirmation(userID, slotID);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelBooking(int appointmentID, int slotID, int userID) {

        logger.info("Cancellation attempt started — userID: {}, slotID: {}", userID, slotID);

        appointmentRepository.cancelAppointment(appointmentID, userID);

        AvailabilityTimeSlot slot = slotRepository.findByIdForUpdate(slotID);
        int newCount = Math.max(0, slot.getBookedCount() - 1);
        String newStatus = (newCount < slot.getCapacity()) ? "AVAILABLE" : "BOOKED";
        slotRepository.updateSlotBooking(slotID, newCount, newStatus);

        totalCancellations.incrementAndGet();
        logger.info("Cancellation confirmed — userID: {}, slotID: {}", userID, slotID);

        notificationService.sendCancellationNotification(userID, slotID);
    }

    public int getTotalBookings() {
        return totalBookings.get();
    }
    public int getFailedBookings() {
        return failedBookings.get();
    }
    public int getTotalCancellations() {
        return totalCancellations.get();
    }
}