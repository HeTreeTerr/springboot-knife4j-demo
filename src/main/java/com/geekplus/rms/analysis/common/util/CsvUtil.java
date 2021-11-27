package com.geekplus.rms.analysis.common.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);


    public static void writeCSV(List<Map> dataList,String csvFilePath) {
        // 定义一个CSV路径
        try {
            // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));
            // 写表头
            String[] csvHeaders = { "name" , "城市" , "cityname" , "adname" , "地址" , "经纬度"};

            csvWriter.writeRecord(csvHeaders);

            // 写内容
            for (int i = 0; i < dataList.size(); i++) {
                Map dataMap = dataList.get(i);

                String shopName = dataMap.get("name")==null? "":dataMap.get("name").toString();
                String address = dataMap.get("address")==null? "":dataMap.get("address").toString();
                String location = dataMap.get("location")==null? "":dataMap.get("location").toString();

                String id = dataMap.get("id")==null? "":dataMap.get("id").toString();
                String pname =  dataMap.get("pname")==null ? "":dataMap.get("pname").toString();
                String cityname = dataMap.get("cityname")==null ? "":dataMap.get("cityname").toString();
                String adname = dataMap.get("adname")==null ? "":dataMap.get("adname").toString();

                String[] csvContent = {shopName, address, location, id,pname, cityname ,adname };
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
            System.out.println("--------CSV文件已经写入--------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //ssid  与 shopName匹配
    public static void writeCSV(String[] headers,String[] propertis,List<Map<String,String>> dataList,String csvFilePath) {
        // 定义一个CSV路径
        try {
            // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("utf-8"));

            // 写表头
            csvWriter.writeRecord(headers);

            // 写内容
            for (int i = 0; i < dataList.size(); i++) {
                Map dataMap = dataList.get(i);
                String[] csvContent = new String[headers.length];

                for(int j=0;j<propertis.length;j++){
                    String property = propertis[j];
                    String value = dataMap.get(property)==null? "":dataMap.get(property).toString();
                    csvContent[j] = value;
                }
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
            System.out.println("--------CSV文件已经写入--------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeCSV(String[] headers, String[] propertis, JSONArray dataList, String csvFilePath) {
        // 定义一个CSV路径
        try {
            // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));

            // 写表头
            csvWriter.writeRecord(headers);

            // 写内容
            for (int i = 0; i < dataList.size(); i++) {
                //Map dataMap = dataList.get(i);

                JSONObject dataMap = (JSONObject)dataList.get(i);

                String[] csvContent = new String[headers.length];

                for(int j=0;j<propertis.length;j++){
                    String property = propertis[j];
                    String value = dataMap.get(property)==null? "":dataMap.get(property).toString();

                    csvContent[j] = value;
                }
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
            System.out.println("--------CSV文件已经写入--------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList readCSV() {
        //用来放店铺名
        ArrayList shopNameList = new ArrayList();
        try {
            // 用来保存数据
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // 定义一个CSV路径
            String csvFilePath = "D:\\ssidShop匹配\\海岸城购物中心.csv";
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("gbk"));
            // 跳过表头 如果需要表头的话，这句可以忽略
            reader.readHeaders();
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                //System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

            // 遍历读取的CSV文件
            for (int row = 0; row < csvFileList.size(); row++) {
                // 取得第row行第0列的数据
                String cell = csvFileList.get(row)[0];
                shopNameList.add(cell);
               // System.out.println("------------>"+cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(shopNameList.toString());
        return shopNameList;
    }


    public static ArrayList readCSV(String path) {
        //用来放店铺名
        ArrayList ssidList = new ArrayList();
        //用来存放范围
       // ArrayList rangeList = new ArrayList();
        //用来存放店名和范围

        //HashMap ssidRangeMap = new HashMap();
        try {
            // 用来保存数据
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // 定义一个CSV路径
            String csvFilePath = path;
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
            // 跳过表头 如果需要表头的话，这句可以忽略
            reader.readHeaders();
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                //System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

            // 遍历读取的CSV文件
            for (int row = 0; row < csvFileList.size(); row++) {
                // 取得第row行第0列的数据
                String cell = csvFileList.get(row)[0];
             // String rangecell = csvFileList.get(row)[4];
                ssidList.add(cell);
               //rangeList.add(rangecell);
                // System.out.println("------------>"+cell);
            }

            //ssidRangeMap.put("ssidList",ssidList);
            //ssidRangeMap.put("rangeList",rangeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // System.out.println(ssidList.toString());
        return ssidList;
    }


    public static ArrayList<Map<String,String>> readCSV(String columns[], String path) {
        //用来放店铺名
        ArrayList<Map<String,String>> ssidList = new ArrayList();
        //用来存放范围
        // ArrayList rangeList = new ArrayList();
        //用来存放店名和范围

        //HashMap ssidRangeMap = new HashMap();
        try {
            // 用来保存数据
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // 定义一个CSV路径
            String csvFilePath = path;
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
            reader.setSafetySwitch(false); //设置SafetySwitch为false,支持读取十万行以上数据.有内存溢出风险
            // 跳过表头 如果需要表头的话，这句可以忽略
            reader.readHeaders();
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                //System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

            // 遍历读取的CSV文件
            for (int row = 0; row < csvFileList.size(); row++) {
                // 取得第row行第0列的数据
                Map<String,String> map = new LinkedHashMap<String,String>();

                for (int j=0;j<columns.length;j++){
                    String cellData = csvFileList.get(row)[j];
                    if("shopName".equals(columns[j]) && (StringUtils.isBlank(cellData) || "null".equals(cellData))){
                        continue;
                    };
                    map.put(columns[j], cellData);
                }
                ssidList.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ssidList;
    }

}
