package it.academy.service.rest;

import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.services.SparePartOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.academy.service.utils.UIConstants.UPDATE_SUCCESSFULLY;

@RestController
@RequestMapping("/spare-part-orders-rest")
@RequiredArgsConstructor
public class SparePartOrderRestController {
    private final SparePartOrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody SparePartOrderDTO orderDTO, BindingResult bindingResult) {
        String errors = DtoValidator.getErrors(bindingResult);
        if (!StringUtils.isBlank(errors)) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        SparePartOrderDTO result = orderService.createOrUpdate(orderDTO);
        if (StringUtils.isNotBlank(result.getErrorMessage())) {
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

}
