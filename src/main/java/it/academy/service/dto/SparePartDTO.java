package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparePartDTO {

    private Long id;

    @NotBlank(message = "Название не может быть пустым!")
    private String name;

    private Boolean isActive;

    @NotEmpty(message = "Нужно выбрать хотя бы одну модель!")
    private List<Long> modelIdList;

    private String errorMessage;

}
