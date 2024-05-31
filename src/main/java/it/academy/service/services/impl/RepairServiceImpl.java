package it.academy.service.services.impl;

import it.academy.service.dto.CompleteRepairDTO;
import it.academy.service.dto.RepairDTO;
import it.academy.service.dto.RepairForTableDTO;
import it.academy.service.dto.RepairSparePartDTO;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.StockSparePartPK;
import it.academy.service.exceptions.ObjectNotFound;
import it.academy.service.mappers.*;
import it.academy.service.repositories.*;
import it.academy.service.repositories.impl.RepairSpecification;
import it.academy.service.repositories.impl.StockSparePartSpecification;
import it.academy.service.services.RepairService;
import it.academy.service.services.SortHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService {
    private final RepairRepository repairRepository;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final RepairTypeRepository repairTypeRepository;
    private final StockSparePartRepository stockSparePartRepository;
    private final RepairSparePartRepository sparePartRepository;

    @Override
    public RepairForm createOrUpdate(@NotNull RepairDTO repairDTO) {
        if (!checkDateOfSale(repairDTO.getDateOfSale())) {
            repairDTO.setErrorMessage(INVALID_DATE_OF_SALE);
            return getDataForRepairForm(repairDTO);
        }

        return Optional.of(repairDTO)
                .map(RepairMapper.INSTANCE::toEntity)
                .map(repair -> setModelToDevice(repair, repairDTO.getModelId()))
                .map(repairRepository::save)
                .map(repair -> getRepairForm(repair.getId()))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public RepairDTO findById(Long id) {
        return Optional.ofNullable(id)
                .map(repairRepository::getById)
                .map(RepairMapper.INSTANCE::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public RepairForm getRepairForm(Long id) {
        RepairDTO repair = Optional.ofNullable(id)
                .map(repairRepository::getById)
                .map(RepairMapper.INSTANCE::toDTO)
                .orElse(new RepairDTO());
        return getDataForRepairForm(repair);
    }

    @Transactional(readOnly = true)
    @Override
    public TablePage<RepairForTableDTO> findForPage(Long serviceCenterId,
                                                    int pageNumber,
                                                    String sortField,
                                                    String sortDir,
                                                    String keyword) {
        Sort sort = SortHelper.defineCurrentSort(sortField, sortDir);
        Specification<Repair> spec = serviceCenterId != null ?
                RepairSpecification.searchByServiceCenter(serviceCenterId, keyword)
                : RepairSpecification.search(keyword);

        Pageable pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE, sort);
        Page<Repair> temp = repairRepository.findAll(spec, pageRequest);

        List<RepairForTableDTO> repairs = RepairMapper.INSTANCE.toDTOListForTable(temp.getContent());
        return TablePage.<RepairForTableDTO>builder()
                .listForTable(repairs)
                .totalPages(temp.getTotalPages())
                .pageNum(pageNumber)
                .sortDir(sortDir)
                .sortField(sortField)
                .keyword(keyword)
                .paginationUrl(REPAIR_TABLE_PAGE)
                .build();
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
    public RepairDTO changeStatus(Long id, RepairStatus repairStatus) {
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

    private RepairForm getDataForRepairForm(RepairDTO repairDTO) {
        Long brandId = Objects.requireNonNullElse(repairDTO.getBrandId(), DEFAULT_ID);
        List<Brand> brands = brandRepository.findAllByIsActiveIsTrue();
        List<Model> models = modelRepository.findAllByBrand_IdIsAndIsActiveTrue(brandId);
        return new RepairForm(BrandMapper.INSTANCE.toDTOList(brands),
                ModelMapper.INSTANCE.toDTOList(models), repairDTO);
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
        RepairType repairType = repairTypeRepository.findById(repairTypeId).orElse(null);
        if (repair == null || repairType == null) {
            throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
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
        if (stockSparePart.getQuantity() < quantityForDelete) {
            throw new IllegalArgumentException();
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
