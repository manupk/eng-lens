package com.englens.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Developer> members;

    public Team(Long id) {
        this.id = id;
    }

    public Team() {

    }

} 