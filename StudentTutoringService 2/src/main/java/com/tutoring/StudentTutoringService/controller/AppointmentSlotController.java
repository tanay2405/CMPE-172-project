package com.tutoring.StudentTutoringService.controller;

import com.tutoring.StudentTutoringService.service.AppointmentSlotService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppointmentSlotController {

    private final AppointmentSlotService appointmentSlotService;

    public AppointmentSlotController(AppointmentSlotService slotService) {
        this.appointmentSlotService = slotService;
    }

    @GetMapping("/slots")
    public String viewSlots(@RequestParam(required = false) String search,
                            HttpSession session,
                            Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/login";
        }
        model.addAttribute("slots", appointmentSlotService.searchSlots(search));
        model.addAttribute("search", search);
        model.addAttribute("userName", session.getAttribute("userName"));
        return "slots";
    }
}