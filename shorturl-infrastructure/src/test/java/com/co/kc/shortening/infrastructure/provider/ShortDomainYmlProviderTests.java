package com.co.kc.shortening.infrastructure.provider;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class ShortDomainYmlProviderTests {
    @Autowired
    private ShortDomainYmlProvider shortDomainYmlProvider;

    @Test
    void testGetShortDomainPropertiesByYml() {
        Assertions.assertEquals(ShorturlFactory.testShortDomain, shortDomainYmlProvider.getDomain());
    }
}
