<template>
	<div class="music">
		<el-icon :size="30" color="white" class="marginRight20" @click="prev"><DArrowLeft /></el-icon>
		<el-icon :size="45" color="white" class="marginRight20" v-if="!info.play" @click="playOrPause"><VideoPlay /></el-icon>
		<el-icon :size="45" color="white" class="marginRight20" v-else @click="playOrPause"><VideoPause /></el-icon>
		<el-icon :size="30" color="white" class="marginRight20" @click="next"><DArrowRight /></el-icon>
		<el-avatar shape="square" :size="50" class="marginRight20" v-if="store.music.pic == null">
			<el-icon :size="35">
				<Headset />
			</el-icon>
		</el-avatar>
		<el-avatar :src="api.baseUrl + store.music.pic" shape="square" v-else @click="router.push(`/lyrics/${store.music.songId}`)"></el-avatar>
		<div class="progress">
			<span class="name" v-if="store.music.songName == null">&nbsp;</span>
			<span class="name" v-else>{{store.music.songName.split('-')[1]}}</span>
			<span class="time">{{obj.startTime}}/{{obj.endTime}}</span>
			<el-slider v-model="obj.nowTime" size="small" class="input" :show-tooltip="false" @change="updateTime" :disabled="info.drag"/>
		</div>
		<el-dropdown placement="top" class="volume">
			<i class="fa fa-volume-up" style="color:white;cursor: pointer;font-size: 20px;" v-if="obj.volume != 0"></i>
			<i class="fa fa-volume-off" style="color:white;cursor: pointer;font-size: 20px;" v-else></i>
			<template #dropdown>
				<el-slider v-model="obj.volume" vertical height="150px" @change="updateVolume"/>
			</template>
		</el-dropdown>
		<i class="fa fa-exchange whiteColor" v-if="obj.type == 0" @click="changeType"></i>
		<i class="fa fa-refresh whiteColor" v-else @click="changeType"></i>
		<i class="fa fa-download whiteColor" @click="downLoadMusic"></i>
		<i class="fa fa-heart-o whiteColor" v-if="!obj.collect" @click="addCollect"></i>
		<i class="fa fa-heart whiteColor" style="color: red;" v-else @click="cancelCollect"></i>
		<i class="fa fa-th-list whiteColor" @click="store.show = !store.show"></i>
		<div class="currentPlaylist"></div>
		<audio :src="obj.url" controls class="audio" preload="auto" ref="audioRef" @canplay="canplay"
		@timeupdate="timeupdate" @ended="ended"></audio>
		<!-- 
			canplay当浏览器可以播放音频/视频时
			timeupdate 当目前的播放位置已更改时
			preload 是否在页面加载后立即加载（设置 autoplay 后无效）
			ended 音频是否结束
		 -->
	</div>	
</template>

