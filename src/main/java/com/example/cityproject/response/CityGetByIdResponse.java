package com.example.cityproject.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CityGetByIdResponse {

    private Long id;

    private String cityName;

    private String imageURL;

    private Integer plateNumber;

    private List<DistrictResponse> districtList;
}
