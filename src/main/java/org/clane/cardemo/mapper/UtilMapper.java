package org.clane.cardemo.mapper;

import org.apache.commons.lang3.StringUtils;
import org.clane.cardemo.entity.CarEntity;
import org.clane.cardemo.entity.CategoryEntity;
import org.clane.cardemo.entity.ImageEntity;
import org.clane.cardemo.entity.TagEntity;
import org.clane.cardemo.payload.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UtilMapper {

    public Function<CreateCarRequest, CarEntity> mapCarEntity = createCarRequest -> {
        CarEntity carEntity = new CarEntity();
        carEntity.setName(createCarRequest.getName());
        carEntity.setDescription(StringUtils.isBlank(createCarRequest.getDescription()) ? "Description of " + createCarRequest.getName() : createCarRequest.getDescription());
        carEntity.setYearOfMake(Long.valueOf(createCarRequest.getYearOfMake()));
        return carEntity;
    };

    public Function<Image, ImageEntity> mapImageEntity = image -> {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageLocation(image.getImageLocation());
        imageEntity.setImageName(image.getImageName());
        return imageEntity;
    };

    public CarResponse mapCarResponse(CarEntity carEntity) {
        List<Category> categoryList = carEntity.getCategoryEntityList()
                .stream()
                .map(mapCategory)
                .collect(Collectors.toList());

        List<Image> imageList = carEntity.getImageEntityList()
                .stream()
                .map(mapImage)
                .collect(Collectors.toList());

        List<Tag> tagList = carEntity.getTagEntityList()
                .stream()
                .map(mapTag)
                .collect(Collectors.toList());

        return CarResponse.builder()
                .carId(carEntity.getId())
                .categoryList(categoryList)
                .description(carEntity.getDescription())
                .engineNo(carEntity.getEngineNo())
                .imageList(imageList)
                .name(carEntity.getName())
                .tagList(tagList)
                .yearOfMake(carEntity.getYearOfMake())
                .createDate(carEntity.getCreatedAt())
                .build();
    };

    private Function<ImageEntity,Image> mapImage = imageEntity -> {
        Image image = new Image();
        image.setImageLocation(imageEntity.getImageLocation());
        image.setImageName(imageEntity.getImageName());
        return image;
    };

    private Function<TagEntity, Tag> mapTag = tagEntity -> {
        Tag tag = new Tag();
        tag.setTagId(tagEntity.getTagId());
        tag.setTagName(tagEntity.getTagName());
        return tag;
    };

    private Function<CategoryEntity, Category> mapCategory = categoryEntity -> {
        Category category = new Category();
        category.setCategoryId(categoryEntity.getCategoryId());
        category.setCategoryName(categoryEntity.getCategoryName());
        return category;
    };
}
