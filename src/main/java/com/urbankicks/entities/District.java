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
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    @Column(nullable = false)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
