package it.academy.service.services.auth;

import it.academy.service.dto.AccountDTO;
import it.academy.service.entity.Account;
import it.academy.service.exceptions.UserIsBlocked;
import it.academy.service.mappers.AccountMapper;
import it.academy.service.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, UserIsBlocked {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("could not find account");
        }
        if (!account.getIsActive()) {
            throw new UserIsBlocked();
        }

        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(account);
        return new AccountDetailsImpl(accountDTO);
    }

}
