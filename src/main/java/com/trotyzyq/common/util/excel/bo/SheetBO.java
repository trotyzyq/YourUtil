package com.trotyzyq.common.util.excel.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * sheet bo
 * @author zyq
 */
public class SheetBO {
    /** sheet 名称**/
    private String sheetName;
    /** 数据内容 **/
    private List list;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
