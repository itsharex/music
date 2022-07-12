<template>
	<el-row class="header">
		<el-col :span="1">
			<p style="color: blue;font-size: 16px;font-weight: bolder;cursor: pointer;"
			@click="router.push('/')">Music</p>
		</el-col>
		<el-col :span="5">
			<el-menu :default-active="index" mode="horizontal" active-text-color="green" :router="true"
			@select="handleSelect">
				<el-menu-item index="/">首页</el-menu-item>
				<el-menu-item index="/playlist">歌单</el-menu-item>
				<el-menu-item index="/singer">歌手</el-menu-item>
			</el-menu>
		</el-col>
		<el-col :span="10">
			<el-input v-model.trim="search" placeholder="请输入搜索关键字">
				<template #append>
					<el-button icon="Search" @click="searchSong"></el-button>
				</template>
			</el-input>
		</el-col>
		<el-col :span="5"></el-col>
		<el-col :span="3" >
			<div v-if="!info">
				<el-button text @click="router.push('/login')">登录</el-button>
				<el-button text @click="router.push('/register')">注册</el-button>
			</div>
			<el-dropdown v-else>
				<el-avatar :size="30" :src="url">
				</el-avatar>
				<template #dropdown>
					<el-dropdown-item @click="router.push('/userinfo')">用户设置</el-dropdown-item>
					<el-dropdown-item @click="router.push('/personal')">个人主页</el-dropdown-item>
					<el-dropdown-item @click="logout">退出登录</el-dropdown-item>
				</template>
			</el-dropdown>
		</el-col>
	</el-row>
</template>

<script setup lang="ts">
import { ref, onMounted} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {sessionStorage} from '@/utils/storage.ts'
import {api} from '@/api/api.ts'
import {get} from '@/utils/request.ts'
import {useStore} from '@/store/index.ts'

const store = useStore();
const search = ref('');
const router = useRouter();
const route = useRoute();
const index = ref(store.activeNav);


let info = sessionStorage.get('info');
const url = ref(null);

//获取用户信息
const getInfo = () => {
	if(info != null){
		url.value = api.baseUrl + info.avator;
	}
}
//退出登录
const logout = () => {
	sessionStorage.remove('info');
	router.push('/login');
}

//切换菜单
const handleSelect = (key: string) => {
  store.setActiveNav(key)
}

const searchSong = () => {
	store.keyword = search.value;
	router.push('/search');
}

onMounted(() => {
	getInfo();
});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.header{
		border-bottom: 2px solid #dcdfe6;
		.el-menu--horizontal{
			border-bottom: none;
		}
		width: $width81;
		@include Vcenter;
	}
</style>