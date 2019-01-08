package com.trotyzyq.common.util;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

/**
 * IO工具类
 * @author zyq
 */
public class IOUtil {

    /**
     * 读取文本内容
     * @param file
     * @return String 文件
     * @throws IOException
     */
    public static String getFileString(File file)  {
        try {
            FileInputStream  fileInputStream=new FileInputStream(file);
            List<String> stringList=IOUtils.readLines(fileInputStream);
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<stringList.size();i++){
                stringBuffer.append(stringList.get(i)+"\r\n");
            }
            IOUtils.closeQuietly(fileInputStream);
            return stringBuffer.toString();
        }catch (Exception e){
            return null;
        }

    }
    /**
     * 读取文本内容
     * @param filePath 字符串
     * @return String
     * @throws IOException
     */
    public static String getFileString(String filePath){
        File file = new File(filePath);
        return getFileString(file);
    }

    /**
     * 字符串写入到文件
     * @param str 需要写入的字符串
     * @param fileName 需要写入进去的文件名称（包含路径）
     * @return
     */
    public static boolean writeStringToFile(String str,String fileName) {
        try {
            File file = new File(fileName);
            OutputStream os = new FileOutputStream(file);
            IOUtils.write(str,os,"utf-8");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 字节写入到文件
     * @param str 需要写入的字节
     * @param fileName 需要写入进去的文件名称（包含路径）
     * @return
     */
    public static boolean writeStringToFile(byte[] str,String fileName) {
        try {
            File file = new File(fileName);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            IOUtils.write(str,writer,"utf-8");
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static void main(String[] args) throws IOException {
        String s=getFileString(new File("/Users/zyq/Desktop/a.txt"));
        System.out.println(s);
    }
}
