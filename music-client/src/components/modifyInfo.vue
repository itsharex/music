<template>
	<el-form :model="info" :rules="rules" ref="ruleForm" label-width="88px" class="modifyInfo">
		<el-form-item label="用户名" prop="username">
			<el-input v-model.trim="info.username" placeholder="用户名" class="input-width"></el-input>
		</el-form-item>
		<el-form-item label="手机号码" prop="phone">
			<el-input v-model.trim="info.phone" placeholder="手机号码" class="input-width"></el-input>
		</el-form-item>
		<el-form-item label="邮箱" prop="email">
			<el-input v-model.trim="info.email" placeholder="邮箱" class="input-width"></el-input>
		</el-form-item>
		<el-form-item label="性别" prop="sex">
			<el-radio-group v-model="info.sex">
				<el-radio :label="0">男</el-radio>
				<el-radio :label="1">女</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="生日" prop="birth">
			<el-date-picker v-model="info.birth" type="date" label="选择时间" placeholder="选择时间" class="input-width"></el-date-picker>
		</el-form-item>
		<el-form-item label="个性签名" prop="introduction">
			<el-input v-model="info.introduction" type="textarea" placeholder="个性签名" class="input-width"></el-input>
		</el-form-item>
		<el-form-item>
			<el-button @click="resetForm(ruleForm)">取消</el-button>
			<el-button type="primary" @click="save(ruleForm)">保存</el-button>
		</el-form-item>
	</el-form>
</template>

<script setup lang="ts">
	import {ref, reactive, getCurrentInstance,onMounted} from 'vue'
	import type { FormInstance, FormRules } from 'element-plus'
	import {useRouter} from 'vue-router'
	import {api} from '@/api/api.ts'
	import {post,get} from '@/utils/request.ts'
	import {sessionStorage} from '@/utils/storage.ts'

	const router = useRouter();
	const {proxy} = getCurrentInstance();
	const user = sessionStorage.get('info');
	const ruleForm = ref<FormInstance>();
	let info = ref({
		username: null,
		email: null,
		sex: null,
		phone: null,
		birth: null,
		introduction: null,
		accountId: null
	});
	const rules = reactive<FormRules>({
		username: [
			{required: true, message: '用户名不能为空', trigger: 'blur'}
		],
		email: [
			{required: true, message: '邮箱不能为空', trigger: 'blur'},
			{type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur','change']}
		],
		sex: [
			{required: true, message: '请选择性别', trigger: 'change'}
		]
	});

	const save = async (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		await formEl.validate(valid => {
			if(valid){
				post(api.modifyInfo,info.value).then(res => {
					console.log(res)
					if(res.code == 200){
						proxy.$message.success(res.message);
					}else{
						proxy.$message.error(res.message);
					}
				})
			}else{
				return false;
			}
		})
	}
	const resetForm = (formEl: FormInstance | undefined) => {
		if(!formEl) return;
		formEl.resetFields();
	}
	const accountDetail = () => {
		get(api.accountDetail,{
			params: {
				accountId: user.accountId
			}
		}).then(res => {
			if(res.code == 200){
				info.value = res.data;
			}
		});
	}

	onMounted(() => {
		accountDetail();
	});
</script>

<style lang="scss" scoped>
	@import '@/assets/global.scss';
	.input-width{
		width: $width30;
	}
</style>