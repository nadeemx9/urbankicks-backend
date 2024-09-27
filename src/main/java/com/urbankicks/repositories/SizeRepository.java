package com.urbankicks.repositories;

import com.urbankicks.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
    boolean existsBySizeName(String sizeName);
}
