package it.academy.service.services.impl;

import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.entity.ServiceCenter;
import it.academy.service.mappers.ServiceCenterMapper;
import it.academy.service.repositories.AccountRepository;
import it.academy.service.repositories.ServiceCenterRepository;
import it.academy.service.repositories.impl.ServiceCenterSpecification;
import it.academy.service.services.ServiceCenterService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.academy.service.utils.UIConstants.SERVICE_CENTER_TABLE_PAGE;

@Transactional
@Service
public class ServiceCenterServiceImpl extends CrudServiceImpl<ServiceCenter, ServiceCenterDTO, Long>
        implements ServiceCenterService {
    private final ServiceCenterRepository repository;
    private final AccountRepository accountRepository;

    public ServiceCenterServiceImpl(ServiceCenterRepository repository, AccountRepository accountRepository) {
        super(repository, ServiceCenterMapper.INSTANCE);
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceCenterDTO createOrUpdate(ServiceCenterDTO dto) {
        Optional.ofNullable(dto)
                .filter(s -> !s.getIsActive())
                .map(ServiceCenterDTO::getId)
                .map(accountRepository::findAllByServiceCenterId)
                .orElse(new ArrayList<>())
                .forEach(a -> {
                    a.setIsActive(false);
                    accountRepository.save(a);
                });
        return super.createOrUpdate(dto);
    }

    @Override
    protected String getTablePagePath() {
        return SERVICE_CENTER_TABLE_PAGE;
    }

    @Override
    protected Specification<ServiceCenter> getSpecification(Long serviceCenterId, String keyword) {
        return ServiceCenterSpecification.search(keyword);
    }

    @Override
    public List<ServiceCenterDTO> findActiveServiceCenters() {
        return repository.findAllByIsActiveTrue().stream()
                .map(ServiceCenterMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}
