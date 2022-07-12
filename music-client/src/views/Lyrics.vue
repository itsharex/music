<template>
	<el-container class="container">
		<el-aside class="aside">
			<img :src="api.baseUrl + store.music.pic" :alt="store.music.songName">
			<p>歌手名：{{store.music.singerName}}</p>
			<p>歌曲名：{{store.music.songName.split('-')[1]}}</p>
		</el-aside>
		<el-main class="main">
			<ul class="lyrics" ref="lyricRef">
				<li v-if="info.lyrics.lines.length > 0" v-for="(item,index) in info.lyrics.lines" :key="index">
					<p :class="{'active':obj.currentLineNum == index}" ref="lyricLine">{{item.txt}}</p>
				</li>
			</ul>
		</el-main>
	</el-container>
</template>

<script setup lang="ts">
	import {useStore} from '@/store'
	import {api} from '@/api/api.ts'
	import Lyric from 'lyric-parser'
	import {ref,reactive,watch} from 'vue'
	import {useRouter} from 'vue-router'

	const store = useStore();
	const router = useRouter();
	let lyricRef = ref();
	const lyricLine = ref();
	const obj = ref({
		currentLineNum: 0,
		index: 0,
		geci: store.music.geci
	});
	const info = reactive({
		lyrics: null
	});

	info.lyrics = new Lyric(obj.value.geci, handler);

	function handler({lineNum, txt}){
	  obj.value.currentLineNum = lineNum;
	  let height = 560;
	  if(obj.value.currentLineNum >=  14){
	  	obj.value.index = parseInt(lineNum / 14);
	  	lyricRef.value.scrollTo(obj.value.index * height,obj.value.index * height);
	  }else{
	  	lyricRef.value.scrollTo(0,0);
	  }
	}
	watch(() => [store.play,store.currentTime,store.music.url],(current,previous) =>{
		if(current[0]){
			info.lyrics.togglePlay();
		}else{
			info.lyrics.stop();
		}
		if(current[1] != previous[1]){
			info.lyrics.seek(current[1] * 1000);
			info.lyrics.stop();
		}
		if(current[2] != previous[2]){
			router.go(0);
		}
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.container{
		width: $width;
		padding-top: 30px;
		.aside{
			width: 20%;
			img{
				width: 90%;
			}
			p{
				font-size: 16px;
				font-weight: 700;
				padding-left: 15px;
			}
		}
		.main{
			width: 80%;
			.lyrics{
				max-height: 555px;
				overflow: auto;
				background-color: #e3e3e3;
				li{
					width: $width;
					height: 40px;
					text-align: center;
			        font-size: 14px;
			        line-height: 40px;
			        .active{
			        	color: skyblue;
			        }
				}
			}
		}
	}
</style>