package it.academy.service.contollers;

import it.academy.service.dto.forms.ModelForm;
import it.academy.service.dto.ModelDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.Model_;
import it.academy.service.services.ModelService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import static it.academy.service.utils.Constants.*;
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService service;

    @GetMapping
    public String showPage(Model model) {
        return showModelsPage(model, FIRST_PAGE, Model_.NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showModelsPage(Model model,
                                 @PathVariable(PAGE_NUM) int pageNum,
                                 @RequestParam(SORT_FIELD) String sortField,
                                 @RequestParam(SORT_DIR) String sortDir,
                                 @RequestParam(KEYWORD) String keyword) {
        TablePage<ModelDTO> page = service.findForPage(pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return MODEL_TABLE;
    }

    @GetMapping("/model-create")
    public String showCreatePage(Model model) {
        ModelForm modelForm = service.getModelForm(null);
        model.addAttribute(BRAND_LIST, modelForm.getBrands());
        model.addAttribute(DEVICE_TYPE_LIST, modelForm.getDeviceTypes());
        model.addAttribute(MODEL, modelForm.getModel());
        return ADD_MODEL_PAGE;
    }


    @GetMapping("/model-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        ModelForm modelForm = service.getModelForm(id);
        model.addAttribute(BRAND_LIST, modelForm.getBrands());
        model.addAttribute(DEVICE_TYPE_LIST, modelForm.getDeviceTypes());
        model.addAttribute(MODEL, modelForm.getModel());
        return UPDATE_MODEL_PAGE;
    }

    @GetMapping("/model-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try{
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(model);
    }
}
