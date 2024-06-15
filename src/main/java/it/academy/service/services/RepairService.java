package it.academy.service.services;

import it.academy.service.dto.*;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.entity.RepairStatus;

public interface RepairService extends CrudService<RepairDTO, Long> {

    boolean completeRepair(CompleteRepairDTO completeRepairDTO);

    RepairForFormsDTO findById(Long id);

    RepairForm getRepairForm(Long id);

    RepairTypeForm findByRepairId(Long id);

    RepairForFormsDTO changeStatus(Long id, RepairStatus repairStatus);

}
