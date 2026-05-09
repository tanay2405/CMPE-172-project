package com.tutoring.StudentTutoringService.service;

import com.tutoring.StudentTutoringService.model.RequestNotification;
import com.tutoring.StudentTutoringService.model.ResponseNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final RestTemplate restTemplate;
    private static final String NOTIFICATION_URL = "http://localhost:8080/external/notify";

    public NotificationService() {
        this.restTemplate = new RestTemplate();
    }

    public void sendBookingConfirmation(int userID, int slotID) {
        RequestNotification request = new RequestNotification(
                userID,
                slotID,
                "BOOKING_CONFIRMED",
                "Your booking for slotID " + slotID + " has been confirmed."
        );
        sendNotification(request);
    }

    public void sendCancellationNotification(int userID, int slotID) {
        RequestNotification request = new RequestNotification(
                userID,
                slotID,
                "BOOKING_CANCELLED",
                "Your booking for slotID " + slotID + " has been cancelled."
        );
        sendNotification(request);
    }

    private void sendNotification(RequestNotification request) {
        try {
            logger.info("Sending notification — type: {}, userID: {}, slotID: {}",
                    request.getType(), request.getUserID(), request.getSlotID());
            ResponseNotification response = restTemplate.postForObject(
                    NOTIFICATION_URL,
                    request,
                    ResponseNotification.class
            );
            if (response != null) {
                logger.info("Notification successful — status: {}", response.getStatus());
            }
        } catch (Exception e) {
            logger.error("Notification service failed — type: {}, userID: {}, error: {}",
                    request.getType(), request.getUserID(), e.getMessage());
        }
    }
}