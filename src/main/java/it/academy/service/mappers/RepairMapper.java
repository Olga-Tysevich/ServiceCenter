package it.academy.service.mappers;

import it.academy.service.dto.RepairDTO;
import it.academy.service.dto.RepairForFormsDTO;
import it.academy.service.dto.RepairForTableDTO;
import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.Buyer;
import it.academy.service.entity.embeddable.Salesman;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.academy.service.utils.Constants.DEVICE_DESCRIPTION_PATTERN;

@Mapper
public interface RepairMapper extends CustomMapper<Repair, RepairDTO> {

    RepairMapper INSTANCE = Mappers.getMapper(RepairMapper.class);

    @Mappings({
            @Mapping(source = "serviceCenter.serviceName", target = "serviceCenterName"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "repairNumber", target = "repairNumber"),
            @Mapping(expression = "java(deviceToDeviceDescription(repair.getDevice()))", target = "deviceDescription"),
            @Mapping(source = "device.serialNumber", target = "serialNumber"),
            @Mapping(source = "defectDescription", target = "defectDescription"),
            @Mapping(source = "status", target = "status")
    })
    RepairForTableDTO toDTOForTable(Repair repair);

    List<RepairForTableDTO> toDTOListForTable(List<Repair> repairs);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "serviceCenter.id", target = "serviceCenterId"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "device.model.brand.id", target = "brandId"),
            @Mapping(source = "device.model.id", target = "modelId"),
            @Mapping(expression = "java(deviceToDeviceDescription(repair.getDevice()))", target = "deviceDescription"),
            @Mapping(source = "device.serialNumber", target = "serialNumber"),
            @Mapping(source = "defectDescription", target = "defectDescription"),
            @Mapping(source = "repairNumber", target = "repairNumber"),
            @Mapping(source = "device.dateOfSale", target = "dateOfSale"),
            @Mapping(source = "device.salesman.name", target = "salesmanName"),
            @Mapping(source = "device.salesman.phone", target = "salesmanPhone"),
            @Mapping(source = "device.buyer.name", target = "buyerName"),
            @Mapping(source = "device.buyer.surname", target = "buyerSurname"),
            @Mapping(source = "device.buyer.phone", target = "buyerPhone")
    })
    RepairForFormsDTO toDTO(Repair repair);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(expression = "java(getServiceCenter(repairForFormsDTO.getServiceCenterId()))",target = "serviceCenter"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "category", target = "category"),
            @Mapping(expression = "java(getDevice(repairForFormsDTO))", target = "device"),
            @Mapping(source = "defectDescription", target = "defectDescription"),
            @Mapping(source = "repairNumber", target = "repairNumber"),
            @Mapping(target = "repairType", ignore = true),
            @Mapping(target = "startDate", ignore = true),
            @Mapping(target = "endDate", ignore = true)
    })
    Repair toEntity(RepairForFormsDTO repairForFormsDTO);

    default List<RepairDTO> toDTOList(List<Repair> repairs) {
        if ( repairs == null ) {
            return null;
        }

        return repairs.stream()
                .map(this::toDTOForTable)
                .collect(Collectors.toList());
    }


    default String categoryToName(RepairCategory category) {
        return category.name();
    }

    default String statusToName(RepairStatus status) {
        return status.name();
    }

    default String deviceToDeviceDescription(Device device) {
        return String.format(DEVICE_DESCRIPTION_PATTERN,
                device.getModel().getType().getName(),
                device.getModel().getBrand().getName(),
                device.getModel().getName());
    }

    default ServiceCenter getServiceCenter(Long id) {
        return ServiceCenter.builder()
                .id(id)
                .build();
    }

    default Device getDevice(RepairForFormsDTO repairForFormsDTO) {
        return Device.builder()
                .serialNumber(repairForFormsDTO.getSerialNumber())
                .buyer(Buyer.builder()
                        .name(repairForFormsDTO.getBuyerName())
                        .surname(repairForFormsDTO.getBuyerSurname())
                        .phone(repairForFormsDTO.getBuyerPhone())
                        .build())
                .salesman(Salesman.builder()
                        .name(repairForFormsDTO.getSalesmanName())
                        .phone(repairForFormsDTO.getSalesmanPhone())
                        .build())
                .dateOfSale(repairForFormsDTO.getDateOfSale())
                .build();
    }
}
