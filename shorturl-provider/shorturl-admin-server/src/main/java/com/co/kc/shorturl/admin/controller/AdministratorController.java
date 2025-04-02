package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.domain.SecurityAuth;
import com.co.kc.shorturl.admin.model.dto.request.*;
import com.co.kc.shorturl.admin.model.dto.response.AdministratorDetailDTO;
import com.co.kc.shorturl.admin.model.dto.response.AdministratorSignInDTO;
import com.co.kc.shorturl.admin.model.dto.response.AdministratorListDTO;
import com.co.kc.shorturl.admin.security.annotation.Auth;
import com.co.kc.shorturl.admin.security.authentication.holder.AdministratorHolder;
import com.co.kc.shorturl.admin.service.AdministratorBizService;
import com.co.kc.shorturl.common.model.io.PagingResult;
import com.co.kc.shorturl.repository.dao.AdministratorRepository;
import com.co.kc.shorturl.repository.po.entity.Administrator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorBizService administratorBizService;
    @Autowired
    private AdministratorRepository administratorRepository;

    @ApiOperation(value = "登入")
    @PostMapping(value = "/v1/signIn")
    public AdministratorSignInDTO signIn(@RequestBody @Validated AdministratorSignInRequest request) {
        Long administratorId = administratorBizService.getAdministratorIdIfCheckPass(request.getAccount(), request.getPassword());
        String authToken = new SecurityAuth(String.valueOf(administratorId)).echoToken();
        return new AdministratorSignInDTO(authToken);
    }

    @Auth
    @ApiOperation(value = "登出")
    @PostMapping(value = "/v1/signOut")
    public void signOut() {
    }

    @Auth
    @ApiOperation(value = "获取管理者信息")
    @GetMapping(value = "/v1/administratorDetail")
    public AdministratorDetailDTO getAdministratorDetail() {
        String administratorId = AdministratorHolder.getAdministratorId();
        String administratorName = AdministratorHolder.getAdministratorName();
        return new AdministratorDetailDTO(administratorId, administratorName);
    }

    @Auth
    @ApiOperation(value = "获取管理者列表")
    @GetMapping(value = "/v1/administratorList")
    public PagingResult<AdministratorListDTO> getAdministratorList(@ModelAttribute @Validated AdministratorsGetRequest request) {
        return administratorBizService.getAdministratorList(request.getAccount(), request.getEmail(), request.getUsername(), request.getPaging());
    }

    @Auth
    @ApiOperation(value = "添加管理者")
    @PostMapping(value = "/v1/addAdministrator")
    public void addAdministrator(@RequestBody @Validated AdministratorAddRequest request) {
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(request, administrator);
        administratorRepository.save(administrator);
    }

    @Auth
    @ApiOperation(value = "更新管理者")
    @PostMapping(value = "/v1/updateAdministrator")
    public void updateAdministrator(@RequestBody @Validated AdministratorUpdateRequest request) {
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(request, administrator);
        administratorRepository.updateById(administrator);
    }

    @Auth
    @ApiOperation(value = "删除管理者")
    @PostMapping(value = "/v1/removeAdministrator")
    public void removeAdministrator(@RequestBody @Validated AdministratorRemoveRequest request) {
        administratorRepository.removeById(request.getId());
    }
}
