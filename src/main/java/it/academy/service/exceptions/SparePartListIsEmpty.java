package it.academy.service.exceptions;

import static it.academy.service.utils.UIConstants.SPARE_PART_LIST_IS_EMPTY;

public class SparePartListIsEmpty extends RuntimeException {

    public SparePartListIsEmpty() {
        super(SPARE_PART_LIST_IS_EMPTY);
    }
}
