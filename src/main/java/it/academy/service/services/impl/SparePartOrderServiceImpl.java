package it.academy.service.services.impl;

import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.entity.RepairStatus;
import it.academy.service.entity.SparePartOrder;
import it.academy.service.entity.StockSparePart;
import it.academy.service.entity.embeddable.StockSparePartPK;
import it.academy.service.mappers.SparePartOrderMapper;
import it.academy.service.repositories.RepairRepository;
import it.academy.service.repositories.SparePartOrderRepository;
import it.academy.service.repositories.StockSparePartRepository;
import it.academy.service.repositories.impl.SparePartOrderSpecification;
import it.academy.service.services.SparePartOrderService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.academy.service.utils.Constants.ZERO;
import static it.academy.service.utils.UIConstants.*;

@Transactional
@Service
public class SparePartOrderServiceImpl extends CrudServiceImpl<SparePartOrder, SparePartOrderDTO, Long> implements SparePartOrderService {
    private final SparePartOrderRepository orderRepository;
    private final RepairRepository repairRepository;
    private final StockSparePartRepository stockSparePartRepository;

    public SparePartOrderServiceImpl(SparePartOrderRepository orderRepository,
                                     RepairRepository repairRepository,
                                     StockSparePartRepository stockSparePartRepository) {
        super(orderRepository, SparePartOrderMapper.INSTANCE);
        this.orderRepository = orderRepository;
        this.repairRepository = repairRepository;
        this.stockSparePartRepository = stockSparePartRepository;

    }

    @Override
    public SparePartOrderDTO createOrUpdate(@NotNull SparePartOrderDTO orderDTO) {
        Long id = orderDTO.getId();
        return id == null ? createSparePartOrder(orderDTO) : updateSparePartOrder(orderDTO);
    }

    @Override
    public List<SparePartOrderDTO> getListByRepairId(Long id) {
        return Optional.ofNullable(id)
                .map(orderRepository::findAllByRepairId)
                .map(SparePartOrderMapper.INSTANCE::toDTOList)
                .orElse(new ArrayList<>());
    }


    @Override
    protected String getTablePagePath() {
        return SPARE_PART_ORDER_TABLE_PAGE;
    }

    @Override
    protected Specification<SparePartOrder> getSpecification(String keyword) {
        return SparePartOrderSpecification.search(keyword);
    }

    private SparePartOrderDTO createSparePartOrder(SparePartOrderDTO orderDTO) {
        if (orderDTO.getOrderItems().isEmpty()) {
            orderDTO.setErrorMessage(ORDER_IS_EMPTY);
        }

        SparePartOrder order = SparePartOrderMapper.INSTANCE.toEntity(orderDTO);
        SparePartOrder result = orderRepository.save(order);
        repairRepository.findById(orderDTO.getRepairId())
                .ifPresent(r -> {
                    r.setStatus(RepairStatus.WAITING_FOR_SPARE_PARTS);
                    repairRepository.save(r);
                });

        return SparePartOrderMapper.INSTANCE.toDTO(result);
    }

    private SparePartOrderDTO updateSparePartOrder(SparePartOrderDTO orderDTO) {
        if (!checkDates(orderDTO.getDepartureDate(), orderDTO.getDeliveryDate())) {
            orderDTO.setErrorMessage(INVALID_DATE);
            return orderDTO;
        }

        SparePartOrder order = orderRepository.getById(orderDTO.getId());
        if (order.getDepartureDate() == null) {
            order.setDepartureDate(orderDTO.getDepartureDate());
        }

        if (order.getDeliveryDate() == null) {
            order.setDeliveryDate(orderDTO.getDeliveryDate());
            createStockSparePart(order);
        }

        SparePartOrder result = orderRepository.save(order);
        return SparePartOrderMapper.INSTANCE.toDTO(result);
    }

    private boolean checkDates(Date departureDate, Date deliveryDate) {
        if (departureDate == null || departureDate.toLocalDate().isBefore(java.time.LocalDate.now())) {
            return false;
        }
        if (deliveryDate != null) {
            return departureDate.compareTo(deliveryDate) <= ZERO;
        }
        return true;
    }


    private void createStockSparePart(SparePartOrder order) {
        List<StockSparePart> stockSpareParts = order.getOrderItems().stream()
                .map(i -> StockSparePart.builder()
                        .primaryKey(new StockSparePartPK(i.getSparePart(), i.getSparePartOrder()))
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());
        stockSparePartRepository.saveAll(stockSpareParts);
    }
}
