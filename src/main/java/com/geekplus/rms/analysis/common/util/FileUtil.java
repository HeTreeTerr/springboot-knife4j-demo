package com.geekplus.rms.analysis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static String reader(String filePath) {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getResourceAsStream(filePath);


            byte []cha = new byte[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder("");
            while((len=inputStream.read(cha))!=-1){
                sb.append(new String(cha,0,len));
            }
            String content= sb.toString();
            return content;
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public static String reader2(String filePath) {
        InputStreamReader input = null;
        try {
            File file = new File (filePath);
            input =new InputStreamReader(new FileInputStream(file),"UTF-8");
            char []cha = new char[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder("");
            while((len=input.read(cha))!=-1){
                sb.append(new String(cha,0,len));
            }
            String content= sb.toString();
            return content;
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }finally {
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    private static final String TAG = "MyFile";
    /**
     * deleteFile
     * @param filePath
     * @return boolean
     */
    public static boolean deleteFile(String filePath) {
        if(filePath == null) {
            return false;
        }

        File file = new File(filePath);
        if(file.exists()) {
            return file.delete();
        }
        logger.info(TAG,"file no exist filePath = " + filePath    );
        return false;
    }

    /**
     * deleteFileRecursively
     * @param filePath
     * @return List<String>
     */
    public static List<String> deleteFileRecursively(String  filePath) {

        List<String> result = null;
        if(filePath == null){
            return result;
        }

        File file = new File(filePath);
        if(!file.exists()){
            return result;
        }

        if (file.isDirectory()) {
            for (String fileName : file.list()) {
                File item = new File(file, fileName);
                if (item.isDirectory()) {
                    result.addAll(deleteFileRecursively(item.getPath()));
                } else {
                    if (!item.delete()) {
                        logger.info(TAG , " file's path is: " + item.getPath());
                        result.add(item.getPath());
                    }
                }
            }
            if (!file.delete()) {
                logger.info(TAG, "directories' path is: " + file.getPath());
                result.add(file.getPath());
            }

        } else {
            if (!file.delete()) {
                logger.info(TAG, " its path is: " +  file.getPath());
                result.add(file.getPath());
            }
        }
        return result;
    }

    /**
     * copyMvFile
     * @param fromFile
     * @param toFile
     * @param action  0 移动  1 复制
     * @return List<String>
     */
    public static List<String> copyMvFile(List<String> fromFile,List<String> toFile,int action) {
        List<String> result = null;
        if (fromFile.isEmpty() || toFile.isEmpty() || (fromFile.size() == toFile.size() ) ) {
            return result;
        }

        for (int i = 0 ; i < fromFile.size(); i++) {
            String fFileName = fromFile.get(i);
            String tFileName = toFile.get(i);

            if (!copyMvFileDir(fFileName,tFileName,action)) {
                result.add(fFileName);
            }
        }
        return result;
    }

    /**
     * copyMvFileDir
     * @param fromFileDir
     * @param toFileDir
     * @param action  0 移动  1 复制
     * @return boolean
     */
    public static boolean copyMvFileDir(String fromFileDir,String toFileDir,int action) {

        if (fromFileDir == null || toFileDir == null ) {
            return false;
        }

        File fFileDir = new File(fromFileDir);

        if (fFileDir.isDirectory()) {
            File tFileDir = new File(toFileDir);

            if (!tFileDir.exists()) {
                tFileDir.mkdirs();
            }

            for (String fileName : fFileDir.list()) {
                File item = new File(fFileDir, fileName);
                if (item.isDirectory()) {
                    copyMvFileDir(item.getPath() + "/",toFileDir + item.getName() + "/",action);
                } else {
                    if (!CopyMvFile(item.getPath(),toFileDir + item.getName(),action )) {
                        logger.info(TAG , " file's path is: " + item.getPath());
                        return false;
                    }
                }
            }
        } else {
            if (!CopyMvFile(fromFileDir,toFileDir + fFileDir.getName(),action )) {
                logger.info(TAG, " its path is: " +  fFileDir.getPath());
                return false;
            }
        }
        return true;
    }

    /**
     * CopyMvFile
     * @param fromFile
     * @param toFile
     * @param action  0 移动  1 复制
     * @return boolean
     */
    public static boolean CopyMvFile(String fromFile, String toFile,int action ) {

        if (fromFile == null || toFile == null){
            return false;
        }

        File fFile = new File(fromFile);
        if (!fFile.exists()) {
            return false;
        }


        boolean resultCopy = false;
        boolean resultDel = true;

        if (action == 0) {
            resultCopy = copyFileStream(fromFile,toFile);
            resultDel = fFile.delete();
        } else if (action == 1) {
            resultCopy =  copyFileStream(fromFile,toFile);
        }
        return (resultCopy & resultDel);
    }

    /**
     * copyFileStream
     * @param fromFile
     * @param toFile
     * @return boolean
     */
    public static boolean copyFileStream(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


}


