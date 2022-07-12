import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: { //设置别名
      '@': path.join(__dirname,'src')
    }
  },
  server: {
    host: '0.0.0.0',
    port: 3000,
    open: true, //自动打开游览器
    hmr: true
  }
})
