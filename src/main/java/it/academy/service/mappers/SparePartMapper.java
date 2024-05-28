package it.academy.service.mappers;

import it.academy.service.dto.SparePartDTO;
import it.academy.service.entity.Model;
import it.academy.service.entity.SparePart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SparePartMapper extends CustomMapper<SparePart, SparePartDTO>{

    SparePartMapper INSTANCE = Mappers.getMapper(SparePartMapper.class);

    @Mapping(expression = "java(getModelsId(sparePart.getModels()))", target = "modelIdList")
    SparePartDTO toDTO(SparePart sparePart);

    List<SparePartDTO> toDTOList(List<SparePart> spareParts);

    default List<Long> getModelsId(Set<Model> models) {
        return models.stream()
                .map(Model::getId)
                .collect(Collectors.toList());
    }
}
