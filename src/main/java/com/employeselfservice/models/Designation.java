package com.employeselfservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "designations")
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designation_id")
    private Long id;

    @Column(name = "designation_name",unique = true,nullable = false)
    private String name;

    @Column(name = "designation_description")
    private String description;

    @OneToMany(mappedBy = "designation")
    @JsonManagedReference(value="employee-designation")
    private List<Employee> employees;

    public Designation(String id) {
        this.id = Long.parseLong(id);
    }
}
