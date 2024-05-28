package it.academy.service.rest;

import it.academy.service.dto.StockSparePartDTO;
import it.academy.service.services.StockSparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static it.academy.service.utils.Constants.OBJECT_ID;

@RestController
@RequestMapping("stock-spare-part-rest")
@RequiredArgsConstructor
public class StockSparePartRestController {
    private final StockSparePartService stockSparePartService;

    @GetMapping("by-model-id/{id}")
    public ResponseEntity<List<StockSparePartDTO>> getSpareParts(@PathVariable(OBJECT_ID) Long id) {
        List<StockSparePartDTO> spareParts = stockSparePartService.findByModelId(id);
        if (spareParts == null || spareParts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(spareParts, HttpStatus.OK);
    }

}
