package it.academy.service.services.auth;

import it.academy.service.dto.AccountDTO;
import it.academy.service.entity.Account;
import it.academy.service.entity.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AccountDetailsImpl implements UserDetails {
    private final AccountDTO account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(account.getRole().name()));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUserName();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public Long getId() {
        return account.getId();
    }

    public String getServiceCenterName() {
        return account != null?
                account.getServiceCenterName() : RoleEnum.ADMIN.name();
    }

    public RoleEnum getRole() {
        return account != null?
                account.getRole() : RoleEnum.SERVICE_CENTER;
    }

    public Long getServiceCenterId() {
        return account != null?
                account.getServiceCenterId() : null;
    }

    public Boolean getIsActive() {
        return account != null? account.getIsActive() : true;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
