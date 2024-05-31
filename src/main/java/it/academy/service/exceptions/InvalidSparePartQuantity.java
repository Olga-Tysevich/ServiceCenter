package it.academy.service.exceptions;

import static it.academy.service.utils.UIConstants.INVALID_SPARE_PART_QUANTITY;

public class InvalidSparePartQuantity extends RuntimeException {

    public InvalidSparePartQuantity() {
        super(INVALID_SPARE_PART_QUANTITY);
    }
}
