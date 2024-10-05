package com.urbankicks.repositories;

import com.urbankicks.entities.Country;
import com.urbankicks.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
    boolean existsByStateNameAndCountry(String stateName, Country country);
}