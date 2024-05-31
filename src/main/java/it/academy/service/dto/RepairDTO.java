package it.academy.service.dto;

import it.academy.service.entity.RepairStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairDTO {

    private Long id;

    private Long serviceCenterId;

    @NotNull(message = "Ошибка статуса ремонта!")
    private String status = RepairStatus.REQUEST.name();

    @NotNull(message = "Категория обязателеный параметр!")
    private String category;

    @NotNull(message = "Бренд обязателеный параметр!")
    private Long brandId;

    @NotNull(message = "Модель обязателеный параметр!")
    private Long modelId;

    private String deviceDescription;

    @NotBlank(message = "Серийный номер обязателеный параметр!")
    private String serialNumber;

    @NotBlank(message = "Описание дефекта обязателеный параметр!")
    private String defectDescription;

    @NotBlank(message = "Размер обязателеный параметр!")
    private String repairNumber;

    @NotNull(message = "Дата продажи обязателеный параметр!")
    private Date dateOfSale;

    @NotBlank(message = "Название продавца обязателеный параметр!")
    private String salesmanName;

    @NotBlank(message = "Телефон продавца обязателеный параметр!")
    private String salesmanPhone;

    @NotBlank(message = "Имя покупателя обязателеный параметр!")
    private String buyerName;

    @NotBlank(message = "Фамилия покупателя обязателеный параметр!")
    private String buyerSurname;

    @NotBlank(message = "Телефон покупателя обязателеный параметр!")
    private String buyerPhone;

    private RepairTypeDTO repairType;

    private String errorMessage;

}
