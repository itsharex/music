<template>
	<el-row :gutter="20">
		<el-col :span="4">
			<el-button type="danger" @click="deletes">批量删除</el-button>
			<el-button type="success" @click="addDisplay">新增歌手</el-button>
		</el-col>
		<el-col :span="7">
			<el-input v-model.trim="pager.keyword" placeholder="请输入用户名搜索">
				<template #append>
					<el-button icon="Search" @click="search"></el-button>
				</template>
			</el-input>
		</el-col>
	</el-row>
	<el-table class="table" :data="info.singerArr" border @selection-change="handleSelectionChange">
		<el-table-column type="selection" width="55"></el-table-column>
		<el-table-column label="ID" prop="singerId" width="55"></el-table-column>
		<el-table-column label="歌手图片">
			<template #default="scope">
				<img :src="baseUrl + scope.row.pic" width="100">
			</template>
		</el-table-column>
		<el-table-column label="歌手名" prop="name"></el-table-column>
		<el-table-column label="性别">
			<template #default="scope">
				<p v-if="scope.row.sex == 0">男</p>
				<p v-else>女</p>
			</template>
		</el-table-column>
		<el-table-column label="生日">
			<template #default="scope">
				{{dateFormat(scope.row.birth)}}
			</template>
		</el-table-column>
		<el-table-column label="地区" prop="address"></el-table-column>
		<el-table-column label="简介" prop="introduction" :show-overflow-tooltip="true"></el-table-column>
		<el-table-column label="歌曲管理">
			<template #default="scope">
				<el-button @click="router.push(`/singer/${scope.row.singerId}`)">歌曲管理</el-button>
			</template>
		</el-table-column>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="primary" @click="updateDisplay(scope.row)">编辑</el-button>
				<el-button type="danger" @click="deleteSinger(scope.row.singerId)">删除</el-button>
			</template>
		</el-table-column>
	</el-table>
	<el-pagination background layout="prev, pager, next" :total="pager.total" v-if="pager.total > 0"
	v-model:page-size="pager.pageSize" v-model:current-page="pager.currentPage" @current-change="currentChange" class="pager"/>
	<el-dialog v-model="dialog.visible" :title="dialog.title" width="30%" center @close="close">
		<el-form :model="singer" :rules="rules" label-width="88px" ref="ruleFormRef">
			<el-form-item label="名字" prop="name">
				<el-input v-model.trim="singer.name" placeholder="请输入名字"></el-input>
			</el-form-item>
			<el-form-item label="性别" prop="sex">
				<el-radio-group v-model="singer.sex">
					<el-radio :label="0">男</el-radio>
					<el-radio :label="1">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="图片" prop="pic">
				<el-upload
				   class="avatar-uploader"
				   :action="baseUrl + api.upload"
				   :show-file-list="false"
				   :on-success="handleAvatarSuccess"
				 >
				   <img v-if="singer.pic" :src="baseUrl + singer.pic" class="avatar" />
				   <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
				    <template #tip>
				     <div class="el-upload__tip text-red">
				       limit 1 file, new file will cover the old file
				     </div>
				   </template>
				</el-upload>
			</el-form-item>
			<el-form-item label="生日" prop="birth">
				<el-date-picker
	            v-model="singer.birth"
	            type="date"
	            label="选择时间"
	            placeholder="选择时间"
	            style="width: 100%"
	          />
			</el-form-item>
			<el-form-item label="地址" prop="address">
				<el-input v-model="singer.address"></el-input>
			</el-form-item>
			<el-form-item label="简介" prop="introduction">
				<el-input v-model="singer.introduction" type="textarea"></el-input>
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
	import {singerList,Dialog} from '@/types'
	import {dateFormat} from '@/utils'
	import type { FormInstance, FormRules } from 'element-plus'
	import {useRouter} from 'vue-router'

	const router = useRouter();
	const {proxy} = getCurrentInstance();
	const ruleFormRef = ref<FormInstance>();
	const info = reactive({
		singerArr: [] as singerList,
		dialog: {
			title: '添加歌手',
			visible: false
		} as Dialog,
		singer: {
			singerId: null,
			name: '',
			sex: null,
			pic: null,
			birth: null,
			address: '',
			introduction: null
		} as singerList,
		rules: {
			name: [
				{required: true,message:'名字不能为空',trigger:'blur'},
				{max: 20,message:"长度不能超过20",trigger: 'blur'}
			],
			sex: [
				{required: true,message:'请选择性别',trigger:'change'}
			],
			pic: [
				{required: true,message:'图片不能为空',trigger:'blur'}
			],
			birth: [
				{required: true,message:'请选择生日',trigger:'change'}
			],
			address: [
				{required: true,message:'地址不能为空',trigger:'blur'}
			],
			introduction: [
				{required: true,message:'简介不能为空',trigger:'blur'}
			]
		} as FormRules,
		Ids: []
	});
	const pager = ref({
		total: 0,
		currentPage: 1,
		pageSize: 5, //后台已经写死了每页显示的条数
		keyword: null
	});
	const {dialog,singer,rules} = toRefs(info);

	const getSingerList  = () => {
		get(api.getSingerList,{
			params: {
				currentPage: pager.value.currentPage,
				keyword: pager.value.keyword
			}
		}).then(res => {
			info.singerArr = res.data.records;
			pager.value.total = res.data.total;
		})
	}
	//搜索方法
	const search = () => {
		pager.value.currentPage = 1;
		getSingerList();
	}
	//翻页
	const currentChange = () => {
		getSingerList();
	}
	//显示添加歌手
	const addDisplay = () => {
		dialog.value.visible = true;
		dialog.value.title = '添加歌手';
	}
	//对话框闭关时的回调方法
	const close = () => {
		singer.value = {};
		ruleFormRef.value.resetFields();
	}
	//显示修改轮播图
	const updateDisplay = (row) => {
		dialog.value.visible = true;
		dialog.value.title = '修改歌手';
		singer.value.singerId = row.singerId;
		singer.value.name = row.name;
		singer.value.sex = row.sex;
		singer.value.pic = row.pic;
		singer.value.address = row.address;
		singer.value.birth = row.birth;
		singer.value.introduction = row.introduction;
	}
	//上传图片成功的方法
	const handleAvatarSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
		singer.value.pic = response;
	}
	//删除歌手
	const deleteSinger = (singerId) => {
		post(api.delelteSinger + singerId).then(res => {
			if(res.code === 200){
				proxy.$message.success(res.message);
				getSingerList();
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	//添加和修改歌手的提交方法
	const save = async (formEl: FormInstance | undefined) => {
		if (!formEl) return
		await formEl.validate((valid, fields) => {
		  if (valid) {
		    if(!singer.value.singerId){
		    	post(api.addSinger,singer.value).then(res => {
		    		if(res.code === 200){
		    			proxy.$message.success(res.message);
		    			dialog.value.visible = false;
		    			getSingerList();
		    		}else{
		    			proxy.$message.error(res.message);
		    		}
		    	});
		    }else{
		    	post(api.updateSinger,singer.value).then(res => {
		    		if(res.code === 200){
		    			proxy.$message.success(res.message);
		    			dialog.value.visible = false;
		    			getSingerList();
		    		}else{
		    			proxy.$message.error(res.message);
		    		}
		    	});
		    }
		  } else {
		    console.log('error submit!', fields)
		  }
		})
	}
	//添加批量删除
	const handleSelectionChange = (val: nubmer[]) => {
		info.Ids = [];
		val.forEach(i => {
			info.Ids.push(i.singerId);
		});
	}
	//批量删除
	const deletes = () => {
		if(info.Ids.length == 0){
			proxy.$message.warning('请选择要批量删除的选项');
		}else{
			post(api.deletesSinger,info.Ids).then(res => {
				if(res.code === 200){
					proxy.$message.success(res.message);
					getSingerList();
					info.Ids = [];
				}else{
					proxy.$message.error(res.message);
				}
			})
		}	
	}
	onMounted(() => {
		getSingerList();
	});
</script>

<style scoped lang="scss">
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