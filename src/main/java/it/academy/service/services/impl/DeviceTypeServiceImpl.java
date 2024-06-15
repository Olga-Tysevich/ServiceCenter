package it.academy.service.services.impl;

import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.entity.DeviceType;
import it.academy.service.mappers.DeviceTypeMapper;
import it.academy.service.repositories.DeviceTypeRepository;
import it.academy.service.repositories.impl.DeviceTypeSpecification;
import it.academy.service.services.DeviceTypeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.*;

@Transactional
@Service
public class DeviceTypeServiceImpl extends CrudServiceImpl<DeviceType, DeviceTypeDTO, Long> implements DeviceTypeService {
    private final DeviceTypeRepository repository;

    public DeviceTypeServiceImpl(DeviceTypeRepository repository) {
        super(repository, DeviceTypeMapper.INSTANCE);
        this.repository = repository;
    }

    @Override
    protected String getTablePagePath() {
        return DEVICE_TYPE_TABLE_PAGE;
    }

    @Override
    protected Specification<DeviceType> getSpecification(Long serviceCenterId, String keyword) {
        return DeviceTypeSpecification.search(keyword);
    }

    @Override
    public DeviceTypeDTO createOrUpdate(@NotNull DeviceTypeDTO dto) {
        Long id = Objects.requireNonNullElse(dto.getId(), ID_FOR_CHECK);
        if (repository.existsByNameAndIdIsNot(dto.getName(), id)) {
            dto.setErrorMessage(DEVICE_TYPE_ALREADY_EXISTS);
            return dto;
        }
        return super.createOrUpdate(dto);
    }
}
