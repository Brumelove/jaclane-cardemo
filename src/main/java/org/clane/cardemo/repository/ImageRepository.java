package org.clane.cardemo.repository;

import org.clane.cardemo.entity.ImageEntity;
import org.clane.cardemo.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Long> {
}
