import request from '@/utils/request'
export default {
  //1 生成统计数据
  createStaData(day) {
    return request({
      url: `/statistics/sta/registerCount/${day}`,
      method: 'post'
    })
  },
  //2 获取统计数据
  getStaData(searchObj) {
    return request({
      url: `/statistics/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    })
  }
}
