package com.example.cityproject.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityResponse {

    private Long id;

    private String cityName;

    private String imageURL;

    private Integer plateNumber;

    private String area;
}
