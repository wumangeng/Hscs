package com.hscs.service;

import com.hscs.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
public interface EduCommentService extends IService<EduComment> {

    /**删除评论*/
    boolean deleteById(String commentId, String memberId);

    /**判断是否是该用户的评论*/
    boolean isComment(String commentId, String memberId);
}
