package com.hscs.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

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
 * @author testjava
 * @since 2020-12-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@ApiModel(value="HscsTeacher对象", description="")
public class HscsTeacher implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "教师表主键id")
        @TableId(value = "teacher_id", type = IdType.ID_WORKER_STR)
      private String teacherId;

      @ApiModelProperty(value = "关联用户表")
      private String userId;

      private String name;

      private String sex;

      @ApiModelProperty(value = "教师简介")
      private String intro;

      @ApiModelProperty(value = "教师资历")
      private String career;

      @ApiModelProperty(value = "头衔 0普通教师 1高级教师 2特级教师3首席教师")
      private Integer level;

      @ApiModelProperty(value = "教师头像")
      private String avatar;

      @ApiModelProperty(value = "逻辑删除，默认0删除1")
      @TableLogic  //做逻辑删除字段注解
      private Integer isDeleted;

      @ApiModelProperty(value = "创建时间")
      @TableField(fill = FieldFill.INSERT)
      private Date gmtCreate;

      @ApiModelProperty(value = "更新时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
      private Date gmtModified;

      private String phone;

      private String email;

      @ApiModelProperty(value = "教师编号")
      private String number;


}
