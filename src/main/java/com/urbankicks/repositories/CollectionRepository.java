package com.urbankicks.repositories;

import com.urbankicks.entities.Brand;
import com.urbankicks.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    boolean existsByCollectionNameAndBrand(String collectionName, Brand brand);
}
