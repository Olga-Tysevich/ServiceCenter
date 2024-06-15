package it.academy.service.contollers;

import it.academy.service.dto.SparePartOrderDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.entity.SparePartOrder_;
import it.academy.service.services.SparePartOrderService;
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
@RequestMapping("spare-part-orders")
@RequiredArgsConstructor
public class SparePartOrderController {
    private final SparePartOrderService orderService;

    @GetMapping
    public String showPage(Authentication authentication, Model model) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        return showPage(authentication, model, new TablePageReq(serviceCenterId, FIRST_PAGE, SparePartOrder_.ID, Sort.Direction.DESC.name(), StringUtils.EMPTY));
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(Authentication authentication, Model model, @ModelAttribute TablePageReq tablePageReq) {
        Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
        tablePageReq.setServiceCenterId(serviceCenterId);
        TablePage<SparePartOrderDTO> page = orderService.findForPage(tablePageReq);
        model.addAttribute(TABLE_PAGE, page);
        return SPARE_PART_ORDER_TABLE;
    }


    @GetMapping("/spare-part-order-update/{id}")
    public String showUpdatePage(Model model,
                                 @PathVariable(OBJECT_ID) Long id,
                                 @ModelAttribute TablePageReq tablePageReq) {
        SparePartOrderDTO sparePartOrderDTO = orderService.findById(id);
        model.addAttribute(SPARE_PART_ORDER, sparePartOrderDTO);
        model.addAttribute(LAST_PAGE, tablePageReq);
        return UPDATE_SPARE_PART_ORDER_PAGE;
    }

    @PostMapping("/spare-part-order-update")
    public String update(Model model,
                         @Valid SparePartOrderDTO sparePartOrderDTO,
                         BindingResult bindingResult,
                         @ModelAttribute TablePageReq tablePageReq) {
        if (DtoValidator.isValid(model, sparePartOrderDTO, SPARE_PART_ORDER, bindingResult)) {
            SparePartOrderDTO result = orderService.createOrUpdate(sparePartOrderDTO);
            if (StringUtils.isBlank(result.getErrorMessage())) {
                return SPARE_PART_ORDER_PAGE_REDIRECT;
            }
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
        }
        return showUpdatePage(model, sparePartOrderDTO.getId(), tablePageReq);
    }

    @GetMapping("/show-repair-orders/{id}")
    public String showOrdersPage(Model model,
                                 @PathVariable(OBJECT_ID) Long repairId,
                                 @RequestParam(REPAIR_NUMBER) String repairNumber) {
        List<SparePartOrderDTO> orders = orderService.getListByRepairId(repairId);
        model.addAttribute(REPAIR_ID, repairId);
        model.addAttribute(REPAIR_NUMBER, repairNumber);
        model.addAttribute(ORDER_LIST, orders);
        return REPAIR_ORDER_LIST_PAGE;
    }

}
