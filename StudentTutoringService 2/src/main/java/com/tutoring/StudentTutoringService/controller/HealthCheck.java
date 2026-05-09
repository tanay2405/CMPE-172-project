package com.tutoring.StudentTutoringService.controller;

import com.tutoring.StudentTutoringService.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheck {

    private final AppointmentService appointmentService;

    public HealthCheck(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Student Tutoring Service");
        response.put("database", "MySQL");
        response.put("totalBookings", appointmentService.getTotalBookings());
        response.put("failedBookings", appointmentService.getFailedBookings());
        response.put("totalCancellations", appointmentService.getTotalCancellations());
        return ResponseEntity.ok(response);
    }
}
