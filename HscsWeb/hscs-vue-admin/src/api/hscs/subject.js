import request from '@/utils/request'
import { data } from 'autoprefixer'

export default {
  // 获取所有课程
  getSubjectList() {
    return request({
      url: '/edu/subject/getAllSubjects',
      method: 'get'
    })
  }
}

