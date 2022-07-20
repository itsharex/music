# music

## 项目介绍

该项目的客户端和管理端都是使用vue3 + vite + typescript框架来实现的，服务端使用了Spring Boot + Mybatis-plus，数据库使用了Mysql,客户端的首页使用了redis来做缓存使得加载速度更加快速

## 技术选型

> 后端技术

| 名称                   | 版本         |
| ---------------------- | ------------ |
| Spring Boot            | 2.6.8        |
| hibernate-validator    | 6.0.19.Final |
| lombok                 | 1.18.24      |
| druid                  | 1.2.8        |
| hutool-all             | 5.5.7        |
| mybatis-plus           | 3.4.3.1      |
| mybatis-plus-generator | 3.3.0        |
| fastjson2              | 2.0.7        |
| jedis                  | 3.7.1        |
| spring-boot-cache      | 2.6.8        |

> 前端技术

| 名称         | 版本   |
| ------------ | ------ |
| vue          | 3.2.25 |
| vue-router   | 4.0.16 |
| pinia        | 2.0.14 |
| element-plus | 2.2.6  |
| axios        | 0.27.2 |
| font-awesome | 4.7.0  |
| lyric-parser | 1.0.1  |
| sass         | 1.52.3 |
| typescript   | 4.5.4  |
| echart       | 5.3.2  |

## 项目截图

> 客户端截图

![image-20220712125712450](https://s2.loli.net/2022/07/12/j4kIody62ha9pCV.png)

![image-20220712125808367](https://s2.loli.net/2022/07/12/cAF4zGDuMHiRrel.png)

![image-20220712125838786](https://s2.loli.net/2022/07/12/8ZKL2oGHUgF5b4k.png)

![image-20220712125900948](https://s2.loli.net/2022/07/12/LfbyJR4iz965Xcx.png)

![image-20220712130030420](https://s2.loli.net/2022/07/12/gcPH5nyGINTYhr7.png)

![image-20220712130042267](https://s2.loli.net/2022/07/12/PSd1QC4RKoWbVXI.png)

![image-20220712130100847](https://s2.loli.net/2022/07/12/zyUDlrGaMTwPJR1.png)

![image-20220712130113413](https://s2.loli.net/2022/07/12/NUpwTbPFihm85WD.png)

![image-20220712130136660](https://s2.loli.net/2022/07/12/qIWtNw42MTVsAby.png)

![image-20220712130149670](https://s2.loli.net/2022/07/12/CHGkiMTRI9mDy6a.png)

![image-20220712130200039](https://s2.loli.net/2022/07/12/NHG4gTrzQXmZwe5.png)

![image-20220712130212777](https://s2.loli.net/2022/07/12/OxqgL5IZpkyRs2A.png)

![image-20220712130234216](https://s2.loli.net/2022/07/12/fxsolUwHCOBde5S.png)

![image-20220712130245540](https://s2.loli.net/2022/07/12/D5H9uSEcowsfPjp.png)

![image-20220712130302028](https://s2.loli.net/2022/07/12/1L6rlXdi492faVu.png)

![image-20220712130333892](https://s2.loli.net/2022/07/12/283sPn5gY1hfkex.png)

> 管理端截图

![image-20220712130437995](https://s2.loli.net/2022/07/12/7gr1XdFSipNRqJI.png)

![image-20220712130529282](https://s2.loli.net/2022/07/12/OubZdfmlqo953V1.png)

![image-20220712130539636](https://s2.loli.net/2022/07/12/g9yoaWs1zBvFTAu.png)

![image-20220712130553062](https://s2.loli.net/2022/07/12/mJMEcqkz1Pb9rXN.png)

![image-20220712130607742](https://s2.loli.net/2022/07/12/lQMCptbG5P1i3Ok.png)

![image-20220712130619102](https://s2.loli.net/2022/07/12/LZxqovO18hzcl7t.png)

![image-20220712130629505](https://s2.loli.net/2022/07/12/xeZNw9QshXak1Ly.png)

![image-20220712130646498](https://s2.loli.net/2022/07/12/5XIf2Ht489WqFOU.png)

![image-20220712130657173](https://s2.loli.net/2022/07/12/FwHfsSPdlC8rKnk.png)

![image-20220712130706477](https://s2.loli.net/2022/07/12/dnjREVOK8uc4SfA.png)

### 项目功能：

* 客户端功能
  * 登录/注册
  * 修改密码
  * 修改个人信息、修改头像
  * 歌曲收藏
  * 创建歌单
  * 歌单歌曲添加
  * 歌词显示
  * 播放歌曲、下载歌曲、拖动音乐进度条、音量控制
  * 歌曲/歌单搜索

* 管理端功能
  * 用户管理
  * 歌手管理
  * 歌曲管理
  * 轮播图管理
  * 登录
  * 修改密码

## 部署教程

`music-cleint`是客户端，`music-manage`是管理端，`music-server`后台

> 客户端和管理端运行

```shell
# 下载依赖
npm install
# 运行项目
npm run dev
```

> 后台运行事项

file.7z是客户端和服务端所需的静态资源，需要放在`D:\project`路径下，如想更换路径只需将application.yaml的`filePath`的值改变即可

`file.7z`的下载路径是https://wwd.lanzouq.com/i64IW07tzd7c

该项目是参考 [music-website](https://github.com/Yin-Hongwei/music-website)所作