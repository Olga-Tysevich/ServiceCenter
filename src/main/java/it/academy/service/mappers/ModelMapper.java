package it.academy.service.mappers;

import it.academy.service.dto.ModelDTO;
import it.academy.service.entity.Brand;
import it.academy.service.entity.DeviceType;
import it.academy.service.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelMapper extends CustomMapper<Model, ModelDTO>{

    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    @Mappings({
            @Mapping(source = "brand.id", target = "brandId"),
            @Mapping(source = "brand.name", target = "brandName"),
            @Mapping(source = "type.id", target = "deviceTypeId"),
            @Mapping(source = "type.name", target = "deviceTypeName"),
    })
    ModelDTO toDTO(Model model);

    @Mappings({
            @Mapping(expression = "java(getBrand(modelDTO.getBrandId()))", target = "brand"),
            @Mapping(expression = "java(getDeviceType(modelDTO.getDeviceTypeId()))", target = "type"),
    })
    Model toEntity(ModelDTO modelDTO);

    List<ModelDTO> toDTOList(List<Model> models);

    default Brand getBrand(Long brandId) {
        return Brand.builder()
                .id(brandId)
                .build();
    }

    default DeviceType getDeviceType(Long deviceTypeId) {
        return DeviceType.builder()
                .id(deviceTypeId)
                .build();
    }

}
