package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartOrderDTO {

    private Long id;

    @NotNull
    private Long repairId;

    private String repairNumber;

    private Date orderDate;

    private Date departureDate;

    private Date deliveryDate;

    @NotEmpty(message = "В заказе должна быть хотя бы одна запчасть!")
    private List<OrderItemDTO> orderItems;

    private String errorMessage;

}
