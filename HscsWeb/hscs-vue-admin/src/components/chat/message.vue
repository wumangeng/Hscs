<template>
    <div id="message" v-scroll-bottom="sessions">
         <ul v-if="currentSession">
             <div style="float:right">
                 <li v-for="note in notes" :key="note.time">
                <div class="main" v-if="!note.self" style="float:right">
                     <p class="time">
                    <span>{{note.time}}</span>
                     </p>
                    <img class="avatar" :src="note.userFace" alt="">
                    <p class="text">{{note.notSelf}}:    {{note.content}}</p>
                </div>
               
            </li>
             </div>
            

            <div style="float:left" clolor="green">
                 <li v-for="note in notes" :key="note.time">
                <div class="self" v-if="note.self" style="float:right">
                    <p class="time">
                    <span>{{note.time}}</span>
                     </p>
                    <img class="avatar" :src="note.userFace" alt="">
                    <p class="text">{{note.notSelf}}:    {{note.content}}</p>
                </div>
               
            </li>
             </div>
         </ul>
    </div>
</template>

<script>
    import {mapState} from 'vuex';
    // import req from '../../utils/api';
    import store from '../../store';

    export default {
        name: 'message',
        data() {
            return {
               user: JSON.parse(window.sessionStorage.getItem('user')),
                notes: ''
            }
        },
        mounted(){
            var name=window.sessionStorage.getItem('from');
            this.getRequest('/chat/get?username=' +name ).then(resp => {
                    if (resp) {
                        console.log("获取接受消息");
                        console.log(resp);
                        this.notes=resp;
                    }
                })
        }
        ,
        computed: mapState([
            'sessions',
            'currentSession'
        ]),
        methods: {
            //获取后台的信息
           getMessage(name){
              this.getRequest('/chat/get?username=' +name ).then(resp => {
                    if (resp) {
                        console.log("获取接受消息");
                        console.log(resp);
                        return resp;
                    }
                })
           }
        },
        filters: {
            time(date) {
                if (date) {
                    date = new Date(date);
                }
                return `${date.getHours()}:${date.getMinutes()}`;
            }
        },
        directives: {/*这个是vue的自定义指令,官方文档有详细说明*/
            // 发送消息后滚动到底部,这里无法使用原作者的方法，也未找到合理的方法解决，暂用setTimeout的方法模拟
            'scroll-bottom'(el) {
                //console.log(el.scrollTop);
                setTimeout(function () {
                    el.scrollTop += 9999;
                }, 1)
            }
        }
    }
</script>

<style lang="scss" scoped>
    #message {
        padding: 15px;
        max-height: 68%;
        overflow-y: scroll;

        ul {
            list-style-type: none;
            padding-left: 0px;

            li {
                margin-bottom: 15px;
            }
        }

        .time {
            text-align: center;
            margin: 7px 0;

            > span {
                display: inline-block;
                padding: 0 18px;
                font-size: 12px;
                color: #FFF;
                background-color: #dcdcdc;
                border-radius: 2px;
            }
        }

        .main {
            .avatar {
                float: left;
                margin: 0 10px 0 0;
                border-radius: 3px;
                width: 30px;
                height: 30px;

            }

            .text {
                display: inline-block;
                padding: 0 10px;
                max-width: 80%;
                background-color: #fafafa;
                border-radius: 4px;
                line-height: 30px;
            }
        }

        .self {
            text-align: right;

            .avatar {
                float: right;
                margin: 0 0 0 10px;
                border-radius: 3px;
                width: 30px;
                height: 30px;
            }

            .text {
                display: inline-block;
                padding: 0 10px;
                max-width: 80%;
                background-color: #b2e281;
                border-radius: 4px;
                line-height: 30px;
            }
        }
    }
</style>
