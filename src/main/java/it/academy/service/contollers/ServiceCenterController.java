package it.academy.service.contollers;

import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.ServiceCenter_;
import it.academy.service.services.ServiceCenterService;
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
@RequestMapping("service-centers")
@RequiredArgsConstructor
public class ServiceCenterController {
    private final ServiceCenterService service;

    @GetMapping
    public String showPage(Model model) {
        return showPage(model, new TablePageReq(null, FIRST_PAGE, ServiceCenter_.SERVICE_NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY));
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Model model, @ModelAttribute TablePageReq tablePageReq) {
        TablePage<ServiceCenterDTO> page = service.findForPage(tablePageReq);
        model.addAttribute(TABLE_PAGE, page);
        return SERVICE_CENTER_TABLE;
    }

    @GetMapping("/service-center-create")
    public String showCreatePage(Model model) {
        model.addAttribute(SERVICE_CENTER, new ServiceCenterDTO());
        return ADD_SERVICE_CENTER_PAGE;
    }

    @PostMapping("/service-center-create")
    public String create(Model model, @ModelAttribute @Valid ServiceCenterDTO serviceCenterDTO, BindingResult bindingResult) {
        if (!DtoValidator.isValid(model, serviceCenterDTO, SERVICE_CENTER, bindingResult)) {
            return ADD_SERVICE_CENTER_PAGE;
        }
        service.createOrUpdate(serviceCenterDTO);
        return SERVICE_CENTERS_PAGE_REDIRECT;
    }

    @GetMapping("/service-center-update/{id}")
    public String showUpdatePage(@PathVariable(OBJECT_ID) Long id, Model model, @ModelAttribute TablePageReq tablePageReq) {
        ServiceCenterDTO serviceCenterDTO = service.findById(id);
        model.addAttribute(SERVICE_CENTER, serviceCenterDTO);
        model.addAttribute(LAST_PAGE, tablePageReq);
        return UPDATE_SERVICE_CENTER_PAGE;
    }

    @PostMapping("/service-center-update")
    public String update(Model model,
                         @ModelAttribute @Valid ServiceCenterDTO serviceCenterDTO,
                         BindingResult bindingResult,
                         @ModelAttribute TablePageReq tablePageReq) {
        if (!DtoValidator.isValid(model, serviceCenterDTO, SERVICE_CENTER, bindingResult)) {
            model.addAttribute(LAST_PAGE, tablePageReq);
            return UPDATE_SERVICE_CENTER_PAGE;
        }
        service.createOrUpdate(serviceCenterDTO);
        return SERVICE_CENTERS_PAGE_REDIRECT;
    }


    @GetMapping("/service-center-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try {
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return SERVICE_CENTERS_PAGE_REDIRECT;
    }

}
