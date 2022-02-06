package com.hscs.entity.excel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类用于返回树形数据封装一级分类
 *
 * @author ：Carina
 * @method : OneSubject
 * @date : 2020/12/29 21:48
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    // 一级分类中含有多个二级分类
    private List<TwoSubject> children=new ArrayList<>();
}
