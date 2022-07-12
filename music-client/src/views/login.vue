<template>
	<div class="login">
		<div class="form">
			<h2 style="text-align: center;padding: 10px 0;">登录</h2>
			<el-form :model="info" :rules="rules" ref="ruleForm">
				<el-form-item prop="email">
					<el-input v-model.trim="info.email" placeholder='请输入邮箱'></el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input v-model.trim="info.password" placeholder="请输入密码" type="password" show-password></el-input>
				</el-form-item>
				<el-form-item prop="code">
					<el-row style="width:100%">
						<el-col :span="18">
							<el-input v-model.trim="info.code" placeholder="请输入验证码"></el-input>
						</el-col>
						&nbsp;
						<el-col :span="5">
							<img :src="info.verifyCode" height="31" @click="getCode" style="width:100%">
						</el-col>
					</el-row>
				</el-form-item>
				<el-form-item>
					<el-row style="width:100%;" justify="space-between">
						<el-col :span="12">
							<el-button style="width:97%;" @click="router.push('/register')">注册</el-button>
						</el-col>
						<el-col :span="12">
							<el-button type="primary" @click="login(ruleForm)" style="width: 100%;">登录</el-button>
						</el-col>
					</el-row>
				</el-form-item>
			</el-form>
		</div>
	</div>
</template>

<script setup lang="ts">
	import {ref, reactive, onMounted,getCurrentInstance} from 'vue'
	import type { FormInstance, FormRules } from 'element-plus'
	import {useRouter} from 'vue-router'
	import {api} from '@/api/api.ts'
	import {post} from '@/utils/request.ts'
	import request from '@/utils/request.ts'
	import {sessionStorage} from '@/utils/storage.ts'

	const router = useRouter();
	const {proxy} = getCurrentInstance();
	const ruleForm = ref<FormInstance>();
	const info = reactive({
		email: null,
		password: null,
		code: null,
		verifyCode: null
	});
	const rules = reactive<FormRules>({
		email: [
			{required: true,message: '邮箱不能为空',trigger: 'blur'},
			{type: 'email', message: '请输入正确的邮箱格式',trigger: ['blur','change']}
		],
		password: [
			{required: true,message: '密码不能为空',trigger: 'blur'}
		],
		code: [
			{required: true,message: '验证码不能为空',trigger: 'blur'}
		],
	});

	const login = async (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		await formEl.validate((valid,fields) => {
			if(valid){
				post(api.login,{
					email: info.email,
					password: info.password,
					code: info.code
				}).then(res => {
					console.log(res)
					if(res.code == 200){
						proxy.$message.success(res.message);
						sessionStorage.set("info",res.data.info);
						router.push('/');
					}else{
						proxy.$message.error(res.message);
						getCode();
					}
				});
			}else{
				console.log('error submit',fields);
			}
		})
	}

	const getCode = () => {
		request({
			method: 'get',
			url: api.getCode,
			data: null,
			responseType: 'blob'
		}).then(res => {
			info.verifyCode = window.URL.createObjectURL(res.data);
		});
	}
	onMounted(() => {
		getCode();
	});
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.login{
		width: $width;
		height: $height;
		background: url('@/assets/bg.jpg') no-repeat;
		background-size: cover;
		@include VHcenter;
		.form{
			width: 18%;
			height: 40%;
		}
	}
</style>