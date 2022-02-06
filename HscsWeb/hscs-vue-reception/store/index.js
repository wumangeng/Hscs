import Vue from 'vue'
import Vuex from 'vuex'
import loginApi from '@/api/login'
import cookie from 'js-cookie'
// import SockJS from  'sockjs-client'
// import  Stomp from 'stompjs'

Vue.use(Vuex)

const now = new Date();

const store = new Vuex.Store({

	state:{
    counter: 0,

	  sessions: {},   //使用对象的方式存储聊天记录
	  admins: [],  //保存其它用户的数据
    //当前用户
	  currentAdmin: cookie.get("user"),
    currentSession: null,
    filterKey: '',
    stomp: null,
    idDot: {},
    chatMen:''
	},
	mutations:{
        increment (state) {
            state.counter++
          },
        
    INIT_ADMIN(state, admin) {
        state.currentAdmin = admin;
    },
    initRoutes(state, data) {
        state.routes = data;
    },
		changecurrentSession (state, currentSession) {
      state.currentSession = currentSession;
      //保存聊天记录
      Vue.set(state.idDot, state.currentAdmin.nickname + '-->' + state.currentSession.nickname, false);
		},
		addMessage (state,msg) {
      let mss = state.sessions[state.currentAdmin.nickname + '-->' + msg.to];
      if (!mss) {
          // state.sessions[state.currentAdmin.nickname + '-->' + msg.to] = [];
          Vue.set(state.sessions, state.currentAdmin.nickname + '-->' + msg.to, []);
      }
      state.sessions[state.currentAdmin.nickname + '-->' + msg.to].push({
          content: msg.content, //内容
          date: new Date(), //日期
          self: !msg.notSelf 
      })
		},
		// 浏览器本地的历史聊天记录
		INIT_DATA (state) {
      let data = localStorage.getItem('vue-chat-session');
      if (data) {
        state.sessions = JSON.parse(data);
      }
		},

		INIT_ADMINS(state, data) {
            state.admins = data;
    },
	},
	actions:{
     connect(context) {
           context.state.stomp = Stomp.over(new SockJS('/userventer/ws/ep'));
          //  context.state.stomp = Stomp.over(new SockJS('/websocket/1'));
            let token = cookie.get("token")//window.sessionStorage.getItem('token');
            //连接
            context.state.stomp.connect({'token': token}, success => {
              //订阅
                context.state.stomp.subscribe('/usercenter/queue/chat', msg => {
                    let receiveMsg = JSON.parse(msg.body);
                    //收到新的消息进行弹框提示
                    if (!context.state.currentSession || receiveMsg.from != context.state.currentSession.nickname) {
                        Notification.info({
                            title: '【' + receiveMsg.formNickName + '】发来一条消息',
                            message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
                            position: 'bottom-right'
                        });
                        Vue.set(context.state.idDot, context.state.currentAdmin.nickname + '-->' + receiveMsg.from, true);
                        console.log(context.state.idDot)
                    }
                    receiveMsg.notSelf = true;
                    receiveMsg.to = receiveMsg.from;
                    context.commit('addMessage', receiveMsg);
                })
            }, error => {
                console.log("聊天信息发送失败！")
            })
        },

    initData (context) {
      context.commit('INIT_DATA');
      //请求后台获取用户数据
      loginApi.getUserInfoAll().then(resp => {
        if (resp) {
          context.commit('INIT_ADMINS', resp)
        }
    	})
    },
  
	}
})


store.watch(function (state) {
  return state.sessions
},function (val) {
  console.log('CHANGE: ', val);
  localStorage.setItem('vue-chat-session', JSON.stringify(val));
},{
  deep:true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})


export {store};