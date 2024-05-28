package it.academy.service.contollers;

import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.ServiceCenter_;
import it.academy.service.services.ServiceCenterService;
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
import static it.academy.service.utils.Constants.OBJECT_ID;
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("service-centers")
@RequiredArgsConstructor
public class ServiceCenterController {
    private final ServiceCenterService service;

    @GetMapping
    public String showPage(Model model) {
        return showPage(model, FIRST_PAGE, ServiceCenter_.SERVICE_NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Model model,
                           @PathVariable(PAGE_NUM) int pageNum,
                           @RequestParam(SORT_FIELD) String sortField,
                           @RequestParam(SORT_DIR) String sortDir,
                           @RequestParam(KEYWORD) String keyword) {
        TablePage<ServiceCenterDTO> page = service.findForPage(pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return SERVICE_CENTER_TABLE;
    }

    @GetMapping("/service-center-create")
    public String showCreatePage(Model model) {
        model.addAttribute(SERVICE_CENTER, new ServiceCenterDTO());
        return ADD_SERVICE_CENTER_PAGE;
    }

    @PostMapping("/service-center-create")
    public String create(Model model, @Valid ServiceCenterDTO serviceCenterDTO, BindingResult bindingResult) {
        if (!DtoValidator.isValid(model, serviceCenterDTO, REPAIR_TYPE, bindingResult)) {
            model.addAttribute(SERVICE_CENTER, serviceCenterDTO);
            return ADD_SERVICE_CENTER_PAGE;
        }
        service.createOrUpdate(serviceCenterDTO);
        return SERVICE_CENTERS_PAGE_REDIRECT;
    }

    @GetMapping("/service-center-update/{id}")
    public String showUpdatePage(@PathVariable(OBJECT_ID) Long id, Model model) {
        ServiceCenterDTO serviceCenterDTO = service.findById(id);
        model.addAttribute(SERVICE_CENTER, serviceCenterDTO);
        return UPDATE_SERVICE_CENTER_PAGE;
    }

    @PostMapping("/service-center-update")
    public String update(Model model,
                         @Valid ServiceCenterDTO serviceCenterDTO,
                         BindingResult bindingResult) {
        if (!DtoValidator.isValid(model, serviceCenterDTO, REPAIR_TYPE, bindingResult)) {
            model.addAttribute(SERVICE_CENTER, serviceCenterDTO);
            return UPDATE_SERVICE_CENTER_PAGE;
        }
        service.createOrUpdate(serviceCenterDTO);
        return SERVICE_CENTERS_PAGE_REDIRECT;
    }


    @GetMapping("/service-center-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try{
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(model);
    }
}
