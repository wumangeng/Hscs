<template>
    <div id="uesrtext">
        <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
    </div>
</template>

<script>
    import {mapState} from 'vuex';
    // import req from '../../utils/api';

    export default {
        name: 'uesrtext',
        data() {
            return {
                content: ''
            }
        },
        computed: mapState([
            'currentSession'
        ]),
        methods: {
            addMessage(e) {
                if (e.ctrlKey && e.keyCode === 13 && this.content.length) {
                    let msgObj = new Object();
                    console.log(this.currentSession);
                    msgObj.to = this.currentSession.username;
                    msgObj.content = this.content;
                    // this.$store.state.stomp.send('/ws/chat', {}, JSON.stringify(msgObj));
                    // this.$store.commit('addMessage',msgObj);
                    this.postRequest('/chat/send', msgObj).then(resp => {
                    if (resp) {
                        console.log("消息");
                        console.log(resp);
                        this.$store.commit('addMessage',resp);
                        this.$router.go(0);
                    }})
                }
            }
        }
        
    }
</script>

<style lang="scss" scoped>
    #uesrtext {
        position: absolute;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 30%;
        border-top: solid 1px #DDD;

        > textarea {
            padding: 10px;
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
        }
    }
</style>