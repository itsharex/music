<template>
	<div class="info">
		<div style="padding-top: 20px;">
			<el-page-header content="个人设置" @click="goBack"></el-page-header>
			<el-divider></el-divider>
		</div>
		<el-tabs tab-position="left">
			<el-tab-pane label="个人信息">
				<modifyInfo />
			</el-tab-pane>
			<el-tab-pane label="修改密码">
				<modifyPassword />
			</el-tab-pane>
			<el-tab-pane label="注销账号" class="logoff">
				<el-button type="danger" icon="Delete" @click="logoff">注销账号</el-button>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script setup lang="ts">
	import {getCurrentInstance} from 'vue'
	import {useRouter} from 'vue-router'
	import modifyPassword from '@/components/modifyPassword.vue'
	import modifyInfo from '@/components/modifyInfo.vue'
	import {api} from '@/api/api.ts'
	import {post} from '@/utils/request.ts'
	import {sessionStorage} from '@/utils/storage.ts'

	const router = useRouter();
	const user = sessionStorage.get('info');
	const {proxy} = getCurrentInstance();
	const logoff = () => {
		post(api.logoff+user.accountId,null).then(res => {
			if(res.code == 200){
				proxy.$message.success(res.message);
				sessionStorage.remove('info');
				router.push('/login');
			}else{
				proxy.$message.error(res.message);
			}
		});
	}
	const goBack = () => {
		router.push('/')
	}
</script>

<style lang="scss" scoped>
	@import '@/assets/global.scss';
	.info{
		width: $width81;
		margin: 0 auto;
		.logoff{
			@include VHcenter;
		}
	}
</style>