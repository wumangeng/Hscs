import request from '@/utils/request'
export default {
  //分页查询讲师的方法
  getTeacherPageList(page,limit) {
    return request({
      url: `/edu/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post',
    })
  },

  //讲师详情的方法
  getTeacherDetailInfo(teacherId) {
    return request({
      url: `/edu/teacherfront/getTeacherFrontInfo/${teacherId}`,
      method: 'get',
    })
  },
}
