package com.urbankicks.repositories;

import com.urbankicks.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findByCountryName(String countryName);
    boolean existsByCountryName(String countryName);
}
