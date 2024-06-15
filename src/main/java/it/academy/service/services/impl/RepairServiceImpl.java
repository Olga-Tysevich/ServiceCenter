package it.academy.service.services.impl;

import it.academy.service.dto.*;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.StockSparePartPK;
import it.academy.service.exceptions.*;
import it.academy.service.mappers.*;
import it.academy.service.repositories.*;
import it.academy.service.repositories.impl.RepairSpecification;
import it.academy.service.repositories.impl.StockSparePartSpecification;
import it.academy.service.services.RepairService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.academy.service.utils.Constants.DEFAULT_ID;
import static it.academy.service.utils.Constants.ZERO;
import static it.academy.service.utils.UIConstants.*;

@Transactional
@Service
public class RepairServiceImpl extends CrudServiceImpl<Repair, RepairDTO, Long> implements RepairService {
    private final RepairRepository repairRepository;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final RepairTypeRepository repairTypeRepository;
    private final StockSparePartRepository stockSparePartRepository;
    private final RepairSparePartRepository sparePartRepository;

    public RepairServiceImpl(RepairRepository repository, RepairRepository repairRepository,
                             BrandRepository brandRepository, ModelRepository modelRepository,
                             RepairTypeRepository repairTypeRepository, StockSparePartRepository stockSparePartRepository,
                             RepairSparePartRepository sparePartRepository) {
        super(repository, RepairMapper.INSTANCE);
        this.repairRepository = repairRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.repairTypeRepository = repairTypeRepository;
        this.stockSparePartRepository = stockSparePartRepository;
        this.sparePartRepository = sparePartRepository;
    }


