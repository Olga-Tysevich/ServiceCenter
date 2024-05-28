package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteRepairDTO {

    private Long repairId;

    private Long repairTypeId;

    private List<RepairSparePartDTO> spareParts;

}
