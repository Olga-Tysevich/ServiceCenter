package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairTypeDTO {

    private Long id;

    @NotNull(message = "Тип ремонта не может быть пустым!")
    private String name;

    @NotNull(message = "Код не может быть пустым!")
    private String code;

    @NotNull(message = "Уровень не может быть пустым!")
    private String level;

    private Boolean isActive;

}
