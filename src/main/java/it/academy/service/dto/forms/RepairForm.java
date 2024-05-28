package it.academy.service.dto.forms;

import it.academy.service.dto.BrandDTO;
import it.academy.service.dto.ModelDTO;
import it.academy.service.dto.RepairDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairForm {
    private List<BrandDTO> brandList;
    private List<ModelDTO> modelList;
    private RepairDTO repair;
}
