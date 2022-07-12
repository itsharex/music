<template>
	<ul class="list">
		<li v-for="item in props.list" :key="item.songmenuId" class="item" @click="goto(item)">
			<el-image :src="api.baseUrl + item.pic" class="img"/>
			<p>{{item.title || item.name}}</p>
		</li>
	</ul>
</template>

<script setup lang="ts">
	import {api} from '@/api/api.ts'

	const props = defineProps(['list']);
	const emit = defineEmits(['jump']);
	const goto = (item) => {
		emit('jump',item);
	}
</script>

<style lang="scss" scoped>
	@import '@/assets/global.scss';
	.list{
		padding: 0 1rem;
		width: $width;
		.item{
			cursor: pointer;
			width: 20%;
			.img{
				width: 287px;
				height: 287px;
				border-radius: 2%;
			}
			p{
				padding-bottom: 20px;
				font-size: 15px;
			}
		}
	}
</style>