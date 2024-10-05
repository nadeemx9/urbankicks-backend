package com.urbankicks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;

    @Column(nullable = false)
    private String stateName;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}
