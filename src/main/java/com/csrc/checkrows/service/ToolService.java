package com.csrc.checkrows.service;

import com.csrc.checkrows.config.checkrows;
import com.csrc.checkrows.config.filepath;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class ToolService {


    /**
     * 核对行数
     */
    public List<checkrows> checkrowsservice(String txtfile,String excelfile){
        Map<String,filepath> txtfiles=getfilepath(txtfile);
        Map<String,filepath> excelfiles=getfilepath(excelfile);
        List<checkrows> re=new ArrayList<>();
        for(String excelname:excelfiles.keySet()){
            StringBuilder str =new StringBuilder();
           if(txtfiles.containsKey(excelname)){
               checkrows  res=new checkrows();
               int excelrows = readExcelFile(excelfiles.get(excelname).getFilepath());
               int txtrows = readExcelFile(excelfiles.get(excelname).getFilepath());
               res.setExcel(excelfiles.get(excelname).getFilename());
               res.setExcelrows(String.valueOf(excelrows));
               res.setTxt(txtfiles.get(excelname).getFilename());
               res.setTxtrows(String.valueOf(txtrows));
               if(excelrows==txtrows) {
                   res.setRe("校验成功");
               }else{
                   res.setRe("校验失败");
               }
               re.add(res);
           }
        }
        return re;
    }

    /**
     * 遍历文件
     */
    public Map<String,filepath> getfilepath(String path){
        Map<String, filepath> filenames =new TreeMap<>();
        File dir = new File(path);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if(fileName.endsWith(".txt")){
                    filepath filepath=new filepath();
                    filepath.setFilename(fileName.substring(0,fileName.indexOf(".")));
                    filepath.setFilepath(files[i].getAbsolutePath());
                    filenames.put(fileName.substring(14,19),filepath);
                }
                if(fileName.endsWith(".xls")){
                    filepath filepath=new filepath();
                    filepath.setFilename(fileName.substring(0,fileName.indexOf(".")));
                    filepath.setFilepath(files[i].getAbsolutePath());
                    filenames.put(fileName.substring(5,10),filepath);
                }
            }
        }
        return filenames;
    }

    /**
     * 读取excel文件，获取行数
     */
    public Integer readExcelFile(String fileName)  {
        HSSFWorkbook HssfWorkbook=null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            HssfWorkbook = new HSSFWorkbook(fileInputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (HssfWorkbook != null) {
            // 获取第一个Sheet 索引为０
            HSSFSheet sheet = HssfWorkbook.getSheetAt(0);
            // 获取总共有多少行
            return sheet.getLastRowNum();
        }
        return 0;
    }

    /**
     * 读取txt文件，获取行数
     */
    public Integer readTxtFile(String fileName)  {
        File file = new File(fileName);
        Integer rows=0;
        InputStream is=null;
        BufferedReader br=null;
        try{
         is = new FileInputStream(fileName);
         br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String temp;
        while ((temp = br.readLine()) != null) {
            rows++;
        }
            is.close();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rows;
    }





}
