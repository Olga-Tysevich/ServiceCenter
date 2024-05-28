package it.academy.service.services.impl;

import it.academy.service.dto.SparePartDTO;
import it.academy.service.dto.forms.SparePartForm;
import it.academy.service.entity.Model;
import it.academy.service.entity.SparePart;
import it.academy.service.mappers.ModelMapper;
import it.academy.service.mappers.SparePartMapper;
import it.academy.service.repositories.ModelRepository;
import it.academy.service.repositories.SparePartRepository;
import it.academy.service.repositories.impl.SparePartSpecification;
import it.academy.service.services.SparePartService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static it.academy.service.utils.UIConstants.SPARE_PART_TABLE_PAGE;

@Transactional
@Service
public class SparePartServiceImpl extends CrudServiceImpl<SparePart, SparePartDTO, Long> implements SparePartService {
    private final SparePartRepository sparePartRepository;
    private final ModelRepository modelRepository;

    public SparePartServiceImpl(SparePartRepository repository, SparePartRepository sparePartRepository, ModelRepository modelRepository) {
        super(repository, SparePartMapper.INSTANCE);
        this.sparePartRepository = sparePartRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public SparePartDTO createOrUpdate(SparePartDTO dto) {
        return Optional.ofNullable(dto)
                .map(SparePartMapper.INSTANCE::toEntity)
                .map(sparePartRepository::save)
                .map(sp -> addSparePartToModels(sp, modelRepository.findAllById(dto.getModelIdList())))
                .map(SparePartMapper.INSTANCE::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SparePartDTO> findByModelId(Long id) {
        return Objects.requireNonNull(Optional.ofNullable(id)
                .flatMap(i -> modelRepository.findById(id))
                .map(sparePartRepository::findAllByIsActiveTrueAndModelsContains)
                .map(SparePartMapper.INSTANCE::toDTOList)
                .orElse(null))
                .stream()
                .sorted(Comparator.comparing(SparePartDTO::getName))
                .collect(Collectors.toList());
    }

    @Override
    public SparePartForm getSparePartForm(Long id) {
        SparePartDTO sparePart = Optional.ofNullable(id)
                .map(sparePartRepository::getById)
                .map(SparePartMapper.INSTANCE::toDTO)
                .orElse(new SparePartDTO());
        List<Model> models = modelRepository.findAllByIsActiveTrue()
                .stream()
                .sorted(Comparator.comparing(m -> m.getBrand().getName()))
                .collect(Collectors.toList());
        return new SparePartForm(sparePart,
                ModelMapper.INSTANCE.toDTOList(models));
    }


    @Override
    protected String getTablePagePath() {
        return SPARE_PART_TABLE_PAGE;
    }

    @Override
    protected Specification<SparePart> getSpecification(String keyword) {
        return SparePartSpecification.search(keyword);
    }

    @Override
    public boolean delete(Long id) {
        SparePart sparePart = sparePartRepository.getById(id);
        Iterator<Model> iterator = sparePart.getModels().iterator();
        while (iterator.hasNext()) {
            Model m = iterator.next();
            m.getSpareParts().remove(sparePart);
            iterator.remove();
            modelRepository.save(m);
        }
        sparePart.getModels().clear();
        sparePartRepository.delete(sparePart);
        return sparePartRepository.existsById(id);
    }

    private SparePart addSparePartToModels(SparePart sparePart, List<Model> models) {
        models.forEach(m -> {
            m.getSpareParts().add(sparePart);
            sparePart.getModels().add(m);
            modelRepository.save(m);
        });
        return sparePart;
    }
}
