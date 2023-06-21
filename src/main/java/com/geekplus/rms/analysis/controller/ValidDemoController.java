package com.geekplus.rms.analysis.controller;

import com.geekplus.rms.analysis.base.BaseResult;
import com.geekplus.rms.analysis.vo.FindInfoReqVO;
import com.geekplus.rms.analysis.vo.FindInfoResVO;
import com.geekplus.rms.analysis.vo.UpdateStatusReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * jsr-303参数校验 Controller
 * </p>
 *
 * @author Hss
 * @date 2023-06-21
 */
@RestController
@RequestMapping("/validDemo")
@Slf4j
@Api(value = "validDemo", tags = "validDemo")
public class ValidDemoController {

    /**
     * GET 实体类参数
     * @return
     */
    @ApiOperation(value = "demo4(GET 实体类参数)",notes="若至，到底至没至")
    @GetMapping("/demo4")
    public BaseResult<FindInfoResVO> findInfo(@Validated @ModelAttribute FindInfoReqVO findInfoReqVO){

        log.info("===============findInfoReqVO={}",findInfoReqVO);
        return new BaseResult(FindInfoResVO.builder()
                .id(0L)
                .name(findInfoReqVO.getName())
                .age(findInfoReqVO.getAge())
                .build());
    }

    /**
     * POST 表单-实体参数
     * @return
     */
    @ApiOperation(value = "demo6(POST 表单-实体参数)", notes = "状态修改")
    @PostMapping(value = "/demo6",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public BaseResult<Object> updateStatus(@Validated @ModelAttribute UpdateStatusReqVO updateStatusReqVO){

        log.info("===============updateStatusReqVO={}",updateStatusReqVO);
        return new BaseResult<>().success("操作成功！");
    }

    /**
     * POST json
     * @param updateStatusReqVO
     * @return
     */
    @ApiOperation(value = "demo7(POST json)", notes = "状态修改")
    @PostMapping("/demo7")
    public BaseResult<Object> editInfo(@Validated @RequestBody UpdateStatusReqVO updateStatusReqVO){

        log.info("===============updateStatusReqVO={}",updateStatusReqVO);
        return new BaseResult<>().success("操作成功！");
    }
}
