package com.geekplus.rms.analysis.controller;

import com.geekplus.rms.analysis.base.BaseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * login - Controller
 * session特性探究
 * </p>
 *
 * @author Hss
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/loginDemo")
@Slf4j
@Api(value = "loginDemo", tags = "loginDemo")
public class LoginDemoController {

    @GetMapping("/genImageCode")
    public BaseResult<Object> genImageCode(String imageCode,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response){
        log.info("method={}","genImageCode");
        //获取session
        HttpSession httpSession = request.getSession();
        log.info("==========sessionId={},maxInactiveInterval={}",httpSession.getId(),httpSession.getMaxInactiveInterval());
        log.info("imageCode={}",imageCode);
        //存入imageCode
        httpSession.setAttribute("imageCode",imageCode);
        return new BaseResult().success("操作成功").successContent(imageCode);
    }

    @GetMapping("/login")
    public BaseResult<Object> login(HttpServletRequest request,
                                    HttpServletResponse response){
        log.info("method={}","login");
        //获取session
        HttpSession httpSession = request.getSession();
        log.info("==========sessionId={},maxInactiveInterval={}",httpSession.getId(),httpSession.getMaxInactiveInterval());
        //获取imageCode
        Object imageCodeObj = httpSession.getAttribute("imageCode");
        log.info("imageCode={}",ObjectUtils.isEmpty(imageCodeObj) ? null : imageCodeObj.toString());
        return new BaseResult().success("操作成功");
    }

    @GetMapping("/loginOut")
    public BaseResult<Object> loginOut(HttpServletRequest request,
                                    HttpServletResponse response){
        log.info("method={}","loginOut");
        //获取session
        HttpSession httpSession = request.getSession();
        log.info("==========sessionId={},maxInactiveInterval={}",httpSession.getId(),httpSession.getMaxInactiveInterval());
        //销毁session
        httpSession.invalidate();
        return new BaseResult().success("操作成功");
    }
}
