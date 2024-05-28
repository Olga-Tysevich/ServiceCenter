package it.academy.service.services;

import it.academy.service.dto.SparePartOrderDTO;

import java.util.List;

public interface SparePartOrderService {

     SparePartOrderDTO createOrUpdateOrder(SparePartOrderDTO orderDTO);

     List<SparePartOrderDTO> getListByRepairId(Long id);
}
