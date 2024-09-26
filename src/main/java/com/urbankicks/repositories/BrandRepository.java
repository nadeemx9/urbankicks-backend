package com.urbankicks.repositories;

import com.urbankicks.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    boolean existsByBrandName(String brandName);
    List<Brand> findAllByIsActiveTrue();
}
