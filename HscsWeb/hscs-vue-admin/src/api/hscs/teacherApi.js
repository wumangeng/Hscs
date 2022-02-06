import request from '@/utils/request'

export default {
  getTeacherList(current, limit) {
    //分页查询所有教师
    return request({
      url: `/edu/teacher/getTeacherList/${current}/${limit}`,
      method: 'get'
    }) 
  },
   // 条件分页查询 teacherQuery为查询条件
   getTeacherListPage(current, limit, teacherQuery) {
    return request({
      url: `/edu/teacher/getTeacherListCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery // data把对象转换成json传递到接口里
    })
  },
  //删除教师
  deleteTeacher(teacherId){
    return request({
      url: `/edu/teacher/${teacherId}`,
      method: 'delete'
    })
  },
  // 添加讲师
  addTeacher(teacher) {
    return request({
      url: `/edu/teacher/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  // 根据id查询讲师用于数据回显
  getTeacherById(teacherId) {
    return request({
      url: `/edu/teacher/getTeacherById/${teacherId}`,
      method: 'get'
    })
  },
  // 根据id修改讲师
  updateTeacher(teacher) {
    return request({
      url: `/edu/teacher/updateTeacher`,
      method: 'post',
      data: teacher
    })
  }

}