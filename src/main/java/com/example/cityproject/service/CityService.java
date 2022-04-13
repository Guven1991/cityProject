package com.example.cityproject.service;

import com.example.cityproject.dto.CityDto;
import com.example.cityproject.dto.DistrictDto;
import com.example.cityproject.entity.City;
import com.example.cityproject.exception.CityNotFoundException;
import com.example.cityproject.repository.CityRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private final CityRepository cityRepository;
    private final DistrictService districtService;

    public CityService(CityRepository cityRepository, @Lazy DistrictService districtService) {
        this.cityRepository = cityRepository;
        this.districtService = districtService;
    }

    public CityDto createCity(CityDto cityDto) {
        City city = dozerBeanMapper.map(cityDto, City.class);
        City cityReturned = cityRepository.save(city);
        return dozerBeanMapper.map(cityReturned, CityDto.class);
    }

    public List<CityDto> getAllCities() {
        List<City> cityList = cityRepository.findAll();
        return cityList.stream().map(city ->
                dozerBeanMapper.map(city, CityDto.class)).collect(Collectors.toList());
    }

    public CityDto getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("Not Found City With Id: " + id));
        List<DistrictDto> districtDtoList = districtService.getAllDistrictByCityId(id);
        CityDto cityDto = dozerBeanMapper.map(city, CityDto.class);
        cityDto.setDistrictList(districtDtoList);
        return cityDto;
    }

    public CityDto updateCity(CityDto cityDto, Long id) {

        City city = dozerBeanMapper.map(cityDto, City.class);

        City updatedCity = cityRepository.findById(id).map(x -> {
            x.setCityName(city.getCityName());
            x.setImageURL(city.getImageURL());
            x.setPlateNumber(city.getPlateNumber());
            return cityRepository.save(x);
        }).orElseGet(() -> {
            city.setId(id);
            return cityRepository.save(city);
        });
        return dozerBeanMapper.map(updatedCity, CityDto.class);
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new CityNotFoundException("City Not Found");
        }
        cityRepository.deleteById(id);
    }
}
