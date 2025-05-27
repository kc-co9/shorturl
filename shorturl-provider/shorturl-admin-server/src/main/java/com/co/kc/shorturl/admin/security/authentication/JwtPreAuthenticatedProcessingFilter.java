package com.co.kc.shorturl.admin.security.authentication;

import com.co.kc.shorturl.admin.model.domain.SecurityAuth;
import com.co.kc.shorturl.admin.utils.SecurityUtils;
import com.co.kc.shorturl.provider.common.constants.ParamsConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author kc
 */
public class JwtPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    public JwtPreAuthenticatedProcessingFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ParamsConstants.TOKEN))
                .map(SecurityUtils::parseToken)
                .map(SecurityAuth::getUserId)
                .orElse(null);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return request.getHeader(ParamsConstants.TOKEN);
    }
}
