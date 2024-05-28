package it.academy.service.services.impl;

import it.academy.service.dto.StockSparePartDTO;
import it.academy.service.entity.StockSparePart;
import it.academy.service.mappers.StockSparePartMapper;
import it.academy.service.repositories.StockSparePartRepository;
import it.academy.service.repositories.impl.StockSparePartSpecification;
import it.academy.service.services.StockSparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class StockSparePartServiceImpl implements StockSparePartService {
    private final StockSparePartRepository sparePartRepository;

    @Override
    public List<StockSparePartDTO> findByModelId(Long id) {
        return Optional.ofNullable(id)
                .map(i -> {
                    Specification<StockSparePart> specification = StockSparePartSpecification
                            .searchByModelIdAndRepairIsFinished(id);
                    return sparePartRepository.findAll(specification);
                })
                .map(StockSparePartMapper.INSTANCE::toDTOList)
                .orElse(null);
    }

}
