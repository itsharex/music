import {defineStore} from 'pinia'

export const useStore = defineStore({
	id: 'store',
	state: () => ({
		activeNav: '/',
		keyword: null,
		music: {
			url: null,
			pic: null,
			songName: null,
			singerName: null,
			geci: null,
			songId: null
		},
		playlist: [], //播放列表
		show: false,
		play: false,
		currentTime: 0
	}),
	getters: () => {
	},
	actions: {
		setActiveNav(data){
			this.activeNav = data;
		}
	},
	persist: {
		// 修改存储中使用的键名称，默认为当前 Store的 id
		key: 'store',
		// 修改为 sessionStorage，默认为 localStorage
		storage: window.sessionStorage,
		//只持久化activeNav
		paths: ['activeNav','keyword','music','playlist']
	}
});
