package org.clane.cardemo.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String engineNo;

    @Column(nullable = false)
    private Long yearOfMake;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<CategoryEntity> categoryEntityList;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<TagEntity> tagEntityList;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<ImageEntity> imageEntityList;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedAt;
}
