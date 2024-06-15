package it.academy.service.repositories.impl;

import it.academy.service.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class RepairSpecification {

//    public static Specification<Repair> searchByServiceCenter(Long serviceCenterId, String keyword) {
//        return getSearch(serviceCenterId, keyword);
//    }

//    public static Specification<Repair> search(String keyword) {
//        return getSearch(null, keyword);
//    }

    public static Specification<Repair> search(Long serviceCenterId, String keyword) {
        return (Specification<Repair>) (root, query, cb) -> {
            if (StringUtils.isBlank(keyword) && serviceCenterId != null) {
                return cb.equal(root.join(Repair_.SERVICE_CENTER).get(ServiceCenter_.ID), serviceCenterId);
            }

            List<String> searchKeywords = QueryHelper.getSearchKeywords(keyword);
            List<Predicate> searchPredicates = new ArrayList<>();

            for (String currentKeyword : searchKeywords) {
                List<Predicate> fieldPredicates = new ArrayList<>();
                fieldPredicates.add(cb.like(root.join(Repair_.SERVICE_CENTER).get(ServiceCenter_.SERVICE_NAME), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Repair_.CATEGORY).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Repair_.STATUS).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Repair_.START_DATE).as(String.class), currentKeyword));
                fieldPredicates.add(cb.like(root.get(Repair_.REPAIR_NUMBER), currentKeyword));
                fieldPredicates.add(cb.like(root.join(Repair_.DEVICE).get(Device_.SERIAL_NUMBER), currentKeyword));
                fieldPredicates.add(cb.like(root.join(Repair_.DEVICE)
                        .join(Device_.MODEL)
                        .get(Model_.NAME), currentKeyword));

                searchPredicates.add(cb.or(fieldPredicates.toArray(new Predicate[0])));
                if (serviceCenterId != null) {
                    searchPredicates.add(cb.and(cb.equal(root.join(Repair_.SERVICE_CENTER).get(ServiceCenter_.ID), serviceCenterId)));
                }
            }
            return cb.and(searchPredicates.toArray(new Predicate[0]));
        };
    }

}
