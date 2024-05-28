package it.academy.service.mappers;

import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.entity.DeviceType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeviceTypeMapper extends CustomMapper<DeviceType, DeviceTypeDTO>{

    DeviceTypeMapper INSTANCE = Mappers.getMapper(DeviceTypeMapper.class);

    DeviceType toEntity(DeviceTypeDTO deviceTypeDTO);

    DeviceTypeDTO toDTO(DeviceType deviceType);

    List<DeviceTypeDTO> toDTOList(List<DeviceType> deviceTypeList);

}
