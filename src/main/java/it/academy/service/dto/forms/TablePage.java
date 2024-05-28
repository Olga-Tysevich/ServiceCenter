package it.academy.service.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TablePage<T> {

    private List<T> listForTable;

    private int pageNum;

    private int totalPages;

    private String sortDir;

    private String sortField;

    private String keyword;

    private String paginationUrl;
}
