package com.urbankicks.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @Column(nullable = false)
    private String collectionName;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @JsonBackReference
    private Brand brand;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isActive;
}
