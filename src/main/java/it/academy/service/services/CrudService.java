package it.academy.service.services;

import it.academy.service.dto.forms.TablePage;

public interface CrudService<T, ID> {

    T createOrUpdate(T dto);

    void delete(ID id);

    T findById(ID id);

    TablePage<T> findForPage(int pageNumber, String sortField, String sortDir, String keyword);

}
