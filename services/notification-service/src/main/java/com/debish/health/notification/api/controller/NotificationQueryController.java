package com.debish.health.notification.api.controller;

import com.debish.health.notification.api.response.NotificationResponse;
import com.debish.health.notification.domain.repository.NotificationRecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationQueryController {

    private final NotificationRecordRepository repository;

    public NotificationQueryController(NotificationRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<NotificationResponse> findAll() {
        return repository.findAll().stream().map(NotificationResponse::from).toList();
    }
}
