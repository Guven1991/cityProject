package com.example.cityproject.serviceimpl;

import com.example.cityproject.dto.CityDto;
import com.example.cityproject.dto.DistrictDto;
import com.example.cityproject.entity.City;
import com.example.cityproject.exception.CityAlreadyDefinedException;
import com.example.cityproject.exception.CityNotFoundException;
import com.example.cityproject.repository.CityRepository;
import com.example.cityproject.service.ICityService;
import com.example.cityproject.service.IDistrictService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityService implements ICityService {

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private final CityRepository cityRepository;
    private final IDistrictService districtService;

    public CityService(CityRepository cityRepository, @Lazy IDistrictService districtService) {
        this.cityRepository = cityRepository;
        this.districtService = districtService;
    }

    @Override
    public CityDto createCity(CityDto cityDto) {
        List<CityDto> cityDtoList = getAllCities();
        String cityDtoName = cityDto.getCityName().toUpperCase();

        if (isCityNameExists(cityDtoName, cityDtoList)) {
            log.error("Same cityName Error");
            throw new CityAlreadyDefinedException("This city name already defined");
        }

        City city = dozerBeanMapper.map(cityDto, City.class);
        city.setCityName(cityDtoName);
        LocalDateTime localDateTime = LocalDateTime.now();
        city.setCreateDate(localDateTime);
        City cityReturned = cityRepository.save(city);
        log.info("City added is successfully");


        return dozerBeanMapper.map(cityReturned, CityDto.class);
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cityList = cityRepository.findAll();
        log.info("getAllCities is successful");
        return cityList.stream().map(city ->
                dozerBeanMapper.map(city, CityDto.class)).collect(Collectors.toList());
    }

    @Override
    public CityDto getCityById(Long id) {

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("Not Found City With Id: " + id));
        List<DistrictDto> districtDtoList = districtService.getAllDistrictByCityId(id);
        CityDto cityDto = dozerBeanMapper.map(city, CityDto.class);
        cityDto.setDistrictList(districtDtoList);
        log.info("getCityById is successful");
        return cityDto;
    }
    @Override
    public CityDto updateCity(CityDto cityDto, Long id) {

        City city = dozerBeanMapper.map(cityDto, City.class);

        City updatedCity = cityRepository.findById(id).map(x -> {
            x.setCityName(city.getCityName());
            x.setImageURL(city.getImageURL());
            x.setPlateNumber(city.getPlateNumber());
            LocalDateTime localDateTime = LocalDateTime.now();
            x.setUpdateDate(localDateTime);
            return cityRepository.save(x);
        }).orElseGet(() -> {
            city.setId(id);
            return cityRepository.save(city);
        });

        log.info("updateCity is successful");
        return dozerBeanMapper.map(updatedCity, CityDto.class);
    }
    @Override
    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            log.error("deleteCity error");
            throw new CityNotFoundException("City Not Found");
        }

        cityRepository.deleteById(id);
        log.info("deleteCity is successful");

    }

    public boolean isCityNameExists(String cityName, List<CityDto> cityDtoList) {

        for (CityDto cityDto : cityDtoList) {
            if (cityDto.getCityName().equals(cityName)) {
                return true;
            }
        }
        return false;
    }
}
