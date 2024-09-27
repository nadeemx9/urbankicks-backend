package com.urbankicks.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productColorId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference  // Manage the relationship to avoid infinite recursion
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(nullable = false)
    private Double price;  // Price for this specific color variation

    @Column(nullable = false)
    private Integer quantity;  // Stock for this specific color variation

    @OneToMany(mappedBy = "productColor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // Manage the relationship to avoid infinite recursion
    private List<ProductImage> images;  // Images specific to this color variation

    @Column(nullable = false)
    private Boolean isActive;
}
