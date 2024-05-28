package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import it.academy.service.entity.embeddable.StockSparePartPK_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockSparePartSpecification {

    public static Specification<StockSparePart> searchByRepairId(Long id) {
        return (Specification<StockSparePart>) (root, query, cb) ->
                Optional.ofNullable(id)
                    .map(i -> cb.equal(root.join(StockSparePart_.PRIMARY_KEY)
                            .join(StockSparePartPK_.ORDER)
                            .get(SparePartOrder_.REPAIR)
                            .get(Repair_.ID), id))
                    .orElse(cb.conjunction());
    }

    public static Specification<StockSparePart> searchByModelIdAndRepairIsFinished(Long id) {
        return (Specification<StockSparePart>) (root, query, cb) -> {
            List<Predicate> searchPredicates = new ArrayList<>();

            Optional.ofNullable(id)
                    .ifPresent(i -> searchPredicates.add(cb.equal(root.join(StockSparePart_.PRIMARY_KEY)
                            .join(StockSparePartPK_.SPARE_PART)
                            .joinSet(SparePart_.MODELS)
                            .get(Model_.ID), id)));
            searchPredicates.add(cb.isNotNull(root.join(StockSparePart_.PRIMARY_KEY)
                    .join(StockSparePartPK_.ORDER)
                    .join(SparePartOrder_.REPAIR)
                    .get(Repair_.END_DATE)));

            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }
}
