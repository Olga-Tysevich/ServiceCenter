package it.academy.service.repositories;

import it.academy.service.entity.DeviceType;

import java.util.List;

public interface DeviceTypeRepository extends CustomCrudRepository<DeviceType, Long> {

    List<DeviceType> findAllByIsActiveTrue();

    boolean existsByNameAndIdIsNot(String name, Long id);

}
