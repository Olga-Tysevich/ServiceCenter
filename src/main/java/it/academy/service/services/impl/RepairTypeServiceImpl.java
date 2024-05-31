package it.academy.service.services.impl;

import it.academy.service.dto.RepairTypeDTO;
import it.academy.service.entity.RepairType;
import it.academy.service.mappers.RepairTypeMapper;
import it.academy.service.repositories.RepairTypeRepository;
import it.academy.service.repositories.impl.RepairTypeSpecification;
import it.academy.service.services.RepairTypeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.REPAIR_TYPE_ALREADY_EXISTS;
import static it.academy.service.utils.UIConstants.REPAIR_TYPE_TABLE_PAGE;

@Transactional
@Service
public class RepairTypeServiceImpl extends CrudServiceImpl<RepairType, RepairTypeDTO, Long> implements RepairTypeService {
    private final RepairTypeRepository repository;

    public RepairTypeServiceImpl(RepairTypeRepository repository) {
        super(repository, RepairTypeMapper.INSTANCE);
        this.repository = repository;
    }

    @Override
    public RepairTypeDTO createOrUpdate(@NotNull RepairTypeDTO dto) {
        Long id = Objects.requireNonNullElse(dto.getId(), ID_FOR_CHECK);
        if (repository.existsByNameAndIdIsNot(dto.getName(), id)) {
           dto.setErrorMessage(REPAIR_TYPE_ALREADY_EXISTS);
           return dto;
        }
        return super.createOrUpdate(dto);
    }

    @Override
    protected String getTablePagePath() {
        return REPAIR_TYPE_TABLE_PAGE;
    }

    @Override
    protected Specification<RepairType> getSpecification(String keyword) {
        return RepairTypeSpecification.search(keyword);
    }

}
