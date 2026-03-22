package com.debish.health.notification.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notification_records")
public class NotificationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_id", nullable = false, unique = true, length = 32)
    private String notificationId;

    @Column(name = "claim_id", nullable = false, length = 32)
    private String claimId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "sent_at", nullable = false)
    private Instant sentAt;
}
