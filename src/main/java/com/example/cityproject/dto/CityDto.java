package com.example.cityproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private Long id;

    private String cityName;

    private String imageURL;

    private Integer plateNumber;

    private List<DistrictDto> districtList;
}
