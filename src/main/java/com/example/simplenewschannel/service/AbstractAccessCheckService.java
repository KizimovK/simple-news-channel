package com.example.simplenewschannel.service;

import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.entity.RoleType;
import com.example.simplenewschannel.security.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.Map;

public abstract class AbstractAccessCheckService implements AccessCheckService {
    private static final String ID = "Id";

    @Override
    @SuppressWarnings("unchecked")
    public boolean getResultCheck(HttpServletRequest request, Accessible accessible) {
        if (!isUserAuthenticated()){
            return false;
        }
        if (hasAccessByRole(accessible)){
            return true;
        }
        var pathVariable =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long id = Long.parseLong(pathVariable.get(accessible.checkBy().toString().toLowerCase()+ID));
        var user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long idUser = user.getId();

        return check(id, idUser);
    }

    protected boolean hasAccessByRole(Accessible accessible) {
        var user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accessible.availableForModerator() &&
                user.hasRole(List.of(RoleType.ROLE_ADMIN,RoleType.ROLE_MODERATOR));
    }

    protected boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AppUserDetails;
    }

    protected abstract boolean check(long id, long idUser);

}
