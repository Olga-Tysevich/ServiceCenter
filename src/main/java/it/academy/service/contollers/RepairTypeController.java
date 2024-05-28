package it.academy.service.contollers;

import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.dto.RepairTypeDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.RepairType_;
import it.academy.service.exceptions.ObjectAlreadyExist;
import it.academy.service.services.RepairTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static it.academy.service.utils.Constants.*;
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("repair-types")
@RequiredArgsConstructor
public class RepairTypeController{
    private final RepairTypeService service;

    @GetMapping
    public String showPage(Model model) {
        return showRepairTypesPage(model, FIRST_PAGE, RepairType_.NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showRepairTypesPage(Model model,
                                      @PathVariable(PAGE_NUM) int pageNum,
                                      @RequestParam(SORT_FIELD) String sortField,
                                      @RequestParam(SORT_DIR) String sortDir,
                                      @RequestParam(KEYWORD) String keyword) {
        TablePage<RepairTypeDTO> page = service.findForPage(pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return REPAIR_TYPE_TABLE;
    }

    @GetMapping("/repair-type-create")
    public String showCreatePage(Model model) {
        model.addAttribute(REPAIR_TYPE, new RepairTypeDTO());
        return ADD_REPAIR_TYPE_PAGE;
    }

    @PostMapping("/repair-type-create")
    public String create(Model model, @Valid RepairTypeDTO repairTypeDTO, BindingResult bindingResult) {
       return updateRepairType(model,
               repairTypeDTO,
               bindingResult,
               ADD_REPAIR_TYPE_PAGE);
    }

    @GetMapping("/repair-type-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        RepairTypeDTO repairTypeDTO = service.findById(id);
        model.addAttribute(REPAIR_TYPE, repairTypeDTO);
        return UPDATE_REPAIR_TYPE_PAGE;
    }

    @PostMapping("/repair-type-update")
    public String update(Model model, @Valid RepairTypeDTO repairTypeDTO, BindingResult bindingResult) {
        return updateRepairType(model,
                repairTypeDTO,
                bindingResult,
                UPDATE_REPAIR_TYPE_PAGE);
    }

    @GetMapping("/repair-type-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try{
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(model);
    }

    private String updateRepairType(Model model,
                                    RepairTypeDTO repairTypeDTO,
                                    BindingResult bindingResult,
                                    String formPath) {
        if (!DtoValidator.isValid(model, repairTypeDTO, REPAIR_TYPE, bindingResult)) {
            model.addAttribute(REPAIR_TYPE, repairTypeDTO);
            return formPath;
        }
        try {
            service.createOrUpdate(repairTypeDTO);
        } catch (ObjectAlreadyExist e) {
            model.addAttribute(ERROR_MESSAGE, REPAIR_TYPE_ALREADY_EXISTS);
            model.addAttribute(REPAIR_TYPE, repairTypeDTO);
            return formPath;
        }

        return REPAIR_TYPES_PAGE_REDIRECT;
    }

}
