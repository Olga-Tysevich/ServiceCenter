package it.academy.service.services;

import it.academy.service.dto.StockSparePartDTO;

import java.util.List;

public interface StockSparePartService {

    List<StockSparePartDTO> findByModelId(Long id);

}
