package com.tutoring.StudentTutoringService.controller;

import com.tutoring.StudentTutoringService.model.AvailabilityTimeSlot;
import com.tutoring.StudentTutoringService.repository.AppointmentRepository;
import com.tutoring.StudentTutoringService.repository.AppointmentSlotRepository;
import com.tutoring.StudentTutoringService.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TutorController {

    private final AppointmentSlotRepository slotRepository;
    private final AppointmentRepository appointmentRepository;
    private final NotificationService notificationService;

    public TutorController(AppointmentSlotRepository slotRepository,
                           AppointmentRepository appointmentRepository,
                           NotificationService notificationService) {
        this.slotRepository = slotRepository;
        this.appointmentRepository = appointmentRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/tutorPage")
    public String tutorPage(HttpSession session, Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        if (!"TUTOR".equals(session.getAttribute("userRole"))) {
            return "redirect:/slots";
        }
        int tutorID = (int) session.getAttribute("userID");
        List<AvailabilityTimeSlot> slots = slotRepository.findByTutorID(tutorID);

        Map<Integer, List<String>> studentMap = new HashMap<>();
        for (AvailabilityTimeSlot slot : slots) {
            List<String> students = appointmentRepository.findStudentsBySlotID(slot.getSlotID());
            studentMap.put(slot.getSlotID(), students);
        }

        model.addAttribute("slots", slots);
        model.addAttribute("studentMap", studentMap);
        model.addAttribute("userName", session.getAttribute("userName"));
        return "tutorPage";
    }

    @PostMapping("/addSlot")
    public String addSlot(@RequestParam String subject,
                          @RequestParam String startTime,
                          @RequestParam String endTime,
                          @RequestParam int capacity,
                          HttpSession session) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        if (!"TUTOR".equals(session.getAttribute("userRole"))) {
            return "redirect:/slots";
        }
        int tutorID = (int) session.getAttribute("userID");
        String tutorName = (String) session.getAttribute("userName");
        slotRepository.insertSlot(tutorID, tutorName, subject, startTime, endTime, capacity);
        return "redirect:/tutorPage";
    }

    @PostMapping("/cancelSlot")
    public String cancelSlot(@RequestParam int slotID,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }

        List<Integer> studentIDs = appointmentRepository.findUserIDsBySlotID(slotID);

        for (int studentID : studentIDs) {
            notificationService.sendCancellationNotification(studentID, slotID);
        }

        slotRepository.deleteSlot(slotID);

        return "redirect:/tutorPage?cancelled=true";
    }
}