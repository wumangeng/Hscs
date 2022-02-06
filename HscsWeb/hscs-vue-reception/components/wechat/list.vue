<template>
  <div id="list">
  	<ul style="padding-left: 0px">
		  <!-- TODO -->
  		<li v-for="item in admins" v-bind:key="item"
		   :class="{ active: currentSession?item.nickname === currentSession.nickname:false }"
		    v-on:click="changecurrentSession(item)">
  			<img class="avatar" :src="item.avatar" :alt="item.nickname">
			<el-badge :is-dot="idDot[user.nickname+'-->'+item.nickname]">
  				<p class="name">{{item.nickname}}</p>
			</el-badge>
  		</li>
  	</ul>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import cookie from 'js-cookie'

export default {
  name: 'list',
  data () {
    return {
		user: cookie.get("user")
    }
  },
  computed: mapState([
	'idDot',
	'admins',
	'currentSession'
  ]),
  methods:{
  changeCurrentSession: function (currentSession) {
    this.$store.commit('changeCurrentSession', currentSession);
    //this.$store.state.changeMen=currentSession.name;
     //store.state.changeMen=currentSession.name;
	 cookie.set('from',currentSession.nickname,{domain:'localhost'})
  }
  }
}
</script>

<style lang="scss" scoped>
#list {
	li {
		padding: 15px 15px;
		border-bottom: 1px solid #292C33;
		cursor: pointer;
		&:hover {
			background-color: rgba(255, 255, 255, 0.03);
		}
	}
  li.active {/*注意这个是.不是冒号:*/
		background-color: rgba(255, 255, 255, 0.1);
	}
	.avatar {
		border-radius: 2px;
		width: 30px;
		height: 30px;
		vertical-align: middle;
	}
	.name {
		display: inline-block;
		margin-left: 15px;
		margin-top: 0px;
        margin-bottom: 0px;
	}
}
</style>
