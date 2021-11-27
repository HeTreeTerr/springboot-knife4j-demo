package com.geekplus.rms.analysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geekplus.rms.analysis.base.BaseResult;
import com.geekplus.rms.analysis.service.FileUploadService;
import com.geekplus.rms.analysis.service.impl.CommandServiceImpl;

@RestController
@RequestMapping("/hello")
@Api(tags = "首页")
public class HelloController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CommandServiceImpl commandService;

    @GetMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "test")
    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@RequestParam(value = "name")String name){
        return ResponseEntity.ok("Hi:"+name);
    }

    @ApiOperation(value = "uploadFile", notes = "uploadFile")
    @PostMapping("/uploadFile")
    public BaseResult uploadFile(@RequestParam(required = true,name = "myfile") MultipartFile file) {
        String uploadFile = fileUploadService.uploadFile(file);
        return new BaseResult("上传成功:"+uploadFile);
    }


    @ApiImplicitParam(name = "cmd",value = "cmd",required = true)
    @ApiOperation(value = "execCmd")
    @GetMapping("/execCmd")
    public ResponseEntity<String> execCmd(@RequestParam(value = "cmd")String cmd){

        String result = commandService.executeCmd(cmd);

        return ResponseEntity.ok(result);
    }

}
