package it.academy.service.repositories.impl;

import it.academy.service.entity.Brand;
import it.academy.service.entity.Brand_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BrandSpecification {

    public static Specification<Brand> search(String keyword) {
        return (Specification<Brand>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return cb.conjunction();
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.get(Brand_.NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Brand_.IS_ACTIVE).as(String.class), currentKeyword));
                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
            }
            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }

}
