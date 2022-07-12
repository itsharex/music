<template>
	<div class="content">
		<h2 class="title">后台管理系统</h2>
		<el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" class="login">
			<el-form-item prop="username">
				<el-input v-model.trim="ruleForm.username" placeholder="请输入用户名"></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input v-model.trim="ruleForm.password" placeholder="请输入密码" show-password type="password"></el-input>
			</el-form-item>
			<el-form-item prop="code">
				<el-row style="width: 100%;">
					<el-col :span="17">
						<el-input v-model.trim="ruleForm.code" placeholder="验证码"></el-input>
					</el-col>
					&nbsp;
					<el-col :span="5">
						<img :src="ruleForm.verifyCode" alt="验证码" height="32" class="img" @click="getCode">
					</el-col>
				</el-row>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" style="width: 100%;" size="large" @click="login(ruleFormRef)">登录</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script setup lang="ts">
	import {useRouter} from 'vue-router'
	import { reactive, ref, getCurrentInstance,onMounted} from 'vue'
	import type { FormInstance, FormRules } from 'element-plus'
	import {api,baseUrl} from '@/api/api.ts'
	import request,{post} from '@/api/request.ts'
	import {sessionStorage} from '@/utils/storage.ts'

	const router = useRouter();
	const ruleFormRef = ref<FormInstance>();
	const {proxy} = getCurrentInstance();
	const ruleForm = reactive({
		username: '',
		password: '',
		verifyCode: null,
		code: null
	});
	const rules = reactive<FormRules>({
		username: [
			{required: true,message: '用户名不能为空',trigger: 'blur'},
			{min: 3,max: 10,message: '长度在3-10之间',trigger: 'blur'}
		],
		password: [
			{required: true,message: '密码不能为空',trigger: 'blur'},
			{min: 3,max: 10,message: '长度在3-10之间',trigger: 'blur'}
		],
		code: [
			{required: true,message: '验证码不能为空',trigger: 'blur'}
		]
	});

	//用户登录
	const login = async (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		await formEl.validate((valid,fields) => {
			if(valid){
				post(api.login,{
					username: ruleForm.username,
					password: ruleForm.password,
					code: ruleForm.code
				}).then(res => {
					console.log(res);
					if(res.code == 200){
						sessionStorage.set('info',res.data);
						proxy.$message.success(res.message);
						router.push('/')
					}else{
						proxy.$message.error(res.message);
						getCode();
					}
					
				})
			}else{
				console.log('error submig',fields);
			}
		})
	}
	//获取验证码
	const getCode = () => {
		request({
			method: 'get',
			url: api.getCode,
			data: null,
			responseType: 'blob'
		}).then(res => {
			ruleForm.verifyCode =  window.URL.createObjectURL(res.data);
		});
	}
	onMounted(() => {
		getCode();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.content{
		height: $height;
		width: $width;
		background: url('@/assets/bg.jpg');
		background-size: cover;
		display: flex;
		flex-direction: column;
		flex-wrap: wrap;
		justify-content: center;
		align-items: center;
		.title{
			font-size: 25px;
			font-weight: 700;
			padding-bottom: 20px;
		}
		.login{
			width: 360px;
			padding: 30px;
			background-color: #edebeb;
			border-radius: 5px;
			box-shadow: 10px 10px 5px #888888;
			.img{
				line-height: 43px;
			}
		}
	}
</style>