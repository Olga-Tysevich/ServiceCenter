package it.academy.service.contollers;

import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.DeviceType_;
import it.academy.service.services.DeviceTypeService;
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
@RequestMapping("device-types")
@RequiredArgsConstructor
public class DeviceTypeController {
    private final DeviceTypeService service;

    @GetMapping
    public String showPage(Model model) {
        return showPage(model, FIRST_PAGE, DeviceType_.NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Model model,
                           @PathVariable(PAGE_NUM) int pageNum,
                           @RequestParam(SORT_FIELD) String sortField,
                           @RequestParam(SORT_DIR) String sortDir,
                           @RequestParam(KEYWORD) String keyword) {
        TablePage<DeviceTypeDTO> page = service.findForPage(pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return DEVICE_TYPE_TABLE;
    }

    @GetMapping("/device-type-create")
    public String showCreatePage(Model model) {
        model.addAttribute(DEVICE_TYPE, new DeviceTypeDTO());
        return ADD_DEVICE_TYPE_PAGE;
    }

    @PostMapping("/device-type-create")
    public String create(Model model, @Valid DeviceTypeDTO deviceTypeDTO, BindingResult bindingResult) {
        return createOrUpdate(model, deviceTypeDTO, bindingResult, ADD_DEVICE_TYPE_PAGE);
    }

    @GetMapping("/device-type-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        DeviceTypeDTO deviceTypeDTO = service.findById(id);
        model.addAttribute(DEVICE_TYPE, deviceTypeDTO);
        return UPDATE_DEVICE_TYPE_PAGE;
    }

    @PostMapping("/device-type-update")
    public String update(Model model, @Valid DeviceTypeDTO deviceTypeDTO, BindingResult bindingResult) {
        return createOrUpdate(model, deviceTypeDTO, bindingResult, UPDATE_DEVICE_TYPE_PAGE);
    }

    @GetMapping("/device-type-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try {
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(model);
    }

    private String createOrUpdate(Model model, @Valid DeviceTypeDTO deviceTypeDTO,
                                  BindingResult bindingResult, String formPage) {
        if (DtoValidator.isValid(model, deviceTypeDTO, DEVICE_TYPE, bindingResult)) {
            DeviceTypeDTO result = service.createOrUpdate(deviceTypeDTO);
            if (StringUtils.isBlank(result.getErrorMessage())) {
                return DEVICE_TYPES_PAGE_REDIRECT;
            }
        }
        model.addAttribute(ERROR_MESSAGE, DEVICE_TYPE_ALREADY_EXISTS);
        model.addAttribute(DEVICE_TYPE, deviceTypeDTO);
        return formPage;
    }
}
