<template>
	<el-breadcrumb separator="/">
	   <el-breadcrumb-item :to="{ path: '/singer' }">歌手管理</el-breadcrumb-item>
	   <el-breadcrumb-item>歌曲信息管理</el-breadcrumb-item>
	 </el-breadcrumb>
	 <el-row :gutter="20" class="top">
	 	<el-col :span="4">
	 		<el-button type="danger" @click="deletes">批量删除</el-button>
	 		<el-button type="success" @click="addDisplay">新增歌曲</el-button>
	 	</el-col>
	 	<el-col :span="7">
	 		<el-input v-model.trim="pager.keyword" placeholder="请输入歌曲名搜索">
	 			<template #append>
	 				<el-button icon="Search" @click="search"></el-button>
	 			</template>
	 		</el-input>
	 	</el-col>
	 </el-row>
	 <el-table border class="table" :data="info.songArr" @selection-change="handleSelectionChange">
	 	<el-table-column type="selection" width="55"></el-table-column>
		<el-table-column label="ID" prop="songId" width="55"></el-table-column>
		<el-table-column label="歌曲图片" width="100">
			<template #default="scope">
				<img :src="baseUrl + scope.row.pic" width="100">
			</template>
		</el-table-column>
		<el-table-column label="歌名" prop="name"></el-table-column>
		<el-table-column label="歌曲" width="300">
			<template #default="scope">
				<audio :src="baseUrl + api.audio + scope.row.url" preload="auto" controls></audio>
			</template>
		</el-table-column>
		<el-table-column label="专辑" prop="introduction"></el-table-column>
		<el-table-column label="歌词" prop="geci" :show-overflow-tooltip="true"></el-table-column>
		<el-table-column label="歌手" prop="songName"></el-table-column>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="primary" @click="updateDisplay(scope.row)">编辑</el-button>
				<el-button type="danger" @click="deleteSong(scope.row.songId)">删除</el-button>
			</template>
		</el-table-column>
	 </el-table>
	 <el-pagination background layout="prev, pager, next" :total="pager.total" v-if="pager.total > 0"
	 v-model:page-size="pager.pageSize" v-model:current-page="pager.currentPage" @current-change="currentChange" class="pager"/>
	 <el-dialog v-model="dialog.visible" :title="dialog.title" width="30%" @close="close">
	 	<el-form :model="song" :rules="rules" ref="ruleFormRef" label-width="88px">
	 		<el-form-item label="名字" prop="name">
	 			<el-input v-model.trim="song.name" placeholder="请输入歌曲名"></el-input>
	 		</el-form-item>
	 		<el-form-item label="专辑名" prop="introduction">
	 			<el-input v-model.trim="song.introduction" placeholder="请输入专辑名"></el-input>
	 		</el-form-item>
	 		<el-form-item label="歌曲图片" prop="pic">
	 			<el-upload
	 			   class="avatar-uploader"
	 			   :action="baseUrl + api.upload"
	 			   :show-file-list="false"
	 			   :on-success="handleAvatarSuccess"
	 			 >
	 			   <img v-if="song.pic" :src="baseUrl + song.pic" class="avatar" />
	 			   <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
	 			    <template #tip>
	 			     <div class="el-upload__tip text-red">
	 			       只能上传一个文件，新的文件会覆盖旧的文件
	 			     </div>
	 			   </template>
	 			</el-upload>
	 		</el-form-item>
	 		<el-form-item label="歌曲" prop="url">
	 			<el-upload
	 				accept="audio/*"
	 			    :action="baseUrl + api.upload"
	 			    :limit="1"
	 			    :on-success="audioSuccess"
	 			    :on-error="audioError"
	 			  >
	 			    <el-button type="primary">Click to upload</el-button>
	 			    <template #tip>
	 			      <div class="el-upload__tip">
	 			        只能上传一个文件，新的文件会覆盖旧的文件
	 			      </div>
	 			    </template>
	 			  </el-upload>
	 		</el-form-item>
	 		<el-form-item label="歌词" prop="geci">
	 			<el-input v-model="song.geci" type="textarea" placeholder="请输入歌词"></el-input>
	 		</el-form-item>
	 		<el-form-item label="歌手名" prop="songName">
	 			<el-input v-model.trim="song.songName" placeholder="请输入歌手名"></el-input>
	 		</el-form-item>
	 		<el-form-item>
	 			<el-button type="primary" @click="save(ruleFormRef)">确认</el-button>
	 			<el-button @click="dialog.visible=false">取消</el-button>
	 		</el-form-item>
	 	</el-form>
	 </el-dialog>
</template>

