package it.academy.service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {

    private Long id;

    @NotBlank(message = "Название модели не может быть пустым!")
    private String name;

    @NotNull(message = "Ошибка при выборе бренда!")
    private Long brandId;

    private String brandName;

    @NotNull(message = "Ошибка при выборе типа устройства!")
    private Long deviceTypeId;

    private String deviceTypeName;

    private Boolean isActive;

}
