<template>
	<div class="playlist" v-if="store.show">
		<h3 class="padding10">当前播放</h3>
		<h5 class="padding10">共{{playlist.length || 0}}首</h5>
		<ul v-if="playlist.length != 0" class="padding10">
			<li v-for="item in playlist" :key="item.songId" @click="playMusic(item)"
			:class="{active: item.songId == store.music.songId}">
				<p>{{item.name}}</p>
			</li>
		</ul>
		<el-empty description="暂时没有播放列表" v-else></el-empty>
	</div>
</template>

<script setup lang="ts">
	import {useStore} from '@/store'
	import {Song} from '@/types'
	import {ref,onMounted,watch} from 'vue'

	const store = useStore();
	const playlist = ref([]);

	const playMusic = (item) => {
		store.$patch(state => {
			state.music = {
				url: item.url,
				pic: item.pic,
				songName: item.name,
				singerName: item.songName,
				geci: item.geci,
				songId: item.songId
			}
		})
	}
	onMounted(() => {
		playlist.value = store.playlist;
	});

	watch(() => store.playlist,(current,previous)=> {
		if(current){
			playlist.value = current;
		}
	});
</script>

<style scoped lang="scss">
	.playlist{
		width: 480px;
		height: 410px;
		background-color: #ffffff;
		box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.4);
		z-index: 100;
		.padding10{
			padding: 10px 0 0 10px;
		}
		ul{
			li{
				width: 100%;
				height: 40px;
				cursor: pointer;
				border-bottom: 1px solid #e5e5e5;
				p{
					line-height: 40px;
				}
			}
		}
		.active{
			background-color: #83f383;
		}
	}
</style>