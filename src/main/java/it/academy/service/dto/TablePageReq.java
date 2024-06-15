package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TablePageReq {

    private Long serviceCenterId;

    private int pageNum;

    private String sortField;

    private String sortDir;

    private String keyword;
}
