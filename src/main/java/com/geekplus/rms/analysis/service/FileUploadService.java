package com.geekplus.rms.analysis.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 动态配置规则，人群包与任务关联
 * 人群包设置优先级
 */
@Service
@Slf4j
public class FileUploadService {

    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Value("${rule.upload.path}")
    private String uploadPath;


    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) {

        //将现有规则加载出来

        //创建输入输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            //指定上传的位置为
            String path = uploadPath;
            //获取文件的输入流
            inputStream = file.getInputStream();
            //获取上传时的文件名
            String fileName = file.getOriginalFilename();
            /*if (!fileName.contains(".xls") && !fileName.contains(".xlsx")) {
                log.error(" fileName :{} is not excel ", fileName);
                return null;
            }*/

            //注意是路径+文件名
            File targetFile = new File(path + df.format(new Date()) + "_" + fileName);
            //如果之前的 String path = "d:/upload/" 没有在最后加 / ，那就要在 path 后面 + "/"

            //判断文件父目录是否存在
            if (!targetFile.getParentFile().exists()) {
                //不存在就创建一个
                targetFile.getParentFile().mkdir();
            }

            //获取文件的输出流
            outputStream = new FileOutputStream(targetFile);
            //最后使用资源访问器FileCopyUtils的copy方法拷贝文件
            FileCopyUtils.copy(inputStream, outputStream);


            //读取文件
            log.warn(" targetFile :" + targetFile.getPath());

            return targetFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //无论成功与否，都有关闭输入输出流
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
