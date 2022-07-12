import {Song} from './singer'

export interface playList = {
	songmenuId: number,
	accountId: number,
	title: string,
	pic: string,
	classifyId: number,
	state: number,
	introduction: string,
	hot: number,
	songList: [] as Song
}