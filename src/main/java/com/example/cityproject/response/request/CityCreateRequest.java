package com.example.cityproject.response.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityCreateRequest {

    private String cityName;

    private String imageURL;

    private Integer plateNumber;

    private String area;
}
