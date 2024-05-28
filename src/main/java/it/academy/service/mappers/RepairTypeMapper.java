package it.academy.service.mappers;

import it.academy.service.dto.RepairTypeDTO;
import it.academy.service.entity.RepairType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RepairTypeMapper extends CustomMapper<RepairType, RepairTypeDTO>{
    RepairTypeMapper INSTANCE = Mappers.getMapper(RepairTypeMapper.class);

    RepairTypeDTO toDTO(RepairType repairType);

    RepairType toEntity(RepairTypeDTO repairType);

    List<RepairTypeDTO> toDTOList(List<RepairType> repairTypes);
}
