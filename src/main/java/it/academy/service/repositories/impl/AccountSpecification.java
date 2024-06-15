package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {

    public static Specification<Account> search(Long serviceCenterId, String keyword) {
        return (Specification<Account>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return cb.and(cb.notEqual(root.get(Account_.ROLE).as(String.class), RoleEnum.ADMIN.name()));
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.get(Account_.EMAIL), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Account_.IS_ACTIVE).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Account_.USER_NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Account_.USER_SURNAME), currentKeyword));
                fieldPredicates.add(cb.like(root.join(Account_.SERVICE_CENTER).get(ServiceCenter_.SERVICE_NAME), currentKeyword));
                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
            }
            searchPredicates.add(cb.and(cb.notEqual(root.get(Account_.ROLE).as(String.class), RoleEnum.ADMIN.name())));
            if (serviceCenterId != null) {
                searchPredicates.add(cb.and(cb.equal(root.join(Account_.SERVICE_CENTER).get(ServiceCenter_.ID), serviceCenterId)));
            }

            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }
}
