package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartOrderDTO {

    private Long id;

    private Long repairId;

    private String repairNumber;

    private Date orderDate;

    private Date departureDate;

    private Date deliveryDate;

    private List<OrderItemDTO> orderItems;

}
