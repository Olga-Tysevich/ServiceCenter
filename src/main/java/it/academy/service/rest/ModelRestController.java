package it.academy.service.rest;

import it.academy.service.dto.ModelDTO;
import it.academy.service.dto.validator.DtoValidator;
import it.academy.service.services.ModelService;
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
@RequestMapping("/models-rest")
@RequiredArgsConstructor
public class ModelRestController {
    private final ModelService modelService;

    @GetMapping(value = "by-brand/{id}")
    public ResponseEntity<List<ModelDTO>> getModels(@PathVariable(OBJECT_ID) Long id) {
        List<ModelDTO> models = modelService.findModelsByBrandId(id);
        if (models == null || models.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(models, HttpStatus.OK);
    }


    @PostMapping("/model-create-or-update")
    public ResponseEntity<String> create(@RequestBody @Valid ModelDTO modelDTO, BindingResult bindingResult) {
        String errors = DtoValidator.getErrors(bindingResult);
        if (!StringUtils.isBlank(errors)) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ModelDTO result = modelService.createOrUpdate(modelDTO);
        if (result.getErrorMessage() != null) {
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

}
