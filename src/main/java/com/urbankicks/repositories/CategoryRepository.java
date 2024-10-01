package com.urbankicks.repositories;

import com.urbankicks.entities.Category;
import com.urbankicks.entities.Gender;
import com.urbankicks.models.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
                SELECT new map(
                    g.genderId AS genderId,
                    CASE
                        WHEN g.genderName = 'MALE' THEN 'Men'
                        WHEN g.genderName = 'FEMALE' THEN 'Women'
                        WHEN g.genderName = 'UNISEX' THEN 'Unisex'
                    END AS genderName,
                    c.categoryId AS categoryId,
                    c.categoryName AS categoryName
                )
                FROM Category c
                INNER JOIN c.gender g
                WHERE c.isActive = true
                ORDER BY
                    CASE
                        WHEN g.genderName = 'MALE' THEN 1
                        WHEN g.genderName = 'FEMALE' THEN 2
                        WHEN g.genderName = 'UNISEX' THEN 3
                    END,
                    c.categoryName
            """)
    List<Map<String, Object>> getCategoriesDropdown();

    @Query("""
            SELECT new com.urbankicks.models.CategoryDto(c.categoryId, c.categoryName)
            FROM Category c
            WHERE c.gender.genderId = 3 AND c.isActive = true
            ORDER BY c.categoryName
            LIMIT 12
            """)
    List<CategoryDto> getCategoriesSection();

    boolean existsByCategoryNameAndGender(String categoryName, Gender gender);
}
