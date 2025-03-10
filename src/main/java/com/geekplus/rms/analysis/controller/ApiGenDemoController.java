package com.geekplus.rms.analysis.controller;

import com.geekplus.rms.analysis.service.CommandService;
import com.geekplus.rms.analysis.vo.FindInfoReqVO;
import com.geekplus.rms.analysis.vo.FindInfoResVO;
import com.geekplus.rms.analysis.vo.UpdateStatusReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.geekplus.rms.analysis.base.BaseResult;

/**
 * 接口文档自动生成 Controller
 */
@RestController
@RequestMapping("/apiDemo")
@Slf4j
@Api(value = "apiDemo", tags = "apiGenDemo")
public class ApiGenDemoController {

    @Autowired
    private CommandService commandService;

    /**
     * GET 无参
     * @return
     */
    @ApiOperation(value = "demo1(GET 无参)",notes = "demo1")
    @GetMapping("/demo1")
    public BaseResult<String> index() {

        return new BaseResult("Greetings from Spring Boot!");
    }

    /**
     * GET 单参
     * @param cmd
     * @return
     */
    @ApiOperation(value = "demo2(GET 单参)",notes = "执行命令")
    @GetMapping("/demo2")
    public BaseResult<String> execCmd(@ApiParam(name = "cmd",value = "命令",required = true) @RequestParam(value = "cmd")String cmd){

        String result = commandService.executeCmd(cmd);

        return new BaseResult("命令：" + result);
    }

    /**
     * GET 多参数
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value = "demo3(GET 多参数)",notes="如来，到底来没来")
    @GetMapping("/demo3")
    public BaseResult<String> sayHi(@ApiParam(name = "name",value = "名称",required = true) @RequestParam(value = "name")String name,
                                    @ApiParam(name = "age",value = "年龄",required = true) @RequestParam(value = "age")Integer age){
        return new BaseResult("Hi:" + name + "--" + age);
    }

    /**
     * GET 实体类参数
     * @return
     */
    @ApiOperation(value = "demo4(GET 实体类参数)",notes="若至，到底至没至")
    @GetMapping("/demo4")
    public BaseResult<FindInfoResVO> findInfo(@ModelAttribute FindInfoReqVO findInfoReqVO){

        log.info("===============findInfoReqVO={}",findInfoReqVO);
        return new BaseResult(FindInfoResVO.builder()
                .id(0L)
                .name(findInfoReqVO.getName())
                .age(findInfoReqVO.getAge())
                .build());
    }

    /**
     * POST 表单-多参
     * @param file
     * @return
     */
    @ApiOperation(value = "demo5(POST 表单-多参)", notes = "文件上传")
    @PostMapping(value = "/demo5",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResult<String> uploadFile(@ApiParam(name = "name",value = "名称",required = true) @RequestParam(value = "name")String name,
                                         @ApiParam(name = "myfile",value = "文件对象",required = true) @RequestParam(name = "myfile") MultipartFile file) {

        return new BaseResult("上传成功:" + file.getOriginalFilename() + "--" + name);
    }

    /**
     * POST 表单-实体参数
     * @return
     */
    @ApiOperation(value = "demo6(POST 表单-实体参数)", notes = "状态修改")
    @PostMapping(value = "/demo6",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResult<Object> updateStatus(@ModelAttribute UpdateStatusReqVO updateStatusReqVO){

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
    public BaseResult<Object> editInfo(@RequestBody UpdateStatusReqVO updateStatusReqVO){

        log.info("===============updateStatusReqVO={}",updateStatusReqVO);
        return new BaseResult<>().success("操作成功！");
    }
}
