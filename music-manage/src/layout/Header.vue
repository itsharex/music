<template>
	<div class="header">
		<div class="collapse-btn" @click="handleCollapse">
			<el-icon v-if="!sidebar.collapse" :size="25"><Expand /></el-icon>
			<el-icon v-else :size="25"><Fold /></el-icon>
		</div>
		<div class="title">后台管理系统</div>
		<el-dropdown trigger="click" class="right">
			<el-avatar :size="40" :src="avatar"></el-avatar>
			<template #dropdown>
				<el-dropdown-item @click="router.push('/setting')">个人中心</el-dropdown-item>
				<el-dropdown-item @click="logout">退出登录</el-dropdown-item>
			</template>
		</el-dropdown>
	</div>
</template>

<script setup lang="ts">
	import avatar from '@/assets/avatar.png'
	import {useStore} from '@/store'
	import {sessionStorage} from '@/utils/storage.ts'
	import {useRouter} from 'vue-router'
	import {getCurrentInstance} from 'vue'

	const sidebar = useStore();
	const router = useRouter();
	const {proxy} = getCurrentInstance();
	const handleCollapse = () => {
		sidebar.handleCollapse();
	}

	const logout = () => {
		proxy.$message.success('退出登录');
		sessionStorage.remove('info');
		router.push('/login');
	}
</script>

<style scoped lang="scss">
	.header{
		width: 100%;
		height: 60px;
		box-sizing: border-box;
		background-color: white;
		box-shadow: 0px 0px 8px 2px rgba(0, 0, 0, 0.3);
		z-index: 100;
		display: flex;
  		align-items: center;
		.collapse-btn{
			padding: 0 21px;
	    	cursor: pointer;
		}
		.title{
			font-weight: 700;
			font-size: 20px;
		}
		.right{
			position: absolute;
			right: 30px;
		}
	}
</style>