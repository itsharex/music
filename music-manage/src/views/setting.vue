<template>
	<el-row :gutter="20">
		<el-col :span="10">
			<el-card shadow="hover" class="avatar">
				<el-avatar :size="166" :src="avatar"></el-avatar>
			</el-card>
		</el-col>
		<el-col :span="14">
			<el-card shadow="hover">
				<el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="88px">
					<el-form-item label="用户名">
						<el-input v-model="ruleForm.username" :disabled="true"></el-input>
					</el-form-item>
					<el-form-item label="旧密码" prop="oldPassword">
						<el-input v-model.trim="ruleForm.oldPassword" type="password" show-password></el-input>
					</el-form-item>
					<el-form-item label="新密码" prop="password">
						<el-input v-model.trim="ruleForm.password" type="password" show-password></el-input>
					</el-form-item>
					<el-form-item label="确认密码" prop="confirmPwd">
						<el-input v-model.trim="ruleForm.confirmPwd" type="password" show-password></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="save(ruleFormRef)">确认</el-button>
						<el-button @click="reset(ruleFormRef)">重置</el-button>
					</el-form-item>
				</el-form>
			</el-card>
		</el-col>
	</el-row>
</template>

<script setup lang="ts">
	import avatar from '@/assets/avatar.png'
	import { reactive, ref, getCurrentInstance} from 'vue'
	import type { FormInstance, FormRules } from 'element-plus'
	import {sessionStorage} from '@/utils/storage.ts'
	import {api} from '@/api/api.ts'
	import {post} from '@/api/request.ts'
	import {useRouter} from 'vue-router'

	const router = useRouter();
	const user = sessionStorage.get('info');
	const {proxy} = getCurrentInstance();
	const ruleFormRef = ref<FormInstance>();
	const ruleForm = reactive({
		username: user.username,
		oldPassword: null,
		password: '',
		confirmPwd: ''
	});
	//自定义确认密码验证
	const confirmPassword = (rule: any, value: any, callback: any) => {
		if(value == ''){
			callback(new Error('确认密码不能为空'));
		}else if(value !== ruleForm.password){
			callback(new Error('确认密码和新密码不一致'));
		}else{
			callback();
		}
	}
	const rules = reactive<FormRules>({
		oldPassword: [
			{required: true,message: '旧密码不能为空',trigger:'blur'}
		],
		password: [
			{required: true,message: '新密码不能为空',trigger:'blur'},
			{min: 3,max: 10,message: '长度在3-10之间',trigger: 'blur'}
		],
		confirmPwd: [
			{validator: confirmPassword, trigger: 'blur'}
		]
	});

	const save = async (formEl: FormInstance | undefined) => {
		if (!formEl) return
		  await formEl.validate((valid, fields) => {
		    if (valid) {
		      post(api.updatePassword,{
		      	username: ruleForm.username,
		      	oldPassword: ruleForm.oldPassword,
		      	password: ruleForm.password
		      }).then(res => {
		      	console.log(res);
		      	if(res.code === 200){
		      		proxy.$message.success(res.message);
		      		sessionStorage.remove('info');
		      		router.push('/login');
		      	}else{
		      		proxy.$message.error(res.message);
		      	}
		      });
		    } else {
		      console.log('error submit!', fields)
		    }
		  })
	}

	const reset = (formEl: FormInstance | undefined) => {
		if (!formEl) return
	    formEl.resetFields()
	}
</script>

<style scoped lang="scss">
	.avatar{
		height:100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>