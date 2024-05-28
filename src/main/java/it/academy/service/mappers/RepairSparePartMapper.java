package it.academy.service.mappers;

import it.academy.service.dto.RepairSparePartDTO;
import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.RepairSparePartPK;
import it.academy.service.entity.embeddable.StockSparePartPK;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RepairSparePartMapper {
    RepairSparePartMapper INSTANCE = Mappers.getMapper(RepairSparePartMapper.class);

    @Mappings({
            @Mapping(source = "primaryKey.repair.id", target = "actualRepairId"),
            @Mapping(source = "primaryKey.order.repair.id", target = "repairIdFromOrder"),
            @Mapping(source = "primaryKey.order.repair.repairNumber", target = "repairNumberFromOrder"),
            @Mapping(source = "primaryKey.order.id", target = "orderId"),
            @Mapping(source = "primaryKey.order.orderDate", target = "orderDate"),
            @Mapping(source = "primaryKey.sparePart.id", target = "sparePartId"),
            @Mapping(source = "primaryKey.sparePart.name", target = "sparePartName")
    })
    RepairSparePartDTO toDTO(RepairSparePart repairSparePart);

    List<RepairSparePartDTO> toDTOList(List<RepairSparePart> spareParts);

    @Mapping(expression = "java(createPK(repairSparePartDTO))", target = "primaryKey")
    RepairSparePart toEntity(RepairSparePartDTO repairSparePartDTO);

    List<RepairSparePart> toEntityList(List<RepairSparePartDTO> sparePartDTOList);

    @Mapping(expression = "java(createStockPK(repairSpareParts.getPrimaryKey()))", target = "primaryKey")
    StockSparePart toStockSparePart(RepairSparePart repairSpareParts);

    default StockSparePartPK createStockPK(RepairSparePartPK repairSparePartPK) {
        return StockSparePartPK.builder()
                .order(repairSparePartPK.getOrder())
                .sparePart(repairSparePartPK.getSparePart())
                .build();
    }

    default RepairSparePartPK createPK(RepairSparePartDTO repairSparePartDTO) {
        return RepairSparePartPK.builder()
                .sparePart(createSparePart(repairSparePartDTO.getSparePartId()))
                .order(createSparePartOrder(repairSparePartDTO.getOrderId()))
                .repair(createRepair(repairSparePartDTO.getActualRepairId()))
                .build();
    }

    default Repair createRepair(Long repairId) {
        return Repair.builder()
                .id(repairId)
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
