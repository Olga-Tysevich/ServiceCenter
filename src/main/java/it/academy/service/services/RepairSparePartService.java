package it.academy.service.services;

import it.academy.service.dto.RepairSparePartDTO;

import java.util.List;

public interface RepairSparePartService{

    List<RepairSparePartDTO> findAllByRepairId(Long id);
}
