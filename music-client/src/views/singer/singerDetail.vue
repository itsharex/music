<template>
	<el-container class="content" v-if="info.singers != null">
		<el-aside class="aside">
			<img :src="api.baseUrl + info.singers.pic" >
		</el-aside>
		<el-main class="introduction">
			<el-descriptions :title="info.singers.name" :column="1">
			  <el-descriptions-item label="出生地">{{info.singers.address}}</el-descriptions-item>
			  <el-descriptions-item label="出生日期">{{info.singers.birth.substring(0,11)}}</el-descriptions-item>
			  <el-descriptions-item label="简介"
			    >{{info.singers.introduction}}</el-descriptions-item
			  >
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
	import type {singer,Song} from '@/types'
	import {ref, reactive, onMounted} from 'vue'
	import songList from '@/components/songList.vue'
	import {useStore} from '@/store'

	const store = useStore();
	const route = useRoute();
	const singerId = Number(route.params.id);
	const info = reactive({
		singers<singer>: null,
		songList: [] as Song,
		currentPage: 1,
		total: 0,
		pageSize: 10
	});

	const getSingerDetail = () => {
		get(api.getSingerDetail + singerId).then(res => {
			if(res.data != null){
				info.singers = res.data;
			}
		})
	}
	const getSongList = () => {
		get(api.getSongList,{
			params: {
				type: 0,
				singerId,
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
		getSingerDetail();
		getSongList();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.content{
		padding-top: 30px;
		width: 100%;
		.aside{
			width: 20%;
			img{
				border-radius: 5%;
				width: 90%;
			}
		}
		.introduction{
			width: 80%;
		}
		.pager{
			padding-top: 50px;
			@include center;
		}
	}
</style>