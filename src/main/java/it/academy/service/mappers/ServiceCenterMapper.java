package it.academy.service.mappers;

import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.entity.ServiceCenter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ServiceCenterMapper extends CustomMapper<ServiceCenter, ServiceCenterDTO> {
    ServiceCenterMapper INSTANCE = Mappers.getMapper(ServiceCenterMapper.class);

    @Mappings({
            @Mapping(source = "fullName", target = "requisites.fullName"),
            @Mapping(source = "actualAddress", target = "requisites.actualAddress"),
            @Mapping(source = "legalAddress", target = "requisites.legalAddress"),
            @Mapping(source = "phone", target = "requisites.phone"),
            @Mapping(source = "email", target = "requisites.email"),
            @Mapping(source = "taxpayerNumber", target = "requisites.taxpayerNumber"),
            @Mapping(source = "registrationNumber", target = "requisites.registrationNumber"),
            @Mapping(source = "bankAccount", target = "bankAccount.bankAccount"),
            @Mapping(source = "bankCode", target = "bankAccount.bankCode"),
            @Mapping(source = "bankName", target = "bankAccount.bankName"),
            @Mapping(source = "bankAddress", target = "bankAccount.bankAddress"),
    })
    ServiceCenter toEntity(ServiceCenterDTO serviceCenterDTO);

    @Mappings({
            @Mapping(source = "requisites.fullName", target = "fullName"),
            @Mapping(source = "requisites.actualAddress", target = "actualAddress"),
            @Mapping(source = "requisites.legalAddress", target = "legalAddress"),
            @Mapping(source = "requisites.phone", target = "phone"),
            @Mapping(source = "requisites.email", target = "email"),
            @Mapping(source = "requisites.taxpayerNumber", target = "taxpayerNumber"),
            @Mapping(source = "requisites.registrationNumber", target = "registrationNumber"),
            @Mapping(source = "bankAccount.bankAccount", target = "bankAccount"),
            @Mapping(source = "bankAccount.bankCode", target = "bankCode"),
            @Mapping(source = "bankAccount.bankName", target = "bankName"),
            @Mapping(source = "bankAccount.bankAddress", target = "bankAddress")
    })
    ServiceCenterDTO toDTO(ServiceCenter serviceCenter);

    List<ServiceCenterDTO> toDTOList(List<ServiceCenter> serviceCenters);
}
