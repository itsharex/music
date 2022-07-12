<template>
	<el-tabs v-model="index" class="menu" @tab-click="handleClick">
	    <el-tab-pane label="全部歌手" name="3"></el-tab-pane>
	    <el-tab-pane label="男歌手" name="0"></el-tab-pane>
	    <el-tab-pane label="女歌手" name="1"></el-tab-pane>
	    <el-tab-pane label="组合歌手" name="2"></el-tab-pane>
	</el-tabs>
	<list :list="info.singerList" v-if="total > 0" @jump="jumpSinger"></list>
	<el-pagination background layout="prev,pager,next" :total="total" 
	v-if="total > 0" v-model:currentPage="currentPage" v-model:page-size="pageSize" @current-change="handleCurrentChange" class="pager"></el-pagination>
</template>

<script setup lang="ts">
	import {ref, onMounted, reactive} from 'vue'
	import {useRouter} from 'vue-router'
	import {singer} from '@/types'
	import {get} from '@/utils/request.ts'
	import {api} from '@/api/api.ts'
	import list from '@/components/list.vue'
	import type { TabsPaneContext } from 'element-plus'

	const index = ref('3');
	const currentPage = ref(1);
	const router = useRouter();
	const total = ref(0);
	const pageSize = ref(10);
	const info = reactive({
		singerList: [] as singer
	});

	const handleClick = (tab: TabsPaneContext) => {
	  index.value = tab.props.name;
	  currentPage.value = 1;
	  getSinger(tab.props.name);
	}

	//获取歌手数据
	const getSinger = (sex) => {
		//查询全部歌手
		if(index.value === '3'){
			get(api.getSingerByPage,{
				params: {
					currentPage: currentPage.value,
					sex: null
				}
			}).then(res => {
				info.singerList = res.data.records;
				currentPage.value = res.data.current;
				total.value = res.data.total;
			})
		}else{//分类查询歌手
			get(api.getSingerByPage,{
				params: {
					currentPage: currentPage.value,
					sex: sex
				}
			}).then(res => {
				console.log(res)
				info.singerList = res.data.records;
				currentPage.value = res.data.current;
				total.value = res.data.total;
			})
		}
	}
	const handleCurrentChange = (val: number) => {
	  getSinger();
	}
	//歌单详情路由跳转
	const jumpSinger = (item) => {
		router.push(`/singer/detail/${item.singerId}`);
	}

	onMounted(() => {
		getSinger();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.menu{
		margin-top: 15px;
		width: $width;
		::v-deep(.el-tabs__nav-wrap::after) {
			background-color: white;
		}
	}
	.pager{
		margin: 0 auto;
	}
</style>