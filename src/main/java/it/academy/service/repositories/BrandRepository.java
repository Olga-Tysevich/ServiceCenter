package it.academy.service.repositories;

import it.academy.service.entity.Brand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends CustomCrudRepository<Brand, Long> {

    List<Brand> findAllByIsActiveIsTrue();

    boolean existsByNameAndIdIsNot(String name, Long id);

}
