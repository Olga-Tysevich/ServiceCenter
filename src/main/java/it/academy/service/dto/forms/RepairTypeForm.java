package it.academy.service.dto.forms;

import it.academy.service.dto.RepairTypeDTO;
import it.academy.service.dto.StockSparePartDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairTypeForm {

    private List<RepairTypeDTO> repairTypes;

    private List<StockSparePartDTO> spareParts;

}
