<template>
	<div class="personal">
		<div style="padding-top: 20px;">
			<el-page-header content="个人主页" @click="router.push('/');"></el-page-header>
			<el-divider></el-divider>
		</div>
		<div class="bg">
			<el-avatar :src="api.baseUrl + user.avator" :size="150" class="img"/>
			<el-icon :size="90" color="white" class="pic" @click="visible = true"><Picture /></el-icon>
			<p class="name">{{info.account.username}}</p>
			<p class="introduction">简介：{{info.account.introduction}}</p>
		</div>
		<el-tabs v-model="obj.activeName" @tab-click="handleClick">
			<el-tab-pane name="collect" label="我喜欢">
				<div v-if="info.songList.length > 0">
					<el-button icon="Delete" type="danger" @click="multipleCancelCollecte">批量删除</el-button>
					<el-table :data="info.songList" @selection-change="handleSelectionChange" style="margin-top:20px" >
						<el-table-column type="selection" width="55" />
						<el-table-column label="歌名" prop="name" :formatter="format"></el-table-column>
						<el-table-column label="歌手" prop="songName"></el-table-column>
						<el-table-column label="专辑" prop="introduction"></el-table-column>
						<el-table-column label="操作">
							<template #default="scope">
								<el-tooltip effect="light" content="取消收藏" placement="top">
									<i class="fa fa-heart" style="color: red;" @click="cancelCollect(scope.row.songId)"></i>
							    </el-tooltip>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background layout="prev,pager,next" :total="obj.total" v-if="obj.total > 10"
						v-model:currentPage="obj.currentPage" v-model:page-size="obj.pageSize" @current-change="handleCurrentChange" class="pager"></el-pagination>
				</div>
				<el-empty description="暂时没有收藏的歌曲" v-else></el-empty>
			</el-tab-pane>
			<el-tab-pane name="playlist" label="我创建的歌单">
				<div v-if="info.playlist.length > 0">
					<el-button type="primary" @click="obj.visible = true;obj.title='新建歌单'">新建歌单</el-button>
					<el-table :data="info.playlist" :show-header="false">
						<el-table-column width="200">
							<template #default="scope">
								<el-image :src="api.baseUrl + scope.row.pic" style="width: 100px;" @click="router.push(`/playlist/detail/${scope.row.songmenuId}`)"></el-image>
							</template>
						</el-table-column>
						<el-table-column prop="title"></el-table-column>
						<el-table-column>
							<template #default="scope">
								<el-icon @click="editEcho(scope.row)" :size="20"><Edit /></el-icon>&nbsp;&nbsp;&nbsp;
								<el-icon :size="20" @click="deletePlayList(scope.row.songmenuId)"><Delete /></el-icon>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background layout="prev,pager,next" :total="obj.playlistTotal" v-if="obj.playlistTotal > 5"
					v-model:currentPage="obj.playlistCurrent" v-model:page-size="info.pageSize" @current-change="CurrentChange" class="pager"></el-pagination>
				</div>
				<el-empty description="暂时没有自制歌单" v-else></el-empty>
			</el-tab-pane>
		</el-tabs>
		<el-dialog :title="obj.title" v-model="obj.visible"  width="30%" center @close="close">
			<el-form label-width="80px" ref="ruleFormRef" :rules="rules" :model="ruleForm">
				<el-form-item prop="title" label="标题">
					<el-input v-model="ruleForm.title"></el-input>
				</el-form-item>
				<el-form-item label ="歌单类型" prop="classifyId">
					<el-select v-model="ruleForm.classifyId" clearable>
						<el-option v-for="item in info.classifyList" :key="item.classifyId"
						:label="item.classifyName" :value="item.classifyId"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label ="封面" prop="pic">
					<el-upload :action="api.baseUrl + api.upload" :show-file-list="false" :on-success="handleAvatarSuccess"
					class="avatar-uploader">
						<img :src="api.baseUrl + ruleForm.pic" v-if="ruleForm.pic" class="avatar">
						<el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
					</el-upload>
				</el-form-item>
				<el-form-item label="简介" prop="introduction">
					<el-input v-model="ruleForm.introduction" type="textarea" resize="vertical"></el-input>
				</el-form-item>
				<el-form-item label="歌单隐私" prop="state">
					<el-radio-group v-model="ruleForm.state">
						<el-radio :label="0">所有人可见</el-radio>
						<el-radio :label="1">仅自己可见</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="submitForm(ruleFormRef)" >保存</el-button>
					<el-button @click="resetForm(ruleFormRef)">取消</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		<el-dialog title="更换头像" v-model="visible" width="30%" center>
			<el-upload
			    class="upload-demo"
			    drag
			    :action="api.baseUrl + api.upload"
			    :on-success="success"
			    :limit="1"
			    :on-exceed="exceed"
			  >
			    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
			    <div class="el-upload__text">
			      拖拽文件到这或者 <em>点击这里上传</em>
			    </div>
			    <template #tip>
			         <div class="el-upload__tip">
			           上传的图片格式最好是jpg/png的，图片大小不要超过1MB 
			         </div>
			       </template>
			  </el-upload>
			  <template #footer>
			  	<el-button type="primary" @click="updateAvatar">确认更换</el-button>
			  </template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts">
	import personalBg from '@/assets/17.jpg'
	import {sessionStorage} from '@/utils/storage.ts'
	import {api} from '@/api/api.ts'
	import {ref,reactive,onMounted,getCurrentInstance} from 'vue'
	import {useRouter} from 'vue-router'
	import {accountInfo,Song,playList,classify} from '@/types'
	import {get,post} from '@/utils/request.ts'
	import type { TabsPaneContext } from 'element-plus'
	import type { FormInstance, FormRules } from 'element-plus'
	import type { UploadProps } from 'element-plus'
	import { UploadFilled } from '@element-plus/icons-vue'

	let user = sessionStorage.get('info');
	const obj = ref({
		activeName: 'collect',
		currentPage: 1,
		total: 0,
		playlistCurrent: 1,
		playlistTotal: 0,
		title: '新建歌单',
		visible: false,
		pageSize: 10,
		pic: null
	});
	const visible = ref(false);
	const ruleFormRef = ref<FormInstance>();
	const ruleForm = reactive({
		title: '',
		classifyId: null,
		introduction: '',
		pic: null,
		state: 0,
		songmenuId: null
	});
	//验证图片是否上传
	const validateImage = (rule: any, value: any, callback: any) => {
		if(!ruleForm.pic || ruleForm.pic.length == 0){
			callback(new Error('请上传图片'));
		}else{
			callback();
		}
	}
	const rules = reactive<FormRules>({
		title: [
			{required: true, message: 'title不能为空', trigger: 'blur'},
			{min: 3, max: 20, message: '长度在3-20之间', trigger: 'blur'}
		],
		classifyId: [
			{required: true, message: '请选择歌单分类', trigger: 'change'}
		],
		introduction: [
			{required: true, message: '简介不能为空', trigger: 'blur'},
			{min: 10, max: 100, message: '长度在10-100之间', trigger: 'blur'}
		],
		state: [
			{required: true, message: '请选择是否私人歌单', trigger:'change'}
		],
		pic: [
			{validator: validateImage, trigger: 'blur'}
		]
	});
	const info = reactive({
		account: {} as accountInfo,
		songList: [] as Song,
		songIdArr: [] as number,
		playlist: [] as playList,
		classifyList: [] as classify,
		pageSize: 5
	});
	const router = useRouter();
	const {proxy} = getCurrentInstance();

	//提交歌单
	const submitForm = async (formEl: FormInstance | undefined) => {
	  if (!formEl) return;
	  let params = {
	  	accountId: user.accountId,
	  	title: ruleForm.title,
	  	pic: ruleForm.pic,
	  	classifyId: ruleForm.classifyId,
	  	state: ruleForm.state,
	  	introduction: ruleForm.introduction
	  };
	  await formEl.validate((valid, fields) => {
	    if (valid) {
	    	if(obj.value.title === '新建歌单'){
	    		post(api.addPlayList,params).then(res => {
	    			if(res.code == 200){
	    				proxy.$message.success(res.message);
	    				queryPlayList();
	    			}else{
	    				proxy.$message.error(res.message);
	    			}
	    		})
	    	}else{
	    		params.songmenuId = ruleForm.songmenuId;
	    		post(api.updatePlayList,params).then(res => {
	    			if(res.code == 200){
	    				proxy.$message.success(res.message);
	    				queryPlayList();
	    			}else{
	    				proxy.$message.error(res.message);
	    			}
	    		})
	    	}
	    	obj.value.visible = false;
	    } else {
	      console.log('error submit!', fields);
	    }
	  })
	}
	//修改回显
	const editEcho = (row) => {
		obj.value.visible = true;
		obj.value.title = "修改歌单";
		ruleForm.title = row.title;
		ruleForm.classifyId = row.classifyId;
		ruleForm.pic = row.pic;
		ruleForm.introduction = row.introduction;
		ruleForm.state = row.state;
		ruleForm.songmenuId = row.songmenuId;
	}
	//删除歌单
	const deletePlayList = (songmenuId) => {
		post(api.deletePlayList + songmenuId).then(res => {
			if(res.code == 200){
				proxy.$message.success(res.message);
				queryPlayList();
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	//当对话框关闭时调用
	const close = () => {
		ruleForm.title = '';
		ruleForm.classifyId = null;
		ruleForm.pic = null;
		ruleForm.introduction = '';
		ruleForm.state = 0;
		ruleForm.songmenuId = null;
	}
	//重置歌单
	const resetForm = (formEl: FormInstance | undefined) => {
	  if (!formEl) return;
	  formEl.resetFields();
	  obj.value.visible = false;
	}
	//获取歌单类型
	const getClassify = () => {
		get(api.getClassify,null).then(res => {
			info.classifyList = res.data;
		})
	}
	//图片上传成功之后回调方法
	const handleAvatarSuccess: UploadProps['onSuccess'] = (
	  response,
	  uploadFile
	) => {
		ruleForm.pic = response;
	}
	//头像上传成功之后
	const success: UploadProps['onSuccess'] = (
	  response,
	  uploadFile
	) => {
		obj.value.pic = response;
	}
	//图片上传超出限制的回调方法
	const exceed = () => {
		proxy.$message.warning('只能上传一张图片');
	}
	//确认上传图片
	const updateAvatar = () => {
		post(api.updateAvatar,{
			accountId: user.accountId,
			avator: obj.value.pic
		}).then(res => {
			if(res.code == 200){
				proxy.$message.success(res.message);
				user.avator = obj.value.pic;
				sessionStorage.set('info',user);
				user = sessionStorage.get('info');
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	//获取用户详情
	const accountDetail = () => {
		get(api.accountDetail,{
			params: {
				accountId: user.accountId
			}
		}).then(res => {
			if(res.code == 200){
				info.account = res.data;
			}
		});
	}
	//歌曲名格式化方法
	const format = (row, column, cellValue, index) => {
		return cellValue.split('-')[1];
	}
	//获取分类获取所有收藏歌曲
	const getCollectList = () => {
		get(api.queryCollect,{
			params: {
				accountId: user.accountId,
				currentPage: obj.value.currentPage
			}
		}).then(res => {
			info.songList = res.data.records;
			obj.value.total = res.data.total;
		});
	}

	//单个取消收藏
	const cancelCollect = (songId) => {
		post(api.cancelCollect,{
			songId,
			accountId: user.accountId
		}).then(res => {
			if(res.code == 200){
				proxy.$message.success(res.message);
				getCollectList();
			}else{
				proxy.$message.error(res.message);
			}
		})
	}

	//多个取消收藏
	const multipleCancelCollecte = () => {
		if(info.songIdArr.length > 0){
			post(api.multipleCancelCollect,{
				accountId: user.accountId,
				songIdArr: info.songIdArr
			}).then(res => {
				proxy.$message.success(res.message);
				info.songIdArr = [];
				getCollectList();
				console.log(info.songIdArr);
			});
		}else{
			proxy.$message.warning('请选择要删除的歌曲');
		}
		
	}

	//翻页
	const handleCurrentChange = (val: number) => {
	  getSongList();
	}

	//选择收藏歌曲时获取歌曲id
	const handleSelectionChange = (val: Song[]) => {
	  info.songIdArr = [];
	  val.forEach(i => {
	  	info.songIdArr.push(i.songId);
	  })
	}
	//获取所有自制歌单
	const queryPlayList = () => {
		get(api.queryPlayList,{
			params: {
				accountId: user.accountId,
				currentPage: obj.value.playlistCurrent
			}
		}).then(res => {
			obj.value.playlistTotal = res.data.total;
			info.playlist = res.data.records;
		})
	}
	//切换tab返回tab的name
	const handleClick = (tab: TabsPaneContext) => {
	  if(tab.props.name === 'playlist'){
	  	queryPlayList();
	  	getClassify();
	  }
	}
	//歌单的翻页方法
	const CurrentChange = (val: number) => {
		queryPlayList();
	}
	onMounted(() => {
		accountDetail();
		getCollectList();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.personal{
		width: $width81;
		margin: 0 auto;
		.bg{
			background-color: #eaedef;
			display: flex;
			flex-direction: column;
			align-items: center;
			padding: 25px 0;
			position: relative;
			.name{
				font-size: 30px;
				font-weight: 900;
			}
			.introduction{
				font-weight: 700;
			}
			.pic{
				position: absolute;
				top: 50px;
				display: none;
			}
		}
		.bg:hover{
				.pic{
					display: block;
				}
		}
		.pager{
			padding-top: 50px;
			@include center;
		}
		i{
			cursor: pointer;
		}
		.avatar-uploader .avatar {
		  width: 178px;
		  height: 178px;
		  display: block;
		}
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