package it.academy.service.dto;

import it.academy.service.dto.validator.PasswordMatches;
import it.academy.service.entity.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static it.academy.service.utils.Constants.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class AccountDTO {

    private Long id;

    private Long serviceCenterId;

    private String serviceCenterName;

    @NotNull(message = INVALID_ROLE)
    private RoleEnum role;

    @NotEmpty(message = EMAIL_IS_EMPTY)
    @Pattern(regexp = EMAIL_REGEX, message = INVALID_EMAIL)
    private String email;

    @NotEmpty(message = USER_NAME_IS_EMPTY)
    private String userName;

    @NotEmpty(message = LAST_NAME_IS_EMPTY)
    private String userSurname;

    private String password;

    private String passwordConfirm;

    @NotNull
    private Boolean isActive;

    private String errorMessage;

}
