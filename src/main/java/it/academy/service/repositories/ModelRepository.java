package it.academy.service.repositories;

import it.academy.service.entity.Model;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends CustomCrudRepository<Model, Long> {

    List<Model> findAllByBrand_IdIsAndIsActiveTrue(Long id);

    boolean existsByBrand_IdAndType_IdAndNameAndIdIsNot(Long brandId, Long typeId, String name, Long id);

    List<Model> findAllByIsActiveTrue();

}
