package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairSparePartDTO {

    private Long actualRepairId;

    private Long repairIdFromOrder;

    private String repairNumberFromOrder;

    private Long orderId;

    private Date orderDate;

    private Long sparePartId;

    private String sparePartName;

    private Integer quantity;
}
