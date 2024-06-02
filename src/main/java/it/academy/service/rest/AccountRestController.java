package it.academy.service.rest;

import it.academy.service.dto.AccountDTO;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static it.academy.service.utils.UIConstants.UPDATE_SUCCESSFULLY;

@RestController
@RequestMapping("/accounts-rest")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService service;

    @PostMapping("/create-service-center-account")
    public ResponseEntity<String> createAccount(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        String errors = DtoValidator.getErrors(bindingResult);
        if (!StringUtils.isBlank(errors)) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AccountDTO result = service.createOrUpdate(accountDTO);
        if (result.getErrorMessage() != null) {
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

}
