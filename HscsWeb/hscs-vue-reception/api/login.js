import request from '@/utils/request'
export default {
  //根据token获取用户信息
  getLoginUserInfo() {
    return request({
      url: `/usercenter/user/getUserInfo`,
      method: 'get'
    })
  },

  //登录
  loginUser(user) {
    return request({
      url: `/usercenter/user/login`,
      method: 'post',
      data: user
    })
  },

  //根据id获取用户信息
  getUserInfo(id) {
    return request({
      url: `/usercenter/user/getUserInfoOrder/${id}`,
      method: 'post'
    })
  },

  //根据id获取用户信息  （个人中心用）
  getUserInfoSelf(id) {
    return request({
      url: `/usercenter/user/getUserInfo/${id}`,
      method: 'post'
    })
  },

  //用户信息修改功能
  updataUserInfo(usercenter) {
    return request({
      url: `/usercenter/user/updateUser`,
      method: 'post',
      data: usercenter
    })
  },

  //修改密码
  ChangePassword(formItem) {
    return request({
      url: `/usercenter/user/change`,
      method: 'post',
      data: formItem
    })
  },

  //获取所有用户信息
  getUserInfoAll() {
    return request({
      url: `/usercenter/user/getUserInfoAll`,
      method: 'get'
    })
  },
}
