package it.academy.service.contollers;

import it.academy.service.dto.BrandDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.Brand_;
import it.academy.service.services.BrandService;
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
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public String showPage(Model model) {
        return showPage(model, FIRST_PAGE, Brand_.NAME, Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Model model,
                           @PathVariable(PAGE_NUM) int pageNum,
                           @RequestParam(SORT_FIELD) String sortField,
                           @RequestParam(SORT_DIR) String sortDir,
                           @RequestParam(KEYWORD) String keyword) {
        TablePage<BrandDTO> page = brandService.findForPage(pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return BRAND_TABLE;
    }

    @GetMapping("/brand-create")
    public String showCreatePage(Model model) {
        model.addAttribute(BRAND, new BrandDTO());
        return ADD_BRAND_PAGE;
    }

    @PostMapping("/brand-create")
    public String create(Model model, @Valid BrandDTO brandDTO, BindingResult bindingResult) {
        return createOrUpdate(model, brandDTO, bindingResult, ADD_BRAND_PAGE);
    }

    @GetMapping("/brand-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        BrandDTO brandDTO = brandService.findById(id);
        model.addAttribute(BRAND, brandDTO);
        return UPDATE_BRAND_PAGE;
    }

    @PostMapping("/brand-update")
    public String update(Model model, @Valid BrandDTO brandDTO, BindingResult bindingResult) {
        return createOrUpdate(model, brandDTO, bindingResult, UPDATE_BRAND_PAGE);
    }

    @GetMapping("/brand-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try {
            brandService.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(model);
    }

    private String createOrUpdate(Model model, @Valid BrandDTO brandDTO,
                                  BindingResult bindingResult, String formPage) {
        if (DtoValidator.isValid(model, brandDTO, BRAND, bindingResult)) {
            BrandDTO result = brandService.createOrUpdate(brandDTO);
            if (StringUtils.isBlank(result.getErrorMessage())) {
                return BRANDS_PAGE_REDIRECT;
            }
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
        }
        model.addAttribute(BRAND, brandDTO);
        return formPage;
    }
}
