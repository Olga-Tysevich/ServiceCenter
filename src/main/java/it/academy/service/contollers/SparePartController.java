package it.academy.service.contollers;

import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.SparePartForm;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.dto.SparePartDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.SparePart_;
import it.academy.service.services.SparePartService;
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
@RequestMapping("spare-parts")
@RequiredArgsConstructor
public class SparePartController {
    private final SparePartService service;

    @GetMapping
    public String showPage(Authentication authentication, Model model) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        return showPage(authentication, model,  new TablePageReq(serviceCenterId, FIRST_PAGE, SparePart_.NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY));
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Authentication authentication, Model model, @ModelAttribute TablePageReq tablePageReq) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        tablePageReq.setServiceCenterId(serviceCenterId);
        TablePage<SparePartDTO> page = service.findForPage(tablePageReq);
        model.addAttribute(TABLE_PAGE, page);
        return SPARE_PART_TABLE;
    }

    @GetMapping("/spare-part-create")
    public String showCreatePage(Model model) {
        SparePartForm form = service.getSparePartForm(null);
        model.addAttribute(MODEL_LIST, form.getModels());
        model.addAttribute(SPARE_PART, form.getSparePart());
        return ADD_SPARE_PART_PAGE;
    }

    @PostMapping("/spare-part-create")
    public String create(Model model, @Valid SparePartDTO sparePartDTO, BindingResult bindingResult) {
        if (!DtoValidator.isValid(model, sparePartDTO, SPARE_PART, bindingResult)) {
            model.addAttribute(SPARE_PART, sparePartDTO);
            return ADD_SPARE_PART_PAGE;
        }
        service.createOrUpdate(sparePartDTO);
        return SPARE_PARTS_PAGE_REDIRECT;
    }

    @GetMapping("/spare-part-update/{id}")
    public String showUpdatePage(Model model,
                                 @PathVariable(OBJECT_ID) Long id,
                                 @ModelAttribute TablePageReq tablePageReq) {
        SparePartForm form = service.getSparePartForm(id);
        model.addAttribute(MODEL_LIST, form.getModels());
        model.addAttribute(SPARE_PART, form.getSparePart());
        model.addAttribute(LAST_PAGE, tablePageReq);
        return UPDATE_SPARE_PART_PAGE;
    }

    @PostMapping("/spare-part-update")
    public String update(Model model,
                         @Valid SparePartDTO sparePartDTO,
                         BindingResult bindingResult,
                         @ModelAttribute TablePageReq tablePageReq) {
        model.addAttribute(LAST_PAGE, tablePageReq);
        if (DtoValidator.isValid(model, sparePartDTO, SPARE_PART, bindingResult)) {
            model.addAttribute(SPARE_PART, sparePartDTO);
            return UPDATE_SPARE_PART_PAGE;
        }
        service.createOrUpdate(sparePartDTO);
        return SPARE_PARTS_PAGE_REDIRECT;
    }


    @GetMapping("/spare-part-delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable(OBJECT_ID) Long id) {
        try{
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(authentication, model);
    }
}
