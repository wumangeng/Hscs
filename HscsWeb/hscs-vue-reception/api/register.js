import request from '@/utils/request'
export default {
  //根据手机号发送验证码
  sendCode(phoneNumber) {
    return request({
      url: `/message/msm/send/${phoneNumber}`,
      method: 'get'
    })
  },

  //注册
  registerUser(formItem) {
    return request({
      url: `/usercenter/user/register`,
      method: 'post',
      data: formItem
    })
  },
}
