package it.academy.service.dto.forms;

import it.academy.service.dto.ModelDTO;
import it.academy.service.dto.SparePartDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartForm {

    private SparePartDTO sparePart;

    private List<ModelDTO> models;

}
