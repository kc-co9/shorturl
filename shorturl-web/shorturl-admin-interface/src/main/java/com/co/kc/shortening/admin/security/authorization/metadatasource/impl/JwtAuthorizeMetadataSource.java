package com.co.kc.shortening.admin.security.authorization.metadatasource.impl;

import com.co.kc.shortening.admin.security.annotation.Auth;
import com.co.kc.shortening.admin.security.authorization.configattribute.AuthorizeConfigAttribute;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;

import java.util.Collection;
import java.util.Collections;

/**
 * @author kcl.co
 * @since 2022/02/19
 */
public class JwtAuthorizeMetadataSource extends SecuredAnnotationSecurityMetadataSource {
    public JwtAuthorizeMetadataSource() {
        super(new AuthorizeMetadataExtractor());
    }

    public JwtAuthorizeMetadataSource(AnnotationMetadataExtractor annotationMetadataExtractor) {
        super(annotationMetadataExtractor);
    }

    private static class AuthorizeMetadataExtractor implements AnnotationMetadataExtractor<Auth> {

        @Override
        public Collection<ConfigAttribute> extractAttributes(Auth auth) {
            return Collections.singletonList(new AuthorizeConfigAttribute());
        }
    }

}
