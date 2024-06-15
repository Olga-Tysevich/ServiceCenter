package it.academy.service.services.impl;

import it.academy.service.dto.TablePageReq;
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
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public T findById(ID id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public TablePage<T> findForPage(TablePageReq tablePageReq) {
        Sort sort = SortHelper.defineCurrentSort(tablePageReq.getSortField(), tablePageReq.getSortDir());
        Specification<R> spec = getSpecification(tablePageReq.getServiceCenterId(), tablePageReq.getKeyword());

        Pageable pageRequest = PageRequest.of(tablePageReq.getPageNum() - 1, PAGE_SIZE, sort);
        Page<R> temp = repository.findAll(spec, pageRequest);

        List<T> dtoList = mapper.toDTOList(temp.getContent());
        return TablePage.<T>builder()
                .listForTable(dtoList)
                .totalPages(temp.getTotalPages())
                .pageNum(tablePageReq.getPageNum())
                .sortDir(tablePageReq.getSortDir())
                .sortField(tablePageReq.getSortField())
                .keyword(tablePageReq.getKeyword())
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

    protected abstract Specification<R> getSpecification(Long serviceCenterId, String keyword);

}
