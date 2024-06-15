package it.academy.service.services.impl;

import it.academy.service.dto.BrandDTO;
import it.academy.service.entity.Brand;
import it.academy.service.mappers.BrandMapper;
import it.academy.service.repositories.BrandRepository;
import it.academy.service.repositories.impl.BrandSpecification;
import it.academy.service.services.BrandService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.*;

@Transactional
@Service
public class BrandServiceImpl extends CrudServiceImpl<Brand, BrandDTO, Long> implements BrandService {
    private final BrandRepository repository;

    public BrandServiceImpl(BrandRepository repository) {
        super(repository, BrandMapper.INSTANCE);
        this.repository = repository;
    }

    @Override
    protected String getTablePagePath() {
        return BRAND_TABLE_PAGE;
    }

    @Override
    protected Specification<Brand> getSpecification(Long serviceCenterId, String keyword) {
        return BrandSpecification.search(keyword);
    }

    @Override
    public BrandDTO createOrUpdate(@NotNull BrandDTO dto) {
        Long id = Objects.requireNonNullElse(dto.getId(), ID_FOR_CHECK);
        if (repository.existsByNameAndIdIsNot(dto.getName(), id)) {
            dto.setErrorMessage(BRAND_ALREADY_EXISTS);
            return dto;
        }
        return super.createOrUpdate(dto);
    }
}
