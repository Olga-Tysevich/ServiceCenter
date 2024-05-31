package it.academy.service.rest;

import it.academy.service.dto.SparePartDTO;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.services.SparePartService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static it.academy.service.utils.Constants.OBJECT_ID;
import static it.academy.service.utils.UIConstants.UPDATE_SUCCESSFULLY;

@RestController
@RequestMapping("/spare-part-rest")
@RequiredArgsConstructor
public class SparePartRestController {
    private final SparePartService sparePartService;

    @GetMapping("by-model-id/{id}")
    public ResponseEntity<List<SparePartDTO>> getSpareParts(@PathVariable(OBJECT_ID) Long id) {
        List<SparePartDTO> spareParts = sparePartService.findByModelId(id);
        if (spareParts == null || spareParts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(spareParts, HttpStatus.OK);
    }

    @PostMapping("/spare-part-create-or-update")
    public ResponseEntity<String> create(@RequestBody @Valid SparePartDTO sparePartDTO, BindingResult bindingResult) {
        String errors = DtoValidator.getErrors(bindingResult);
        if (!StringUtils.isBlank(errors)) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        SparePartDTO result = sparePartService.createOrUpdate(sparePartDTO);
        if (StringUtils.isBlank(result.getErrorMessage())) {
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

}
