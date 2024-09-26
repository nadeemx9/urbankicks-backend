package com.urbankicks.repositories;

import com.urbankicks.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenderRepository extends JpaRepository<Gender, Integer> {
    boolean existsByGenderName(Gender.GenderName genderName);
    Gender findByGenderName(Gender.GenderName genderName);

    @Override
    List<Gender> findAll();
}
