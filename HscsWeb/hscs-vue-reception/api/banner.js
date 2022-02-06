import request from '@/utils/request'

export default {
    //查询前3条banner数据
  getListBanner() {
    return request({
      url: '/infopush/bannerfront/getAllBanner',
      method: 'get'
    })
  }
}