    @Override
    public RepairDTO createOrUpdate(@NotNull RepairDTO repairDTO) {
        RepairForFormsDTO repairForFormsDTO = (RepairForFormsDTO) repairDTO;
        if (!checkDateOfSale(repairForFormsDTO.getDateOfSale())) {
            repairForFormsDTO.setErrorMessage(INVALID_DATE_OF_SALE);
            return getDataForRepairForm(repairForFormsDTO);
        }

        return Optional.of(repairForFormsDTO)
                .map(RepairMapper.INSTANCE::toEntity)
                .map(repair -> setModelToDevice(repair, repairForFormsDTO.getModelId()))
                .map(repairRepository::save)
                .map(repair -> getRepairForm(repair.getId()))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public RepairForFormsDTO findById(Long id) {
        return (RepairForFormsDTO) Optional.ofNullable(super.findById(id))
                .orElse(null);
    }

    @Override
    protected String getTablePagePath() {
        return REPAIR_TABLE_PAGE;
    }

    @Override
    protected Specification<Repair> getSpecification(Long serviceCenterId, String keyword) {
        return RepairSpecification.search(serviceCenterId, keyword);
    }

    @Transactional(readOnly = true)
    @Override
    public RepairForm getRepairForm(Long id) {
        RepairForFormsDTO repair = Optional.ofNullable(id)
                .map(repairRepository::getById)
                .map(RepairMapper.INSTANCE::toDTO)
                .orElse(new RepairForFormsDTO());
        return getDataForRepairForm(repair);
    }

    @Override
    public TablePage<RepairDTO> findForPage(TablePageReq tablePageReq) {
        return super.findForPage(tablePageReq);
    }

    @Transactional(readOnly = true)
    @Override
    public RepairTypeForm findByRepairId(Long id) {
        return Optional.ofNullable(id)
                .map(i -> {
                    Specification<StockSparePart> specification = StockSparePartSpecification.searchByRepairId(id);
                    List<StockSparePart> spareParts = stockSparePartRepository.findAll(specification);
                    List<RepairType> repairTypes = repairTypeRepository.findAllByIsActiveTrue()
                            .stream()
                            .sorted(Comparator.comparing(RepairType::getName))
                            .collect(Collectors.toList());
                    return new RepairTypeForm(
                            RepairTypeMapper.INSTANCE.toDTOList(repairTypes),
                            StockSparePartMapper.INSTANCE.toDTOList(spareParts));
                })
                .orElse(null);
    }

    @Override
    public RepairForFormsDTO changeStatus(Long id, RepairStatus repairStatus) {
        return Optional.ofNullable(id)
                .map(repairRepository::getById)
                .map(r -> changeRepairStatus(r, repairStatus))
                .map(RepairMapper.INSTANCE::toDTO)
                .orElse(null);
    }

    @Override
    public boolean completeRepair(CompleteRepairDTO completeRepairDTO) {
        if (completeRepairDTO == null || !checkRepairStatus(completeRepairDTO.getRepairId())) {
            return false;
        }

        List<RepairSparePartDTO> spareParts = completeRepairDTO.getSpareParts();

        if (!spareParts.isEmpty()) {
            createRepairSpareParts(completeRepairDTO.getSpareParts());
        }

        Long repairId = completeRepairDTO.getRepairId();
        Long repairTypeId = completeRepairDTO.getRepairTypeId();
        setRepairType(repairId, repairTypeId);
        return true;
    }

    private RepairForm getDataForRepairForm(RepairForFormsDTO repairForFormsDTO) {
        Long brandId = Objects.requireNonNullElse(repairForFormsDTO.getBrandId(), DEFAULT_ID);
        List<Brand> brands = brandRepository.findAllByIsActiveIsTrue();
        List<Model> models = modelRepository.findAllByBrand_IdIsAndIsActiveTrue(brandId);
        return new RepairForm(BrandMapper.INSTANCE.toDTOList(brands),
                ModelMapper.INSTANCE.toDTOList(models), repairForFormsDTO);
    }

    private boolean checkDateOfSale(Date date) {
        return date.compareTo(new java.util.Date()) < ZERO;
    }

    private Repair changeRepairStatus(Repair repair, RepairStatus repairStatus) {
        if (repair == null) {
            throw new ObjectNotFound();
        }

        RepairStatus currentStatus = repair.getStatus();
        if (RepairStatus.REQUEST.equals(currentStatus)) {
            repair.setStatus(RepairStatus.CURRENT);
        } else if (RepairStatus.REJECTED.equals(currentStatus)) {
            repair.setStatus(RepairStatus.CURRENT);
        } else {
            repair.setStatus(repairStatus);
        }

        return repairRepository.save(repair);
    }

    private Repair setModelToDevice(Repair repair, Long modelId) {
        Model model = modelRepository.findById(modelId).orElseThrow(IllegalArgumentException::new);
        repair.getDevice().setModel(model);
        return repair;
    }

    private void setRepairType(Long repairId, Long repairTypeId) {
        Repair repair = repairRepository.findById(repairId).orElse(null);
        if (repair == null) {
            throw new RepairNotFound();
        }
        RepairType repairType = repairTypeRepository.findById(repairTypeId).orElse(null);
        if (repairType == null) {
            throw new RepairTypeNotFound();
        }
        repair.setRepairType(repairType);
        repair.setStatus(RepairStatus.COMPLETED);
        repair.setEndDate(Date.valueOf(LocalDate.now()));
        repairRepository.save(repair);

    }

    private boolean checkRepairStatus(Long repairId) {
        Repair repair = repairRepository.findById(repairId).orElse(null);
        if (repair != null) {
            RepairStatus currentStatus = repair.getStatus();
            return RepairStatus.CURRENT.equals(currentStatus)
                    || RepairStatus.WAITING_FOR_SPARE_PARTS.equals(currentStatus);
        }
        return false;
    }

    private void createRepairSpareParts(List<RepairSparePartDTO> sparePartDTOList) {
        Optional.ofNullable(sparePartDTOList)
                .map(RepairSparePartMapper.INSTANCE::toEntityList)
                .ifPresent(this::addSparePartToRepair);
    }

    private void addSparePartToRepair(List<RepairSparePart> spareParts) {
        List<RepairSparePart> filteredSpareParts = spareParts.stream()
                .filter(sp -> !sparePartRepository.existsById(sp.getPrimaryKey()))
                .collect(Collectors.toList());
        if (filteredSpareParts.isEmpty()) {
            throw new SparePartListIsEmpty();
        }
        filteredSpareParts.stream()
                .map(RepairSparePartMapper.INSTANCE::toStockSparePart)
                .forEach(this::removePartFromStock);
        sparePartRepository.saveAll(spareParts);
    }

    private void removePartFromStock(StockSparePart forRemove) {
        StockSparePartPK primaryKey = forRemove.getPrimaryKey();
        int quantityForDelete = forRemove.getQuantity();
        StockSparePart stockSparePart = stockSparePartRepository.getById(primaryKey);
        if (quantityForDelete <= 0 || stockSparePart.getQuantity() < quantityForDelete) {
            throw new InvalidSparePartQuantity();
        }

        if (stockSparePart.getQuantity() == quantityForDelete) {
            stockSparePartRepository.delete(stockSparePart);
        } else {
            int currentQuantity = stockSparePart.getQuantity();
            int newQuantity = currentQuantity - quantityForDelete;
            stockSparePart.setQuantity(newQuantity);
            stockSparePartRepository.save(stockSparePart);
        }
    }

}
