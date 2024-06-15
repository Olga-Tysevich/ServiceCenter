package it.academy.service.services;

import it.academy.service.dto.CompleteRepairDTO;
import it.academy.service.dto.RepairForFormsDTO;
import it.academy.service.dto.RepairForTableDTO;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.RepairStatus;

public interface RepairService {

    RepairForm createOrUpdate(RepairForFormsDTO repairForFormsDTO);

    boolean completeRepair(CompleteRepairDTO completeRepairDTO);

    RepairForFormsDTO findById(Long id);

    RepairForm getRepairForm(Long id);

    TablePage<RepairForTableDTO> findForPage(Long serviceCenterId, int pageNumber, String sortField, String sortDir, String keyword);

    RepairTypeForm findByRepairId(Long id);

    RepairForFormsDTO changeStatus(Long id, RepairStatus repairStatus);

}
