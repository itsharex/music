<template>
	<div class="content">
		<songList :list="info.songList" v-if="info?.songList" style="width: 60%;"/>
	</div>
	<div class="pager">
		<el-pagination background layout="prev,pager,next" :total="pager.total" v-if="pager.total > 10"
		v-model:currentPage="pager.currentPage" v-model:page-size="pager.pageSize" @current-change="handleCurrentChange"></el-pagination>
	</div>
</template>

<script setup lang="ts">
	import {api} from '@/api/api.ts'
	import {get} from '@/utils/request.ts'
	import {useStore} from '@/store/index.ts'
	import {ref, reactive, onMounted,getCurrentInstance, watch} from 'vue'
	import type {Song} from '@/types'
	import songList from '@/components/songList.vue'

	const {proxy} = getCurrentInstance();
	const pager = ref({
		currentPage: 1,
		total: 0,
		pageSize: 10
	});
	const info = reactive({
		songList: [] as Song
	});

	const store = useStore();
	let keyword = store.keyword;

	const getSongList = () => {
		get(api.searchSong,{
			params: {
				currentPage: pager.value.currentPage,
				keyword: keyword
			}
		}).then(res => {
			if(res.code == 200){
				info.songList = res.data.records;
				pager.value.total = res.data.total;
			}else{
				info.songList = [];
				pager.value.total = 0;
			}
		})
	}

	const handleCurrentChange = (val: number) => {
	  getSongList();
	}

	//监听搜索栏
	watch(() => store.keyword,(current,old) => {
			keyword = current;
			getSongList();
		}
	);

	onMounted(() => {
		getSongList();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.content{
		width: 60%;
		@include center;
		flex-wrap: wrap;
	}
	.pager{
		padding-top: 50px;
	}
</style>