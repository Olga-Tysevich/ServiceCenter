package it.academy.service.contollers;

import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.entity.SparePartOrder_;
import it.academy.service.services.SparePartOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static it.academy.service.utils.Constants.*;
import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("repair-orders")
@RequiredArgsConstructor
public class OrderController {
    private final SparePartOrderService orderService;

    @GetMapping("/show-orders/{id}")
    public String showOrdersPage(Model model,
                                 @PathVariable(OBJECT_ID) Long repairId,
                                 @RequestParam(value = SORT_FIELD, defaultValue = SparePartOrder_.ORDER_DATE) String sortField,
                                 @RequestParam(value = SORT_DIR, defaultValue = ASC) String sortDir,
                                 @RequestParam(REPAIR_NUMBER) String repairNumber) {
        List<SparePartOrderDTO> orders = orderService.getListByRepairId(repairId);
        model.addAttribute(REPAIR_ID, repairId);
        model.addAttribute(REPAIR_NUMBER, repairNumber);
        model.addAttribute(ORDER_LIST, orders);
        model.addAttribute(SORT_FIELD, sortField);
        model.addAttribute(SORT_DIR, sortDir);
        return REPAIR_ORDER_LIST_PAGE;
    }
}
