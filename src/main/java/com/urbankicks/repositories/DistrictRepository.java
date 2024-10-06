package com.urbankicks.repositories;

import com.urbankicks.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    boolean existsByDistrictName(String stateName);
    List<District> findByState_StateId(Integer stateId);
    District findByDistrictId(Integer districtId);
}