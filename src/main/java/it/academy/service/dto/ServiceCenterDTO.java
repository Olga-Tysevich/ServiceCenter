package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterDTO {

    private Long id;

    @NotBlank(message = "Имя сервисного центра не может быть пустым!")
    private String serviceName;

    @NotBlank(message = "Название банка не может быть пустым!")
    private String bankName;

    @NotBlank(message = "Банковский аккаунт не может быть пустым!")
    private String bankAccount;

    @NotBlank(message = "Код банка не может быть пустым!")
    private String bankCode;

    @NotBlank(message = "Адреес банка не может быть пустым!")
    private String bankAddress;

    @NotBlank(message = "Название сервисного центра не может быть пустым!")
    private String fullName;

    @NotBlank(message = "Юр. адрес не может быть пустым!")
    private String legalAddress;

    @NotBlank(message = "Фактический адрес не может быть пустым!")
    private String actualAddress;

    @NotBlank(message = "Телефон не может быть пустым!")
    @Pattern(regexp = "^(\\+375|80) ?(29|25|44|33) ?(\\d{3})\\-?(\\d{2})\\-?(\\d{2})$",
            message = "Номер телефона должен быть в формате +375 29 111-11-11/80 29 111-11-11")
    private String phone;

    @NotBlank(message = "Email не может быть пустым!")
    @Pattern(regexp = "^[a-zA-Z0-9-.]+@([a-zA-Z-]+\\.)+[a-zA-Z-]{2,4}$", message = "Ведите email в формате email@mail.com")
    private String email;

    @NotNull(message = "Учетные данные не могут быть пустыми!")
    private Integer taxpayerNumber;

    @NotNull(message = "Учетные данные не могут быть пустыми!")
    private Integer registrationNumber;

    private Boolean isActive;

}
