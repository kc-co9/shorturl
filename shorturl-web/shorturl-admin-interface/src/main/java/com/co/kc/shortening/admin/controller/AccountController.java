package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.model.response.AdministratorDetailVO;
import com.co.kc.shortening.admin.security.authentication.holder.AdministratorHolder;
import com.co.kc.shortening.application.model.cqrs.command.user.SignInCommand;
import com.co.kc.shortening.application.model.cqrs.command.user.SignOutCommand;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.admin.model.request.AdministratorSignInRequest;
import com.co.kc.shortening.admin.model.response.AdministratorSignInVO;
import com.co.kc.shortening.application.annotation.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@Api(tags = "帐号路由")
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final UserAppService userAppService;

    @ApiOperation(value = "登入")
    @PostMapping(value = "/v1/signIn")
    public AdministratorSignInVO signIn(@RequestBody @Validated AdministratorSignInRequest request) {
        SignInCommand command = new SignInCommand(request.getEmail(), request.getPassword());
        SignInDTO signInDTO = userAppService.signIn(command);
        return new AdministratorSignInVO(signInDTO.getToken());
    }

    @Auth
    @ApiOperation(value = "登出")
    @PostMapping(value = "/v1/signOut")
    public void signOut() {
        Long administratorId = AdministratorHolder.getAdministratorId();
        SignOutCommand signOutCommand = new SignOutCommand(administratorId);
        userAppService.signOut(signOutCommand);
    }

    @Auth
    @ApiOperation(value = "帐号详情")
    @GetMapping(value = "/v1/accountDetail")
    public AdministratorDetailVO accountDetail() {
        Long administratorId = AdministratorHolder.getAdministratorId();
        UserDetailQuery userDetailQuery = new UserDetailQuery(administratorId);
        UserDetailDTO userDetailDTO = userAppService.userDetail(userDetailQuery);
        return new AdministratorDetailVO(userDetailDTO.getUserId(), userDetailDTO.getUserName());
    }
}
