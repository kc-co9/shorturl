package com.co.kc.shortening.admin.security.authentication;

import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.client.TokenDTO;
import com.co.kc.shortening.web.common.constants.ParamsConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author kc
 */
public class JwtPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final TokenClient tokenClient;

    public JwtPreAuthenticatedProcessingFilter(AuthenticationManager authenticationManager, TokenClient tokenClient) {
        super.setAuthenticationManager(authenticationManager);
        this.tokenClient = tokenClient;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ParamsConstants.TOKEN))
                .map(tokenClient::parse)
                .map(TokenDTO::getUserId)
                .orElse(null);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return request.getHeader(ParamsConstants.TOKEN);
    }
}
