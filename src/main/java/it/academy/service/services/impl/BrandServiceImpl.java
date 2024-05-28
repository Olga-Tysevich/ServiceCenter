package it.academy.service.services.impl;

import it.academy.service.dto.BrandDTO;
import it.academy.service.entity.Brand;
import it.academy.service.exceptions.ObjectAlreadyExist;
import it.academy.service.mappers.BrandMapper;
import it.academy.service.repositories.BrandRepository;
import it.academy.service.repositories.impl.BrandSpecification;
import it.academy.service.services.BrandService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.BRAND_TABLE_PAGE;

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
    protected Specification<Brand> getSpecification(String keyword) {
        return BrandSpecification.search(keyword);
    }
    @Override
    public BrandDTO createOrUpdate(BrandDTO dto) {
        Long id = Optional.ofNullable(dto)
                .map(BrandDTO::getId)
                .orElse(ID_FOR_CHECK);
        if (dto != null && repository.existsByNameAndIdIsNot(dto.getName(), id)) {
            throw new ObjectAlreadyExist();
        }
        return super.createOrUpdate(dto);
    }
}
