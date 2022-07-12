<template>
	<el-button type="primary" @click="addDisplay">添加轮播图</el-button>
	<el-table :data="info.carouselList" border class="table">
		<el-table-column lable="ID" prop="carouselId"></el-table-column>
		<el-table-column lable="轮播图" width="200" align="center">
			<template #default="scope">
				<img :src="baseUrl + scope.row.imgUrl" width="200">
				<el-button @click="updateDisplay(scope.row)">更换图片</el-button>
			</template>
		</el-table-column>
		<el-table-column label="创建时间" prop="createTime"></el-table-column>
		<el-table-column label="修改时间" prop="updateTime"></el-table-column>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="danger" @click="deleteCaoursel(scope.row.carouselId)" icon="Delete">删除</el-button>
			</template>
		</el-table-column>
	</el-table>
	<el-dialog v-model="dialog.visible" :title="dialog.title" width="20%" @close="close">
		 <el-upload
		    class="avatar-uploader"
		    :action="baseUrl + api.upload"
		    :show-file-list="false"
		    :on-success="handleAvatarSuccess"
		  >
		    <img v-if="obj.imgUrl" :src="baseUrl + obj.imgUrl" class="avatar" />
		    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
		     <template #tip>
		      <div class="el-upload__tip text-red">
		        limit 1 file, new file will cover the old file
		      </div>
		    </template>
		  </el-upload>
		<template #footer>
			<el-button type="primary" @click="save">确认</el-button>
			<el-button @click="dialog.visible=false">取消</el-button>
		</template>
	</el-dialog>
</template>

<script setup lang="ts">
	import {api,baseUrl} from '@/api/api.ts'
	import {post,get} from '@/api/request.ts'
	import {reactive,onMounted,getCurrentInstance,toRefs} from 'vue'
	import {carousel,Dialog} from '@/types'

	const {proxy} = getCurrentInstance();
	const info = reactive({
		carouselList: [] as carousel,
		dialog:{
			title: '添加轮播图',
			visible: false
		} as Dialog,
		obj: {
			carouselId: null,
			imgUrl: null
		} as carousel
	});
	const {dialog,obj} = toRefs(info);


	const getCarouselList = () =>{
		get(api.getCarouselList,null).then(res => {
			info.carouselList = res.data;
		})
	}
	//图片上传成功的回调方法
	const handleAvatarSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
		obj.value.imgUrl = response;
	}

	//显示添加轮播图
	const addDisplay = () => {
		dialog.value.visible = true;
		dialog.value.title = '添加轮播图';
	}
	//显示修改轮播图
	const updateDisplay = (row) => {
		dialog.value.visible = true;
		dialog.value.title = '修改轮播图';
		obj.value.imgUrl = row.imgUrl;
		obj.value.carouselId = row.carouselId;
	}

	//对话款关闭时的回调方法
	const close = () => {
		obj.value.carouselId = null;
		obj.value.imgUrl = null;
	}
	const save = () => {
		console.log(obj.value.imgUrl);
		if(!obj.value.imgUrl){
			proxy.$message.warning('轮播图不能为空');
		}else{
			if(!obj.value.carouselId){
				post(api.addCarousel,{
					imgUrl: obj.value.imgUrl
				}).then(res => {
					if(res.code === 200){
						proxy.$message.success(res.message);
						dialog.value.visible = false;
						getCarouselList();
					}else{
						proxy.$message.error(res.message);
					}
				})
			}else{
				post(api.updateCarousel,{
					carouselId: obj.value.carouselId,
					imgUrl: obj.value.imgUrl
				}).then(res => {
					if(res.code === 200){
						proxy.$message.success(res.message);
						dialog.value.visible = false;
						getCarouselList();
					}else{
						proxy.$message.error(res.message);
					}
				})
			}
		}
		
	}
	//删除轮播图
	const deleteCaoursel = (carouselId) => {
		post(api.delCarousel + carouselId).then(res => {
			if(res.code === 200){
				proxy.$message.success(res.message);
				getCarouselList();
			}else{
				proxy.$message.error(res.message);
			}
		});
	}
	onMounted(() => {
		getCarouselList();
	});
</script>

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

<style scoped lang="scss">
	.avatar-uploader .avatar {
	  width: 178px;
	  height: 178px;
	  display: block;
	}
</style>