package com.trotyzyq.common.util.excel.bo;

import java.util.List;

/**
 * Excel创建实体类
 *
 */
public class ExcelBO {

    /**sheet集合 **/
    private List<SheetBO> sheetBOList;

    public List<SheetBO> getSheetBOList() {
        return sheetBOList;
    }

    public void setSheetBOList(List<SheetBO> sheetBOList) {
        this.sheetBOList = sheetBOList;
    }

    public ExcelBO(List<SheetBO> sheetBOList) {
        this.sheetBOList = sheetBOList;
    }
}
