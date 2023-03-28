import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router';
import {sessionStorage} from '@/utils/storage.ts'
import { ElMessage } from 'element-plus'

const routes: RouteRecordRaw[] = [
	{
		path: '/login',
		component: () => import('@/views/login.vue')
	},
	{
		path: '/',
		component: () => import('@/layout/layout.vue'),
		children: [
			{
				path: '/',
				component: () => import('@/views/home.vue')
			},
			{
				path: '/user',
				component: () => import('@/views/user.vue')
			},
			{
				path: '/singer',
				component: () => import('@/views/singer.vue')
			},
			{
				path: '/carousel',
				component: () => import('@/views/carousel.vue')
			},
			{
				path: '/singer/:id',
				component: () => import('@/views/song.vue')
			},
			{
				path: '/setting',
				component: () => import('@/views/setting.vue')
			},
			{
				path: '/:pathMatch(.*)*',
				component: () => import('@/views/404.vue')
			}
		]
	}
]

const router = createRouter({
	history: createWebHashHistory(), //路由模式
	routes
});


router.beforeEach((to,form,next)  => {
	if(to.path === '/login') return next();
	const token = sessionStorage.get('info');
	if(!token){
		ElMessage.warning('请登录账号');
		return next('/login');
	}else{
		next();
	}
});

export default router;