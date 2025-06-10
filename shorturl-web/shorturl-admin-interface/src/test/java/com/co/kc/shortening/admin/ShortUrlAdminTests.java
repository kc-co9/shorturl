package com.co.kc.shortening.admin;

import com.co.kc.shortening.admin.controller.AccountControllerTests;
import com.co.kc.shortening.admin.controller.AdministratorControllerTests;
import com.co.kc.shortening.admin.controller.BlocklistControllerTests;
import com.co.kc.shortening.admin.controller.ShorturlControllerTests;
import com.co.kc.shortening.admin.security.SecurityAuthenticationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountControllerTests.class,
        AdministratorControllerTests.class,
        BlocklistControllerTests.class,
        ShorturlControllerTests.class,
        SecurityAuthenticationTests.class,
})
public interface ShortUrlAdminTests {
}
