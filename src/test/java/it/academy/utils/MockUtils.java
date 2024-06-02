package it.academy.utils;

import it.academy.service.dto.AccountDTO;
import it.academy.service.dto.BrandDTO;
import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.entity.RoleEnum;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static it.academy.utils.MockConstant.*;

public class MockUtils {

    public static ServiceCenterDTO buildServiceCenter(String serviceCenterName) {
        return ServiceCenterDTO.builder()
                .serviceName(serviceCenterName)
                .bankName(SERVICE_CENTER_BANK_NAME)
                .bankAccount(SERVICE_CENTER_BANK_ACCOUNT)
                .bankCode(SERVICE_CENTER_BANK_CODE)
                .bankAddress(SERVICE_CENTER_BANK_ADDRESS)
                .fullName(SERVICE_CENTER_FULL_NAME)
                .legalAddress(SERVICE_CENTER_LEGAL_ADDRESS)
                .actualAddress(SERVICE_CENTER_ACTUAL_ADDRESS)
                .phone(SERVICE_CENTER_PHONE)
                .email(SERVICE_CENTER_EMAIL)
                .taxpayerNumber(SERVICE_CENTER_TAXPAYER_NUMBER)
                .registrationNumber(SERVICE_CENTER_REGISTRATION_NUMBER)
                .isActive(true)
                .build();
    }

    public static List<ServiceCenterDTO> buildServiceCenterList(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> buildServiceCenter(String.format(SERVICE_CENTER_NAME, i)))
                .collect(Collectors.toList());
    }

    public static AccountDTO buildAccount(String email, RoleEnum role, Long serviceCenterId) {
        return AccountDTO.builder()
                .serviceCenterId(serviceCenterId)
                .email(email)
                .role(role)
                .userName(ACCOUNT_USER_NAME)
                .userSurname(ACCOUNT_USER_SURNAME)
                .password(ACCOUNT_PASSWORD)
                .passwordConfirm(ACCOUNT_PASSWORD)
                .isActive(true)
                .build();
    }

    public static List<AccountDTO> buildAccountList(int listSize, RoleEnum role, Long serviceCenterId) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> buildAccount(String.format(ACCOUNT_EMAIL, i), role, serviceCenterId))
                .collect(Collectors.toList());
    }

    public static BrandDTO buildBrand(String name) {
        return BrandDTO.builder()
                .name(name)
                .isActive(true)
                .build();
    }

    public static List<BrandDTO> buildBrandList(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> buildBrand(String.format(BRAND_NAME, i)))
                .collect(Collectors.toList());
    }

    public static DeviceTypeDTO buildDeviceType(String name) {
        return DeviceTypeDTO.builder()
                .name(name)
                .isActive(true)
                .build();
    }

    public static List<DeviceTypeDTO> buildDeviceTypeList(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> buildDeviceType(String.format(DEVICE_TYPE_NAME, i)))
                .collect(Collectors.toList());
    }


}
