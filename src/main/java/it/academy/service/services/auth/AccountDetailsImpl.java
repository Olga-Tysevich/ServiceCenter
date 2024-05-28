package it.academy.service.services.auth;

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
    private final Account account;

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
        return account.getServiceCenter() != null?
                account.getServiceCenter().getServiceName() : RoleEnum.ADMIN.name();
    }

    public Long getServiceCenterId() {
        return account.getServiceCenter() != null?
                account.getServiceCenter().getId() : null;
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
