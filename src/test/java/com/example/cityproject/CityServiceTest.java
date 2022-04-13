package com.example.cityproject;

import com.example.cityproject.dto.CityDto;
import com.example.cityproject.dto.DistrictDto;
import com.example.cityproject.entity.City;
import com.example.cityproject.entity.District;
import com.example.cityproject.exception.CityNotFoundException;
import com.example.cityproject.repository.CityRepository;
import com.example.cityproject.service.CityService;
import com.example.cityproject.service.DistrictService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private DistrictService districtService;

    @InjectMocks
    @Spy
    private CityService cityService;

    City city;
    CityDto cityDto;
    District district;
    DistrictDto districtDto;

    @Before
    public void init() {
        city = City.builder()
                .id(1L)
                .cityName("istanbul")
                .plateNumber(34)
                .imageURL("image.png")
                .build();

        cityDto = CityDto.builder()
                .id(city.getId())
                .cityName(city.getCityName())
                .imageURL(city.getImageURL())
                .plateNumber(city.getPlateNumber())
                .build();

        district = District.builder()
                .id(1L)
                .districtName("üsküdar")
                .build();

        districtDto = DistrictDto.builder()
                .id(district.getId())
                .districtName(district.getDistrictName())
                .city(cityDto)
                .build();
    }

    @Test
    public void createCity() {
        when(cityRepository.save(any())).thenReturn(city);

        CityDto cityDtoReturned = cityService.createCity(cityDto);
        assertEquals(Optional.of(1L), Optional.ofNullable(cityDtoReturned.getId()));
    }

    @Test
    public void getAllCities() {
        when(cityRepository.findAll()).thenReturn(List.of(city));
        List<CityDto> cityList = cityService.getAllCities();
        assertEquals(1, cityList.size());
        assertEquals("istanbul", cityList.get(0).getCityName());
    }

    @Test
    public void getCityById() {
        when(cityRepository.findById(1L)).thenReturn(Optional.ofNullable(city));
        when(districtService.getAllDistrictByCityId(any())).thenReturn(List.of(districtDto));

        CityDto cityDto = cityService.getCityById(1L);

        assertEquals(Optional.of(1L), Optional.ofNullable(cityDto.getId()));
        assertEquals("istanbul", cityDto.getCityName());
        assertEquals(Optional.of(34), Optional.ofNullable(cityDto.getPlateNumber()));
        assertEquals("üsküdar", cityDto.getDistrictList().get(0).getDistrictName());

    }

    @Test
    public void updateCity() {
        when(cityRepository.findById(any())).thenReturn(Optional.of(city));
        when(cityRepository.save(any())).thenReturn(city);
        CityDto cityDtoReturned = cityService.updateCity(cityDto, 1L);

        assertEquals(Optional.of(1L), Optional.ofNullable(cityDtoReturned.getId()));
        assertEquals("istanbul", cityDtoReturned.getCityName());
    }

    @Test
    public void deleteCity() {
        when(cityRepository.existsById(any())).thenReturn(true);
        cityService.deleteCity(1L);
        verify(cityRepository).deleteById(1L);
    }

    @Test(expected = CityNotFoundException.class)
    public void deleteCityWhenCityNotFound(){
        when(cityRepository.existsById(any())).thenReturn(false);
        cityService.deleteCity(100L);
    }

}
