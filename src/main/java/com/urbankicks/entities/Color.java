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
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer colorId;

    @Column(nullable = false, length = 50, unique = true)
    private String colorName;  // e.g., Red, Blue, Black

    @Column(nullable = true, length = 7)
    private String hexCode;  // Optionally, you can store the hex color code for front-end use
}
