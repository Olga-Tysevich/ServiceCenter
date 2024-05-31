package it.academy.service.services;

import it.academy.service.dto.CompleteRepairDTO;
import it.academy.service.dto.RepairDTO;
import it.academy.service.dto.RepairForTableDTO;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.RepairStatus;

public interface RepairService {

    RepairForm createOrUpdate(RepairDTO repairDTO);

    boolean completeRepair(CompleteRepairDTO completeRepairDTO);

    RepairDTO findById(Long id);

    RepairForm getRepairForm(Long id);

    TablePage<RepairForTableDTO> findForPage(Long serviceCenterId, int pageNumber, String sortField, String sortDir, String keyword);

    RepairTypeForm findByRepairId(Long id);

    RepairDTO changeStatus(Long id, RepairStatus repairStatus);

}
