package com.example.cityproject.controller;

import com.example.cityproject.dto.DistrictDto;
import com.example.cityproject.request.DistrictCreateRequest;
import com.example.cityproject.request.DistrictUpdateRequest;
import com.example.cityproject.response.DistrictResponse;
import com.example.cityproject.service.DistrictService;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/district")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    @PostMapping("/{cityId}")
    public ResponseEntity<DistrictResponse> createDistrict(@PathVariable Long cityId, @RequestBody DistrictCreateRequest districtCreateRequest) {

        DistrictDto districtDto = dozerBeanMapper.map(districtCreateRequest, DistrictDto.class);
        DistrictDto districtDtoReturned = districtService.createDistrict(districtDto, cityId);
        return ResponseEntity.ok(dozerBeanMapper.map(districtDtoReturned, DistrictResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<DistrictResponse>> getAllDistricts() {

        List<DistrictDto> districtDtoList = districtService.getAllDistricts();

        return ResponseEntity.ok(districtDtoList.stream().map(districtDto ->
                dozerBeanMapper.map(districtDto, DistrictResponse.class)).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictResponse> updateDistrict(@PathVariable Long id, @RequestBody DistrictUpdateRequest districtUpdateRequest) {
        DistrictDto districtDto = dozerBeanMapper.map(districtUpdateRequest, DistrictDto.class);
        DistrictDto districtDtoReturned = districtService.updateDistrict(id, districtDto);
        return ResponseEntity.ok(dozerBeanMapper.map(districtDtoReturned, DistrictResponse.class));
    }

}
