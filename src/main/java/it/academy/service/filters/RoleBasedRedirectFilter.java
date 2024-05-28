package it.academy.service.filters;

import it.academy.service.entity.RoleEnum;
import it.academy.service.services.auth.AccountDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static it.academy.service.utils.Constants.OBJECT_ID;
import static it.academy.service.utils.UIConstants.MAIN_PAGE;
import static it.academy.service.utils.UIConstants.SERVICE_CENTER_ID;

@Component
public class RoleBasedRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                Long accountId = ((AccountDetailsImpl) authentication.getPrincipal()).getId();
                request.setAttribute(OBJECT_ID, accountId);

                if (authority.getAuthority().equals(RoleEnum.SERVICE_CENTER.name())) {
                    Long serviceCenterId = ((AccountDetailsImpl) authentication.getPrincipal()).getServiceCenterId();
                    request.setAttribute(SERVICE_CENTER_ID, serviceCenterId);
                    if ((request.getRequestURI().equals("/service-centers")
                            || request.getRequestURI().equals("/accounts")) && !response.isCommitted()) {
                        response.sendRedirect(request.getContextPath() + MAIN_PAGE);
                        return;
                    }
                }

            }

        }

        filterChain.doFilter(request, response);
    }
}