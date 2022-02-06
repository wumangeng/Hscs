package com.hscs.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author Painter
 * @Description TODO
 * @Date 2021/3/1 23:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Api("学生搜索VO类")
public class StudentSearchVO {

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "班主任名字")
    private String teacherName;

    @ApiModelProperty(value = "入学日期")
    private LocalDate beginDate;

}
