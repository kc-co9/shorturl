package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.model.request.*;
import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.appservice.UserAppService;
import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.service.queryservice.UserQueryService;
import com.co.kc.shortening.admin.assembler.AdministratorListVoAssembler;
import com.co.kc.shortening.admin.model.response.AdministratorDetailVO;
import com.co.kc.shortening.admin.model.response.AdministratorListVO;
import com.co.kc.shortening.application.annotation.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@Api(tags = "管理员路由")
@RestController
@RequiredArgsConstructor
@RequestMapping("/administrator")
public class AdministratorController {
    private final UserAppService userAppService;
    private final UserQueryService userQueryService;

    @Auth
    @ApiOperation(value = "获取管理者列表")
    @GetMapping(value = "/v1/administratorList")
    public PagingResult<AdministratorListVO> getAdministratorList(@ModelAttribute @Validated AdministratorListGetRequest request) {
        UserQuery query = new UserQuery();
        query.setEmail(request.getEmail());
        query.setUsername(request.getUsername());
        query.setPageNo(request.getPageNo());
        query.setPageSize(request.getPageSize());
        PagingResult<UserQueryDTO> pagingResult = userQueryService.queryUser(query);
        return pagingResult.mapping(AdministratorListVoAssembler::userQueryDTOToVO);
    }

    @Auth
    @ApiOperation(value = "获取管理者信息")
    @GetMapping(value = "/v1/administratorDetail")
    public AdministratorDetailVO getAdministratorDetail(@ModelAttribute @Validated AdministratorDetailGetRequest request) {
        UserDetailQuery userDetailQuery = new UserDetailQuery(request.getAdministratorId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(userDetailQuery);
        return new AdministratorDetailVO(userDetailDTO.getUserId(), userDetailDTO.getUserName());
    }

    @Auth
    @ApiOperation(value = "添加管理者")
    @PostMapping(value = "/v1/addAdministrator")
    public void addAdministrator(@RequestBody @Validated AdministratorAddRequest request) {
        UserAddCommand command = new UserAddCommand(request.getEmail(), request.getUsername(), request.getPassword());
        userAppService.addUser(command);
    }

    @Auth
    @ApiOperation(value = "更新管理者")
    @PostMapping(value = "/v1/updateAdministrator")
    public void updateAdministrator(@RequestBody @Validated AdministratorUpdateRequest request) {
        UserUpdateCommand command = new UserUpdateCommand(
                request.getAdministratorId(), request.getEmail(), request.getUsername(), request.getPassword());
        userAppService.updateUser(command);
    }

    @Auth
    @ApiOperation(value = "删除管理者")
    @PostMapping(value = "/v1/removeAdministrator")
    public void removeAdministrator(@RequestBody @Validated AdministratorRemoveRequest request) {
        UserRemoveCommand command = new UserRemoveCommand(request.getAdministratorId());
        userAppService.removeUser(command);
    }
}
