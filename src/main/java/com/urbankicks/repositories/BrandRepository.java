package com.urbankicks.repositories;

import com.urbankicks.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    boolean existsByBrandName(String brandName);

    @Query("""
                SELECT b
                FROM Brand b
                LEFT JOIN b.collections c
                WHERE b.isActive = true
            """)
    List<Brand> getBrandsDropdown();

    Brand findByBrandName(String brandName);
}
