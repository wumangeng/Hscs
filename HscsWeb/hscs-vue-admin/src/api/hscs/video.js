import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: '/edu/video/addVideo',
            method: 'post',
            data: video
          })
    },
    
    //删除小节
    deleteVideo(id) {
        return request({
            url: '/edu/video/'+id,
            method: 'delete'
          })
    },

    //修改小节
    updateVideo(video) {
        return request({
            url: '/edu/video/updateVideo',
            method: 'post',
            data: video
          })
    },

    //通过id查询小节
    getVideoById(id) {
        return request({
            url: '/edu/video/getVideo/'+id,
            method: 'get'
        })
    },
    // 删除阿里云中的视频
     removeALiYunVideo(id) {
        return request({
            url: '/upload/video/'+id,
            method: 'delete'
        })
    }
}