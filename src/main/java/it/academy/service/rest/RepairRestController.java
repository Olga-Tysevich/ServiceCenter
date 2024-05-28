package it.academy.service.rest;

import it.academy.service.dto.CompleteRepairDTO;
import it.academy.service.services.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.academy.service.utils.UIConstants.UPDATE_SUCCESSFULLY;
import static it.academy.service.utils.UIConstants.UPDATE_FAILED;

@RestController
@RequestMapping("repairs-rest")
@RequiredArgsConstructor
public class RepairRestController {
    private final RepairService repairService;

    @PostMapping("/complete-repair")
    public ResponseEntity<String> completeRepair(@RequestBody CompleteRepairDTO completeRepairDTO) {
        boolean isCompleted = repairService.completeRepair(completeRepairDTO);
        if (isCompleted) {
            return new ResponseEntity<>(UPDATE_SUCCESSFULLY, HttpStatus.OK);
        }
        return new ResponseEntity<>(UPDATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
