package com.demoProject.security.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Session extends BaseModel {
    private String token;
    @Column(name = "expiring_at")
    private Date expiringAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "session_status")
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
