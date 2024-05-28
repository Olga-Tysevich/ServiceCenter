package it.academy.service.repositories;

import it.academy.service.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CustomCrudRepository<Account, Long> {

    Account findByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, Long id);

    List<Account> findAllByServiceCenterId(Long id);

}
