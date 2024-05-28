package it.academy.service.services;

import it.academy.service.dto.ModelDTO;
import it.academy.service.dto.forms.ModelForm;

import java.util.List;

public interface ModelService extends CrudService<ModelDTO, Long> {

    List<ModelDTO> findModelsByBrandId(Long id);

    ModelForm getModelForm(Long id);

}
