package com.example.cityproject.controller;

import com.example.cityproject.dto.CityDto;
import com.example.cityproject.request.CityCreateRequest;
import com.example.cityproject.response.CityGetByIdResponse;
import com.example.cityproject.response.CityResponse;
import com.example.cityproject.serviceimpl.CityService;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@RequestBody CityCreateRequest cityCreateRequest) {
        CityDto cityDto = dozerBeanMapper.map(cityCreateRequest, CityDto.class);
        CityDto cityDtoReturned = cityService.createCity(cityDto);
        return ResponseEntity.ok(dozerBeanMapper.map(cityDtoReturned, CityResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {

        List<CityDto> cityDtoList = cityService.getAllCities();

        return ResponseEntity.ok(cityDtoList.stream().map(cityDto ->
                dozerBeanMapper.map(cityDto, CityResponse.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityGetByIdResponse> getCityById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(dozerBeanMapper.map(cityService.getCityById(id), CityGetByIdResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(@RequestBody CityDto cityDto, @PathVariable Long id) {

        CityDto cityDtoReturned = cityService.updateCity(cityDto, id);

        return ResponseEntity.ok(dozerBeanMapper.map(cityDtoReturned, CityResponse.class));

    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }

}
