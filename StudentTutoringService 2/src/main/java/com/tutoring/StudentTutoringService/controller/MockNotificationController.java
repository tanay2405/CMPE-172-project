package com.tutoring.StudentTutoringService.controller;

import com.tutoring.StudentTutoringService.model.RequestNotification;
import com.tutoring.StudentTutoringService.model.ResponseNotification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
public class MockNotificationController {

    @PostMapping("/notify")
    public ResponseEntity<ResponseNotification> notify(@RequestBody RequestNotification request) {

        System.out.println("MOCK NOTIFICATION SERVICE CALLED");
        System.out.println("Type: " + request.getType());
        System.out.println("UserID: " + request.getUserID());
        System.out.println("SlotID: " + request.getSlotID());
        System.out.println("Message: " + request.getMessage());

        ResponseNotification response = new ResponseNotification(
                "SUCCESS",
                "Notification sent for userID: " + request.getUserID() +
                        " regarding slotID: " + request.getSlotID()
        );

        return ResponseEntity.ok(response);
    }
}