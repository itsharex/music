<template>
	<div class="content">
		<div>
			<h1>404</h1>
			<h1>oh!你好像走丢了...</h1>
			<el-button type="primary" @click="router.push('/')">回到首页</el-button>
		</div>
		 <img src="@/assets/404.gif" alt="404" />
	</div>

</template>

<script setup lang="ts">
	import {useRouter} from 'vue-router'

	const router = useRouter();
</script>

<style scoped lang="scss">
	.content{
		width: 100%;
		height: 100vh;
		background-color: white;
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>