package com.example.cityproject.serviceimpl;

import com.example.cityproject.dto.CityDto;
import com.example.cityproject.dto.DistrictDto;
import com.example.cityproject.entity.District;
import com.example.cityproject.exception.DistrictNotFoundException;
import com.example.cityproject.repository.DistrictRepository;
import com.example.cityproject.service.ICityService;
import com.example.cityproject.service.IDistrictService;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictService implements IDistrictService {

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private final DistrictRepository districtRepository;
    private final ICityService cityService;

    public DistrictService(DistrictRepository districtRepository, @Lazy ICityService cityService) {
        this.districtRepository = districtRepository;
        this.cityService = cityService;
    }

    @Override
    public DistrictDto createDistrict(DistrictDto districtDto, Long cityId) {

        CityDto cityDto = cityService.getCityById(cityId);

        districtDto.setCity(cityDto);

        District district = dozerBeanMapper.map(districtDto, District.class);
        return dozerBeanMapper.map(districtRepository.save(district), DistrictDto.class);

    }
    @Override
    public List<DistrictDto> getAllDistrictByCityId(Long id) {
        List<District> districtList = districtRepository.getDistrictsByCity_Id(id);
        return districtList.stream().map(district ->
                dozerBeanMapper.map(district, DistrictDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<DistrictDto> getAllDistricts() {
        List<District> districtList = districtRepository.findAll();
        return districtList.stream().map(district ->
                dozerBeanMapper.map(district, DistrictDto.class)).collect(Collectors.toList());
    }
    @Override
    public void deleteDistrict(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new DistrictNotFoundException("District Not Found");
        }
        districtRepository.deleteById(id);
    }
    @Override
    public DistrictDto updateDistrict(Long id, DistrictDto districtDto) {
        District district = dozerBeanMapper.map(districtDto, District.class);
        District districtUpdated = districtRepository.findById(id).map(d -> {

            d.setDistrictName(districtDto.getDistrictName());
            return districtRepository.save(d);
        }).orElseGet(() -> {
            district.setId(id);
            return districtRepository.save(district);
        });
        return dozerBeanMapper.map(districtUpdated, DistrictDto.class);
    }

}
