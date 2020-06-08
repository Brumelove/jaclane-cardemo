package org.clane.cardemo.repository;

import org.clane.cardemo.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> , JpaSpecificationExecutor<CarEntity> {

    List<CarEntity> findByOrderByNameAsc();

    Optional<CarEntity> findByName(String name);
}
