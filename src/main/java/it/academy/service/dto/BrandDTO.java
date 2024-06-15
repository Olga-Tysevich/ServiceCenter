package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static it.academy.service.utils.Constants.NAME_IS_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;

    @NotBlank(message = NAME_IS_EMPTY)
    private String name;

    private Boolean isActive;

    private String errorMessage;

}
