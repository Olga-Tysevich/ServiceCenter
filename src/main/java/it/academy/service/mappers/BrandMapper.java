package it.academy.service.mappers;

import it.academy.service.dto.BrandDTO;
import it.academy.service.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BrandMapper  extends CustomMapper<Brand, BrandDTO> {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandDTO toDTO(Brand brand);

    Brand toEntity(BrandDTO brandDTO);

    List<BrandDTO> toDTOList(List<Brand> brands);
}
