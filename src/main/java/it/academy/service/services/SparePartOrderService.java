package it.academy.service.services;

import it.academy.service.dto.SparePartOrderDTO;

import java.util.List;

public interface SparePartOrderService extends CrudService<SparePartOrderDTO, Long> {

     List<SparePartOrderDTO> getListByRepairId(Long id);

}
