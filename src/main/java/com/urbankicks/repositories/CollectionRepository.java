package com.urbankicks.repositories;

import com.urbankicks.entities.Brand;
import com.urbankicks.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    @Query("""
            SELECT new map(
                c.collectionId AS collectionId,
                c.collectionName AS collectionName
            )
            FROM Collection c
            WHERE c.brand.brandId = :brandId
            """)
    List<Map<String, Object>> getCollectionsByBrand(int brandId);
    boolean existsByCollectionNameAndBrand(String collectionName, Brand brand);
}
