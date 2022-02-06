package com.hscs.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Painter
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_student")
@ApiModel(value="EduStudent对象", description="")
public class EduStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "家长姓名")
    @TableField("parent_name")
    private String parentName;

    @ApiModelProperty(value = "家长身份证号")
    @TableField("parent_idcard")
    private String parentIdcard;

    @ApiModelProperty(value = "民族")
    @TableField("nation")
    private String nation;

    @ApiModelProperty(value = "籍贯")
    @TableField("native_place")
    private String nativePlace;

    @ApiModelProperty(value = "政治面貌")
    @TableField("politic")
    private String politic;

    @ApiModelProperty(value = "家长电话号码")
    private String phone;

    @ApiModelProperty(value = "家庭地址")
    private String address;

    @ApiModelProperty(value = "入学日期")
    @TableField("begin_date")
    private LocalDate beginDate;

    @ApiModelProperty(value = "学号")
    @TableField("student_number")
    private String studentNumber;

    @ApiModelProperty(value = "班级名称")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "班主任名字")
    @TableField("teacher_name")
    private String teacherName;

    @ApiModelProperty(value = "班主任电话")
    @TableField("teacher_phone")
    private String teacherPhone;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
