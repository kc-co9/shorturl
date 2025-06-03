package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.model.request.ShorturlAddRequest;
import com.co.kc.shortening.admin.model.request.ShorturlListRequest;
import com.co.kc.shortening.admin.model.request.ShorturlUpdateRequest;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlDTO;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.service.appservice.ShorturlAppService;
import com.co.kc.shortening.application.service.queryservice.ShorturlQueryService;
import com.co.kc.shortening.admin.assembler.ShorturlListVoAssembler;
import com.co.kc.shortening.admin.model.response.ShorturlAddVO;
import com.co.kc.shortening.admin.model.response.ShorturlListVO;
import com.co.kc.shortening.application.annotation.Auth;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.web.common.constants.enums.ShorturlFacadeStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@Api(tags = "短链路由")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shorturl")
public class ShorturlController {
    private final ShorturlAppService shorturlAppService;
    private final ShorturlQueryService shorturlQueryService;

    @Auth
    @GetMapping("/v1/shorturlList")
    @ApiOperation(value = "短链列表")
    public PagingResult<ShorturlListVO> shorturlList(@ModelAttribute @Validated ShorturlListRequest request) {
        ShorturlQuery query = new ShorturlQuery();
        query.setCode(request.getCode());
        query.setStatus(request.getStatus());
        query.setValidTimeStart(request.getValidTimeStart());
        query.setValidTimeEnd(request.getValidTimeEnd());
        query.setPageNo(request.getPageNo());
        query.setPageSize(request.getPageSize());
        PagingResult<ShorturlQueryDTO> pagingResult = shorturlQueryService.queryShorturl(query);
        return pagingResult.mapping(ShorturlListVoAssembler::shorturlQueryDTOToVO);
    }

    @Auth
    @PostMapping("/v1/addShorturl")
    @ApiOperation(value = "短链创建")
    public ShorturlAddVO addShorturl(@RequestBody @Validated ShorturlAddRequest request) {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(request.getRawLink());
        command.setStatus(ShorturlFacadeStatus.convert(request.getStatus()));
        command.setValidTimeStart(request.getValidTimeStart());
        command.setValidTimeEnd(request.getValidTimeEnd());
        ShorturlDTO shorturlDTO = shorturlAppService.add(command);
        return new ShorturlAddVO(shorturlDTO.getShorturl());
    }

    @Auth
    @PostMapping("/v1/updateShorturl")
    @ApiOperation(value = "短链更新")
    public void updateShorturl(@RequestBody @Validated ShorturlUpdateRequest request) {
        ShorturlUpdateCommand command = new ShorturlUpdateCommand();
        command.setShortId(request.getShortId());
        command.setStatus(ShorturlFacadeStatus.convert(request.getStatus()));
        command.setValidTimeStart(request.getValidStart());
        command.setValidTimeEnd(request.getValidEnd());
        shorturlAppService.update(command);
    }

}
