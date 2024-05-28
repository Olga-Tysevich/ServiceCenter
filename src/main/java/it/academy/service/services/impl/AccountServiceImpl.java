package it.academy.service.services.impl;

import it.academy.service.dto.AccountDTO;
import it.academy.service.entity.Account;
import it.academy.service.exceptions.EmailAlreadyRegistered;
import it.academy.service.mappers.AccountMapper;
import it.academy.service.repositories.AccountRepository;
import it.academy.service.repositories.impl.AccountSpecification;
import it.academy.service.services.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.SERVICE_CENTER_TABLE_PAGE;

@Transactional
@Service
public class AccountServiceImpl extends CrudServiceImpl<Account, AccountDTO, Long> implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository repository, PasswordEncoder passwordEncoder) {
        super(repository, AccountMapper.INSTANCE);
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = repository;
    }

    @Override
    public AccountDTO createOrUpdate(AccountDTO dto) {
        return Optional.ofNullable(dto)
                .map(mapper()::toEntity)
                .map(this::checkEmail)
                .map(this::setPassword)
                .map(accountRepository::save)
                .map(mapper()::toDTO)
                .orElse(null);
    }

    @Override
    protected String getTablePagePath() {
        return SERVICE_CENTER_TABLE_PAGE;
    }

    @Override
    protected Specification<Account> getSpecification(String keyword) {
        return AccountSpecification.search(keyword);
    }

    private Account checkEmail(Account account) {
        Long id = Objects.requireNonNullElse(account.getId(), ID_FOR_CHECK);
        if (accountRepository.existsByEmailAndIdIsNot(account.getEmail(), id)) {
            throw new EmailAlreadyRegistered();
        }
        return account;
    }

    private Account setPassword(Account account) {
        if (account.getId() != null && StringUtils.isBlank(account.getPassword())) {
            Account result = accountRepository.getById(account.getId());
            account.setPassword(result.getPassword());
            return account;
        }
        return encodePassword(account);
    }

    private Account encodePassword(Account account) {
        if (account.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(account.getPassword());
            account.setPassword(encodedPassword);
        }
        return account;
    }
}
