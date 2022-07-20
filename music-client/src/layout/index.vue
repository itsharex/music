<template>
	<el-container class="container">
		<el-header class="header">
			<Header></Header>
		</el-header>
	    <el-main class="content">
	    	<router-view />
	    	<MusicPlay />
	    </el-main>
	    <el-footer class="footer">
	    	<currentPlayList class="playlist" />
	    	<Footer></Footer>
	    </el-footer>
	</el-container>
</template>

<script setup lang="ts">
import Header from './Header.vue'
import Footer from './Footer.vue'
import MusicPlay from './MusicPlay.vue'
import currentPlayList from '@/components/currentPlayList.vue'
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.container{
		 min-height: calc(100% - 60px);
		.footer{
			@include center;
			position: relative;
			.playlist{
				position: fixed;
				bottom: 80px;
				left: 55%;
			}
		}
		.header{
			@include center;
		}
		.content{
			width: $width81;
			height: $height;
			margin: 0 auto;
			@include center;
			flex-wrap: wrap;
		}
	}
</style>
