import axios,{ AxiosRequestConfig, AxiosResponse, AxiosPromise} from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
	baseURL: 'http://localhost:9999',
	timeout: 30000,
	headers: { 
		'Content-Type': 'application/json;charset=utf-8'
	}
});

request.interceptors.request.use(
	(config: AxiosRequestConfig) => {
		return config;
	},
	error => {
		return Promise.reject(error);
	} 
);

request.interceptors.response.use(
	(config: AxiosResponse) => {
		return config;
	},
	error => {
		return Promise.reject(error);
	}
)

export function get(url: string, params?: object):AxiosPromise{
	return new Promise((resolve,reject) => {
		request.get(url,params).then(
			response => {resolve(response.data)},
			error => {reject(error)}
		);
	})
}

export function post(url: string, data = {}):AxiosPromise{
	return new Promise((resolve,reject) => {
		request.post(url,data).then(
			response => {resolve(response.data)},
			error => {reject(error)}
		);
	})
}

export default request;