package it.academy.service.contollers;

import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.dto.RepairDTO;
import it.academy.service.dto.RepairForTableDTO;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.*;
import it.academy.service.services.RepairService;
import it.academy.service.services.auth.AccountDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static it.academy.service.utils.Constants.*;
import static it.academy.service.utils.Constants.REPAIR_STATUS;
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("/repairs")
@RequiredArgsConstructor
public class RepairController {
    private final RepairService repairService;

    @GetMapping
    public String showRepairsPage(Authentication authentication, Model model) {
        return showPage(authentication, model, FIRST_PAGE, Repair_.START_DATE, Sort.Direction.DESC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Authentication authentication, Model model,
                           @PathVariable(PAGE_NUM) int pageNum,
                           @RequestParam(SORT_FIELD) String sortField,
                           @RequestParam(SORT_DIR) String sortDir,
                           @RequestParam(KEYWORD) String keyword) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        TablePage<RepairForTableDTO> page = repairService.findForPage(serviceCenterId, pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return REPAIR_TABLE;
    }

    @GetMapping("repair-create")
    public String showCreatePage(Authentication authentication, Model model) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        setRepairFormData(model, null);
        model.addAttribute(SERVICE_CENTER_ID, serviceCenterId);
        return ADD_REPAIR_PAGE;
    }

    @PostMapping("/repair-create")
    public String create(Model model, @Valid RepairDTO repairDTO, BindingResult bindingResult) {
        if (!DtoValidator.isValid(model, repairDTO, REPAIR, bindingResult)) {
            setRepairFormData(model, null);
            return ADD_REPAIR_PAGE;
        }
        repairService.createOrUpdate(repairDTO);
        return REPAIRS_PAGE_REDIRECT;
    }

    @GetMapping("/repair-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        setRepairFormData(model, id);
        return UPDATE_REPAIR_PAGE;
    }

    @PostMapping("/repair-update")
    public String update(Model model, @Valid RepairDTO repairDTO, BindingResult bindingResult) {
        if (!DtoValidator.isValid(model, repairDTO, REPAIR, bindingResult)) {
            setRepairFormData(model, repairDTO.getBrandId());
            return UPDATE_REPAIR_PAGE;
        }
        repairService.createOrUpdate(repairDTO);
        return REPAIRS_PAGE_REDIRECT;
    }

    @GetMapping("/repair-page/{id}")
    public String showRepairPage(Model model, @PathVariable(OBJECT_ID) Long id) {
        RepairDTO repair = repairService.findById(id);
        model.addAttribute(REPAIR, repair);
        return REPAIR_PAGE;
    }

    @GetMapping("complete-repair")
    public String completeRepair(Model model,
                                 @RequestParam(REPAIR_ID) Long id,
                                 @RequestParam(MODEL_ID) Long modelId,
                                 @RequestParam(REPAIR_NUMBER) String repairNumber) {
        RepairTypeForm form = repairService.findByRepairId(id);
        model.addAttribute(REPAIR_ID, id);
        model.addAttribute(MODEL_ID, modelId);
        model.addAttribute(REPAIR_NUMBER, repairNumber);
        model.addAttribute(REPAIR_TYPE_LIST, form.getRepairTypes());
        model.addAttribute(SPARE_PART_LIST, form.getSpareParts());
        return COMPLETE_REPAIR_PAGE;
    }

    @GetMapping("change-status/{id}/{status}")
    public String changeStatus(Model model,
                               @PathVariable(OBJECT_ID) Long id,
                               @PathVariable(REPAIR_STATUS) RepairStatus status) {
        RepairDTO repairDTO = repairService.changeStatus(id, status);
        model.addAttribute(REPAIR, repairDTO);
        return REPAIR_PAGE;
    }

    private void setRepairFormData(Model model, Long id) {
        RepairForm repairForm = repairService.getRepairForm(id);
        model.addAttribute(REPAIR_STATUS,repairForm.getRepair().getStatus());
        model.addAttribute(CATEGORY_LIST, RepairCategory.values());
        model.addAttribute(BRAND_LIST, repairForm.getBrandList());
        model.addAttribute(MODEL_LIST, repairForm.getModelList());
        model.addAttribute(REPAIR, repairForm.getRepair());
    }

}
