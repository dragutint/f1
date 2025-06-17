package com.sporty.f1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "bets")
public class BetEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "driver_id", nullable = false)
    private String driverId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "odd", nullable = false)
    private Double odd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BetStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
