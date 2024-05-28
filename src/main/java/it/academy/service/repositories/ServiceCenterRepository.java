package it.academy.service.repositories;

import it.academy.service.entity.ServiceCenter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCenterRepository extends CustomCrudRepository<ServiceCenter, Long> {

    List<ServiceCenter> findAllByIsActiveTrue();

}
