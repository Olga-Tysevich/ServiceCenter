package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static it.academy.service.utils.Constants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairTypeDTO {

    private Long id;

    @NotNull(message = NAME_IS_EMPTY)
    private String name;

    @NotNull(message = CODE_IS_EMPTY)
    private String code;

    @NotNull(message = LEVEL_IS_EMPTY)
    private String level;

    private Boolean isActive;

    private String errorMessage;

}
