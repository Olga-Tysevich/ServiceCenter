package it.academy.service.repositories.impl;

import it.academy.service.entity.ServiceCenter;
import it.academy.service.entity.ServiceCenter_;
import it.academy.service.entity.embeddable.Requisites_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ServiceCenterSpecification {

    public static Specification<ServiceCenter> search(String keyword) {
        return (Specification<ServiceCenter>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return cb.conjunction();
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.get(ServiceCenter_.SERVICE_NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.FULL_NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.ACTUAL_ADDRESS), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.EMAIL), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.LEGAL_ADDRESS), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.PHONE), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.REGISTRATION_NUMBER).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.join(ServiceCenter_.REQUISITES).get(Requisites_.TAXPAYER_NUMBER).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(ServiceCenter_.IS_ACTIVE).as(String.class), currentKeyword));
                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
            }
            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }

}
