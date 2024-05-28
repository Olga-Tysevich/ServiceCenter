package it.academy.service.services;

import it.academy.service.dto.SparePartDTO;
import it.academy.service.dto.forms.SparePartForm;

import java.util.List;

public interface SparePartService extends CrudService<SparePartDTO, Long>{

    List<SparePartDTO> findByModelId(Long id);

    SparePartForm getSparePartForm(Long id);

}
