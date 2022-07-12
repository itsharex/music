import {defineStore} from 'pinia'

export const useStore = defineStore({
	id: 'store',
	state: () => ({
		collapse: false
	}),
	getters: () => {
	},
	actions: {
		handleCollapse() {
			this.collapse = !this.collapse;
		}
	}
});