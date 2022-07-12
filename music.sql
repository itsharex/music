/*
 Navicat Premium Data Transfer

 Source Server         : Local
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : music

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 12/07/2022 13:19:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '邮箱',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '1表示男，0表示女',
  `phone` char(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `birth` datetime NULL DEFAULT NULL COMMENT '生日',
  `introduction` char(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '介绍',
  `avator` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `enable` tinyint(4) NULL DEFAULT 0 COMMENT '0表示账号可用，1表示账号不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (2, 'user', '202cb962ac59075b964b07152d234b70', '333@qq.com', 0, '1432222', '2022-06-29 00:00:00', '测试', '/file/avatar.jpg', 0, '2022-07-10 18:08:27', '2022-07-10 18:08:27');
INSERT INTO `account` VALUES (3, 'lyj', '202cb962ac59075b964b07152d234b70', '666@qq.com', 0, '1533333', '2022-06-22 00:00:00', '404', '/file/fb4b5294b3b04bff98de93b8264a3cce.jpg', 0, '2022-07-09 20:29:42', '2022-07-09 20:29:42');
INSERT INTO `account` VALUES (4, 'test1', '202cb962ac59075b964b07152d234b70', '222@qq.com', 0, NULL, NULL, NULL, '/file/avatar.jpg', 0, '2022-07-09 20:29:42', '2022-07-09 20:29:42');
INSERT INTO `account` VALUES (5, 'test3', '202cb962ac59075b964b07152d234b70\n', '000@qq.com', 1, '11223232', '2022-06-23 00:00:00', 'test3', '/file/avatar.jpg', 0, '2022-07-09 20:29:42', '2022-07-09 20:29:42');

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 'admin', '202cb962ac59075b964b07152d234b70', '2022-07-10 17:03:43');

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `carousel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `img_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '图片地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, '/file/109951167584427823.jpg', '2022-06-24 20:05:40', '2022-06-24 20:05:40');
INSERT INTO `carousel` VALUES (2, '/file/109951167582733776.jpg', '2022-06-24 20:05:50', '2022-06-24 20:05:50');
INSERT INTO `carousel` VALUES (3, '/file/109951167583037910.jpg', '2022-06-24 20:06:19', '2022-06-24 20:06:19');
INSERT INTO `carousel` VALUES (4, '/file/109951167582742274.jpg', '2022-06-24 20:06:54', '2022-06-24 20:06:54');
INSERT INTO `carousel` VALUES (5, '/file/109951167583984150.jpg', '2022-06-24 20:07:41', '2022-06-24 20:07:41');

-- ----------------------------
-- Table structure for classify
-- ----------------------------
DROP TABLE IF EXISTS `classify`;
CREATE TABLE `classify`  (
  `classify_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `classify_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`classify_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classify
-- ----------------------------
INSERT INTO `classify` VALUES (1, '华语');
INSERT INTO `classify` VALUES (2, '英语');
INSERT INTO `classify` VALUES (3, '粤语');
INSERT INTO `classify` VALUES (4, '轻音乐');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `song_id` int(11) NULL DEFAULT NULL COMMENT '收藏歌曲id',
  `account_id` int(11) NOT NULL COMMENT '收藏者id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (7, 1, 3, '2022-07-05 15:45:10');

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer`  (
  `singer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '名字',
  `sex` tinyint(4) NOT NULL COMMENT '性别 0代表男，1代表女，2代表组合',
  `pic` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `birth` date NULL DEFAULT NULL COMMENT '出生年月',
  `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `introduction` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '介绍',
  PRIMARY KEY (`singer_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of singer
-- ----------------------------
INSERT INTO `singer` VALUES (1, '陈奕迅', 0, '/file/singer1.png', '1974-07-27', '香港', '陈奕迅（Eason Chan），1974年7月27日出生于香港，中国香港男歌手、演员，毕业于英国金斯顿大学。\r\n\r\n1995年因获得第14届新秀歌唱大赛冠军而正式出道。1996年发行个人首张专辑《陈奕迅》。1997年主演个人首部电影《旺角大家姐》。1998年凭借歌曲《天下无双》在乐坛获得关注。2000年发行的歌曲《K歌之王》奠定其在歌坛的地位。2001年发行流行摇滚风格的专辑《反正是我》。2003年发行个人首张概念专辑《黑·白·灰》；专辑中的歌曲《十年》获得第4届百事音乐风云榜十大金曲奖。2005年发行的歌曲《浮夸》成为其歌唱生涯的代表作品之一。2006年起，连续9年获得叱咤乐坛流行榜颁奖典礼叱咤乐坛我最喜爱的男歌手奖。2008年1月，获得十大劲歌金曲最受欢迎男歌星奖”。2010年凭借剧情片《金钱帝国》获得星光大典港台年度电影男演员奖 。2012年发行舞曲风格的粤语专辑《…3mm》。2015年凭借专辑《米·闪》获得第26届台湾金曲奖最佳国语男歌手奖。2016年举行“Another Eason\'s LIFE世界巡回演唱会”。同年10月，发行单曲《I Do》。11月，演唱电影《摆渡人》爱情版主题曲《让我留在你身边》。\r\n\r\n2017年5月18日，陈奕迅发行国语EP《放&披风》，包含两首全新国语双单曲《放》和《披风》。同年，确认加盟原创专业音乐节目《中国新歌声第二季》。10月9日，发行国语专辑《C\'mon in~》。2018年发行专辑《L.O.V.E.》。2020年7月11日在香港举办《Live is so much better with Music Eason Chan Charity Concert》慈善音乐会。');
INSERT INTO `singer` VALUES (2, '周杰伦', 0, '/file/singer2.png', '1979-01-18', '台湾省新北市', '周杰伦（Jay Chou），1979年1月18日出生于台湾省新北市，华语流行男歌手、演员、词曲创作人、MV及电影导演、编剧及制作人。2000年被吴宗宪发掘，发行首张个人专辑《Jay》。\r\n\r\n2001年发行专辑《范特西》。2002年在中国、新加坡、马来西亚、美国等地举办首场个人世界巡回演唱会。\r\n\r\n2003年登上美国《时代周刊》亚洲版封面人物。\r\n\r\n周杰伦的音乐融合中西方元素，风格多变，四次获得世界音乐大奖最畅销亚洲艺人。凭借专辑《Jay》、《范特西》、《叶惠美》及《跨时代》四次获得金曲奖\"最佳国语专辑\"奖，并凭借《魔杰座》、《跨时代》获得第20届和第22届金曲奖“最佳国语男歌手”奖；\r\n\r\n2005年开始涉足影视，以电影《头文字D》获第42届台湾电影金马奖及第25届香港电影金像奖“最佳新人”奖。\r\n\r\n2006年起连续三年获得世界音乐大奖中国区最畅销艺人奖。\r\n\r\n2007年自立门户，成立JVR（杰威尔）有限公司，自编自导自演的电影《不能说的秘密》获得第44届台湾电影金马奖“年度台湾杰出电影”奖。\r\n\r\n2008年凭借歌曲《青花瓷》获得第19届金曲奖最佳作曲人奖。\r\n\r\n2009年入选美国CNN亚洲极具影响力人物；同年凭借专辑《魔杰座》获得第20届金曲奖最佳国语男歌手奖。\r\n\r\n2010年入选美国《Fast Company》评出的“全球百大创意人物”。\r\n\r\n2011年凭借专辑《跨时代》再度获得金曲奖最佳国语男歌手奖，并且第4次获得金曲奖最佳国语专辑奖；同年主演好莱坞电影《青蜂侠》。\r\n\r\n2012年登福布斯中国名人榜榜首。\r\n\r\n2013年自编自导自演第二部电影《天台爱情》取得了不俗的票房与口碑。\r\n\r\n2014年加盟好莱坞电影《惊天魔盗团2》；同年发行华语乐坛首张数字音乐专辑《哎呦，不错哦》。\r\n\r\n娱乐圈外，周杰伦在2011年跨界担任华硕（ASUS）笔电外观设计师并入股香港文化传信集团。2012在中国内地开设真爱范特西连锁KTV。\r\n\r\n除了力拼自己的事业，周杰伦还热心公益慈善活动，多次向内地灾区捐款并与众多艺人募款新建希望小学。\r\n\r\n2014年获QQ音乐年度盛典“港台最受欢迎男歌手”及压轴大奖“最佳全能艺人”。\r\n\r\n2015年担任《中国好声音 第四季》导师。\r\n\r\n2016年发行演唱会专辑《周杰伦魔天伦世界巡回演唱会》；同年推出专辑《周杰伦的床边故事》。\r\n\r\n2017年，确认加盟原创专业音乐节目《中国新歌声第二季》。\r\n\r\n2019年起举行“嘉年华”世界巡回演唱会。');
INSERT INTO `singer` VALUES (3, '薛之谦', 0, '/file/singer3.png', '1983-07-17', '上海', '薛之谦，1983年7月17日出生于上海市，中国内地男歌手。\r\n\r\n2006年6月9日，薛之谦发行首张个人原创同名专辑《薛之谦》；2007年在中国原创音乐流行榜上凭借《认真的雪》获得内地金曲奖；2008年11月26日发行第三张个人原创专辑《深深爱过你》；12月20日举行首场演唱会“谦年传说”；2012年7月17日发行专辑《几个薛之谦》。2013年11月发行专辑《意外》。\r\n\r\n2015年4月1日，薛之谦主演《男人帮·朋友》预告片发布；6月10日正式签约海蝶音乐公司，升为制作人并发行原创EP《绅士》；同年，他还主演都市励志剧《妈妈像花儿一样》。2016年，凭借EP《绅士》、《一半》获得第16届全球华语歌曲排行榜最受欢迎男歌手、五大最受欢迎男歌手奖、上海地区杰出歌手奖及最受欢迎创作歌手奖，而歌曲《初学者》则获得年度二十大金曲奖。2017年4月，开启“我好像在哪见过你”全国巡回演唱会；同年，薛之谦获得第7届全球流行音乐年度盛典年度最佳男歌手及MusicRadio榜中国TOP排行榜内地最佳男歌手。2018年7月，薛之谦“摩天大楼”世界巡回演唱会于北京站启程，整个巡演横跨4大洲，8个国家，21个城市，累计上演场次23场。');
INSERT INTO `singer` VALUES (4, '王心凌', 1, '/file/singer4.png', '1982-09-05', '台湾省新竹县', '王心凌，1982年9月5日出生于台湾省新竹县，中国台湾女歌手、演员。\r\n\r\n2000年主演客家语电视短剧《车正在追》。2003年发行首张个人专辑《Cyndi Begin》。2011年凭借《爱上查美乐》获得华鼎奖偶像励志类题材最佳女主角。2012年11月30日加盟环球唱片并发行首张专辑《爱不爱》。2013年7月相隔两年再拍大型偶像剧《幸福选择题》。2014年7月25日发行个人第十张专辑《第10个王心凌》；同年12月在微电影《爱的圆舞曲》中饰演一名芭蕾舞者。 2015年，王心凌发行第十一张专辑《敢要敢不要》。\r\n\r\n2016年1月2日，王心凌首度登上台北小巨蛋，举办“2016王心凌Cyndi Wants!世界巡回演唱会”。2017年，王心凌重启“Cyndi Wants!世界巡回演唱会”。 2018年，王心凌凭借第十二张专辑《CYNDILOVES2SING 爱。心凌》获得华语五大金榜年榜女冠。2019年，开启“CYNDI LOVES 2SING 爱。心凌巡回演唱会”。');
INSERT INTO `singer` VALUES (5, 'G.E.M. 邓紫棋', 1, '/file/singer5.png', '1991-08-16', '上海', '邓紫棋（Gloria Tang Tsz-Kei），又名G.E.M.，中国香港创作型女歌手、词曲创作人。');
INSERT INTO `singer` VALUES (6, '林俊杰', 0, '/file/singer6.png', '1981-03-27', '新加坡', '林俊杰（JJ Lin），新加坡人，流行歌手、词曲创作者、音乐制作人。JJ林俊杰的创作来自最深的情感，他的声音唱出灵魂的璀璨，他把音乐和梦想当做能量，一路走到无人取代的地位，他写下华语乐坛最动人的经典乐章，撼动亚洲数十亿颗心跳。他是亚洲乐坛全能唱作天王 JJ 林俊杰。2003年首发第一张个人创作专辑《乐行者》，取得不俗成绩；其杰出的创作才能又在之后2004年的凭借歌曲【江南】而成名，并于同年获得第15届金曲奖之「最佳演唱新人奖」。随后的【小酒窝】、【曹操】、【她说】等歌曲亦造成广大回响。2011年8月8日携手华纳，迈出世界。\r\n\r\n2014年以专辑《因你而在 Stories Untold》夺得第25届台湾金曲奖最佳国语男歌手奖 。\r\n\r\n2007年成立个人音乐制作公司JFJ Productions，负责单曲及专辑制作；2008年创立潮流品牌SMG；2012年发行故事影像书《记得》，成功跻身畅销书作家行列 。\r\n\r\n除演艺事业外，林俊杰还担任了2008年北京奥运会 及2010年新加坡青奥会 火炬手，为2010年上海世博会新加坡馆创作并演唱了主题曲《感动每一刻》 。凭借在音乐创作与慈善公益等方面的表现，林俊杰于2009年荣获“新加坡杰出青年奖” ，2014年获得第5届“通商中国青年奖” 。\r\n\r\n林俊杰第十一张创作专辑《新地球GENESIS》于2014年12月27日正式发行 。2013年开启的“时线TimeLine世界巡回演唱会”横跨四大洲、24个城市。12月19日携“时线：新地球世界巡回演唱会”登陆高雄巨蛋；12月25日发行首张实验专辑《和自己对话》。2016年1月15日首次正式参演的电影《消失的爱人》上映。\r\n\r\n2017年8月23日，携虚拟乐团“消除联萌”推出的全新单曲《丹宁执着》上线。2017年10月27日，二度担任常驻导师的浙江卫视音乐综艺《梦想的声音第二季》开播；11月，受邀与Luis Fonsi合作推出2017年度热单《Despacito》中文版；12月29日将推出个人第13张创作专辑《伟大的渺小》。\r\n\r\n2018年3月17日，林俊杰“圣所”世界巡回演唱会将从上海起跑。；6月16日，“圣所”演唱会将在济南奥体中心体育馆开唱。\r\n\r\n截至2020年，已发行13张正式专辑，累计创作数百首歌曲。');
INSERT INTO `singer` VALUES (7, 'BEYOND', 2, '/file/singer7.png', '1974-07-27', '', 'Beyond，中国香港摇滚乐队，由黄家驹、黄贯中、黄家强、叶世荣组成。\r\n\r\n1983年Beyond成立，同年参加“山叶吉他比赛”获得冠军并正式出道。1986年自资发行乐队首张专辑《再见理想》。1988年凭借粤语专辑《秘密警察》获得关注。1989年凭借歌曲《真的爱你》获得第12届十大中文金曲奖、第7届十大劲歌金曲奖 。1990年凭借歌曲《光辉岁月》获得第8届十大劲歌金曲奖 。1991年主演电影《Beyond日记之莫欺少年穷》，同年9月，在香港红磡体育馆举办“生命接触”演唱会 。1992年，赴日本发展演艺事业。1993年发行粤语专辑《乐与怒》，专辑中的歌曲《海阔天空》获得第16届十大中文金曲奖；6月30日，乐队主唱黄家驹去世，Beyond以三名成员的组成形式继续发展。\r\n\r\n1994年发行专辑《二楼后座》 。1996年获得香港叱咤乐坛流行榜颁奖典礼叱咤乐坛组合金奖。1997年发行融入迷幻电子音乐的专辑《请将手放开》，并开始转变音乐风格。1999年Beyond宣布暂时解散 。2002年获得第25届十大中文金曲金曲银禧荣誉大奖 。\r\n\r\n2003年正式复出歌坛并举办“Beyond超越Beyond”世界巡回演唱会 。2004年凭借电影《无间道Ⅱ》的主题曲《长空》获得香港电影金像奖最佳原创电影歌曲奖 。2005年举行“Beyond The Story Live 2005”世界巡回告别演唱会；同年Beyond正式解散 。\r\n\r\n演艺事业外，Beyond关心公益慈善。1991年前往非洲探访第三世界的穷困人民，并成立第三世界基金 。');
INSERT INTO `singer` VALUES (8, '五月天', 2, '/file/singer8.png', '1974-07-27', '', '五月天，台湾摇滚乐团，由乐队团长兼吉他手怪兽（温尚翊）、主唱阿信（陈信宏）、吉他手石头（石锦航）、贝斯玛莎（蔡升晏）和鼓手冠佑（刘谚明）组成，前身为“So Band”乐团，在1997年3月29日更名为“五月天”。至2016年，四次拿下\"台湾金曲奖\"和“最佳乐团奖”。\r\n\r\n1999年，发行首张专辑《第一张创作专辑》。2000年7月7日，发行了第二张创作专辑《爱情万岁》。2001年7月6日，推出暂别歌坛之作第三张创作专辑《人生海海》。2003年11月11日，发行第四张专辑《时光机》。2004年11月5日，推出第五张专辑《神的孩子都在跳舞》。2005年8月，推出精选辑《知足Just My Pride最真杰作选》。2006年12月29日，发行第六张专辑《为爱而生》。2008年10月23日，发行第七张专辑《后青春期的诗》。2011年12月16日，发行第八张专辑《第二人生》。2016年7月21日，发行第九张专辑《自传》。乐团成员也积极为其他歌手创作歌曲，并参与歌曲的编曲、演奏、和声等幕后制作。2017年5月2日，为电影《春娇救志明》演唱主题曲《春娇救志明》，这也是歌曲《志明与春娇》的国语版。\r\n\r\n2019年4月25日，五月天演唱会电影《人生无限公司》宣布定档5月24日,同时影片发布了定档预告和海报。2020年发行单曲《因为你 所以我》。');
INSERT INTO `singer` VALUES (9, '李荣浩', 0, '/file/singer9.png', '1985-07-11', '安徽省蚌埠市', '李荣浩，中国流行乐男歌手、音乐制作人、演员、吉他手。\r\n\r\n2013年9月16日，发行首张专辑《模特》，凭借这张专辑提名第25届金曲奖五项大奖并获得最佳新人奖，实现了从制作人到歌手的转型；2014年9月4日签约华纳唱片，发行第二张同名专辑《李荣浩》，并在次年举办8场“天生李荣浩巡回演唱会”。2016年1月22日发行第三张专辑《有理想》，同年举办“有理想世界巡回演唱会”。2018年1月，参加爱奇艺综艺节目《偶像练习生》。2019年，担任浙江卫视歌唱选秀节目《中国好声音》的导师；同年，获得Mnet亚洲音乐大奖亚洲最佳艺人奖。2020年8月27日，李荣浩名列《2020福布斯中国名人榜》第59位。');
INSERT INTO `singer` VALUES (10, '张杰', 0, '/file/singer10.png', '1982-12-20', '四川成都', '张杰，1982年12月20日出生于四川成都，毕业于四川师范大学，内地流行男歌手，行星文化（音乐厂牌）创始人。\r\n\r\n2004年获我型我秀全国总冠军出道。2007年参加快乐男声；推出EP《最美的太阳》。2008年推出专辑《明天过后》；担任北京奥运会火炬手。2010年推出专辑《这，就是爱》；担任博鳌亚洲论坛表演嘉宾；获韩国MAMA颁奖礼亚洲之星奖。2012年在人民大会堂开启巡演；作品《风华正茂》获湖南省五个一工程奖。2013年首登央视春晚演唱《给我你的爱》；赴美国伯克利音乐学院进修。2014年献唱南京青奥会主题曲《点亮未来》；获第42届全美音乐奖(AMA)年度国际艺人奖。2016年在北京工人体育场开启张杰我想世界巡回演唱会，为内地首位工人体育场开唱80后歌手；献唱好莱坞《星际迷航3》、《间谍同盟》电影推广曲。2017年在洛杉矶杜比剧院开唱，为首位在奥斯卡杜比剧院开唱内地歌手；献唱好莱坞《变形金刚5》片尾曲《Torches》 和第十三届全运会主题曲《健康赢未来》；入驻重庆杜莎夫人蜡像馆；十月世界巡演登陆米兰、伦敦，为首位在欧洲开唱的内地歌手。\r\n\r\n至今已发行12张唱片，举办41场个人演唱会，获33次“最受欢迎男歌手”和13次“最佳男歌手”奖，达成内地乐坛“最受欢迎男歌手”大满贯，三度献唱好莱坞电影推广曲，三度献唱央视春晚。\r\n\r\n此外，张杰致力于慈善公益事业，2012年创立北斗星空爱心基金，累计创建29间“张杰音乐梦想教室”，获中国儿童慈善奖-突出贡献奖 、位列中国慈善名人榜第22位');
INSERT INTO `singer` VALUES (11, '许嵩', 0, '/file/singer11.png', '1986-05-14', '安徽合肥', '许嵩（Vae），1986年5月14日生于安徽省合肥市，中国内地创作型男歌手，毕业于安徽医科大学。\r\n\r\n2009年1月，许嵩发行首张词曲全创作专辑《自定义》；2010年1月，发行第二张词曲全创作专辑《寻雾启示》；2011年4月，发行第三张词曲全创作专辑《苏格拉没有底》；2012年7月，发行第四张词曲全创作专辑《梦游计》；2014年8月，发行第五张词曲全创作专辑《不如吃茶去》；2016年6月，发行第六张词曲全创作专辑《青年晚报》；2018年7月，发行第七张词曲全创作专辑《寻宝游戏》。\r\n\r\n2006年起许嵩开始发表音乐作品，从而正式出道 。至2020年已成功发行七张个人原创专辑以及近四十首原创单曲，所有歌曲由许嵩创作词曲及音乐制作。截至2020年，许嵩已获得中歌榜、中国华鼎奖、东方风云榜、音乐风云榜等权威音乐榜单颁发的“最佳创作歌手奖”、“最佳唱作人奖”等个人奖项共19次；应邀为游戏、影视剧等创作主题曲包括《雨幕》（新天龙八部端游主题曲）、《千古》（电视剧《花千骨》片头曲）等歌曲共15首；五登央视舞台，七上卫视晚会献唱');

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song`  (
  `song_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `singer_id` int(11) NULL DEFAULT NULL COMMENT '歌手id',
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '歌曲名字',
  `introduction` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '介绍',
  `pic` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '歌曲图片',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '歌手地址',
  `geci` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '歌词',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `song_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '歌手名字',
  PRIMARY KEY (`song_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of song
-- ----------------------------
INSERT INTO `song` VALUES (1, 2, '周杰伦-稻香', '魔杰座', '/file/daoxiang.jpg', '/file/周杰伦-稻香.mp3', '[by:橘子大宝宝]\n[ti:稻香]\n[ar:周杰伦]\n[00:31.00]对这个世界如果你有太多的抱怨\n[00:34.05]跌倒了就不敢继续往前走\n[00:37.02]为什麽人要这麽的脆弱 堕落\n[00:41.55]请你打开电视看看\n[00:43.14]多少人为生命在努力勇敢的走下去\n[00:46.89]我们是不是该知足\n[00:49.43]珍惜一切 就算没有拥有\n[00:54.24]还记得你说家是唯一的城堡\n[00:57.49]随着稻香河流继续奔跑\n[01:00.42]微微笑 小时候的梦我知道\n[01:05.51]不要哭让萤火虫带着你逃跑\n[01:09.21]乡间的歌谣永远的依靠\n[01:12.12]回家吧 回到最初的美好\n[01:41.14]不要这麽容易就想放弃 就像我说的\n[01:44.59]追不到的梦想 换个梦不就得了\n[01:47.55]为自己的人生鲜艳上色 先把爱涂上喜欢的颜色\n[01:51.80]笑一个吧 功成名就不是目的\n[01:55.62]让自己快乐快乐这才叫做意义\n[01:58.51]童年的纸飞机 现在终於飞回我手里\n[02:04.41]所谓的那快乐 赤脚在田里追蜻蜓追到累了\n[02:08.62]偷摘水果被蜜蜂给叮到怕了 谁在偷笑呢\n[02:13.16]我靠着稻草人吹着风唱着歌睡着了\n[02:16.72]哦 哦 午后吉它在虫鸣中更清脆\n[02:19.58]哦 哦 阳光洒在路上就不怕心碎\n[02:22.53]珍惜一切 就算没有拥有\n[02:27.36]还记得你说家是唯一的城堡\n[02:31.24]随着稻香河流继续奔跑\n[02:34.18]微微笑 小时候的梦我知道\n[02:39.35]不要哭让萤火虫带着你逃跑\n[02:42.94]乡间的歌谣永远的依靠\n[02:45.91]回家吧 回到最初的美好\n[02:51.03]还记得你说家是唯一的城堡\n[02:54.55]随着稻香河流继续奔跑\n[02:57.62]微微笑 小时候的梦我知道\n[03:02.67]不要哭让萤火虫带着你逃跑\n[03:06.35]乡间的歌谣永远的依靠\n[03:09.23]回家吧 回到最初的美好', '2022-06-30 15:22:01', '2022-06-30 15:22:01', '周杰伦');
INSERT INTO `song` VALUES (2, 2, '周杰伦-告白气球', '床边故事', '/file/gaobaiqiqui.jpg', '/file/周杰伦-告白气球.mp3', '[00:24.456]塞纳河畔 左岸的咖啡\r\n[00:27.036]我手一杯 品尝你的美\r\n[00:30.125]留下唇印 的嘴\r\n[00:35.267]花店玫瑰 名字写错 谁\r\n[00:37.856]告白气球 风吹到 对街\r\n[00:40.947]微笑 在天上飞\r\n[00:45.038]你说 你有点难追\r\n[00:47.617]想让 我知难而退\r\n[00:50.204]礼物 不需挑最贵\r\n[00:52.787]只要 香榭的落叶\r\n[00:55.647]喔 营造浪漫 的约会\r\n[00:58.248]不害怕 搞砸一切\r\n[01:00.807]拥有你就拥有 全世界\r\n[01:05.977]亲爱的 爱上你 从那天起\r\n[01:11.917]甜蜜的很轻易\r\n[01:16.547]亲爱的 别任性 你的眼睛\r\n[01:22.755]在说我愿意\r\n[01:50.293]塞纳河畔 左岸的咖啡\r\n[01:52.343]我手一杯 品尝你的美\r\n[01:55.453]留下唇印的 嘴\r\n[02:00.343]花店玫瑰 名字写错谁\r\n[02:03.162]告白气球 风吹到 对街\r\n[02:06.013]微笑 在天上飞\r\n[02:10.113]你说 你有点难追\r\n[02:12.702]想让 我知难而退\r\n[02:15.532]礼物 不需挑最贵\r\n[02:18.113]只要 香榭的落叶\r\n[02:20.690]喔 营造浪漫 的约会\r\n[02:23.513]不害怕 搞砸一切\r\n[02:26.342]拥有你就拥有 全世界\r\n[02:31.003]亲爱的 爱上你 从那天起\r\n[02:37.213]甜蜜的很轻易\r\n[02:41.613]亲爱的 别任性 你的眼睛\r\n[02:48.063]在说我愿意\r\n[02:52.733]亲爱的 爱上你 恋爱日记\r\n[02:58.653]飘香水的回忆\r\n[03:03.312]一整瓶 的梦境 全都有你\r\n[03:09.498]搅拌在一起\r\n[03:14.163]亲爱的 别任性 你的眼睛\r\n[03:22.413]在说我愿意\r\n', '2022-07-04 17:26:38', '2022-07-04 17:26:38', '周杰伦');
INSERT INTO `song` VALUES (3, 2, '周杰伦-夜曲', '床边故事', '/file/gaobaiqiqui.jpg', '/file/周杰伦-夜曲.mp3', '[00:23.310]一群嗜血的蚂蚁 被腐肉所吸引\n[00:27.490]我面无表情 看孤独的风景\n[00:30.540]失去你 爱恨开始分明\n[00:33.030]失去你 还有什N事好关心\n[00:35.770]当鸽子不再象徵和平\n[00:37.550]我终于被提醒\n[00:38.910]广场上喂食的是秃鹰\n[00:41.580]我用漂亮的押韵\n[00:43.030]形容被掠夺一空的爱情\n[00:46.720]啊 乌云开始遮蔽 夜色不干净\n[00:49.520]公园里 葬礼的回音 在漫天飞行\n[00:52.380]送你的 白色玫瑰\n[00:53.820]在纯黑的环境凋零\n[00:55.520]乌鸦在树枝上诡异的很安静\n[00:57.880]静静听 我黑色的大衣\n[00:59.870]想温暖你 日渐冰冷的回忆\n[01:02.220]走过的 走过的 生命\n[01:03.650]啊 四周弥漫雾气\n[01:05.070]我在空旷的墓地\n[01:06.340]老去后还爱你\n[01:07.430]为你弹奏萧邦的夜曲\n[01:11.550]纪念我死去的爱情\n[01:14.300]跟夜风一样的声音\n[01:17.030]心碎的很好听\n[01:19.830]手在键盘敲很轻\n[01:22.570]我给的思念很小心\n[01:25.300]你埋葬的地方叫幽冥\n[01:29.990]为你弹奏萧邦的夜曲\n[01:33.570]纪念我死去的爱情\n[01:36.340]而我为你隐姓埋名\n[01:39.150]在月光下弹琴\n[01:41.900]对你心跳的感应\n[01:44.670]还是如此温热亲近\n[01:47.410]怀念你那鲜红的唇印\n[01:53.270]\n[02:14.540]那些断翅的蜻蜓 散落在这森林\n[02:17.600]而我的眼睛 没有丝毫同情\n[02:20.450]失去你 泪水混浊不清\n[02:22.950]失去你 我连笑容都有阴影\n[02:25.680]风在长满青苔的屋顶\n[02:27.310]嘲笑我的伤心\n[02:29.170]像一口没有水的枯井\n[02:31.510]我用凄美的字型\n[02:33.030]描绘后悔莫及的那爱情\n[02:36.590]为你弹奏萧邦的夜曲\n[02:39.380]纪念我死去的爱情\n[02:42.100]跟夜风一样的声音\n[02:44.890]心碎的很好听\n[02:47.640]手在键盘敲很轻\n[02:50.370]我给的思念很小心\n[02:53.150]你埋葬的地方叫幽冥\n[02:57.700]为你弹奏萧邦的夜曲\n[03:01.360]纪念我死去的爱情\n[03:04.220]而我为你隐姓埋名 在月光下弹琴\n[03:09.620]对你心跳的感应 还是如此温热亲近\n[03:15.170]怀念你那鲜红的唇印\n[03:20.810]一群嗜血的蚂蚁 被腐肉所吸引\n[03:23.630]我面无表情 看孤独的风景\n[03:26.660]失去你 爱恨开始分明\n[03:29.070]失去你 还有什N事好关心\n[03:31.860]当鸽子不再象徵和平\n[03:33.560]我终于被提醒\n[03:35.320]广场上喂食的是秃鹰\n[03:37.670]我用漂亮的押韵\n[03:38.970]形容被掠夺一空的爱情', '2022-06-30 15:22:39', '2022-06-30 15:22:39', '周杰伦');
INSERT INTO `song` VALUES (4, 1, '陈奕迅 + 王菲-因为爱情', 'Stranger Under My Skin', '/file/yinweiaiqing.jpg', '/file/陈奕迅 + 王菲-因为爱情.mp3', '[00:00.00] 作曲 : 小柯\n[00:01.00] 作词 : 小柯\n[00:11.739]给你一张过去的CD\n[00:16.299]听听那时我们的爱情\n[00:21.340]有时会突然忘了我还在爱着你\n[00:30.990]\n[00:33.390]再唱不出那样的歌曲\n[00:37.690]听到都会红着脸躲避\n[00:43.140]虽然会经常忘了我依然爱着你\n[00:52.110]\n[00:53.790]因为爱情 不会轻易悲伤\n[00:59.540]所以一切都是幸福的模样\n[01:05.129]因为爱情 简单的生长\n[01:10.730]依然随时可以为你疯狂\n[01:15.200]\n[01:16.000]因为爱情 怎么会有沧桑\n[01:21.350]所以我们还是年轻的模样\n[01:26.890]因为爱情 在那个地方\n[01:32.530]依然还有人在那里游荡人来人往\n[01:42.128]\n[02:00.730]再唱不出那样的歌曲\n[02:05.300]听到都会红着脸躲避\n[02:10.509]虽然会经常忘了我依然爱着你\n[02:20.510]\n[02:20.779]因为爱情 不会轻易悲伤\n[02:26.820]所以一切都是幸福的模样\n[02:32.399]因为爱情 简单的生长\n[02:38.100]依然随时可以为你疯狂\n[02:42.140]\n[02:42.589]因为爱情 怎么会有沧桑\n[02:48.499]所以我们还是年轻的模样\n[02:54.700]因为爱情 在那个地方\n[02:59.909]依然还有人在那里游荡人来人往\n[03:09.279]\n[03:11.629]给你一张过去的CD\n[03:16.279]听听那时我们的爱情\n[03:21.640]有时会突然忘了我还在爱着你', '2022-06-30 15:23:18', '2022-06-30 15:23:18', '陈奕迅 & 王菲');
INSERT INTO `song` VALUES (5, 1, '陈奕迅-不要说话', '不想放手', '/file/buyaoshuohua.jpg', '/file/陈奕迅-不要说话.mp3', '[ti:不要说话]\n[ar:陈奕迅]\n[al:博儿Lrc试练]\n[by:博儿]\n[00:00.00] 作曲 : 小柯\n[00:01.00] 作词 : 小柯\n[00:18.77]深色的海面布满白色的月光\n[00:24.51]\n[00:25.11]我出神望着海 心不知飞哪去\n[00:31.64]听到她在告诉你\n[00:35.28]说她真的喜欢你\n[00:39.48]我不知该 躲哪里\n[00:47.17]爱一个人是不是应该有默契\n[00:54.15]我以为你懂得每当我看着你\n[01:00.07]我藏起来的秘密\n[01:03.67]在每一天清晨里\n[01:07.60]暖成咖啡 安静的拿给你\n[01:14.33]愿意 用一支黑色的铅笔\n[01:18.81]画一出沉默舞台剧\n[01:22.95]灯光再亮 也抱住你\n[01:28.53]愿意 在角落唱沙哑的歌\n[01:33.06]再大声也都是给你\n[01:37.24]请用心听 不要说话\n[01:51.54]爱一个人是不是应该要默契\n[01:58.36]我以为你懂得每当我看着你\n[02:04.34]我藏起来的秘密\n[02:08.22]在每一天清晨里\n[02:11.47]暖成咖啡 安静的拿给你\n[02:18.49]愿意 用一支黑色的铅笔\n[02:22.92]画一出沉默舞台剧\n[02:27.31]灯光再亮 也抱住你\n[02:33.04]愿意 在角落唱沙哑的歌\n[02:37.33]再大声也都是给你\n[02:41.46]请用心听 不要说话\n[03:15.81]愿意 用一支黑色的铅笔\n[03:19.95]画一出沉默舞台剧\n[03:24.43]灯光再亮 也抱住你\n[03:29.82]愿意 在角落唱沙哑的歌\n[03:34.19]再大声也都是给你\n[03:38.48]请原谅我 不会说话\n[03:44.11]愿意 用一支黑色的铅笔\n[03:48.55]画一出沉默舞台剧\n[03:52.68]灯光再亮 也抱住你\n[03:58.35]愿意 在角落唱沙哑的歌\n[04:02.84]再大声也都是给你\n[04:06.97]爱是用心吗 不要说话', '2022-06-30 15:23:41', '2022-06-30 15:23:41', '陈奕迅');
INSERT INTO `song` VALUES (6, 1, '陈奕迅-红玫瑰', 'The 1st Eleven Years 然后呢?', '/file/hongmeigui.jpg', '/file/陈奕迅-红玫瑰.mp3', '[ar:陈奕迅]\n[00:00.00] 作曲 : 梁翘柏\n[00:01.00] 作词 : 李焯雄\n[00:16.32]梦里梦到醒不来的梦\n[00:18.92]红线里被软禁的红\n[00:23.62]所有刺激剩下疲乏的痛\n[00:26.82]再无动于衷\n[00:30.12]从背后抱你的时候\n[00:34.02]期待的却是她的面容\n[00:37.75]说来实在嘲讽我不太懂\n[00:41.35]偏渴望你懂\n[00:44.75]是否幸福轻得太沉重\n[00:48.50]过度使用不痒不痛\n[00:52.31]烂熟透红空洞了的瞳孔\n[00:56.56]终于掏空终于有始无终\n[00:59.81]得不到的永远在骚动\n[01:03.41]被偏爱的都有恃无恐\n[01:07.11]玫瑰的红容易受伤的梦\n[01:11.46]握在手中却流失于指缝\n[01:14.96]又落空\n[01:31.02]红是朱砂痣烙印心口\n[01:33.82]红是蚊子血般平庸\n[01:38.73]时间美化那仅有的悸动\n[01:41.82]也磨平激动\n[01:45.12]从背后抱你的时候\n[01:48.98]期待的却是她的面容\n[01:52.82]说来实在嘲讽\n[01:55.15]我不太懂偏渴望你懂\n[01:59.85]是否幸福轻得太沉重\n[02:03.60]过度使用不痒不痛\n[02:07.10]烂熟透红空洞了的瞳孔\n[02:11.45]终于掏空终于有始无终\n[02:14.70]得不到的永远在骚动\n[02:18.50]被偏爱的都有恃无恐\n[02:22.35]玫瑰的红容易受伤的梦\n[02:26.35]握在手中却流失于指缝\n[02:29.75]又落空\n[02:44.99]是否说爱都太过沉重\n[02:48.54]过度使用不痒不痛\n[02:52.24]烧得火红蛇行缠绕心中\n[02:56.34]终于冷冻终于有始无终\n[02:59.74]得不到的永远在骚动\n[03:03.49]被偏爱的都有恃无恐\n[03:07.24]玫瑰的红容易受伤的梦\n[03:11.39]握在手中却流失于指缝\n[03:14.84]得不到的永远在骚动\n[03:18.49]被偏爱的都有恃无恐\n[03:22.34]玫瑰的红伤口绽放的梦\n[03:26.44]握在手中却流失于指缝\n[03:29.79]再落空', '2022-06-30 15:23:29', '2022-06-30 15:23:29', '陈奕迅');
INSERT INTO `song` VALUES (16, 5, 'teest', 'teste', '/file/92c3c193cf064bf686dc79d2a80c52fb.jpg', '/file/3ab4746c202347a790299886f7f0b844.mp3', '1111111', '2022-07-12 12:29:12', '2022-07-12 12:29:12', '1111');

-- ----------------------------
-- Table structure for songmenu
-- ----------------------------
DROP TABLE IF EXISTS `songmenu`;
CREATE TABLE `songmenu`  (
  `songmenu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` int(11) NOT NULL COMMENT '歌单创建者',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '标题',
  `pic` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '歌单图片',
  `classify_id` int(11) NOT NULL COMMENT '歌单分类id',
  `state` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '是否为私人歌单，0代表公开，1代表私人',
  `introduction` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `hot` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '是否热门',
  PRIMARY KEY (`songmenu_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of songmenu
-- ----------------------------
INSERT INTO `songmenu` VALUES (1, 2, '小酒馆爵士_惬意的黄昏爱恋', '/file/playlist1.jpg', 1, 0, '沉醉于心的总是刻骨铭心的爱恋，就像喜欢夕阳的人一样，可他并不喜欢朝阳。 ', 1);
INSERT INTO `songmenu` VALUES (2, 2, '甜味rap:我每首情歌都是唱给你听', '/file/playlist2.jpg', 1, 0, '想对你撒娇想把我仅有的温柔都给你 希望你的余生都与我有关', 1);
INSERT INTO `songmenu` VALUES (3, 2, '失恋日记丨想念的字句仅私人可见', '/file/playlist3.jpg', 1, 0, '这次你离开我，是风，是雨，是夜晚。 光影斑驳，故事变沉默，还剩我在夜里辗转反侧，为一个睡得很香的人失眠。', 1);
INSERT INTO `songmenu` VALUES (4, 3, '「深夜走街」轰鸣の低趴黑武士', '/file/playlist4.jpg', 1, 0, '只有塞车的时候，我们才是不自由的。  油价上涨排量缩减的时代，只有牢牢把握方向盘，每当听到发动机的轰鸣，内心荡漾起千层波浪，想念的是我深爱的姑娘，排气释放的是无尽能量。', 1);
INSERT INTO `songmenu` VALUES (5, 2, '文艺书店的英文歌单列表', '/file/playlist5.jpg', 2, 0, '坐在柔软的书店沙发上，喝一杯咖啡，听着一首首清新温暖的歌曲，生活真的就是这么简单和美好。', 1);
INSERT INTO `songmenu` VALUES (6, 2, '十年之后，还有这些歌让人感动', '/file/playlist6.jpg', 3, 0, '在我的印象里，九十年代的味道是游戏厅里的汗臭味与吵闹声。 在我的印象里，九十年代的味道是冰绿豆汤的甜味。 在我的印象里，九十年代的味道是拉面拌着香菜的味道。 ', 1);
INSERT INTO `songmenu` VALUES (7, 2, '效率加倍丨学习工作专属轻音乐', '/file/playlist7.jpg', 4, 0, '此歌单为精选的在学习、工作时用于静下心来使效率加倍专心学习、工作的轻音乐，听着本歌单，在学习、工作时有一个高效的状态。', 1);
INSERT INTO `songmenu` VALUES (8, 2, '『欧美小众』只属于你的温柔', '/file/playlist8.jpg', 2, 0, '小众欧美，听的就是温柔啊，只在意自己的想法。生命本没有意义，你要能给它什么意义，他就有什么意义，与其终日冥想人生有何意义，不如试用此生做点有意义的事。', 1);
INSERT INTO `songmenu` VALUES (9, 3, '爱河这种东西 还是远离吧 坠不得', '/file/playlist9.jpg', 1, 0, '哆啦A梦不会失灵，海绵宝宝不会不笑，阿狸也不会讨厌桃子。可是我和你却会慢慢陌生。', 1);
INSERT INTO `songmenu` VALUES (10, 3, '入耳即心动，收下这份御姐音', '/file/playlist10.jpg', 1, 0, '总有能瞬间抓住你耳朵的美好旋律和声音。 对于声控党来说，美好的声音总是逃不过我们的耳朵。所以整理了入耳就让人心动的华语女声合集，望喜欢！', 1);
INSERT INTO `songmenu` VALUES (11, 3, '片段翻唱丨坠入星河总归是种温柔', '/file/playlist11.jpg', 1, 0, '天空中闪烁着缥缈的耀光，彗星划破苍穹，天际与地平线毫不费力地融合在了一起。你需要得到夜晚足够的黑暗才能捕捉到他们的目光。', 0);

-- ----------------------------
-- Table structure for songmenu_list
-- ----------------------------
DROP TABLE IF EXISTS `songmenu_list`;
CREATE TABLE `songmenu_list`  (
  `songmenu_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `song_id` int(11) NULL DEFAULT NULL COMMENT '歌曲id',
  `songmenu_id` int(11) NULL DEFAULT NULL COMMENT '歌单id',
  PRIMARY KEY (`songmenu_list_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of songmenu_list
-- ----------------------------
INSERT INTO `songmenu_list` VALUES (1, 1, 1);
INSERT INTO `songmenu_list` VALUES (2, 2, 1);
INSERT INTO `songmenu_list` VALUES (3, 3, 1);
INSERT INTO `songmenu_list` VALUES (4, 4, 2);
INSERT INTO `songmenu_list` VALUES (5, 6, 2);
INSERT INTO `songmenu_list` VALUES (6, 5, 2);
INSERT INTO `songmenu_list` VALUES (7, 5, 1);
INSERT INTO `songmenu_list` VALUES (8, 1, 4);
INSERT INTO `songmenu_list` VALUES (9, 2, 4);

SET FOREIGN_KEY_CHECKS = 1;
