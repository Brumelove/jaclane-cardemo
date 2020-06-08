package org.clane.cardemo.payload;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@ToString
@Data
public class UpdateCarRequest {

    private String name;

    private String description;

    @Pattern(regexp = "^[0-9]*$" , message = "Only digits are allowed ")
    private String yearOfMake;

    private List<Image> imageList;

    private List<String> categoryIds;

    private List<String> tagIds;
}
