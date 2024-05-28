package it.academy.service.rest;

import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.exceptions.OrderIsEmpty;
import it.academy.service.services.SparePartOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.academy.service.utils.UIConstants.*;

@RestController
@RequestMapping("/spare-part-orders-rest")
@RequiredArgsConstructor
public class SparePartOrderRestController {
    private final SparePartOrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody SparePartOrderDTO orderDTO) {
        Long id;

        try {
            id = orderService.createOrUpdateOrder(orderDTO).getId();
        } catch (OrderIsEmpty e) {
            return new ResponseEntity<>(ORDER_IS_EMPTY, HttpStatus.BAD_REQUEST);
        }

        if (id != null) {
            return new ResponseEntity<>(UPDATE_SUCCESSFULLY, HttpStatus.OK);
        }
        return new ResponseEntity<>(UPDATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
