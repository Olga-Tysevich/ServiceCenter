package it.academy.service.services;

import it.academy.service.dto.ServiceCenterDTO;

import java.util.List;

public interface ServiceCenterService extends CrudService<ServiceCenterDTO, Long>{

    List<ServiceCenterDTO> findActiveServiceCenters();

}
