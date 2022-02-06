package com.hscs.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节
 * */
@Data
public class ChapterVO {
    private String id;

    private String title;

    /*存储小节*/
    private List<VideoVO> children=new ArrayList<>();
}
