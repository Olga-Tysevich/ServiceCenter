package it.academy.service.services.impl;

import it.academy.service.dto.RepairSparePartDTO;
import it.academy.service.entity.RepairSparePart;
import it.academy.service.mappers.RepairSparePartMapper;
import it.academy.service.repositories.RepairSparePartRepository;
import it.academy.service.repositories.impl.RepairSparePartSpecification;
import it.academy.service.services.RepairSparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class RepairSparePartServiceImpl implements RepairSparePartService {
    private final RepairSparePartRepository repository;

    @Override
    public List<RepairSparePartDTO> findAllByRepairId(Long id) {
        return Optional.ofNullable(id)
                .map(i -> {
                    Specification<RepairSparePart> specification = RepairSparePartSpecification.searchByRepairId(id);
                    return repository.findAll(specification);
                })
                .map(RepairSparePartMapper.INSTANCE::toDTOList)
                .orElse(null);
    }
}
