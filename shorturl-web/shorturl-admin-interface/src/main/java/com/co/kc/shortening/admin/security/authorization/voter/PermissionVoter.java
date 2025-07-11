package com.co.kc.shortening.admin.security.authorization.voter;

import com.co.kc.shortening.admin.security.authorization.configattribute.PermissionConfigAttribute;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author kcl.co
 * @since 2022/02/19
 */
public class PermissionVoter implements AccessDecisionVoter<MethodInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof PermissionConfigAttribute;
    }

    @Override
    public int vote(Authentication authentication, MethodInvocation object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return AccessDecisionVoter.ACCESS_DENIED;
        }
        int result = AccessDecisionVoter.ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = AccessDecisionVoter.ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return AccessDecisionVoter.ACCESS_GRANTED;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }
}
