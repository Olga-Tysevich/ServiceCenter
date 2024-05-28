package it.academy.service.services.impl;

import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.StockSparePartPK;
import it.academy.service.exceptions.OrderIsEmpty;
import it.academy.service.mappers.SparePartOrderMapper;
import it.academy.service.repositories.RepairRepository;
import it.academy.service.repositories.SparePartOrderRepository;
import it.academy.service.repositories.StockSparePartRepository;
import it.academy.service.services.SparePartOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class SparePartOrderServiceImpl implements SparePartOrderService {
    private final SparePartOrderRepository orderRepository;
    private final RepairRepository repairRepository;
    private final StockSparePartRepository stockSparePartRepository;

    @Override
    public SparePartOrderDTO createOrUpdateOrder(SparePartOrderDTO orderDTO) {
        if (orderDTO == null || orderDTO.getOrderItems().isEmpty()) {
            throw new OrderIsEmpty();
        }

        SparePartOrder order = SparePartOrderMapper.INSTANCE.toEntity(orderDTO);
        SparePartOrder result = orderRepository.save(order);
        repairRepository.findById(orderDTO.getRepairId())
                .ifPresent(r -> {
                    r.setStatus(RepairStatus.WAITING_FOR_SPARE_PARTS);
                    repairRepository.save(r);
                });
        if (order.getDeliveryDate() != null) {
            createStockSparePart(order);
        }
        return SparePartOrderMapper.INSTANCE.toDTO(result);
    }

    @Override
    public List<SparePartOrderDTO> getListByRepairId(Long id) {
        return Optional.ofNullable(id)
                .map(orderRepository::findAllByRepairId)
                .map(SparePartOrderMapper.INSTANCE::toDTOList)
                .orElse(new ArrayList<>());
    }

    private void createStockSparePart(SparePartOrder order) {
        List<StockSparePart> stockSpareParts =order.getOrderItems().stream()
                .map(i -> StockSparePart.builder()
                        .primaryKey(new StockSparePartPK(i.getSparePart(), i.getSparePartOrder()))
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());
        stockSparePartRepository.saveAll(stockSpareParts);
    }
}
