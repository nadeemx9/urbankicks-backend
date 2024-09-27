package com.urbankicks.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = true)
    private Collection collection; // Can be null if the shoe does not belong to a collection

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference  // Manage the relationship to avoid infinite recursion
    private List<ProductColor> productColors; // Variations of this product by color

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference  // Manage the relationship to avoid infinite recursion
    private List<ProductSize> productSizes; // Variations of this product by size

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double basePrice;  // Default price for the product, may vary based on color/size

    @Column(nullable = false)
    private Integer totalQuantity;  // Total stock, may vary based on color/size

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
