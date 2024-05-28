package it.academy.service.mappers;

import it.academy.service.dto.StockSparePartDTO;
import it.academy.service.entity.SparePart;
import it.academy.service.entity.SparePartOrder;
import it.academy.service.entity.StockSparePart;
import it.academy.service.entity.embeddable.StockSparePartPK;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StockSparePartMapper {
    
    StockSparePartMapper INSTANCE = Mappers.getMapper(StockSparePartMapper.class);
    
    @Mappings({
            @Mapping(source = "primaryKey.sparePart.id", target = "id"),
            @Mapping(source = "primaryKey.sparePart.name", target = "name"),
            @Mapping(source = "primaryKey.order.id", target = "orderId"),
            @Mapping(source = "primaryKey.order.repair.id", target = "repairId"),
            @Mapping(source = "primaryKey.order.repair.repairNumber", target = "repairNumber"),
            @Mapping(source = "primaryKey.order.orderDate", target = "orderDate")
    })
    StockSparePartDTO toDTO(StockSparePart stockSparePart);

    @Mapping(expression = "java(createPK(stockSparePartDTO))", target = "primaryKey")
    StockSparePart toEntity(StockSparePartDTO stockSparePartDTO);

    List<StockSparePartDTO> toDTOList(List<StockSparePart> stockSpareParts);

    default StockSparePartPK createPK(StockSparePartDTO stockSparePartDTO) {
        return StockSparePartPK.builder()
                .sparePart(createSparePart(stockSparePartDTO.getId()))
                .order(createSparePartOrder(stockSparePartDTO.getOrderId()))
                .build();
    }

    default SparePart createSparePart(Long sparePartId) {
        return SparePart.builder()
                .id(sparePartId)
                .build();
    }

    default SparePartOrder createSparePartOrder(Long orderId) {
        return SparePartOrder.builder()
                .id(orderId)
                .build();
    }
}
