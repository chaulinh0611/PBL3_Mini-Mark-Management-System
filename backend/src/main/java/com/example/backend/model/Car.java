package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique = true)
    private String licensePlate;

    private LocalDate registrationDate;

    private LocalDateTime lastInTime;
   
    private LocalDateTime lastOutTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    // --- Getter và Setter thủ công ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastInTime() {
        return lastInTime;
    }

    public void setLastInTime(LocalDateTime lastInTime) {
        this.lastInTime = lastInTime;
    }

    public LocalDateTime getLastOutTime() {
        return lastOutTime;
    }

    public void setLastOutTime(LocalDateTime lastOutTime) {
        this.lastOutTime = lastOutTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
