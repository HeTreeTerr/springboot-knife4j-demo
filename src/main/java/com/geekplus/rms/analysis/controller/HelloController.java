package com.geekplus.rms.analysis.controller;

import com.geekplus.rms.analysis.service.CommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geekplus.rms.analysis.base.BaseResult;

/**
 * 接口文档自动生成 Controller
 */
@RestController
@RequestMapping("/apiDemo")
@Api(value = "apiDemo", tags = "apiGenDemo")
public class HelloController {

    @Autowired
    private CommandService commandService;

    /**
     * GET 无参
     * @return
     */
    @ApiOperation(value = "test(GET 无参)",notes = "test")
    @GetMapping("/test")
    public BaseResult<String> index() {

        return new BaseResult("Greetings from Spring Boot!");
    }

    /**
     * GET 单参
     * @param cmd
     * @return
     */
    @ApiOperation(value = "execCmd(GET 单参)",notes = "执行命令")
    @GetMapping("/execCmd")
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
    @ApiOperation(value = "sayHi(GET 多参数)",notes="如来，到底来没来")
    @GetMapping("/sayHi")
    public BaseResult<String> sayHi(@ApiParam(name = "name",value = "名称",required = true) @RequestParam(value = "name")String name,
                                        @ApiParam(name = "age",value = "年龄",required = true) @RequestParam(value = "age")Integer age){
        return new BaseResult("Hi:" + name + "--" + age);
    }

    /**
     * POST form file
     * @param file
     * @return
     */
    @ApiOperation(value = "uploadFile(POST form file)", notes = "文件上传")
    @PostMapping("/uploadFile")
    public BaseResult<String> uploadFile(@ApiParam(name = "myfile",value = "文件对象",required = true) @RequestParam(name = "myfile") MultipartFile file) {

        return new BaseResult("上传成功:" + file.getOriginalFilename());
    }
}
