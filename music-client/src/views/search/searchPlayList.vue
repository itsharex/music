<template>
	<div v-if="info.playlist.length > 0">
		<el-table :data="info.playlist" type="index"  @row-click="goto">
			<el-table-column type="index"></el-table-column>
			<el-table-column>
				<template #default="scope">
					<el-image :src="api.baseUrl + scope.row.pic" style="width: 100px;"></el-image>
				</template>
			</el-table-column>
			<el-table-column prop="title" ></el-table-column>
		</el-table>
		<el-pagination background layout="prev,pager,next" :total="pager.total" v-if="pager.total > 10"
		v-model:currentPage="pager.currentPage" v-model:page-size="pager.pageSize" @current-change="handleCurrentChange" class="pager"></el-pagination>
	</div>
	<el-empty v-else description="暂时没有歌单"></el-empty>
</template>

<script setup lang="ts">
	import {api} from '@/api/api.ts'
	import {get} from '@/utils/request.ts'
	import {useStore} from '@/store/index.ts'
	import {ref, reactive, onMounted,getCurrentInstance, watch} from 'vue'
	import type {playList} from '@/types'
	import songList from '@/components/songList.vue'
	import {useRouter} from 'vue-router'


	const router = useRouter();
	const {proxy} = getCurrentInstance();
	const pager = ref({
		currentPage: 1,
		total: 0,
		pageSize: 10
	});

	const goto = (row) => {
		router.push(`/playlist/detail/${row.songmenuId}`);
	}

	const info = reactive({
		playlist: [] as Song
	});

	const store = useStore();
	console.log(store.keyword);
	let keyword = store.keyword;

	const getPlayList = () => {
		get(api.searchPlayList,{
			params: {
				currentPage: pager.value.currentPage,
				keyword: keyword
			}
		}).then(res => {
			console.log(res)
			if(res.code == 200){
				info.playlist = res.data.records;
				pager.value.total = res.data.total;
			}else{
				info.playlist = [];
				pager.value.total = 0;
			}
		})
	}

	//翻页
	const handleCurrentChange = (val: number) => {
	  getPlayList();
	}

	//监听搜索栏
	watch(() => store.keyword,(current,old) => {
			keyword = current;
			getPlayList();
		}
	);

	onMounted(() => {
		getPlayList();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.pager{
		padding-top: 50px;
		@include center;
	}
</style>