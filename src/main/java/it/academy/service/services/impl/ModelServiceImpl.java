package it.academy.service.services.impl;

import it.academy.service.dto.ModelDTO;
import it.academy.service.dto.forms.ModelForm;
import it.academy.service.entity.Brand;
import it.academy.service.entity.DeviceType;
import it.academy.service.entity.Model;
import it.academy.service.mappers.BrandMapper;
import it.academy.service.mappers.DeviceTypeMapper;
import it.academy.service.mappers.ModelMapper;
import it.academy.service.repositories.BrandRepository;
import it.academy.service.repositories.DeviceTypeRepository;
import it.academy.service.repositories.ModelRepository;
import it.academy.service.repositories.impl.ModelSpecification;
import it.academy.service.services.ModelService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.MODEL_ALREADY_EXISTS;
import static it.academy.service.utils.UIConstants.MODEL_TABLE_PAGE;

@Transactional
@Service
public class ModelServiceImpl extends CrudServiceImpl<Model, ModelDTO, Long> implements ModelService {
    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    public ModelServiceImpl(ModelRepository repository,
                            BrandRepository brandRepository,
                            DeviceTypeRepository deviceTypeRepository) {
        super(repository, ModelMapper.INSTANCE);
        this.modelRepository = repository;
        this.brandRepository = brandRepository;
        this.deviceTypeRepository = deviceTypeRepository;
    }

    @Override
    public ModelDTO createOrUpdate(@NotNull ModelDTO dto) {
        Long id = Objects.requireNonNullElse(dto.getId(), ID_FOR_CHECK);
        if (modelRepository.existsByBrand_IdAndType_IdAndNameAndIdIsNot(dto.getBrandId(),
                dto.getDeviceTypeId(),
                dto.getName(),
                id)) {
            dto.setErrorMessage(MODEL_ALREADY_EXISTS);
        }
        return super.createOrUpdate(dto);
    }

    @Override
    public List<ModelDTO> findModelsByBrandId(@NotNull Long id) {
        return Optional.ofNullable(id)
                .map(modelRepository::findAllByBrand_IdIsAndIsActiveTrue)
                .map(ModelMapper.INSTANCE::toDTOList)
                .orElse(null);
    }

    @Override
    public ModelForm getModelForm(Long id) {
        ModelDTO model = Optional.ofNullable(id)
                .map(modelRepository::getById)
                .map(ModelMapper.INSTANCE::toDTO)
                .orElse(new ModelDTO());
        List<Brand> brands = brandRepository.findAllByIsActiveIsTrue();
        List<DeviceType> deviceTypes = deviceTypeRepository.findAllByIsActiveTrue();
        return new ModelForm(BrandMapper.INSTANCE.toDTOList(brands),
                DeviceTypeMapper.INSTANCE.toDTOList(deviceTypes), model);
    }


    @Override
    protected String getTablePagePath() {
        return MODEL_TABLE_PAGE;
    }

    @Override
    protected Specification<Model> getSpecification(String keyword) {
        return ModelSpecification.search(keyword);
    }
}
