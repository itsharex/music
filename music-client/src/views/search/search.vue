<template>
	<div class="search-top">
		<el-tabs v-model="index" @tab-click="handleClick" class="menu">
		    <el-tab-pane label="歌曲" name="songList"></el-tab-pane>
		    <el-tab-pane label="歌单" name="searchPlayList"></el-tab-pane>
		</el-tabs>
	</div>
	<div class="search-content">
		<component :is="type[index]" style="width: 60%;"/>
	</div>
</template>

<script setup lang="ts">
	import {useStore} from '@/store/index.ts'
	import {ref} from 'vue'
	import type { TabsPaneContext } from 'element-plus'
	import searchPlayList from './searchPlayList.vue'
	import songList from './songList.vue'

	const index = ref('songList');
	const store = useStore();
	
	const type = {
		searchPlayList: searchPlayList,
		songList: songList
	}

	const handleClick = (tab: TabsPaneContext) => {
	  index.value = tab.props.name;
	}
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.search-top{
		width: $width;
		height: 40px;
		@include center;
		.menu{
			width: 60%;
		}
	}
	.search-content{
		width: $width;
		@include center;
		flex-wrap: wrap;
	}
</style>