package com.employeselfservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.naming.Name;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_firstname", nullable = false)
    private String firstname;

    @Column(name = "employee_middlename")
    private String middlename;

    @Column(name = "employee_lastname", nullable = false)
    private String lastname;

    @Column(name = "employee_email", nullable = false, unique = true)
    private String email;

    @Column(name = "employee_password", nullable = false)
    private String password;

    @Column(name = "employee_role", columnDefinition = "VARCHAR(100) DEFAULT 'ROLE_USER'")
    private String roles;

    @Column(name = "employee_birthdate", nullable = true)
    private LocalDate birthdate;

    @Column(name = "employee_joining_date", nullable = true)
    private LocalDate dateOfJoining;

    @Column(name = "employee_gender", nullable = true)
    private String gender;

    @Column(name = "employee_blood_group", nullable = true)
    private String bloodGroup;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    @JsonBackReference(value="employee-designation")
    private Designation designation;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference(value="employee-team")
    private Team team;

    public Employee(long id){
        this.id=id;
    }

}

