package com.tutoring.StudentTutoringService.service;

import com.tutoring.StudentTutoringService.model.AvailabilityTimeSlot;
import com.tutoring.StudentTutoringService.repository.AppointmentSlotRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentSlotService {

    private final AppointmentSlotRepository slotRepository;

    public AppointmentSlotService(AppointmentSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<AvailabilityTimeSlot> getAvailableSlots() {
        return slotRepository.findAllSlots();
    }

    public List<AvailabilityTimeSlot> searchSlots(String search) {
        if (search == null || search.isEmpty()) {
            return slotRepository.findAllSlots();
        }
        return slotRepository.searchSlots(search);
    }
}