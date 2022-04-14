package com.example.cityproject.repository;

import com.example.cityproject.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District,Long> {
    List<District> getDistrictsByCity_Id(Long id);
}
