<template>
	<el-container class="content" v-if="info.list != null">
		<el-aside class="aside">
			<img :src="api.baseUrl + info.list.pic" >
		</el-aside>
		<el-main class="introduction">
			<el-descriptions :title="info.list.title" :column="1">
			  <el-descriptions-item label="简介">{{info.list.introduction}}</el-descriptions-item>
			</el-descriptions>
		</el-main>
	</el-container>
	<songList :list="info.songList" v-if="info?.songList"/>
	<el-pagination background layout="prev,pager,next" :total="info.total" v-if="info.total > 10"
		v-model:currentPage="info.currentPage" v-model:page-size="info.pageSize" @current-change="handleCurrentChange" class="pager"></el-pagination>
</template>

<script setup lang="ts">
	import {useRoute} from 'vue-router'
	import {api} from '@/api/api.ts'
	import {get} from '@/utils/request.ts'
	import {ref, reactive, onMounted} from 'vue'
	import type {playList,Song} from '@/types'
	import songList from '@/components/songList.vue'
	import {useStore} from '@/store'

	const store = useStore();
	const route = useRoute();
	const songmenuId = Number(route.params.id);
	const info = reactive({
		list: {} as playList,
		currentPage: 1,
		songList: [] as Song,
		total: 0,
		pageSize: 10
	});

	const getplaylistDetail = () => {
		get(api.getplaylistDetail + songmenuId,null).then(res => {
			if(!res.data){
				info.list = null;
			}else{
				info.list = res.data;
			}
		})
	}

	const getSongList = () => {
		get(api.getSongList,{
			params: {
				type: 1,
				songmenuId,
				currentPage: info.currentPage
			}
		}).then(res => {
			if(res.code == 200 && res.data != null){
				info.songList = res.data.records;
				info.total = res.data.total;
				store.playlist = res.data.records;
			}
		});
	}

	//翻页
	const handleCurrentChange = (val: number) => {
	  getSongList();
	}

	onMounted(() => {
		getplaylistDetail();
		getSongList();
	});
</script>

<style scoped lang="scss">
	.content{
		padding-top: 30px;
		width: 100%;
		.aside{
			width: 10%;
			img{
				border-radius: 5%;
				width: 70%;
			}
		}
		.introduction{
			width: 90%;
		}
	}
</style>