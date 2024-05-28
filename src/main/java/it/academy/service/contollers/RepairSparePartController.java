package it.academy.service.contollers;

import it.academy.service.dto.RepairSparePartDTO;
import it.academy.service.services.RepairSparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static it.academy.service.utils.UIConstants.*;

@Controller
@RequestMapping("repair-spare-parts")
@RequiredArgsConstructor
public class RepairSparePartController {
    private final RepairSparePartService sparePartService;

    @GetMapping("show-repair-spare-parts")
    public String showRepairSpareParts(Model model,
                                       @RequestParam(REPAIR_ID) Long id,
                                       @RequestParam(REPAIR_NUMBER) String repairNumber) {
        List<RepairSparePartDTO> spareParts = sparePartService.findAllByRepairId(id);
        model.addAttribute(REPAIR_SPARE_PARTS, spareParts);
        model.addAttribute(REPAIR_ID, id);
        model.addAttribute(REPAIR_NUMBER, repairNumber);
        return REPAIR_SPARE_PARTS_PAGE;
    }
}
