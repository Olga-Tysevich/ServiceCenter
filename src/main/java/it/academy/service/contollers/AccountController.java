package it.academy.service.contollers;

import it.academy.service.dto.AccountDTO;
import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.Account_;
import it.academy.service.services.AccountService;
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
    public String showPage(Authentication authentication, Model model) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        return showPage(authentication, model, new TablePageReq(serviceCenterId, FIRST_PAGE, Account_.ID, Sort.Direction.DESC.name(), StringUtils.EMPTY));
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Authentication authentication, Model model, @ModelAttribute TablePageReq tablePageReq) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        tablePageReq.setServiceCenterId(serviceCenterId);
        TablePage<AccountDTO> page = service.findForPage(tablePageReq);
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
        return createOrUpdate(model, accountDTO, bindingResult, ADD_ADMIN_ACCOUNT_PAGE);
    }

    @GetMapping("/account-update/{id}")
    public String showUpdatePage(Model model, @PathVariable(OBJECT_ID) Long id, @ModelAttribute TablePageReq tablePageReq) {
        AccountDTO accountDTO = service.findById(id);
        model.addAttribute(ACCOUNT, accountDTO);
        model.addAttribute(LAST_PAGE, tablePageReq);
        return UPDATE_ACCOUNT_PAGE;
    }

    @PostMapping("/account-update")
    public String update(Model model, @Valid AccountDTO accountDTO,
                         BindingResult bindingResult,
                         @ModelAttribute TablePageReq tablePageReq) {
        model.addAttribute(LAST_PAGE, tablePageReq);
        return createOrUpdate(model, accountDTO, bindingResult, UPDATE_ACCOUNT_PAGE);
    }

    @GetMapping("/account-delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable(OBJECT_ID) Long id) {
        try {
            model.addAttribute("page", ACCOUNT_TABLE);
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, DELETE_FAILED);
        }
        return showPage(authentication, model);
    }

    private String createOrUpdate(Model model, @Valid AccountDTO accountDTO,
                                  BindingResult bindingResult, String formPath) {

        if (DtoValidator.isValid(model, accountDTO, ACCOUNT, bindingResult)) {
            AccountDTO result = service.createOrUpdate(accountDTO);
            if (StringUtils.isBlank(result.getErrorMessage())) {
                return ACCOUNTS_PAGE_REDIRECT;
            }
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
        }
        model.addAttribute(ACCOUNT, accountDTO);
        return formPath;
    }

}
