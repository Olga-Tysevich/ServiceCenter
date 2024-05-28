package it.academy.service.repositories;

import it.academy.service.entity.Model;
import it.academy.service.entity.SparePart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartRepository extends CustomCrudRepository<SparePart, Long> {
    List<SparePart> findAllByIsActiveTrueAndModelsContains(Model model);
}
