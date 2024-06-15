package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static it.academy.service.utils.Constants.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterDTO {
    private Long id;

    @NotBlank(message = NAME_IS_EMPTY)
    private String serviceName;

    @NotBlank(message = BANK_NAME_IS_EMPTY)
    private String bankName;

    @NotBlank(message = BANK_ACCOUNT_IS_EMPTY)
    private String bankAccount;

    @NotBlank(message = BANK_CODE_IS_EMPTY)
    private String bankCode;

    @NotBlank(message = BANK_ADDRESS_IS_EMPTY)
    private String bankAddress;

    @NotBlank(message = FULL_NAME_IS_EMPTY)
    private String fullName;

    @NotBlank(message = LEGAL_ADDRESS_IS_EMPTY)
    private String legalAddress;

    @NotBlank(message = ACTUAL_ADDRESS_IS_EMPTY)
    private String actualAddress;

    @NotBlank(message = PHONE_IS_EMPTY)
    @Pattern(regexp = PHONE_REGEX,
            message = INVALID_PHONE_NUMBER)
    private String phone;

    @NotBlank(message = EMAIL_IS_EMPTY)
    @Pattern(regexp = EMAIL_REGEX, message = INVALID_EMAIL)
    private String email;

    @NotNull(message = TAXPAYER_NUMBER_IS_EMPTY)
    private Integer taxpayerNumber;

    @NotNull(message = REGISTRATION_IS_EMPTY)
    private Integer registrationNumber;

    private Boolean isActive;

}
