package org.clane.cardemo.facade;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.clane.cardemo.entity.CarEntity;
import org.clane.cardemo.entity.ImageEntity;
import org.clane.cardemo.mapper.UtilMapper;
import org.clane.cardemo.payload.*;
import org.clane.cardemo.service.CarDbService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarFacade {

    private UtilMapper utilMapper;

    private CarDbService carDbService;

    public CarResponse handleUpdateCar(UpdateCarRequest updateCarRequest , Long carId){
        CarEntity carEntity = carDbService.getCarById(carId);
        if(StringUtils.isNotBlank(updateCarRequest.getName())){
            carEntity.setName(updateCarRequest.getName());
        }
        if(StringUtils.isNotBlank(updateCarRequest.getDescription())){
            carEntity.setDescription(updateCarRequest.getDescription());
        }
        if(StringUtils.isNotBlank(updateCarRequest.getYearOfMake())){
            carEntity.setYearOfMake(Long.valueOf(updateCarRequest.getYearOfMake()));
        }
        List<String> categoryIds = updateCarRequest.getCategoryIds();

        List<ImageEntity> imageEntityList = updateCarRequest.getImageList().stream()
                .map(utilMapper.mapImageEntity)
                .collect(Collectors.toList());;

        List<String> tagIds = updateCarRequest.getTagIds();

        CarEntity updatedCarEntity =carDbService.updateCar(carEntity,tagIds, categoryIds, imageEntityList);
        return utilMapper.mapCarResponse(updatedCarEntity);
    }

    public CarResponse handleCreateCar(CreateCarRequest createCarRequest){
        carDbService.confirmUniqueCarName(createCarRequest.getName());
        CarEntity carEntity = utilMapper.mapCarEntity.apply(createCarRequest);
        List<ImageEntity> imageEntityList = createCarRequest.getImageList()
                .stream()
                .map(utilMapper.mapImageEntity)
                .collect(Collectors.toList());
        List<String> tagIds = createCarRequest.getTagIds();
        List<String> categoryIds = createCarRequest.getCategoryIds();
        CarEntity updatedCarEntity = carDbService.createCar(carEntity, tagIds, categoryIds, imageEntityList);
        return utilMapper.mapCarResponse(updatedCarEntity);
    }

    public CarResponseList searchCars(SearchCarRequest searchCarRequest){
        List<CarEntity> carEntities = carDbService.searchCar(searchCarRequest);
        List<CarResponse> carRespons = carEntities
                .stream()
                .map(aCarEntity -> utilMapper.mapCarResponse(aCarEntity))
                .collect(Collectors.toList());
        return  CarResponseList.builder()
                .carResponseList(carRespons)
                .build();
    }

    public CarResponseList getAllCars(){
        List<CarEntity> carEntities = carDbService.getAllCars();
        List<CarResponse> carRespons = carEntities
                .stream()
                .map(aCarEntity -> utilMapper.mapCarResponse(aCarEntity))
                .collect(Collectors.toList());
        return  CarResponseList.builder()
                .carResponseList(carRespons)
                .build();
    }

    public CarResponse findCarByName(String carName){
        return utilMapper.mapCarResponse(carDbService.getCarByName(carName));
    }
}
