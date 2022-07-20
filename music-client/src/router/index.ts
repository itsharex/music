import {createRouter, createWebHashHistory, RouterRecordRaw} from 'vue-router';
import { sessionStorage } from '@/utils/storage';
import { ElMessage } from 'element-plus'

const routes: RouterRecordRaw[] = [
	{
		path: '/',
		component: () => import('@/layout/index.vue'),
		children: [
			{
				path: '/',
				component: () => import('@/views/home.vue')
			},
			{
				path: '/singer',
				component: () => import('@/views/singer/singer.vue')
			},
			{
				path: '/playlist',
				component: () => import('@/views/playlist/playlist.vue')
			},
			{
				path: '/playlist/detail/:id',
				component: () => import('@/views/playlist/playlistDetail.vue'),
				meta: {
					required: true
				}
			},
			{
				path: '/singer/detail/:id',
				component: () => import('@/views/singer/singerDetail.vue'),
				meta: {
					required: true
				}
			},
			{
				path: '/search',
				component: () => import('@/views/search/search.vue'),
				meta: {
					required: true
				}
			},
			{
				path: '/lyrics/:id',
				component: () => import('@/views/Lyrics.vue')
			},
			{
				path: '/login',
				component: () => import('@/views/login.vue')
			},
			{
				path: '/:pathMatch(.*)',
				redirect: '/404'
			},
			{
				path: '/404',
				component: () => import('@/views/404.vue')
			}
		]
	},
	{
		path: '/register',
		component: () => import('@/views/register.vue')
	},
	{
		path: '/userinfo',
		component: () => import('@/views/userInfo.vue')
	},
	{
		path: '/personal',
		component: () => import('@/views/personal.vue')
	}
]

const router = createRouter({
	history: createWebHashHistory(), //路由模式
	routes
})

router.beforeEach((to,form,next)  => {
	const info = sessionStorage.get('info');
	if(to.meta.required && !info){
		ElMessage.warning('请登录账号');
		return next('/login');
	}else{
		next();
	}
});

export default router;