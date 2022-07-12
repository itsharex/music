<template>
	<el-table style="width:100%;" :data="props.list" v-if="props.list.length > 0" @row-click="getSong">
		<el-table-column type="index"></el-table-column>
		<el-table-column label="歌曲" prop="name" :formatter="format"></el-table-column>
		<el-table-column label="歌手" prop="songName"></el-table-column>
		<el-table-column label="专辑" prop="introduction"></el-table-column>
		<el-table-column label="编辑">
			<template #default="scope">
				 <el-tooltip
			        effect="light"
			        content="下载"
			        placement="top"
			      >
			        <i class="fa fa-download" @click="down(scope.row.url)"></i>
			     </el-tooltip>
			     &nbsp;&nbsp;
			     <el-tooltip effect="light" content="添加到歌单" placement="top">
				     <el-icon :size="16" @click="obj.visible = true;obj.songmenuId = scope.row.songmenuId;obj.songId = scope.row.songId"><CirclePlus /></el-icon>
			     </el-tooltip>
			</template>
		</el-table-column>
	</el-table>
	<el-empty v-else description="暂时没有歌曲" style="margin: 0 auto;"></el-empty>
	<el-dialog width="30%" title="添加到歌单" v-model="obj.visible">
		请选择歌单：<el-select  clearable v-model="obj.songmenuId">
			<el-option v-for="item in data.playlist" :key="item.songmenuId"
			:label="item.title" :value="item.songmenuId"></el-option>
		</el-select>
		<template #footer>
			<el-button type="primary" @click="addSongToPlayList">确认</el-button>
		</template>
	</el-dialog>
</template>

<script setup lang="ts">
	import {api} from '@/api/api.ts'
	import {get,post} from '@/utils/request.ts'
	import {reactive,getCurrentInstance,ref,onMounted} from 'vue'
	import {useStore} from '@/store'
	import {useHook} from '@/hooks'
	import {useRouter} from 'vue-router'
	import {sessionStorage} from '@/utils/storage.ts'
	import {playList} from '@/types'

	const props = defineProps(['list']);
	const store = useStore();
	const hook = useHook();
	const router = useRouter();
	const {proxy} = getCurrentInstance();
	const info = sessionStorage.get('info');
	const obj = ref({
		visible: false,
		songId: null,
		songmenuId: null
	});
	const data = reactive({
		playlist: [] as playList
	});

	const format = (row, column, cellValue, index) => {
		return cellValue.split('-')[1];
	}

	const down = (url) => {
		get(api.download,{
			params: {
				file: url.split('/')[2]
			}
		}).then(res => {
			hook.download(res,url);
		})
	}
	//获取歌曲的url路径
	const getSong = (row) => {
		store.$patch(state => {
			state.music = {
				url: row.url,
				pic: row.pic,
				songName: row.name,
				singerName: row.songName,
				geci: row.geci,
				songId: row.songId
			}
		})
	}
	//获取自己的歌单
	const getPlayList = () => {
		get(api.playlist,{
			params: {
				accountId: info.accountId
			}
		}).then(res => {
			data.playlist = res.data;
			console.log(data.playlist);
		})
	}

	//将歌曲添加到歌单
	const addSongToPlayList = () => {
		post(api.addSongToPlayList,{
			songId: obj.value.songId,
			songmenuId: obj.value.songmenuId
		}).then(res => {
			if(res.code == 200){
				proxy.$message.success(res.message);
				obj.value.visible = false;
				obj.value.songmenuId = null;
				obj.value.songId = null;
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	onMounted(() => {
		getPlayList();
	});
</script>

<style scoped lang="scss">
	i{
		cursor: pointer;
	}
</style>