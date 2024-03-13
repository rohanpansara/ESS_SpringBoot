package com.employeselfservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`leave`")
public class Leave {

    public enum LeaveStatus {
        PENDING, APPROVED, REJECTED;
    }

    public enum LeaveType {
        PAID, UNPAID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee")
    private Employee employee;

    @Column(name = "leave_reason")
    private String reason;

    @Column(name = "leave_applied_on")
    private LocalDate appliedOn;

    @Column(name = "leave_from")
    private LocalDate from;

    @Column(name = "leave_to")
    private LocalDate to;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status")
    private LeaveStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type")
    private LeaveType type;
}
