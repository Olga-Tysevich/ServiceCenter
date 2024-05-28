package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SparePartOrderSpecification {

    public static Specification<SparePartOrder> search(String keyword) {
        return (Specification<SparePartOrder>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return cb.conjunction();
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.join(SparePartOrder_.REPAIR).get(Repair_.REPAIR_NUMBER), currentKeyword));
                fieldPredicates.add(cb.like(root.get(SparePartOrder_.ID).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(SparePartOrder_.ORDER_DATE).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(SparePartOrder_.DELIVERY_DATE).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(SparePartOrder_.DEPARTURE_DATE).as(String.class), currentKeyword));
                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
            }
            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }

}
