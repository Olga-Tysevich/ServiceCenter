package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.RepairSparePartPK_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class RepairSparePartSpecification {

    public static Specification<RepairSparePart> searchByRepairId(Long id) {
        return (Specification<RepairSparePart>) (root, query, cb) ->
                Optional.ofNullable(id)
                        .map(i -> cb.equal(root.join(RepairSparePart_.PRIMARY_KEY)
                                .join(RepairSparePartPK_.REPAIR)
                                .get(Repair_.ID), id))
                        .orElse(cb.conjunction());
    }
}
