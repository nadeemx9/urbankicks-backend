package com.urbankicks.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;

    @Column(nullable = false, unique = true, length = 100)
    private String brandName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Collection> collections = new ArrayList<>();

}
