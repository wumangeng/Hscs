import request from '@/utils/request'
export default {
  //取消息
  getMessage(nickname){
    return request({
        url: `/usercenter/chat/get`,
        method: 'get',
        data: nickname
      })
 },
  // 发送信息,有效期默认一天
  sendMessage(Message) {
    return request({
      url: `/usercenter/chat/send`,
      method: 'post',
      data: Message
    })
  },
}
