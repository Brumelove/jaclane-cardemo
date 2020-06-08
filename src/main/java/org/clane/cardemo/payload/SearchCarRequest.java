package org.clane.cardemo.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SearchCarRequest {

    private String name ;

    private String description ;

    private String categoryId;

    private  String tagId;
}
