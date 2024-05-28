package it.academy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairForTableDTO {

    private Long id;

    private String serviceCenterName;

    private String category;

    private Date startDate;

    private String repairNumber;

    private String deviceDescription;

    private String serialNumber;

    private String defectDescription;

    private String status;

}
