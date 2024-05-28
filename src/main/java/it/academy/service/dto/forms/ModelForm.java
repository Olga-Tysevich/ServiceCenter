package it.academy.service.dto.forms;

import it.academy.service.dto.BrandDTO;
import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.dto.ModelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelForm {

    private List<BrandDTO> brands;

    private List<DeviceTypeDTO> deviceTypes;

    private ModelDTO model;

}
