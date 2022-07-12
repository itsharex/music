<template>
	<el-form :model="info" :rules="rules" ref="ruleForm" label-width="90px">
		<el-form-item label="旧密码" prop="oldpwd">
			<el-input v-model.trim="info.oldpwd" show-password type="password" class="input-width"></el-input>
		</el-form-item>
		<el-form-item label="新密码" prop="newpwd">
			<el-input v-model.trim="info.newpwd" show-password type="password" class="input-width"></el-input>
		</el-form-item>
		<el-form-item label="确认密码" prop="confirmpwd">
			<el-input v-model.trim="info.confirmpwd" show-password type="password" class="input-width"></el-input>
		</el-form-item>
		<el-form-item>
			<el-button @click="resetForm(ruleForm)">清空</el-button>
			<el-button type="primary" @click="confirmModify(ruleForm)">确认修改</el-button>
		</el-form-item>
	</el-form>
</template>

<script setup lang="ts">
	import {ref, reactive, getCurrentInstance} from 'vue'
	import type { FormInstance, FormRules } from 'element-plus'
	import {useRouter} from 'vue-router'
	import {api} from '@/api/api.ts'
	import {post} from '@/utils/request.ts'
	import {sessionStorage,localStorage} from '@/utils/storage.ts'

	const user = sessionStorage.get('info');
	const {proxy} = getCurrentInstance();
	const ruleForm = ref<FormInstance>();
	const router = useRouter();
	const info = ref({
		oldpwd: null,
		newpwd: null,
		confirmpwd: null
	});

	const validateConfirm = (rule: any,value: any,callback: any) => {
		if(!value){
			callback(new Error('确认密码不能为空'));
		}else if(value != info.value.newpwd){
			callback(new Error('两次密码不一致'));
		}else{
			callback();
		}
	}

	const rules = reactive<FormRules>({
		oldpwd: [
			{required: true,message: '旧密码不能为空',trigger: 'blur'}
		],
		newpwd: [
			{required: true,message: '新密码不能为空',trigger: 'blur'}
		],
		confirmpwd: [
			{validator: validateConfirm, trigger: 'blur'}
		]
	});

	const confirmModify = (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		formEl.validate(valid => {
			if(valid){
				post(api.modifyPassword,{
					password: info.value.newpwd,
					oldPassWord: info.value.oldpwd,
					email: user.email,
					accountId: user.accountId
				}).then(res => {
					if(res.code == 200){
						proxy.$message.success(res.message);
						localStorage.remove("Authorization");
						sessionStorage.remove("info");
						router.push('/login');
					}else{
						proxy.$message.error(res.message);
					}
				});
			}else{
				return false;
			}
		})
	}
	const resetForm = (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		formEl.resetFields();
	}

</script>

<style lang="scss" scoped>
	@import '@/assets/global.scss';
	.input-width{
		width: $width30;
	}
</style>