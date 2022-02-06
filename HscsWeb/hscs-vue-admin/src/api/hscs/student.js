import request from '@/utils/request'

export default {
    getStudentList(current, limit) {
    //分页查询所有学生信息
    return request({
      url: `/edu/student/getStudentList/${current}/${limit}`,
      method: 'get'
    }) 
  },
   // 条件分页查询 studentQuery为查询条件
   getStudentListPage(current, limit, studentQuery) {
    return request({
      url: `/edu/student/getStudentListCondition/${current}/${limit}`,
      method: 'post',
      data: studentQuery // data把对象转换成json传递到接口里
    })
  },
  //删除学生信息
  deleteStudent(id){
    return request({
      url: `/edu/student/${id}`,
      method: 'delete'
    })
  },
  // 添加学生信息
  addStudent(student) {
    return request({
      url: `/edu/student/addStudent`,
      method: 'post',
      data: student
    })
  },
  // 根据id查询学生信息
  getStudentById(id) {
    return request({
      url: `/edu/student/getStudentById/${id}`,
      method: 'get'
    })
  },
  // 根据id修改学生信息
  updateStudent(student) {
    return request({
      url: `/edu/student/updateStudent`,
      method: 'post',
      data: student
    })
  }

}