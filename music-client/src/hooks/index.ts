export function useHook() {
	function download(res: any,url: string){
		// 获取服务端提供的数据
        let blob = new Blob([res]);
        //创建下载元素
		let eLink = document.createElement("a");
        eLink.download = url.split('/')[2]; //下载的文件命名
        eLink.style.display = "none";
        // 创建下载的链接
        eLink.href = URL.createObjectURL(blob);
        document.body.appendChild(eLink);
        // 点击下载
        eLink.click();
        // 释放掉blob对象
        URL.revokeObjectURL(eLink.href);
        // 下载完成移除元素
        document.body.removeChild(eLink);
	}

	return {download};
}