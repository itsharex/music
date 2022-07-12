<template>
	<el-tabs v-model="index" class="playlist" @tab-click="handleClick">
	    <el-tab-pane label="全部歌单" name="0"></el-tab-pane>
	    <el-tab-pane v-for="item in info.classfyList" :key="item.classifyId" :label="item.classifyName" :name="String(item.classifyId)">
	    </el-tab-pane>
	</el-tabs>
	<list :list="info.playlist" v-if="total > 0" @jump="jumpPlayList"></list>
	<el-pagination background layout="prev,pager,next" :total="total" 
	v-if="total > 0" v-model:currentPage="currentPage" v-model:page-size="pageSize" @current-change="handleCurrentChange" class="pager"></el-pagination>
</template>

<script setup lang="ts">
	import {ref, onMounted, reactive} from 'vue'
	import {useRouter} from 'vue-router'
	import {classify,singer} from '@/types'
	import {get} from '@/utils/request.ts'
	import {api} from '@/api/api.ts'
	import list from '@/components/list.vue'
	import type { TabsPaneContext } from 'element-plus'

	const index = ref('0');
	const currentPage = ref(1);
	const router = useRouter();
	const total = ref(0);
	const pageSize = ref(10);
	const info = reactive({
		classfyList: [] as classify,
		playlist: [] as playList
	});

	const handleClick = (tab: TabsPaneContext) => {
	  index.value = tab.props.name;
	  currentPage.value = 1;
	  getPlayList(tab.props.name);
	}

	//获取所有歌单分类
	const getClassify = () => {
		get(api.getClassify,null).then(res => {
			info.classfyList = res.data;
		})
	}

	const getPlayList = (classifyId) => {
		//查询全部歌单
		if(index.value === '0'){
			get(api.getPlayListByPage,{
				params: {
					currentPage: currentPage.value,
					classifyId: null
				}
			}).then(res => {
				info.playlist = res.data.records;
				currentPage.value = res.data.current;
				total.value = res.data.total;
			})
		}else{//分类查询歌单
			console.log('classify')
			get(api.getPlayListByPage,{
				params: {
					currentPage: currentPage.value,
					classifyId: classifyId
				}
			}).then(res => {
				console.log(res)
				info.playlist = res.data.records;
				currentPage.value = res.data.current;
				total.value = res.data.total;
			})
		}
	}

	const handleCurrentChange = (val: number) => {
	  getPlayList();
	}
	//歌单详情路由跳转
	const jumpPlayList = (item) => {
		router.push(`/playlist/detail/${item.songmenuId}`);
	}

	onMounted(() => {
		getClassify();
		getPlayList();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.playlist{
		margin-top: 15px;
		width: $width;
		::v-deep(.el-tabs__nav-wrap::after){
			background-color: white;
		}
	}
	.pager{
		margin: 0 auto;
	}
</style>