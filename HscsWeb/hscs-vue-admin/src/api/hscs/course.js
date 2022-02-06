import request from '@/utils/request'
export default {
    //1 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/addCourseInfo',
            method: 'post',
            data: courseInfo
          })
    },
    //2 查询所有讲师
    getListTeacher() {
        return request({
            url: '/edu/teacher/findAll',
            method: 'get'
          })
    },
    //根据课程id查询课程基本信息
    getCourseInfoId(courseId) {
        return request({
            url: '/edu/course/getCourseInfo/'+courseId,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
          })
    },
    //课程确认信息显示
    getPublihCourseInfo(id) {
        return request({
            url: '/edu/course/getPublishCourseInfo/'+id,
            method: 'get'
          })
    },
    //课程最终发布
    publihCourse(id) {
        return request({
            url: '/edu/course/publishCourse/'+id,
            method: 'post'
          })
    },

    // 课程列表
    getListCourse(current, limit) {
        return request({
            url: `/edu/course/list/${current}/${limit}`,
            method: 'get'
          })
    },
    //条件分页查询课程
    getPageCourseCondition(current, limit,courseQuery){
        return request({
            url: `/edu/course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },
    // 删除课程
    deleteCourse(courseId) {
        return request({
            url: `/edu/course/`+courseId,
            method: 'delete'
          })
    },

}