<script setup lang="ts">
	import {useStore} from '@/store'
	import {ref, reactive, watch, getCurrentInstance,onMounted} from 'vue'
	import {api} from '@/api/api.ts'
	import {useHook} from '@/hooks'
	import {get,post} from '@/utils/request.ts'
	import {sessionStorage} from '@/utils/storage.ts'
	import {useRouter} from 'vue-router'

	const store = useStore();
	const hook = useHook();
	const router = useRouter();
	const userInfo = sessionStorage.get('info');
	const audioRef = ref<HTMLAudioElement>();
	const {proxy} = getCurrentInstance();
	const info = reactive({
		play: false,
		drag: true
	});
	const obj = ref({
		nowTime: 0,
		startTime: '00:00',
		volume: 100,
		changeTime: null,
		type: 0, //播放顺序类型,0代表顺序播放，1代表单曲循环
		collect: false,
		url: null,
		endTime: '00:00'
	});

	//播放or暂停
	const playOrPause = () => {
		if(!obj.value.url){
			proxy.$message.warning('请先选择要播放的歌曲');
		}else{
			if(info.play){
				audioRef.value.pause();
				info.play = false;
				store.play = false;
			}else{
				audioRef.value.play();
				info.play = true;
				store.play = true;
			}
		}
	};

	//将秒转换成分钟
	const convertMinutes = (s) => {
		let temp;
		let min = Math.floor(s/60) % 60;
		let sec = s % 60;
		if(min < 10){
			temp = '0' + min + ':';
		}
		if(sec.toFixed(0) < 10){
			temp += '0';
		}
		temp += sec.toFixed(0);
		return temp;
	};
	//canplay当浏览器可以播放音频/视频时
	const canplay = () => {
		obj.value.endTime = convertMinutes(audioRef.value.duration);
		info.drag = false;
	}
	//实时更新播放时间
	const timeupdate = () => {
		obj.value.startTime = convertMinutes(audioRef.value.currentTime);
		obj.value.nowTime = (audioRef.value.currentTime / audioRef.value.duration) * 100;
	}
	//拖动播放进度条
	const updateTime = (val) => {
		obj.value.startTime = convertMinutes((val/100) * audioRef.value.duration);
		//更改时间
		audioRef.value.currentTime = (val/100) * audioRef.value.duration;
		store.currentTime = audioRef.value.currentTime;
	}
	//修改音量
	const updateVolume = (val) => {
		audioRef.value.volume = val / 100;
	}
	//下载歌曲
	const downLoadMusic = () => {
		if(!store.music.url){
			proxy.$message.warning('请先选择要播放的歌曲');
		}else{
			get(api.download,{
				params: {
					file: store.music.url.split('/')[2]
				}
			}).then(res => {
				hook.download(res,store.music.url);
			})
		}
	}
	//添加收藏
	const addCollect = () => {
		post(api.addCollect,{
			songId: store.music.songId,
			accountId: userInfo.accountId
		}).then(res => {
			console.log(res)
			if(res.code == 200){
				proxy.$message.success(res.message);
				obj.value.collect = true;
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	//判断用户是否收藏歌曲
	const whetherCollect = () => {
		get(api.whetherCollect,{
			params: {
				songId: store.music.songId,
				accountId: userInfo.accountId
			}
		}).then(res => {
			if(res.data){
				obj.value.collect = true;
			}else{
				obj.value.collect = false;
			}
		})
	}
	//取消收藏
	const cancelCollect = () => {
		post(api.cancelCollect,{
			songId: store.music.songId,
			accountId: userInfo.accountId
		}).then(res => {
			if(res.code == 200){
				proxy.$message.success(res.message);
				obj.value.collect = false;
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	//当歌曲播放完
	const ended = () => {
		if(obj.value.type == 1){
			common();
		}else{
			next();
		}
	}
	const common = () => {
		if(audioRef.value !== null){
			let playPromise = audioRef.value.play();
			if(playPromise){
				playPromise.then(() => {
				}).catch((e) => {
					common();
				});
			}
		}
		audioRef.value.play();
		info.play = true;
		store.play = true;
	}
	//改变播放顺序
	const changeType = () => {
		if(obj.value.type == 0){
			obj.value.type = 1;
		}else{
			obj.value.type = 0;
		}
	}
	//下一首
	const next = () => {
		//如果时单曲循环模式，不过下一首还是上一首都是播放当前的歌曲
		let end = store.playlist.length - 1;//播放列表最后一首歌
		if(!obj.value.url){
			proxy.$message.warning('请先选择要播放的歌曲');
		}else{
			if(obj.value.type == 1){
				common();
			}else{
				for(let i in store.playlist){
					if(store.playlist[i].songId == store.music.songId){
						//当播放到列表最后一首歌时，从头开始
						if(end == i){
							store.$patch(state => {
								state.music = {
									url: store.playlist[0].url,
									pic: store.playlist[0].pic,
									songName: store.playlist[0].name,
									singerName: store.playlist[0].songName,
									geci: store.playlist[0].geci,
									songId: store.playlist[0].songId
								}
							})
						}else{
							store.$patch(state => {
								state.music = {
									url: store.playlist[Number(i) + 1].url,
									pic: store.playlist[Number(i) + 1].pic,
									songName: store.playlist[Number(i) + 1].name,
									singerName: store.playlist[Number(i) + 1].songName,
									geci: store.playlist[Number(i) + 1].geci,
									songId: store.playlist[Number(i) + 1].songId
								}
							})
						}
						common();
						return;
					}
				}
			}
		}
	}
	//上一首
	const prev = () => {
		let end = store.playlist.length - 1;//播放列表最后一首歌
		//如果时单曲循环模式，不过下一首还是上一首都是播放当前的歌曲
		if(!obj.value.url){
			proxy.$message.warning('请先选择要播放的歌曲');
		}else{
			if(obj.value.type == 1){
				common();
			}else{
				for(let i in store.playlist){
					if(store.playlist[i].songId == store.music.songId){
						if(0 == i){
							store.$patch(state => {
								state.music = {
									url: store.playlist[end].url,
									pic: store.playlist[end].pic,
									songName: store.playlist[end].name,
									singerName: store.playlist[end].songName,
									geci: store.playlist[end].geci,
									songId: store.playlist[end].songId
								}
							})
						}else{
							store.$patch(state => {
								state.music = {
									url: store.playlist[Number(i) - 1].url,
									pic: store.playlist[Number(i) - 1].pic,
									songName: store.playlist[Number(i) - 1].name,
									singerName: store.playlist[Number(i) - 1].songName,
									geci: store.playlist[Number(i) - 1].geci,
									songId: store.playlist[Number(i) - 1].songId
								}
							})
						}
						common();
						return;
					}
				}
			}
		}
	}
	watch(() => store.music.url,(current,previous) => {
		if(current){
			obj.value.url = api.baseUrl + api.audio + current;
			whetherCollect();
			info.play = false;
			store.play = false;
			//当播放的歌曲路径改变进度条重新开始
			obj.value.nowTime = 0;
			audioRef.value.currentTime = 0;
		}
	});
	onMounted(() => {
		if(store.music.url !== null){
			obj.value.url = api.baseUrl + api.audio + store.music.url;
		}
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.music{
		width: $width !important;
		margin: 0 0 !important;
		position: fixed;
		z-index: 100;
		bottom: 0;
		height: 80px;
		background-color: rgba(0, 0, 0, .35);
		@include VHcenter;
		.audio{
			display: none;
		}
		.progress{
			width: 15%;
			position: relative;
			padding-left: 20px;
			color: white;
			.time{
				position: absolute;
				right: 0;
			}
			input{
				width: $width;
			}
		}
		.whiteColor{
			color: white;
			padding-left: 25px;
			cursor: pointer;
			font-size: 20px;
		}
		.marginRight20{
			margin-right: 10px;
			cursor: pointer;
		}
		.volume{
			padding-left: 25px;
		}
	}
</style>