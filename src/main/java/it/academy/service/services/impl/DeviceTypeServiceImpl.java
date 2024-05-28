package it.academy.service.services.impl;

import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.entity.DeviceType;
import it.academy.service.exceptions.ObjectAlreadyExist;
import it.academy.service.mappers.DeviceTypeMapper;
import it.academy.service.repositories.DeviceTypeRepository;
import it.academy.service.repositories.impl.DeviceTypeSpecification;
import it.academy.service.services.DeviceTypeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.DEVICE_TYPE_TABLE_PAGE;

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
    protected Specification<DeviceType> getSpecification(String keyword) {
        return DeviceTypeSpecification.search(keyword);
    }

    @Override
    public DeviceTypeDTO createOrUpdate(DeviceTypeDTO dto) {
        Long id = Optional.ofNullable(dto)
                .map(DeviceTypeDTO::getId)
                .orElse(ID_FOR_CHECK);
        if (dto != null && repository.existsByNameAndIdIsNot(dto.getName(), id)) {
            throw new ObjectAlreadyExist();
        }
        return super.createOrUpdate(dto);
    }
}
