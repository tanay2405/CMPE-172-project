package com.tutoring.StudentTutoringService.controller;

import com.tutoring.StudentTutoringService.repository.AppointmentRepository;
import com.tutoring.StudentTutoringService.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/bookAppointment")
    public String bookAppointmentPage(@RequestParam int slotID,
                                      @RequestParam String tutorName,
                                      @RequestParam String subject,
                                      @RequestParam String startTime,
                                      @RequestParam String endTime,
                                      HttpSession session,
                                      Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        model.addAttribute("slotID", slotID);
        model.addAttribute("tutorName", tutorName);
        model.addAttribute("subject", subject);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userEmail", session.getAttribute("userEmail"));
        return "bookAppointment";
    }

    @PostMapping("/book")
    public String bookAppointment(@RequestParam int slotID,
                                  HttpSession session,
                                  Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        int userID = (int) session.getAttribute("userID");
        try {
            appointmentService.bookSlot(slotID, userID);
            model.addAttribute("message", "Booking confirmed! A notification has been sent.");
            model.addAttribute("status", "BOOKED");
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("status", "FAILED");
        }
        return "bookingResults";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam int appointmentID,
                                    @RequestParam int slotID,
                                    HttpSession session,
                                    Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        int userID = (int) session.getAttribute("userID");
        try {
            appointmentService.cancelBooking(appointmentID, slotID, userID);
            model.addAttribute("message", "Appointment cancelled. A notification has been sent.");
            model.addAttribute("status", "CANCELLED");
        } catch (Exception e) {
            model.addAttribute("message", "Unable to cancel appointment.");
            model.addAttribute("status", "FAILED");
        }
        return "bookingResults";
    }

    @GetMapping("/myAppointments")
    public String myAppointments(HttpSession session, Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        int userID = (int) session.getAttribute("userID");
        model.addAttribute("appointments", appointmentRepository.findByUserID(userID));
        return "myAppointments";
    }
}