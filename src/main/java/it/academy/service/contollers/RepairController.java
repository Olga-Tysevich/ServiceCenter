package it.academy.service.contollers;

import it.academy.service.dto.RepairForFormsDTO;
import it.academy.service.dto.RepairForTableDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.RepairForm;
import it.academy.service.dto.forms.RepairTypeForm;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.RepairCategory;
import it.academy.service.entity.RepairStatus;
import it.academy.service.entity.Repair_;
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
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("/repairs")
@RequiredArgsConstructor
public class RepairController {
    private final RepairService repairService;

    @GetMapping
    public String showRepairsPage(Authentication authentication, Model model) {
        return showPage(authentication, model, new TablePageReq(FIRST_PAGE, Repair_.START_DATE, Sort.Direction.DESC.name(), StringUtils.EMPTY));
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Authentication authentication, Model model, @ModelAttribute TablePageReq tablePageReq) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        TablePage<RepairForTableDTO> page = repairService.findForPage(serviceCenterId, tablePageReq.getPageNum(), tablePageReq.getSortField(),
                tablePageReq.getSortDir(), tablePageReq.getKeyword());
        model.addAttribute(TABLE_PAGE, page);
        return REPAIR_TABLE;
    }

    @GetMapping("repair-create")
    public String showCreatePage(Authentication authentication, Model model) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        RepairForm repairForm = repairService.getRepairForm(null);
        setRepairFormData(model, repairForm);
        model.addAttribute(SERVICE_CENTER_ID, serviceCenterId);
        return ADD_REPAIR_PAGE;
    }

    @PostMapping("/repair-create")
    public String create(Model model, @Valid RepairForFormsDTO repairForFormsDTO, BindingResult bindingResult) {
        return createOrUpdate(model, repairForFormsDTO, bindingResult, ADD_REPAIR_PAGE);
    }

    @GetMapping("/repair-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        RepairForm repairForm = repairService.getRepairForm(id);
        setRepairFormData(model, repairForm);
        return UPDATE_REPAIR_PAGE;
    }

    @PostMapping("/repair-update")
    public String update(Model model, @Valid RepairForFormsDTO repairForFormsDTO, BindingResult bindingResult) {
        return createOrUpdate(model, repairForFormsDTO, bindingResult, UPDATE_REPAIR_PAGE);
    }

    @GetMapping("/repair-page/{id}")
    public String showRepairPage(Model model, @PathVariable(OBJECT_ID) Long id) {
        RepairForFormsDTO repair = repairService.findById(id);
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
        RepairForFormsDTO repairForFormsDTO = repairService.changeStatus(id, status);
        model.addAttribute(REPAIR, repairForFormsDTO);
        return REPAIR_PAGE;
    }

    private String createOrUpdate(Model model, RepairForFormsDTO repairForFormsDTO, BindingResult bindingResult, String formPage) {
        if (!DtoValidator.isValid(model, repairForFormsDTO, REPAIR, bindingResult)) {
            RepairForm repairForm = repairService.getRepairForm(repairForFormsDTO.getId());
            repairForm.setRepair(repairForFormsDTO);
            setRepairFormData(model, repairForm);
            return formPage;
        }
        RepairForm result = repairService.createOrUpdate(repairForFormsDTO);
        if (StringUtils.isNotBlank(result.getRepair().getErrorMessage())) {
            model.addAttribute(ERROR_MESSAGE, result.getRepair().getErrorMessage());
            result.setRepair(repairForFormsDTO);
            setRepairFormData(model, result);
            return formPage;
        }
        return REPAIRS_PAGE_REDIRECT;
    }

    private void setRepairFormData(Model model, RepairForm repairForm) {
        model.addAttribute(REPAIR_STATUS, repairForm.getRepair().getStatus());
        model.addAttribute(CATEGORY_LIST, RepairCategory.values());
        model.addAttribute(BRAND_LIST, repairForm.getBrandList());
        model.addAttribute(MODEL_LIST, repairForm.getModelList());
        model.addAttribute(REPAIR, repairForm.getRepair());
    }

}
