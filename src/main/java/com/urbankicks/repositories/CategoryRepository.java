package com.urbankicks.repositories;

import com.urbankicks.entities.Category;
import com.urbankicks.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
            select new map(c.categoryId as categoryId, c.categoryName as categoryName)
            from Category c
            where c.isActive = true
            """)
    List<Map<String, Object>> findAllCategories();

    boolean existsByCategoryNameAndGender(String categoryName, Gender gender);
}
