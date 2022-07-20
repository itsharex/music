<template>
	<div class="not">
		<div class="fund">
			<h1>404</h1>
			<h3>Page not fund</h3>
			<el-button type="primary" @click="router.push('/')">返回首页</el-button>
		</div>
	</div>
</template>

<script setup lang="ts">
	import {useRouter} from 'vue-router'

	const router = useRouter();
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.not{
		width: $width;
		@include VHcenter;
		.fund{
			text-align: center;
			h3{
				padding-bottom: 10px;
			}
		}
	}
</style>