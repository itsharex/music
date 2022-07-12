<template>
	<el-row :gutter="20">
		<el-col :span="6">
			<el-card shadow="hover">
				<div class="content">
					<el-icon :size="30"><User /></el-icon>
					<div>
						<div class="title">{{info.count.userCount}}</div>
						<div>用户总数</div>
					</div>
				</div>
			</el-card>
		</el-col>
		<el-col :span="6">
			<el-card shadow="hover">
				<div class="content">
					<el-icon :size="30"><Headset /></el-icon>
					<div>
						<div class="title">{{info.count.songCount}}</div>
						<div>歌曲总数</div>
					</div>
				</div>
			</el-card>
		</el-col>
		<el-col :span="6">
			<el-card shadow="hover">
				<div class="content">
					<el-icon :size="30"><Mic /></el-icon>
					<div>
						<div class="title">{{info.count.singerCount}}</div>
						<div>歌手总数</div>
					</div>
				</div>
			</el-card>
		</el-col>
		<el-col :span="6">
			<el-card shadow="hover">
				<div class="content">
					<el-icon :size="30"><Document /></el-icon>
					<div>
						<div class="title">{{info.count.playListCount}}</div>
						<div>歌单总数</div>
					</div>
				</div>
			</el-card>
		</el-col>
	</el-row>
	<el-row :gutter="20">
		<el-col :span="12">
			<h3>用户性别比例</h3>
			<el-card body-style="{padding: '0px'}" shadow="hover" class="pirture" ref="user"></el-card>
		</el-col>
		<el-col :span="12">
			<h3>歌手性别比例</h3>
			<el-card body-style="{padding: '0px'}" shadow="hover" class="pirture" ref="singer"></el-card>
		</el-col>
	</el-row>
	<el-row>
		<el-col :span="24">
			<h3>歌单类型比列</h3>
			<el-card body-style="{padding: '0px'}" shadow="hover" class="pirture" ref="playlist"></el-card>
		</el-col>
	</el-row>
</template>

<script setup lang="ts">
	import {api} from '@/api/api.ts'
	import {ref,reactive,getCurrentInstance,onMounted} from 'vue'
	import {get} from '@/api/request.ts'
	import {acountList,classifies,singerList} from '/types'
	import * as echarts from "echarts";

	const {proxy} = getCurrentInstance();
	const user = ref();
	const singer = ref();
	const playlist = ref();

	const userSex = reactive({
	  series: [
	    {
	      type: "pie",
	      data: [
	        {
	          value: 1,
	          name: "男",
	        },
	        {
	          value: 1,
	          name: "女",
	        },
	      ],
	    },
	  ],
	});
	const singerSex = reactive({
	  series: [
	    {
	      type: "pie",
	      data: [
	        {
	          value: 1,
	          name: "男",
	        },
	        {
	          value: 1,
	          name: "女",
	        },
	      ],
	    },
	  ],
	});

	const playlistStyle = reactive({
		xAxis: {
		    data: []
		  },
		yAxis: {},
		series: [
		    {
		      type: 'bar',
		      data: []
		    }
		]
	});

	const info = reactive({
		count: {},
		statistical: {
			accountList: [] as accountList,
			classifies: [] as classifies,
			singerList: [] as singerList
		}
	});

	const getCount = () => {
		get(api.getCount,null).then(res => {
			info.count = res.data
		})
	}

	const getStatistical = () => {
		get(api.getStatistical,null).then(res => {
			info.statistical = res.data;
			//设置用户性别
			userSex.series[0].data[0].value = info.statistical.accountList[0].type;
			userSex.series[0].data[1].value = info.statistical.accountList[1].type;
			const userSexChart = echarts.init(user.value.$el);
			userSexChart.setOption(userSex);
			//设置歌手性别
			singerSex.series[0].data[0].value = info.statistical.singerList[0].type;
			singerSex.series[0].data[1].value = info.statistical.singerList[1].type;
			const singerSexChart = echarts.init(singer.value.$el);
			singerSexChart.setOption(singerSex);
			//设置歌单类型
			info.statistical.classifies.forEach(i => {
				playlistStyle.xAxis.data.push(i.classifyName);
				playlistStyle.series[0].data.push(i.classifyId);
			})
			const playlistChart = echarts.init(playlist.value.$el);
			playlistChart.setOption(playlistStyle);
		});
	}

	onMounted(() => {
		getCount();
		getStatistical();
	});
</script>

<style scoped lang="scss">
	.content{
		display: flex;
		align-items: center;
		justify-content: space-around;
		text-align: center;
		.title{
			font-size: 20px;
			font-weight: 900;
		}
	}
	h3 {
	  margin: 10px 0;
	  text-align: center;
	}
	.pirture{
		height: 255px;
	}
</style>