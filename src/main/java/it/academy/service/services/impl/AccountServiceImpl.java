package it.academy.service.services.impl;

import it.academy.service.dto.AccountDTO;
import it.academy.service.entity.Account;
import it.academy.service.mappers.AccountMapper;
import it.academy.service.repositories.AccountRepository;
import it.academy.service.repositories.impl.AccountSpecification;
import it.academy.service.services.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static it.academy.service.utils.Constants.ID_FOR_CHECK;
import static it.academy.service.utils.UIConstants.*;

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
    public AccountDTO createOrUpdate(@NotNull AccountDTO dto) {
        Long id = Objects.requireNonNullElse(dto.getId(), ID_FOR_CHECK);
        if (accountRepository.existsByEmailAndIdIsNot(dto.getEmail(), id)) {
            dto.setErrorMessage(EMAIL_ALREADY_EXISTS);
            return dto;
        }
        Account account = mapper().toEntity(dto);
        setPassword(account);
        return mapper().toDTO(accountRepository.save(account));
    }

    @Override
    protected String getTablePagePath() {
        return ACCOUNT_TABLE_PAGE;
    }

    @Override
    protected Specification<Account> getSpecification(Long serviceCenterId, String keyword) {
        return AccountSpecification.search(serviceCenterId, keyword);
    }

    private void setPassword(Account account) {
        if (account.getId() != null && StringUtils.isBlank(account.getPassword())) {
            Account result = accountRepository.getById(account.getId());
            account.setPassword(result.getPassword());
            return;
        }
        encodePassword(account);
    }

    private void encodePassword(Account account) {
        if (account.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(account.getPassword());
            account.setPassword(encodedPassword);
        }
    }
}
