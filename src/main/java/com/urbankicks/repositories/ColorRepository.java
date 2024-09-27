package com.urbankicks.repositories;

import com.urbankicks.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    boolean existsByColorName(String colorName);
}
