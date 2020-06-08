package org.clane.cardemo.repository;

import org.clane.cardemo.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity,Long> {

    List<TagEntity> findByTagIdIn(List<String> tagIds);

    Optional<TagEntity> findByTagId(String tagId);
}
