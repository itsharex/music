<template>
	<el-row :gutter="20">
		<el-col :span="2">
			<el-button type="primary">批量删除</el-button>
		</el-col>
		<el-col :span="7">
			<el-input v-model.trim="obj.keyword" placeholder="请输入用户名搜索">
				<template #append>
					<el-button icon="Search" @click="search"></el-button>
				</template>
			</el-input>
		</el-col>
	</el-row>
	<el-table :data="info.accountList" border class="table">
		<el-table-column label="ID" prop="accountId"></el-table-column>
		<el-table-column label="头像">
			<template #default="scope">
				<img :src="baseUrl + scope.row.avator" alt="头像" width="100">
			</template>
		</el-table-column>
		<el-table-column label="用户名" prop="username"></el-table-column>
		<el-table-column label="手机号" prop="phone"></el-table-column>
		<el-table-column label="邮箱" prop="email"></el-table-column>
		<el-table-column label="性别">
			<template #default="scope">
				<span v-if="scope.row.sex == 0">男</span>
				<span v-else>女</span>
			</template>
		</el-table-column>
		<el-table-column label="账号状态">
			<template #default="scope">
				<span v-if="scope.row.enable == 0">正常</span>
				<span v-else>锁定</span>
			</template>
		</el-table-column>
		<el-table-column label="生日">
			<template #default="scope">
				{{dateFormat(scope.row.birth)}}
			</template>
		</el-table-column>
		<el-table-column label="介绍" prop="introduction"></el-table-column>
		<el-table-column>
			<template #default="scope">
				<el-button type="danger" v-if="scope.row.enable == 0" @click="forbid(scope.row.accountId)">锁定账号</el-button>
				<el-button type="success" v-else @click="deblock(scope.row.accountId)">解锁账号</el-button>
			</template>
		</el-table-column>
	</el-table>
	 <el-pagination background layout="prev, pager, next" :total="obj.total" v-if="obj.total > 0"
	 v-model:page-size="obj.pageSize" v-model:current-page="obj.currentPage" @current-change="currentChange" class="pager"/>
</template>

<script setup lang="ts">
	import {ref,reactive,onMounted,getCurrentInstance} from 'vue'
	import {api,baseUrl} from '@/api/api.ts'
	import {post,get} from '@/api/request.ts'
	import {account} from '@/types'
	import {dateFormat} from '@/utils'

	const {proxy} = getCurrentInstance();
	const obj = ref({
		keyword: null,
		currentPage: 1,
		total: 0,
		pageSize: 10
	});
	const info = reactive({
		accountList: [] as account
	});

	const getAccountList = () => {
		get(api.getAccountList,{
			params: {
				keyword: obj.value.keyword,
				currentPage: obj.value.currentPage
			}
		}).then(res => {
			obj.value.total = res.data.total;
			info.accountList = res.data.records;
		});
	}
	const formatter = (row, column, cellValue, index) => {
		return cellValue.substring(0,10);
	}
	const forbid = (accountId) => {
		post(api.forbidAccount,{
			accountId,
			enable: 1
		}).then(res => {
			if(res.code === 200){
				proxy.$message.success(res.message);
				getAccountList();
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	const deblock = (accountId) => {
		post(api.forbidAccount,{
			accountId,
			enable: 0
		}).then(res => {
			if(res.code === 200){
				proxy.$message.success(res.message);
				getAccountList();
			}else{
				proxy.$message.error(res.message);
			}
		})
	}
	const currentChange = (val) => {
		getAccountList();
	}
	const search = () => {
		obj.value.currentPage = 1;
		getAccountList();
	}

	onMounted(() => {
		getAccountList();
	});
</script>

<style scoped lang="scss">
</style>