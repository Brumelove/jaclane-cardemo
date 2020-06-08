package org.clane.cardemo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.clane.cardemo.entity.*;
import org.clane.cardemo.exception.CarApiException;
import org.clane.cardemo.exception.ErrorResponse;
import org.clane.cardemo.payload.Image;
import org.clane.cardemo.payload.SearchCarRequest;
import org.clane.cardemo.repository.CarRepository;
import org.clane.cardemo.repository.CategoryRepository;
import org.clane.cardemo.repository.ImageRepository;
import org.clane.cardemo.repository.TagRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CarDbService {

    private CarRepository carRepository;

    private TagRepository tagRepository;

    private CategoryRepository categoryRepository;

    private ImageRepository imageRepository;

    public CarEntity updateCar(CarEntity carEntity,List<String> tagIds , List<String> categoryIds , List<ImageEntity> imageEntityList){
        List<TagEntity> tagEntityList = new ArrayList<>();
        if(tagIds != null && !tagIds.isEmpty()){
            tagEntityList = tagRepository.findByTagIdIn(tagIds);
            //ensure that we do not update with an empty tagList
            if(!tagEntityList.isEmpty()){
                carEntity.setTagEntityList(tagEntityList);
            }

        }
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        if(categoryIds != null && !categoryIds.isEmpty()){
            categoryEntityList = categoryRepository.findByCategoryIdIn(categoryIds);
            //ensure that we do not update with an empty categoryList
            if(!categoryEntityList.isEmpty()){
                carEntity.setCategoryEntityList(categoryEntityList);
            }
        }
        if(imageEntityList!= null && !imageEntityList.isEmpty()){
            carEntity.setImageEntityList(imageRepository.saveAll(imageEntityList));
        }
        return carRepository.save(carEntity);
    }

    @Transactional
    public CarEntity createCar(CarEntity carEntity, List<String> tagIds , List<String> categoryIds , List<ImageEntity> imageEntityList){
        List<TagEntity> tagEntityList = new ArrayList<>();
        if(tagIds != null && !tagIds.isEmpty()){
            tagEntityList = tagRepository.findByTagIdIn(tagIds);
        }
        if(tagEntityList.isEmpty()){
            //since we didn't find any tag, just chose one and use with the car
            List<TagEntity> entities = tagRepository.findAll();
            tagEntityList.add(entities.get((int) RandomUtils.nextLong(0,entities.size()-1)));
        }

        String engineNo = buildSequentialEngineNo();
        carEntity.setEngineNo(engineNo);

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        if(categoryIds != null && !categoryIds.isEmpty()){
            categoryEntityList = categoryRepository.findByCategoryIdIn(categoryIds);
        }
        if(categoryEntityList.isEmpty()){
            //since we didn't find any category, just chose one and use with the car
            List<CategoryEntity> entities = categoryRepository.findAll();
            categoryEntityList.add(entities.get((int) RandomUtils.nextLong(0,entities.size()-1)));
        }

        carEntity.setCategoryEntityList(categoryEntityList);
        carEntity.setImageEntityList(imageRepository.saveAll(imageEntityList));
        carEntity.setTagEntityList(tagEntityList);
        return carRepository.save(carEntity);
    }

    private String buildSequentialEngineNo(){
        Long records = carRepository.count();
        return StringUtils.leftPad(String.valueOf(records + 1), 5, '0');
    }

    public List<CarEntity>  searchCar(SearchCarRequest searchCarRequest){
        return carRepository.findAll((Specification<CarEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(searchCarRequest.getName())){
                Predicate searchByNamePredicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), searchCarRequest.getName()));
                predicates.add(searchByNamePredicate);
            }
            if(StringUtils.isNotBlank(searchCarRequest.getDescription())){
                Predicate searchByDescriptionPredicate = criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%" +searchCarRequest.getDescription() + "%"));
                predicates.add(searchByDescriptionPredicate);
            }

            if(StringUtils.isNotBlank(searchCarRequest.getTagId())){
                ListJoin<CarEntity, TagEntity> carEntityTagEntityListJoin = root.join(CarEntity_.tagEntityList);
                Predicate searchByTagPredicate = criteriaBuilder.and(criteriaBuilder.equal(carEntityTagEntityListJoin.get(TagEntity_.tagId),searchCarRequest.getTagId()));
                predicates.add(searchByTagPredicate);
            }
            if(StringUtils.isNotBlank(searchCarRequest.getCategoryId())){
                ListJoin<CarEntity, CategoryEntity> carEntityCategoryEntityListJoin = root.join(CarEntity_.categoryEntityList);
                Predicate searchByCategoryPredicate = criteriaBuilder.and(criteriaBuilder.equal(carEntityCategoryEntityListJoin.get(CategoryEntity_.categoryId),searchCarRequest.getCategoryId()));
                predicates.add(searchByCategoryPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }


    public List<CarEntity> getAllCars(){
        return carRepository.findByOrderByNameAsc();
    }

    public void confirmUniqueCarName(String carName){
        Optional<CarEntity> optionalCarEntity = carRepository.findByName(carName);
        if(optionalCarEntity.isPresent()){
            throw new CarApiException(new ErrorResponse("Car Name [ " + carName + " ]  not unique"));
        }
    }

    public CarEntity getCarByName(String carName){
        Optional<CarEntity> optionalCarEntity = carRepository.findByName(carName);
        return optionalCarEntity.orElse(new CarEntity());
    }

    public CarEntity getCarById(Long carId){
        Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);
        return optionalCarEntity.orElseThrow(() ->  new CarApiException(new ErrorResponse("Car with Id [ " + carId + " ]  not found")));
    }
}
