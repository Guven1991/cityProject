package com.example.cityproject.service;

import com.example.cityproject.dto.DistrictDto;

import java.util.List;

public interface IDistrictService {

    DistrictDto createDistrict(DistrictDto districtDto, Long cityId);
    List<DistrictDto> getAllDistrictByCityId(Long id);
    List<DistrictDto> getAllDistricts();
    void deleteDistrict(Long id);
    DistrictDto updateDistrict(Long id, DistrictDto districtDto);

}
