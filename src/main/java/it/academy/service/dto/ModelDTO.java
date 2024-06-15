package it.academy.service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static it.academy.service.utils.Constants.NAME_IS_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {

    private Long id;

    @NotBlank(message = NAME_IS_EMPTY)
    private String name;

    @NotNull
    private Long brandId;

    private String brandName;

    @NotNull
    private Long deviceTypeId;

    private String deviceTypeName;

    private Boolean isActive;

    private String errorMessage;

}
