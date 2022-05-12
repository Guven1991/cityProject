package com.example.cityproject.service;

import com.example.cityproject.dto.CityDto;

import java.util.List;

public interface ICityService {

    CityDto createCity(CityDto cityDto);
    List<CityDto> getAllCities();
    CityDto getCityById(Long id);
    CityDto updateCity(CityDto cityDto, Long id);
    void deleteCity(Long id);
}
