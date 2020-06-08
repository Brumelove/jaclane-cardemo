package org.clane.cardemo.payload;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ToString
public class CreateCarRequest {

    @NotBlank(message = "car name required")
    private String name;

    private String description;

    @NotBlank(message = "YearOfMake is required")
    @Pattern(regexp = "^[0-9]*$" , message = "Only digits are allowed ")
    private String yearOfMake;

    @NotEmpty(message = "image list can not be empty")
    private List<Image> imageList;

    private List<String> categoryIds;

    private List<String> tagIds;
}
