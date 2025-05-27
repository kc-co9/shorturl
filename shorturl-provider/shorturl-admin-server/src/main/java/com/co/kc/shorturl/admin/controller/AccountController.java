package com.co.kc.shorturl.admin.controller;

import com.co.kc.shortening.application.model.cqrs.command.user.SignInCommand;
import com.co.kc.shortening.application.model.cqrs.command.user.SignOutCommand;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.service.appservice.UserAppService;
import com.co.kc.shorturl.admin.model.domain.SecurityAuth;
import com.co.kc.shorturl.admin.model.dto.request.AdministratorSignInRequest;
import com.co.kc.shorturl.admin.model.dto.response.AdministratorSignInVO;
import com.co.kc.shorturl.admin.security.annotation.Auth;
import com.co.kc.shorturl.admin.security.authentication.holder.AdministratorHolder;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kc
 */
@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final UserAppService userAppService;

    @ApiOperation(value = "登入")
    @PostMapping(value = "/v1/signIn")
    public AdministratorSignInVO signIn(@RequestBody @Validated AdministratorSignInRequest request) {
        SignInCommand command = new SignInCommand(request.getEmail(), request.getPassword());
        SignInDTO signInDTO = userAppService.signIn(command);
        String authToken = new SecurityAuth(String.valueOf(signInDTO.getUserId())).echoToken();
        return new AdministratorSignInVO(authToken);
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
    @PostMapping(value = "/v1/accountDetail")
    public void accountDetail() {
    }
}
