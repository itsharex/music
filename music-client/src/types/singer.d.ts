export interface singer = {
	singerId: number,
	name: string,
	sex: number,
	pic: string,
	birth: Date,
	address: string,
	introduction: string,
	songList: [] as Song
}

export interface Song = {
	songId: number,
	singerId: number,
	name: string,
	introduction: string,
	pic: string,
	url: string,
	geci?: string,
	songName: string,
	createTime: Date,
	updateTime: Date
}