<script setup lang="ts">
	import {api,baseUrl} from '@/api/api.ts'
	import {post,get} from '@/api/request.ts'
	import {ref,reactive,onMounted,getCurrentInstance,toRefs} from 'vue'
	import {songList,Dialog} from '@/types'
	import type { FormInstance, FormRules } from 'element-plus'
	import {useRoute} from 'vue-router'

	const {proxy} = getCurrentInstance();
	const route = useRoute();
	const ruleFormRef = ref<FormInstance>();
	const audioRef = ref<HTMLAudioElement>();
	const info = reactive({
		songArr: [] as songList,
		dialog: {
			title: '添加歌曲',
			visible: false
		} as Dialog,
		song: {
			songId: null,
			singerId: route.params.id,
			name: '',
			introduction: '',
			pic: null,
			url: null,
			geci: null,
			songName: null,
		} as songList,
		rules: {
			name: [
				{required: true,message:'名字不能为空',trigger:'blur'},
				{max: 20,message:"长度不能超过20",trigger: 'blur'}
			],
			introduction: [
				{required: true,message:'专辑名不能为空',trigger:'blur'},
				{max: 10,message:"长度不能超过10",trigger: 'blur'}
			],
			pic: [
				{required: true,message:'歌曲图片不能为空',trigger:'blur'},
			],
			songName: [
				{required: true,message:'歌手名不能为空',trigger:'blur'}
			]
		},
		Ids: []
	});
	const pager = ref({
		total: 0,
		pageSize: 5,
		currentPage: 1,
		keyword: null
	});
	const {dialog,song,rules} = toRefs(info);

	//获取当前歌手下的所有歌曲
	const getSongList = () => {
		get(api.getSongList,{
			params: {
				currentPage: pager.value.currentPage,
				keyword: pager.value.keyword,
				singerId: song.value.singerId
			}
		}).then(res => {
			info.songArr = res.data.records;
			pager.value.total = res.data.total;
		})
	}
	//上传图片成功的方法
	const handleAvatarSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
		song.value.pic = response;
	}
	//歌曲上传成功
	const audioSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
		song.value.url = response;
		proxy.$message.success('歌曲上传成功');
	}
	//歌曲上传失败
	const audioError = (error: Error, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
		proxy.$message.error('歌曲上传失败');
	}
	//搜索方法
	const search = () => {
		pager.value.currentPage = 1;
		getSongList();
	}
	//翻页
	const currentChange = () => {
		getSongList();
	}
	//显示修改轮播图
	const updateDisplay = (row) => {
		dialog.value.visible = true;
		dialog.value.title = '修改歌手';
		song.value.songId = row.songId;
		song.value.name = row.name;
		song.value.introduction = row.introduction;
		song.value.pic = row.pic;
		song.value.url = row.url;
		song.value.geci = row.geci;
		song.value.songName = row.songName;
	}
	const close = () => {
		song.value = {};
		ruleFormRef.value.resetFields();
	}
	//显示添加歌手
	const addDisplay = () => {
		dialog.value.visible = true;
		dialog.value.title = '添加歌曲';
	}
	//添加和修改歌手的提交方法
	const save = async (formEl: FormInstance | undefined) => {
		if (!formEl) return
		await formEl.validate((valid, fields) => {
		  if (valid) {
		   if(!song.value.url){
		   	proxy.$message.warning('请上传歌曲');
		   }else{
		   	if(!song.value.songId){
		   		post(api.addSong,song.value).then(res => {
		   			if(res.code === 200){
		   				proxy.$message.success(res.message);
		   				dialog.value.visible = false;
		   				getSongList();
		   			}else{
		   				proxy.$message.error(res.message);
		   			}
		   		});
		   	}else{
		   		post(api.updateSong,song.value).then(res => {
		   			if(res.code === 200){
		   				proxy.$message.success(res.message);
		   				dialog.value.visible = false;
		   				getSongList();
		   			}else{
		   				proxy.$message.error(res.message);
		   			}
		   		});
		   	}
		   }
		  } else {
		    console.log('error submit!', fields)
		  }
		})
	}
	//删除歌曲
	const deleteSong = (songId) => {
		post(api.delelteSong + songId).then(res => {
			if(res.code === 200){
				proxy.$message.success(res.message);
				getSongList();
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	//添加批量删除
	const handleSelectionChange = (val: nubmer[]) => {
		info.Ids = [];
		val.forEach(i => {
			info.Ids.push(i.songId);
		});
	}
	//批量删除
	const deletes = () => {
		if(info.Ids.length == 0){
			proxy.$message.warning('请选择要批量删除的选项');
		}else{
			post(api.deletesSong,info.Ids).then(res => {
				if(res.code === 200){
					proxy.$message.success(res.message);
					info.Ids = [];
					getSongList();
				}else{
					proxy.$message.error(res.message);
				}
			})
		}
	}

	onMounted(() => {
		getSongList();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.top{
		padding-top: $padingTop30;
	}
	.avatar-uploader .avatar {
	  width: 178px;
	  height: 178px;
	  display: block;
	}
</style>

<style>
	.avatar-uploader .el-upload {
	  border: 1px dashed var(--el-border-color);
	  border-radius: 6px;
	  cursor: pointer;
	  position: relative;
	  overflow: hidden;
	  transition: var(--el-transition-duration-fast);
	}

	.avatar-uploader .el-upload:hover {
	  border-color: var(--el-color-primary);
	}

	.el-icon.avatar-uploader-icon {
	  font-size: 28px;
	  color: #8c939d;
	  width: 178px;
	  height: 178px;
	  text-align: center;
	}	
</style>