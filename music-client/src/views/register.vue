<template>
	<div class="register">
		<div class="form">
			<h2 style="text-align: center;padding: 10px 0;">用户注册</h2>
			<el-form :model="info" :rules="rules" ref="ruleForm" label-width="88px">
				<el-form-item label="用户名" prop="username">
					<el-input v-model.trim="info.username" placeholder="用户名"></el-input>
				</el-form-item>
				<el-form-item label="手机号码" prop="phone">
					<el-input v-model.trim="info.phone" placeholder="手机号码"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model.trim="info.email" placeholder="邮箱"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model.trim="info.password" placeholder="密码" type="password" show-password></el-input>
				</el-form-item>
				<el-form-item label="性别" prop="sex">
					<el-radio-group v-model="info.sex">
						<el-radio label="0">男</el-radio>
						<el-radio label="1">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="生日" prop="birth">
					<el-date-picker v-model="info.birth" type="date" label="选择时间" placeholder="选择时间" style="width:100%;"></el-date-picker>
				</el-form-item>
				<el-form-item label="个性签名" prop="introduction">
					<el-input v-model.trim="info.introduction" type="textarea" placeholder="个性签名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-row style="width:100%;" justify="space-between">
						<el-col :span="12">
							<el-button style="width: 97%;" @click="router.push('/login')">登录</el-button>
						</el-col>
						<el-col :span="12">
				            <el-button style="width:100%;" type="primary" @click="register(ruleForm)">注册</el-button>
						</el-col>
					</el-row>
				</el-form-item>
			</el-form>
		</div>
	</div>
</template>

<script setup lang="ts">
	import {ref, reactive, getCurrentInstance } from 'vue'
	import type { FormInstance, FormRules } from 'element-plus'
	import {useRouter} from 'vue-router'
	import {post} from '@/utils/request.ts'
	import {api} from '@/api/api.ts'

	const {proxy} = getCurrentInstance();

	const router = useRouter();
	const ruleForm = ref<FormInstance>();
	const info = reactive({
		username: null,
		password: null,
		email: null,
		sex: null,
		phone: null,
		birth: null,
		introduction: null
	});
	const rules = reactive<FormRules>({
		username: [
			{required: true, message: '用户名不能为空', trigger: 'blur'}
		],
		password: [
			{required: true, message: '密码不能为空', trigger: 'blur'},
			{min: 3, max: 10, message: '密码长度在3-10', trigger: 'blur'}
		],
		email: [
			{required: true, message: '邮箱不能为空', trigger: 'blur'},
			{type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur','change']}
		],
		sex: [
			{required: true, message: '请选择性别', trigger: 'change'}
		]
	});
	const register = async (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		await formEl.validate((valid,fields) => {
			if(valid){
				post(api.register,{
					username: info.username,
					password: info.password,
					email: info.email,
					sex: info.sex,
					phone: info.phone,
					birth: info.birth,
					introduction: info.introduction
				}).then(res => {
					console.log(res)
					if(res.code == 200){
						proxy.$message.success(res.message);
						router.push('/login')
					}else{
						proxy.$message.error(res.message);
					}
				});
			}else{
				console.log('error submit',fields);
			}
		});
	};
</script>

<style scoped lang="scss">
	@import '@/assets/global.scss';
	.register{
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