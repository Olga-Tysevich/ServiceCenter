package it.academy.service.mappers;

import it.academy.service.dto.OrderItemDTO;
import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.entity.OrderItem;
import it.academy.service.entity.Repair;
import it.academy.service.entity.SparePart;
import it.academy.service.entity.SparePartOrder;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface SparePartOrderMapper extends CustomMapper<SparePartOrder, SparePartOrderDTO> {

    SparePartOrderMapper INSTANCE = Mappers.getMapper(SparePartOrderMapper.class);

    @Mappings({
            @Mapping(source = "repair.id", target = "repairId"),
            @Mapping(source = "repair.repairNumber", target = "repairNumber"),
            @Mapping(expression = "java(toOrderItemDTOList(sparePartOrder.getOrderItems()))", target = "orderItems")
    })
    SparePartOrderDTO toDTO(SparePartOrder sparePartOrder);

    List<SparePartOrderDTO> toDTOList(List<SparePartOrder> sparePartOrders);

    @Mappings({
            @Mapping(expression = "java(buildRepair(sparePartOrderDTO))", target = "repair"),
            @Mapping(expression = "java(createOrderItems(sparePartOrderDTO, sparePartOrder))", target = "orderItems")
    })
    SparePartOrder toEntity(SparePartOrderDTO sparePartOrderDTO);

    default List<OrderItemDTO> toOrderItemDTOList(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItemDTO.builder()
                        .id(orderItem.getId())
                        .sparePartId(orderItem.getSparePart().getId())
                        .name(orderItem.getSparePart().getName())
                        .quantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    default Repair buildRepair(SparePartOrderDTO sparePartOrderDTO) {
        return Repair.builder()
                .id(sparePartOrderDTO.getRepairId())
                .build();
    }

    default Set<OrderItem> createOrderItems(SparePartOrderDTO sparePartOrderDTO, SparePartOrder order) {
        return sparePartOrderDTO.getOrderItems().stream()
                .map(i -> {
                    SparePart sparePart = SparePart.builder()
                            .id(i.getId())
                            .build();
                    return OrderItem.builder()
                            .sparePartOrder(order)
                            .sparePart(sparePart)
                            .quantity(i.getQuantity())
                            .build();
                }).collect(Collectors.toSet());
    }

}

