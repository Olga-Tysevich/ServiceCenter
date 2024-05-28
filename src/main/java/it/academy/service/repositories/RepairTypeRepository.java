package it.academy.service.repositories;

import it.academy.service.entity.RepairType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairTypeRepository extends CustomCrudRepository<RepairType, Long> {

    List<RepairType> findAllByIsActiveTrue();

    boolean existsByNameAndIdIsNot(String name, Long id);

}
