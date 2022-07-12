import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import '@/assets/index.scss'

const pinia = createPinia();

const app = createApp(App);

//使用element-plus的icon
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(router)
	.use(pinia)
	.use(ElementPlus,{
		locale: zhCn
	})
	.mount('#app')
