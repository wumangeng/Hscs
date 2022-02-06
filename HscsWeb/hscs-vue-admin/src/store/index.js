import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

const now = new Date();

const modulesFiles = require.context('./modules', false, /\.js$/)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // set './app.js' => 'app'
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})

const store = new Vuex.Store({
  modules,
  getters,
  // 解决刷新vuex状态丢失问题
  plugins: [createPersistedState({
    storage: window.sessionStorage,
    reducer(val) {
      return {
        // 只储存state中的assessmentData
        dict: val.dict
      }
    }
  })],

  state: {
    // routes: [],
    sessions: {},
    admins: [],
    currentAdmin: JSON.parse(window.sessionStorage.getItem('user')),
    currentSession: null,
    filterKey: '',
    stomp: null,
    idDot: {},
    chatMen:''
},

mutations: {
    INIT_ADMIN(state, admin) {
        state.currentAdmin = admin;
    },
    // initRoutes(state, data) {
    //     state.routes = data;
    // },
    changeCurrentSession(state, currentSession) {
        state.currentSession = currentSession;
        Vue.set(state.idDot, state.currentAdmin.username + '#' + state.currentSession.username, false);
    },
    addMessage(state, msg) {
        let mss = state.sessions[state.currentAdmin.username + '#' + msg.to];
        if (!mss) {
            // state.sessions[state.currentAdmin.username + '#' + msg.to] = [];
            Vue.set(state.sessions, state.currentAdmin.username + '#' + msg.to, []);
        }
        state.sessions[state.currentAdmin.username + '#' + msg.to].push({
            content: msg.content,
            date: new Date(),
            self: !msg.notSelf
        })
    },
    INIT_DATA(state) {
        //浏览器本地的历史聊天记录
        let data = localStorage.getItem('vue-chat-session');
        if (data) {
            state.sessions = JSON.parse(data);
        }
    },
    INIT_ADMINS(state, data) {
        state.admins = data;

    },
    changeMen(state,chat){
        state.chatMen=chat;
    }
},

actions: {
    initData(context) {
        context.commit('INIT_DATA');
        getRequest('/chat/admin').then(resp => {
            if (resp) {
                context.commit('INIT_ADMINS', resp)
            }
        })
    }
}

})


store.watch(function (state) {
  return state.sessions
}, function (val) {
  localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
  deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})

export default store
