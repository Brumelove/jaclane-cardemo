package org.clane.cardemo.payload;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Category {

    private String categoryName;

    private String categoryId;
}
