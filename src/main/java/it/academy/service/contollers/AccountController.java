package it.academy.service.contollers;

import it.academy.service.dto.AccountDTO;
import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.Account_;
import it.academy.service.exceptions.EmailAlreadyRegistered;
import it.academy.service.services.AccountService;
import it.academy.service.services.ServiceCenterService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static it.academy.service.utils.Constants.*;
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final ServiceCenterService serviceCenterService;

    @GetMapping
    public String showPage(Model model) {
        return showPage(model, FIRST_PAGE, Account_.ID, Sort.Direction.DESC.name(), StringUtils.EMPTY);
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Model model,
                           @PathVariable(PAGE_NUM) int pageNum,
                           @RequestParam(SORT_FIELD) String sortField,
                           @RequestParam(SORT_DIR) String sortDir,
                           @RequestParam(KEYWORD) String keyword) {
        TablePage<AccountDTO> page = service.findForPage(pageNum, sortField, sortDir, keyword);
        model.addAttribute(TABLE_PAGE, page);
        return ACCOUNT_TABLE;
    }

    @GetMapping("/account-create-admin")
    public String showCreateAdminPage(Model model) {
        model.addAttribute(ACCOUNT, new AccountDTO());
        return ADD_ADMIN_ACCOUNT_PAGE;
    }

    @GetMapping("/account-create-service-center")
    public String showCreatePage(Model model) {
        List<ServiceCenterDTO> serviceCenters = serviceCenterService.findActiveServiceCenters();
        model.addAttribute(SERVICE_CENTERS, serviceCenters);
        model.addAttribute(ACCOUNT, new AccountDTO());
        return ADD_SERVICE_CENTER_ACCOUNT_PAGE;
    }

    @PostMapping("/account-create")
    public String create(Model model, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        String formPage = accountDTO.getServiceCenterId() == null ?
                ADD_ADMIN_ACCOUNT_PAGE : ADD_SERVICE_CENTER_ACCOUNT_PAGE;
        return createOrUpdate(model, accountDTO, bindingResult, formPage);
    }

    @GetMapping("/account-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id) {
        AccountDTO accountDTO = service.findById(id);
        model.addAttribute(ACCOUNT, accountDTO);
        return UPDATE_ACCOUNT_PAGE;
    }

    @PostMapping("/account-update")
    public String update(Model model, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        return createOrUpdate(model, accountDTO, bindingResult, UPDATE_ACCOUNT_PAGE);
    }

    @GetMapping("/account-delete/{id}")
    public String delete(Model model, @PathVariable(OBJECT_ID) Long id) {
        try{
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(model);
    }

    private String createOrUpdate(Model model, @Valid AccountDTO accountDTO,
                                  BindingResult bindingResult, String formPath) {
        boolean isValid = DtoValidator.isValid(model, accountDTO, ACCOUNT, bindingResult);

        if (isValid) {
            try {
                service.createOrUpdate(accountDTO);
                return ACCOUNTS_PAGE_REDIRECT;
            } catch (EmailAlreadyRegistered e) {
                model.addAttribute(ERROR_MESSAGE, EMAIL_ALREADY_EXISTS);
            }
        }
        model.addAttribute(ACCOUNT, accountDTO);
        return formPath;
    }

}
