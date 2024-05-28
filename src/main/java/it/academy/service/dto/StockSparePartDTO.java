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
public class StockSparePartDTO {

    private Long id;

    private String name;

    private Long orderId;

    private Long repairId;

    private String repairNumber;

    private Date orderDate;

    private Integer quantity;

}
