package org.clane.cardemo.payload;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Tag {

    private String tagName;

    private String tagId;
}
