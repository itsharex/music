<template>
	<el-carousel :interval="3000" height="300px" class="carousel" type="card" :initial-index="1" ref="slideshow">
		<el-carousel-item v-for="item in info.carouselList" :key="item.carouselId" :name="String(item.carouselId)">
			<img :src="api.baseUrl + item.imgUrl" />
		</el-carousel-item>
	</el-carousel>
	<div class="list">
		<h3>歌单</h3>
		<ul class="playlist">
			<li v-for="item in info.playlist" :key="item.songmenuId" class="item" @click="jumpPlayList(item.songmenuId)">
				<el-image :src="api.baseUrl + item.pic" class="img"/>
				<p>{{item.title || item.name}}</p>
				<el-icon :size="100" class="icon"><VideoPlay /></el-icon>
			</li>
		</ul>
	</div>
	<div class="list">
		<h3>歌手</h3>
		<list :list="info.singerList" v-if="info.singerList.length > 0" @jump="jumpSinger"></list>
	</div>
	<el-backtop :right="100" :bottom="100" />
</template>

<script setup lang="ts">
	import {ref, reactive, onMounted, nextTick} from 'vue'
	import {useRouter} from 'vue-router'
	import {api} from '@/api/api.ts'
	import {get} from '@/utils/request.ts'
	import {carouselArr,playList,singer} from '@/types'
	import list from '@/components/list.vue'

	const router = useRouter();
	const info = reactive({
		carouselList: [] as carouselArr,
		playlist: [] as playList,
		singerList: [] as singer
	});
	const slideshow = ref(null);

	//获取轮播图
	const getCarousel = () => {
		get(api.getCarousel,null).then(res => {
			if(res.code == 200){
				info.carouselList = res.data;
			}
		})
	}
	//获取歌单
	const getPlayList = () => {
		get(api.getPlayList,null).then(res => {
			if(res.code == 200){
				info.playlist = res.data;
			}
		});
	}
	//获取歌手
	const getSinger = () => {
		get(api.getSinger,null).then(res => {
			if(res.code == 200){
				info.singerList = res.data;
			}
		});
	}
	//跳转歌单
	const jumpPlayList = (songmenuId) => {
		router.push(`/playlist/detail/${songmenuId}`);
	}
	//跳转歌手
	const jumpSinger = (item) => {
		router.push(`/singer/detail/${item.singerId}`);
	}
	//由于走马灯在页面初始化展示是空白
	nextTick(() => {
		slideshow.value.next();
	});
	onMounted(() => {
		getCarousel();
		getPlayList();
		getSinger();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.carousel{
		width: $width;
		img{
			width: $width;
			height: $height;
		}
		::v-deep(.el-carousel__indicators--outside){
			@include center;
		}
	}
	.list{
		padding-top: 20px;
		width: $width;
		h3{
			text-align: center;
		}
		.playlist{
			padding: 0 1rem;
			width: $width;
			.item{
				position: relative;
				cursor: pointer;
				width: 20%;
				.img{
					width: 95%;
					border-radius: 2%;
					transition: all 0.4s ease;
				}
				p{
					padding-bottom: 20px;
					font-size: 15px;
				}
				.icon{
					display: none;
					position: absolute;
					top: 33%;
					left: 30%;
					opacity: 0.5;
				}
			}
			.item:hover{
				.icon{
					display: block;
				}
			}
			.item:hover{
			    opacity: 0.7;
			}
		}
	}
</style>