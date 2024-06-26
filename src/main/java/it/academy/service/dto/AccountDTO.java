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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class AccountDTO {

    private Long id;

    private Long serviceCenterId;

    private String serviceCenterName;

    @NotNull(message = "Некорректная роль!")
    private RoleEnum role;

    @NotEmpty(message = "Email не может быть пустым!")
    @Pattern(regexp = "^[a-zA-Z0-9-.]+@([a-zA-Z-]+\\\\.)+[a-zA-Z-]{2,4}$", message = "Некорректный формат email!")
    private String email;

    @NotEmpty(message = "Имя пользователя не может быть пустым!")
    private String userName;

    @NotEmpty(message = "Фамилия пользователя не может быть пустой!")
    private String userSurname;

    private String password;

    private String passwordConfirm;

    @NotNull
    private Boolean isActive;

    private String errorMessage;

}
