package com.hscs.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * excel数据分类的实体类
 *
 * @author ：Carina
 * @method : ExcelSubjectData
 * @date : 2020/12/27 20:39
 */
@Data
public class ExcelSubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
