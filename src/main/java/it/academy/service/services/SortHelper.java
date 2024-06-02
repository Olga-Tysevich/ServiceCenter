package it.academy.service.services;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class SortHelper {

    public Sort defineCurrentSort(String sortField, String sortDir) {
        return Sort.Direction.ASC.name().equalsIgnoreCase(sortDir) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
    }

}
