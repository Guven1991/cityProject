package com.example.cityproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {

    private Long id;

    private String districtName;

    private CityDto city;
}
