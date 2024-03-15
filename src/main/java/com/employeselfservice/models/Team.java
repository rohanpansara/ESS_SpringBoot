package com.employeselfservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private Long id;

    @Column(name = "t_name", nullable = false, unique = true)
    private String name;

    @Column(name = "t_description")
    private String description;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference(value="employee-team")
    private List<Employee> employees;

    public Team(Long id) {
        System.out.println("Team(Long id)");
        this.id = id;
    }

    public Team(int id) {
        System.out.println("Team(int id)");
        this.id = (long) id;
    }

    public Team(String id) {
        System.out.println("Team(String id)");
        this.id = Long.parseLong(id);
    }

}
