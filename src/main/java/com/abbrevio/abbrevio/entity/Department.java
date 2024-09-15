package com.abbrevio.abbrevio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="departments", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String name;
    private Integer countOfEmployees;

    @Setter
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<User> users;

    public void setCountOfEmployees() {
        this.countOfEmployees++;
    }
    public void setCountOfEmployees(Integer num) {
        this.countOfEmployees = num;
    }
}
