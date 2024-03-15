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
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "e_id")
    private Long id;

    @Column(name = "e_firstname", nullable = false)
    private String firstname;

    @Column(name = "e_middlename")
    private String middlename;

    @Column(name = "e_lastname", nullable = false)
    private String lastname;

    @Column(name = "e_email", nullable = false, unique = true)
    private String email;

    @Column(name = "e_password", nullable = false)
    private String password;

    @Column(name = "e_mobile", nullable = false)
    private String mobile;

    @Column(name = "e_emergency")
    private String emergencyMobile;

    @Column(name = "e_role", columnDefinition = "VARCHAR(100) DEFAULT 'ROLE_USER'")
    private String roles;

    @Column(name = "e_birthdate", nullable = true)
    private LocalDate birthdate;

    @Column(name = "e_joining_date", nullable = true)
    private LocalDate dateOfJoining;

    @Column(name = "e_gender", nullable = true)
    private String gender;

    @Column(name = "e_blood_group", nullable = true)
    private String bloodGroup;

    @ManyToOne
    @JoinColumn(name = "d_id")
    @JsonBackReference(value="employee-designation")
    private Designation designation;

    @ManyToOne
    @JoinColumn(name = "t_id")
    @JsonBackReference(value="employee-team")
    private Team team;

    public Employee(long id){
        this.id=id;
    }

}

