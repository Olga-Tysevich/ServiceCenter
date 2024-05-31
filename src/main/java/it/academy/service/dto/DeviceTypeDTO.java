package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeDTO {

    private Long id;

    @NotEmpty(message = "Название не может быть пустым!")
    private String name;

    private Boolean isActive;

    private String errorMessage;

}
