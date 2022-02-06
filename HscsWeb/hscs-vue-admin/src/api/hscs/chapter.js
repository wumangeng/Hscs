import request from '@/utils/request'

export default {
  // 根据章节id获取章节和小节数据
  getAllChapterVideo(courseId) {
    return request({
      url: '/edu/chapter/getAllChapterVideo/'+courseId,
      method: 'get'
    })
  },
  //添加章节
  addChapter(chapter){
    return request({
      url: '/edu/chapter/addChapter',
      method: 'post',
      data: chapter
    })
  },
  // 根据id查询章节
   getChapter(chapterId) {
    return request({
      url: '/edu/chapter/getChapterInfo/'+chapterId,
      method: 'get'
    })
  },
  // 修改章节信息
  updateChapter(chapter){
    return request({
      url: '/edu/chapter/updateChapter',
      method: 'post',
      data: chapter
    })
  },
  //删除章节信息
  deleteChapter(chapterId){
    return request({
      url: '/edu/chapter/'+chapterId,
      method: 'delete'
    })
  }
}
