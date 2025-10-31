package com.payflow.notification_service.controllers;

import com.payflow.notification_service.dtos.NotificationRequestDTO;
import com.payflow.notification_service.dtos.NotificationResponseDTO;
import com.payflow.notification_service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    public NotificationService service;

    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationRequestDTO request) {
        service.sendNotifications(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getNotificationByID(@PathVariable Long id) {
        return new ResponseEntity<>(service.getNotificationByID(id), HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        return new ResponseEntity<>(service.getAllNotifications(), HttpStatus.OK);
    }

}
