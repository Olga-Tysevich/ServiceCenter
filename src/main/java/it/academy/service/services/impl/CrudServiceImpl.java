package it.academy.service.services.impl;

import it.academy.service.dto.forms.TablePage;
import it.academy.service.mappers.CustomMapper;
import it.academy.service.repositories.CustomCrudRepository;
import it.academy.service.services.CrudService;
import it.academy.service.services.SortHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static it.academy.service.utils.UIConstants.PAGE_SIZE;

@Transactional
@Service
@RequiredArgsConstructor
public abstract class CrudServiceImpl<R, T, ID> implements CrudService<T, ID> {
    private final CustomCrudRepository<R, ID> repository;
    private final CustomMapper<R, T> mapper;

    @Override
    public T createOrUpdate(T dto) {
        return Optional.ofNullable(dto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Override
    public boolean delete(ID id) {
        if (id != null) {
            repository.deleteById(id);
            return repository.existsById(id);
        }
        return false;
    }

    @Override
    public T findById(ID id) {
        return Optional.ofNullable(id)
                .map(repository::getById)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Override
    public TablePage<T> findForPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = SortHelper.defineCurrentSort(sortField, sortDir);
        Specification<R> spec = getSpecification(keyword);

        Pageable pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE, sort);
        Page<R> temp = repository.findAll(spec, pageRequest);

        List<T> dtoList = mapper.toDTOList(temp.getContent());
        return TablePage.<T>builder()
                .listForTable(dtoList)
                .totalPages(temp.getTotalPages())
                .pageNum(pageNumber)
                .sortDir(sortDir)
                .sortField(sortField)
                .keyword(keyword)
                .paginationUrl(getTablePagePath())
                .build();
    }

    protected CustomCrudRepository<R, ID> repository() {
        return repository;
    }

    protected CustomMapper<R, T> mapper() {
        return mapper;
    }

    protected abstract String getTablePagePath();
    protected abstract Specification<R> getSpecification(String keyword);

}
