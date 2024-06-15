package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static it.academy.service.utils.Constants.MODELS_IS_EMPTY;
import static it.academy.service.utils.Constants.NAME_IS_EMPTY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparePartDTO {

    private Long id;

    @NotBlank(message = NAME_IS_EMPTY)
    private String name;

    private Boolean isActive;

    @NotEmpty(message = MODELS_IS_EMPTY)
    private List<Long> modelIdList;

    private String errorMessage;

}
