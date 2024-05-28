package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class RepairTypeSpecification {

    public static Specification<RepairType> search(String keyword) {
        return (Specification<RepairType>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return cb.conjunction();
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.get(RepairType_.NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.get(RepairType_.CODE), currentKeyword));
                fieldPredicates.add(cb.like(root.get(RepairType_.LEVEL), currentKeyword));
                fieldPredicates.add(cb.like(root.get(RepairType_.IS_ACTIVE).as(String.class), currentKeyword));
                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
            }
            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }
}
