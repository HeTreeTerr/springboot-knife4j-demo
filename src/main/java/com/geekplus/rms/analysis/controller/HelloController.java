package com.geekplus.rms.analysis.controller;

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
import com.geekplus.rms.analysis.service.impl.CommandServiceImpl;

@RestController
@RequestMapping("/hello")
@Api(tags = "首页")
public class HelloController {

    @Autowired
    private CommandServiceImpl commandService;

    /**
     * 默认（无注解）
     * @return
     */
    @GetMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /**
     * GET 单参
     * @param cmd
     * @return
     */
    @ApiOperation(value = "execCmd",notes = "执行命令")
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
    @ApiOperation(value = "sayHi",notes="如来，到底来没来")
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
    @ApiOperation(value = "uploadFile", notes = "文件上传")
    @PostMapping("/uploadFile")
    public BaseResult<String> uploadFile(@ApiParam(name = "myfile",value = "文件对象",required = true) @RequestParam(name = "myfile") MultipartFile file) {

        return new BaseResult("上传成功:" + file.getOriginalFilename());
    }
}
