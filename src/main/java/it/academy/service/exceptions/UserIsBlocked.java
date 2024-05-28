package it.academy.service.exceptions;

import org.springframework.security.core.AuthenticationException;

import static it.academy.service.utils.UIConstants.USER_IS_BLOCKED;

public class UserIsBlocked extends AuthenticationException {


    public UserIsBlocked() {
        super(USER_IS_BLOCKED);
    }

    public UserIsBlocked(Throwable cause) {
        super(USER_IS_BLOCKED, cause);
    }
}
