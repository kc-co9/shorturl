package com.co.kc.shortening.admin.security.authorization.metadatasource.impl;

import com.co.kc.shortening.admin.security.authorization.configattribute.PermissionConfigAttribute;
import com.co.kc.shortening.application.annotation.Permission;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author kcl.co
 * @since 2022/02/19
 */
public class JwtPermissionMetadataSource extends SecuredAnnotationSecurityMetadataSource {

    public JwtPermissionMetadataSource() {
        super(new PermissionMetadataExtractor());
    }

    public JwtPermissionMetadataSource(AnnotationMetadataExtractor annotationMetadataExtractor) {
        super(annotationMetadataExtractor);
    }

    private static class PermissionMetadataExtractor implements AnnotationMetadataExtractor<Permission> {

        @Override
        public Collection<ConfigAttribute> extractAttributes(Permission permission) {
            return Arrays.stream(permission.value())
                    .map(PermissionConfigAttribute::new)
                    .collect(Collectors.toList());
        }
    }
}
