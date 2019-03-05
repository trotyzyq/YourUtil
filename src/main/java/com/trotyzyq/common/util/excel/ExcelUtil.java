package com.trotyzyq.common.util.excel;

import com.trotyzyq.common.util.excel.bo.User;
import com.trotyzyq.common.util.excel.bo.ExcelBO;
import com.trotyzyq.common.util.excel.bo.SheetBO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作excel 工具类
 * @author zyq
 */
public class ExcelUtil {
//
//
//    /**
//     * 创建Excel
//     * @param excelBO excel实体类
//     * @param classz  数据实体类
//     * @param path  保存路径
//     * @param <T>
//     * @return
//     */
//    public static <T> String createExcel(ExcelBO excelBO,Class<T> classz, String path){
//        /** 1 创建workbook **/
//        HSSFWorkbook wb = new HSSFWorkbook();
//
//            /** 1.1 创建sheet **/
//        List<SheetBO> sheetList =  excelBO.getSheetBOList();
//        sheetList.forEach(sheetBO ->{
//            String sheetName = sheetBO.getSheetName();
//            HSSFSheet sheet = wb.createSheet(sheetName);
//
//                /** 1.1.1 创建行row:添加表头0行 **/
//            HSSFRow rowHead = sheet.createRow(0);
//            HSSFCellStyle style = wb.createCellStyle();
//            //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中
//
//
//                    /** 1.1.1.1 创建单元格 **/
//            Field[] fields = classz.getDeclaredFields();
//            for(int i = 0 ; i < fields.length; i++){
//                Field field = fields[i];
//                fields[i].setAccessible(true);
//                HSSFCell cell = rowHead.createCell(i); //第一行 第一个单元格
//                cell.setCellValue(field.getName());
//                cell.setCellStyle(style);
//            }
//            /** 插入数据**/
//            List dataList = sheetBO.getList();
//            for(int i = 0; i< dataList.size();i++){
//                HSSFRow rowData = sheet.createRow(i + 1);
//                Object object = dataList.get(i);
//
//                for(int j = 0 ; j < fields.length; j++){
//                    Field field = fields[j];
//                    HSSFCell cell = rowData.createCell(j);
//                    String data = "";
//                    try {
//                        data = (String)fields[j].get(object);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    cell.setCellValue(data);
//                    cell.setCellStyle(style);
//                }
//            }
//
//        });
//
//
//        //第六步将生成excel文件保存到指定路径下
//        try {
//            FileOutputStream fout = new FileOutputStream(path);
//            wb.write(fout);
//            fout.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "success";
//    }
//
//    /**
//     *
//     * @param file  上传的文件
//     * @param classz 类型
//     * @return List<class>
//     */
//    public static ExcelBO readExcel(MultipartFile file, Class<T> classz){
//        /** 检测文件**/
//        boolean validateSuccess = validateExcel(file);
//        if (! validateSuccess){
//            return null;
//        }
//
//        /** 判断文件的类型，是2003还是2007 */
//        String fileName = file.getOriginalFilename();
//        boolean isExcel2003 = true;
//        if (isExcel2007(fileName)) {
//            isExcel2003 = false;
//        }
//        /** 读取文件**/
//        ExcelBO excelBO = null;
//        try {
//            InputStream inputStream = file.getInputStream();
//            excelBO = read(file.getInputStream(), isExcel2003, classz);
//            inputStream.close();
//            return excelBO;
//        }catch (Exception e){
//            return null;
//        }
//    }
//
//    /**
//     *
//     * @param stream  输入流
//     * @param isExcel2003  是否是2003版本的
//     * @return
//     */
//    private static  <T> ExcelBO read(InputStream stream, boolean isExcel2003, Class<T> classz) throws IOException, NoSuchFieldException, IllegalAccessException, InstantiationException {
//        Workbook wb = null;
//        if (isExcel2003) {
//            wb = new HSSFWorkbook(stream);
//        } else {
//            wb = new XSSFWorkbook(stream);
//        }
//        /** 得到第一个shell */
//        Sheet sheet = wb.getSheetAt(0);
//        System.out.println(wb.getActiveSheetIndex());
//
//        /** 得到Excel的行数和列数 */
//        int totalRows = sheet.getPhysicalNumberOfRows();
//        int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
//
//        /** 遍历第一行获取字段名 **/
//        String[] cellFields = new String[totalCells];
//        Row rowHead = sheet.getRow(0);
//        for(int i = 0; i< totalCells;i++){
//            cellFields[i] = rowHead.getCell(i).getStringCellValue();
//        }
//        List rowList = new ArrayList();
//        /** 循环Excel的行 */
//        for (int r = 1; r < totalRows; r++) {
//            Row row = sheet.getRow(r);
//            Object object = classz.newInstance();
//            /** 循环遍历列**/
//            for(int i = 0 ;i < cellFields.length; i++){
//                String fieldName = cellFields[i];
//                Field field = object.getClass().getDeclaredField(fieldName);
//                field.setAccessible(true);
//                field.set(object,row.getCell(i).getStringCellValue());
//            }
//            rowList.add(object);
//        }
//
//        List<SheetBO> sheetBOList = new ArrayList<>();
//        SheetBO sheetBO = new SheetBO();
//        sheetBO.setList(rowList);
//        sheetBO.setSheetName(sheet.getSheetName());
//        sheetBOList.add(sheetBO);
//        ExcelBO excelBO = new ExcelBO(sheetBOList);
//        return excelBO;
//    }
//
//
//    /**
//     * 验证excel文件
//     * @param file excel文件
//     * @return
//     */
//    public static boolean validateExcel(MultipartFile file) {
//        if(file.isEmpty()){
//            return false;
//        }
//
//        String fileName = file.getOriginalFilename();
//        /** 检查文件名是否为空或者是否是Excel格式的文件 */
//        if (fileName == null || !(isExcel2003(fileName) || isExcel2007(fileName))) {
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//
//    /**
//     * 检测是否是excel 2003
//     * @param filePath
//     * @return
//     */
//    public static boolean isExcel2003(String filePath)
//    {
//        return filePath.matches("^.+\\.(?i)(xls)$");
//    }
//
//    /**
//     * 检测是否是excel 2007
//     * @param filePath
//     * @return
//     */
//    public static boolean isExcel2007(String filePath)
//    {
//        return filePath.matches("^.+\\.(?i)(xlsx)$");
//    }
//
//
//
//    public static void main(String[] args) throws FileNotFoundException {
////        ExcelBO excelBO = new ExcelBO();
////        SheetBO sheetBO = new SheetBO();
////        sheetBO.setSheetName("测试1");
////        User user = new User();
////        user.set年龄("123");
////        user.set姓名("章御强");
////        ArrayList arrayList = new ArrayList();
////        arrayList.add(user);
////        sheetBO.setList(arrayList);
////        List sheetBoList = new ArrayList();
////        sheetBoList.add(sheetBO);
////        excelBO.setSheetBOList(sheetBoList);
////        createExcel(excelBO,User.class, "path");
//        File file = new File("/Users/zyq/desktop/x.xls");
//        FileInputStream fileInputStream = new FileInputStream(file);
//
//        MultipartFile multipartFile =new MultipartFile() {
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public String getOriginalFilename() {
//                return null;
//            }
//
//            @Override
//            public String getContentType() {
//                return null;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public long getSize() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBytes() throws IOException {
//                return new byte[0];
//            }
//
//            @Override
//            public InputStream getInputStream() throws IOException {
//                return fileInputStream;
//            }
//
//            @Override
//            public void transferTo(File file) throws IOException, IllegalStateException {
//
//            }
//        };
//        try {
//            read(fileInputStream,true, User.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
