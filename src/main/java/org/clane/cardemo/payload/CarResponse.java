package org.clane.cardemo.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.flywaydb.core.internal.database.DatabaseExecutionStrategy;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class CarResponse {

    private Long carId;

    private String name;

    private String description;

    private String engineNo;

    private long yearOfMake;

    private List<Image> imageList;

    private List<Category> categoryList;

    private List<Tag> tagList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createDate;
}
