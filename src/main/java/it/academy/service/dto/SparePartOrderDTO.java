package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private String repairNumber;

    private Date orderDate;

    private Date departureDate;

    private Date deliveryDate;

    private List<OrderItemDTO> orderItems;

    private String errorMessage;

}
