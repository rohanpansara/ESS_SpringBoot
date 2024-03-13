package com.employeselfservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id")
    private Long id;

    @Column(name = "holiday_from", nullable = false)
    private LocalDate from;

    @Column(name = "holiday_to", nullable = true)
    private LocalDate to;

    @Column(name = "holiday_days", nullable = false)
    private int days;

    @Column(name = "holiday_name", nullable = false, unique = true)
    private String name;

    @Column(name = "holiday_description", nullable = true)
    private String description;

    public Long getId() {
        return id;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public int getDays() {
        return days;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
