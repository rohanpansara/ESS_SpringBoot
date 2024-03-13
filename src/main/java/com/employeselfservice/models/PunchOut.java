package com.employeselfservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "punch_outs")
public class PunchOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "punch_out_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonManagedReference
    private Employee employee;

    @Column(name = "punch_out_time")
    private LocalDateTime punchInTime;

    public PunchOut(Employee employee){
        this.employee=employee;
        this.punchInTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "PunchOut{" +
                "id=" + id +
                ", employee=" + employee.getFirstname().trim()+" "+employee.getLastname().trim() +
                ", punchInTime=" + punchInTime +
                '}';
    }
}
