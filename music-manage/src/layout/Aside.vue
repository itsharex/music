<template>
	<div class="side">
		<el-menu active-text-color="green" background-color="white" text-color="black"
		:default-active="active" class="aside" @select="handleSelect" :router="true"
		:collapse="sidebar.collapse">
			<el-menu-item v-for="(item,index) in obj" :key="index" :index="item.index" >
				<el-icon>
					<component :is="item.icon"/>	
				</el-icon>
				<template #title>{{item.title}}</template>
			</el-menu-item>
		</el-menu>
	</div>
</template>

<script setup lang="ts">
	import {useRoute} from 'vue-router'
	import {ref} from 'vue'
	import {useStore} from '@/store'

	const route = useRoute();
	const sidebar = useStore();
	const obj = [
		{
			icon: 'House',
			index: '/',
			title: '系统首页'
		},
		{
			icon: 'User',
			index: '/user',
			title: '用户管理'
		},
		{
			icon: 'Mic',
			index: '/singer',
			title: '歌手管理'
		},
		{
			icon: 'Picture',
			index: '/carousel',
			title: '轮播图管理'
		}
	]
	const active = ref(route.path)

	//切换菜单
	const handleSelect = (key: string) => {
		active.value = key;
	}
</script>

<style scoped lang="scss">
	.side{
		height: 100%;
		overflow-y: scroll;
		z-index: 0;
		position: absolute;
	    left: 0;
	    top: 70px;
	    bottom: 0;
		.aside:not(.el-menu--collapse) {
		    width: 160px;
		}
		.aside{
			height: 100%;
		}
	}
	.side::-webkit-scrollbar {
	    width: 0;
	}
</style>