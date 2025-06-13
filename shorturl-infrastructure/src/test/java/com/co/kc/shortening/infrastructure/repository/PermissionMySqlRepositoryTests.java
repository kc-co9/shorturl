package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class PermissionMySqlRepositoryTests {
    @Autowired
    private PermissionMySqlRepository permissionMySqlRepository;

    @Test
    void testFindPermission() {
        Assertions.assertTrue(CollectionUtils.isEmpty(permissionMySqlRepository.find(Collections.emptyList())));
    }
}
