package com.example.cityproject;

import com.example.cityproject.dto.CityDto;
import com.example.cityproject.dto.DistrictDto;
import com.example.cityproject.entity.City;
import com.example.cityproject.entity.District;
import com.example.cityproject.exception.DistrictNotFoundException;
import com.example.cityproject.repository.DistrictRepository;
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
public class DistrictServiceTest {

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private CityService cityService;

    @InjectMocks
    @Spy
    private DistrictService districtService;

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
                .city(city)
                .build();

        districtDto = DistrictDto.builder()
                .id(city.getId())
                .districtName(city.getCityName())
                .build();
    }

    @Test
    public void createDistrict() {
        when(cityService.getCityById(any())).thenReturn(cityDto);
        when(districtRepository.save(any())).thenReturn(district);
        DistrictDto districtDtoReturned = districtService.createDistrict(districtDto, 1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(districtDtoReturned.getId()));
    }

    @Test
    public void getAllDistricts() {
        when(districtRepository.findAll()).thenReturn(List.of(district));
        List<DistrictDto> districtList = districtService.getAllDistricts();
        assertEquals(1, districtList.size());
        assertEquals("üsküdar", districtList.get(0).getDistrictName());
    }

    @Test
    public void getAllDistrictByCityId() {
        when(districtRepository.getDistrictsByCity_Id(any())).thenReturn(List.of(district));
        List<DistrictDto> districtList = districtService.getAllDistrictByCityId(1L);
        assertEquals(1, districtList.size());
        assertEquals("üsküdar", districtList.get(0).getDistrictName());
    }

    @Test
    public void deleteDistrict() {
        when(districtRepository.existsById(any())).thenReturn(true);
        districtService.deleteDistrict(1L);
        verify(districtRepository).deleteById(1L);
    }

    @Test(expected = DistrictNotFoundException.class)
    public void deleteDistrictWhenDistrictNotFound(){
        when(districtRepository.existsById(any())).thenReturn(false);
        districtService.deleteDistrict(100L);
    }

    @Test
    public void updateDistrict() {
        District updatedDistrict = District.builder()
                .id(district.getId())
                .districtName("kadiköy")
                .city(city)
                .build();

        DistrictDto districtDto = DistrictDto.builder()
                .id(updatedDistrict.getId())
                .districtName(updatedDistrict.getDistrictName())
                .city(cityDto)
                .build();

        when(districtRepository.findById(any())).thenReturn(Optional.of(district));
        when(districtRepository.save(any())).thenReturn(updatedDistrict);

        DistrictDto districtDtoReturned = districtService.updateDistrict(1L, districtDto);
        assertEquals(Optional.of(1L), Optional.ofNullable(districtDtoReturned.getId()));
        assertEquals("kadiköy", districtDtoReturned.getDistrictName());
    }

}
