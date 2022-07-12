<template>
	<el-container>
      <el-header><Header/></el-header>
      <el-container>
      	<el-aside width="160px"><Aside /></el-aside>
        <el-main class="content">
        	<router-view />
        </el-main>
      </el-container>
    </el-container>
</template>

<script setup lang="ts">
	import Aside from './Aside.vue'
	import Header from './Header.vue'
</script>

<style scoped lang="scss">
  .content{
    padding: 20px;
  }
</style>