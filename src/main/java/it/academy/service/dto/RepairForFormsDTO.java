package it.academy.service.dto;

import it.academy.service.entity.RepairStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

import static it.academy.service.utils.Constants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairForFormsDTO implements RepairDTO{

    private Long id;

    private Long serviceCenterId;

    @NotNull
    private String status = RepairStatus.REQUEST.name();

    @NotNull
    private String category;

    @NotNull
    private Long brandId;

    @NotNull
    private Long modelId;

    private String deviceDescription;

    @NotBlank(message = SERIAL_NUMBER_IS_EMPTY)
    private String serialNumber;

    @NotBlank(message = DEFECT_DESCRIPTION_IS_EMPTY)
    private String defectDescription;

    @NotBlank(message = REPAIR_NUMBER_IS_EMPTY)
    private String repairNumber;

    @NotNull(message = DATE_OF_SALE_IS_EMPTY)
    private Date dateOfSale;

    @NotBlank(message = SALESMAN_NAME_IS_EMPTY)
    private String salesmanName;

    @NotBlank(message = SALESMAN_PHONE_IS_EMPTY)
    private String salesmanPhone;

    @NotBlank(message = BUYER_NAME_IS_EMPTY)
    private String buyerName;

    @NotBlank(message = BUYER_SURNAME_IS_EMPTY)
    private String buyerSurname;

    @NotBlank(message = BUYER_PHONE_IS_EMPTY)
    private String buyerPhone;

    private RepairTypeDTO repairType;

    private String errorMessage;

}
