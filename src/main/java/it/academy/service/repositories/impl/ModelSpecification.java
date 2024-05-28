package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ModelSpecification {

    public static Specification<Model> search(String keyword) {
        return (Specification<Model>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return cb.conjunction();
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.get(Model_.NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Model_.IS_ACTIVE).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.join(Model_.BRAND).get(Brand_.NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.join(Model_.TYPE).get(DeviceType_.NAME), currentKeyword));
                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
            }
            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }

}
