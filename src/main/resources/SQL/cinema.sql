/*
Navicat MySQL Data Transfer

Source Server         : LocalHost
Source Server Version : 50551
Source Host           : localhost:3306
Source Database       : cinema

Target Server Type    : MYSQL
Target Server Version : 50551
File Encoding         : 65001

Date: 2017-04-03 16:06:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_cinema
-- ----------------------------
DROP TABLE IF EXISTS `t_cinema`;
CREATE TABLE `t_cinema` (
  `cinemaId` int(11) NOT NULL AUTO_INCREMENT,
  `cinemaName` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `info` text,
  PRIMARY KEY (`cinemaId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cinema
-- ----------------------------
INSERT INTO `t_cinema` VALUES ('1', '中瑞国际影城(福州美凯龙店)', '福州市台江区工业路红星美凯龙(宝龙城市广场旁)7楼', '4000125000', '福州市台江区工业路红星美凯龙(宝龙城市广场旁)7楼');
INSERT INTO `t_cinema` VALUES ('2', '福州万达国际影城(仓山店)', '福州市仓山区浦上大道万达广场2号门四层', '0591-88255688', '福州市仓山区浦上大道万达广场2号门四层');
INSERT INTO `t_cinema` VALUES ('3', '福州金逸国际影城(宝龙IMAX店)', '福建省福州市台江区工业路193号宝龙城市广场5楼', '0591-83822230', '福建省福州市台江区工业路193号宝龙城市广场5楼');
INSERT INTO `t_cinema` VALUES ('4', '福州万达国际影城(金融街店)', '福州市台江区鳌江路8号金融街万达广场A区4层', '0591-88352688', '福州市台江区鳌江路8号金融街万达广场A区4层');
INSERT INTO `t_cinema` VALUES ('5', '万达电影城（福州广达店）', '福州市台江区茶亭街道广达路168号世茂百货负一楼(近八一七路)', '0591-88598556', '福州市台江区茶亭街道广达路168号世茂百货负一楼(近八一七路)');
INSERT INTO `t_cinema` VALUES ('6', '福州中瑞万星国际影城', '福州市鼓楼区五四路320号福建体育中心', '4000125000', '<b>福州市</b>鼓楼区五四路320号福建体育中心<p><br></p>');
INSERT INTO `t_cinema` VALUES ('8', '金逸福州西提影城', '福州市马尾区君竹路37号西提丽府10楼2层第26-42号店面', '0591-83376222', '<p><b>\n\n福州市</b>马尾区君竹路37号西提丽府10楼2层第26-42号店面\n\n<br></p><p><br></p>');

-- ----------------------------
-- Table structure for t_movie
-- ----------------------------
DROP TABLE IF EXISTS `t_movie`;
CREATE TABLE `t_movie` (
  `movieId` int(11) NOT NULL AUTO_INCREMENT,
  `movieName` varchar(50) DEFAULT NULL,
  `info` text,
  `pic` text,
  PRIMARY KEY (`movieId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_movie
-- ----------------------------
INSERT INTO `t_movie` VALUES ('3', '一条狗的使命', '<p>影片讲述了一条狗经历多次重生，在一次次生命的轮回中寻找不同的使命，最后又回到了最初的主人身边的故事。</p><p><br></p>', '/pic/5vcCTF.jpg');
INSERT INTO `t_movie` VALUES ('4', '天才捕手', '<p>该片由迈克尔·格兰达吉（Michael Grandage）导演，描写了美国大作家托马斯·沃尔夫与他的图书编辑麦克斯·珀金斯之间的友谊</p><p><br></p>', '/pic/5vcDzq.jpg');
INSERT INTO `t_movie` VALUES ('5', '金刚：骷髅岛', '<p>上世纪70年代，一支集结了科考队员、探险家、战地摄影记者、军人的探险队，冒险前往南太平洋上的神秘岛屿——骷髅岛。</p><p><br></p>', '/pic/5vcFk6.jpg');
INSERT INTO `t_movie` VALUES ('6', '乐高蝙蝠侠大电影', '<p>华纳今天宣布，启动《乐高蝙蝠侠》，预计2017年上映，有可能先于《乐高大电影2》亮相。</p><p><br></p>', '/pic/5vcGCt.jpg');
INSERT INTO `t_movie` VALUES ('7', '极限特工：终极回归', '<p>故事聚焦在由范·迪塞尔带头的的特工小队和以甄子丹为首的反派组织之间的对决。在这部作品中，迪塞尔饰演的特工凯奇不再是孤胆英雄</p><p><br></p>', '/pic/5vcHXz.jpg');
INSERT INTO `t_movie` VALUES ('8', '西游伏妖篇', '<p>唐三藏在上集感化了杀死段小姐的齐天大圣，并收其为徒后，带着孙悟空、猪八戒及沙僧，一行四人踏上西天取经之旅，路途凶险。</p>', '/pic/5vcIXD.jpg');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `orderId` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL,
  `orderStatus` int(2) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `seats` varchar(50) DEFAULT NULL,
  `scheduleId` int(11) DEFAULT NULL,
  `amount` double(255,2) DEFAULT NULL,
  `chId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `userId` (`userId`),
  KEY `scheduleId` (`scheduleId`),
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`userId`),
  CONSTRAINT `t_order_ibfk_2` FOREIGN KEY (`scheduleId`) REFERENCES `t_schedule` (`scheduleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('01ddf8ab35744292b5787614797662a3', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-17 22:29:54', '1_6,1_7', '11', '22.00', 'ch_m1Kqn9rD408CirfTeHufXDm1');
INSERT INTO `t_order` VALUES ('0e49fce48b7841a49e814d1026249b9a', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 22:58:34', '1_7', '10', '11.00', 'ch_1KC8O4qn90G80WT0KGPCqTW9');
INSERT INTO `t_order` VALUES ('0ec9c4d646c146dd91e158a78b4a134f', '215fca6823d64707a96f996ee3e3d2b4', '1', '2017-03-13 23:13:44', '1_1', '8', '11.22', null);
INSERT INTO `t_order` VALUES ('211601d4a7e04bb7b34ff43d00d6a5b4', '9fb4943bef9843afa730e4f65476cd3a', '2', '2017-03-13 00:29:18', '1_3', '10', '11.11', null);
INSERT INTO `t_order` VALUES ('25ff02ab9af9447fb1eb93e56c996cea', '215fca6823d64707a96f996ee3e3d2b4', '1', '2017-03-13 23:11:38', '1_2,1_3', '8', '22.22', null);
INSERT INTO `t_order` VALUES ('27f3214f90744dcaa31bc2919ce7a5de', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-17 21:45:33', '4_5,4_6', '11', '22.00', 'ch_Pi9C4SD88u98Tm1ynLeP0Wj5');
INSERT INTO `t_order` VALUES ('27fe3dd0accc4c3a904a9559399ed649', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 17:48:21', '1_4', '10', '11.00', 'ch_KmLuTOXHabDOL40C08bHWrD0');
INSERT INTO `t_order` VALUES ('35b26cdf7bdc4d96a54ecc868add6449', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-17 22:34:37', '1_8,1_9', '11', '22.00', 'ch_u5y5SGXDKaHSCyXzL4LWfD0K');
INSERT INTO `t_order` VALUES ('3c94ff3b15884c6892d42bf8f50918ee', '60b93d4d173b431d908f9d89f15bdbda', '1', '2017-03-16 23:08:42', '2_4,2_5', '11', '22.00', null);
INSERT INTO `t_order` VALUES ('492a4bc6f04a435bbafc49be40be33cc', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 23:01:23', '2_5', '8', '1133.11', 'ch_qnzXL0fbnnXD0WvPWTfnH8iT');
INSERT INTO `t_order` VALUES ('4b32e9815dba4cbc8ffca996c02cd385', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-31 00:18:15', '6_5,6_6,6_7,6_8', '10', '44.00', 'ch_K08W5KzHmb18uzzzbHHmvbHK');
INSERT INTO `t_order` VALUES ('67e65489567b4ca4a19511339e8d1048', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-14 23:55:43', '3_6,3_7', '11', '22.00', null);
INSERT INTO `t_order` VALUES ('6c9049a754ae45c09bdb0adaca67d101', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-26 23:57:26', '1_10', '10', '11.00', null);
INSERT INTO `t_order` VALUES ('739ff7af44fd4b478b0bd2dda9fcaa06', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-26 17:23:28', '4_3,4_4', '10', '22.00', null);
INSERT INTO `t_order` VALUES ('7850cf9e90cc4197bc908d106b05931a', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-26 17:23:14', '4_5,4_6', '10', '22.00', null);
INSERT INTO `t_order` VALUES ('7f27186e4eb74433a7e0329de88fa3e8', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-26 23:38:15', '1_9', '10', '11.00', null);
INSERT INTO `t_order` VALUES ('8d1564379bfc4bdbb2f50d030bfdd558', '60b93d4d173b431d908f9d89f15bdbda', '1', '2017-03-13 00:28:54', '1_1', '7', '11.00', null);
INSERT INTO `t_order` VALUES ('93209ad71c934198be6400f6ee2964be', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 23:00:53', '1_5', '8', '1133.11', 'ch_XHGSK0jbnbrLir50uPjPefr1');
INSERT INTO `t_order` VALUES ('992b7910346e406bbb0bfdbae074948c', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 17:55:01', '1_4,1_5', '10', '22.00', 'ch_i5KavHmf9i9KrDKKiP5WDeLC');
INSERT INTO `t_order` VALUES ('a6ba920a2ace4c5ab1ba3d5bb33e4454', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-26 22:59:23', '1_5', '8', '1133.11', null);
INSERT INTO `t_order` VALUES ('b555af2d67ec40bbbd4cf1deecedd018', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-27 20:24:23', '1_10', '10', '11.00', 'ch_GSGWrPjXXn5KfLCiv9HmvXTC');
INSERT INTO `t_order` VALUES ('c5cc7e252a504d0ca52dc299e7b0a35b', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-31 00:35:52', '2_7', '10', '11.00', 'ch_5aT8KSKGub18WjDi5SGiLiXL');
INSERT INTO `t_order` VALUES ('c9a4b9619c784b929226ac036282510e', '1bd1369c4ddb4ad0976eb09298b1b3ec', '1', '2017-03-26 23:58:03', '1_9', '10', '11.00', 'ch_vTmvnLCOurHCL4u9y958a5GC');
INSERT INTO `t_order` VALUES ('ca3f75de72a64f86a76defb34b50a710', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-22 23:37:07', '5_6,5_7', '10', '22.00', 'ch_X9O8uTa9qzf9GSuf545SyDS8');
INSERT INTO `t_order` VALUES ('d4308a27ea9c4cd496ac61088b430df0', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-15 00:36:35', '2_6,2_7', '11', '22.00', 'ch_KCCO4Oqv1uL84Gi5C4Ku18K8');
INSERT INTO `t_order` VALUES ('da68646274dd4333bd999d8cb5624bc8', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-17 22:00:39', '4_7,4_8', '11', '22.00', 'ch_uX9Sa1OOS8mD4m5aDC44u5qH');
INSERT INTO `t_order` VALUES ('dc20735bbac84363a5fddf45e89d8f1f', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-19 17:17:43', '4_2,4_3', '11', '22.00', 'ch_aPezX5unLuvD8ybvjPeLqHe1');
INSERT INTO `t_order` VALUES ('e71f5cf9d4d34e8ab4649f0b3554b652', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 18:00:17', '1_6', '10', '11.00', 'ch_ub5uHGibTqLK0eHebPWbHOOG');
INSERT INTO `t_order` VALUES ('e7606eb9bb99498092fe76d53093aaf5', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-26 17:18:47', '5_4,5_5', '10', '22.00', null);
INSERT INTO `t_order` VALUES ('e8ff4bee46dc4bdd90b991ceec98f2f4', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-03-15 00:37:36', '2_8,2_9', '11', '22.00', 'ch_j1uvz1e5eTyLmrDy5SjXPu14');
INSERT INTO `t_order` VALUES ('fde941dc501842d5aac9277e125f20bc', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-03-26 23:00:08', '1_8', '10', '11.00', 'ch_zD0eH0GeDmTO8S40G8CSOWrH');

-- ----------------------------
-- Table structure for t_room
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `roomId` int(11) NOT NULL AUTO_INCREMENT,
  `roomName` varchar(20) DEFAULT NULL,
  `tmplId` int(11) DEFAULT NULL,
  `cinemaId` int(11) DEFAULT NULL,
  PRIMARY KEY (`roomId`),
  KEY `cinemaId` (`cinemaId`),
  KEY `tmplId` (`tmplId`),
  CONSTRAINT `t_room_ibfk_1` FOREIGN KEY (`cinemaId`) REFERENCES `t_cinema` (`cinemaId`),
  CONSTRAINT `t_room_ibfk_2` FOREIGN KEY (`tmplId`) REFERENCES `t_tmpl` (`tmplId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_room
-- ----------------------------
INSERT INTO `t_room` VALUES ('2', '一号厅', '3', '8');
INSERT INTO `t_room` VALUES ('3', '二号厅', '4', '8');
INSERT INTO `t_room` VALUES ('4', '一号厅', '4', '6');
INSERT INTO `t_room` VALUES ('5', '一号厅', '5', '5');
INSERT INTO `t_room` VALUES ('6', 'MAX厅', '3', '4');
INSERT INTO `t_room` VALUES ('7', 'IMAX厅', '3', '3');

-- ----------------------------
-- Table structure for t_schedule
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule`;
CREATE TABLE `t_schedule` (
  `scheduleId` int(11) NOT NULL AUTO_INCREMENT,
  `roomId` int(11) DEFAULT NULL,
  `movieId` int(11) DEFAULT NULL,
  `beginTime` timestamp NULL DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`scheduleId`),
  KEY `roomId` (`roomId`),
  KEY `movieId` (`movieId`),
  CONSTRAINT `t_schedule_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `t_room` (`roomId`),
  CONSTRAINT `t_schedule_ibfk_2` FOREIGN KEY (`movieId`) REFERENCES `t_movie` (`movieId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_schedule
-- ----------------------------
INSERT INTO `t_schedule` VALUES ('7', '2', '3', '2017-04-13 19:11:00', '23.00', '1');
INSERT INTO `t_schedule` VALUES ('8', '4', '3', '2017-11-13 11:12:00', '1133.11', '1');
INSERT INTO `t_schedule` VALUES ('10', '7', '6', '2017-08-11 11:11:00', '11.00', '1');
INSERT INTO `t_schedule` VALUES ('11', '5', '4', '2017-03-22 22:22:00', '11.00', '0');
INSERT INTO `t_schedule` VALUES ('12', '2', '4', '2017-04-03 11:11:00', '11.00', '0');

-- ----------------------------
-- Table structure for t_tmpl
-- ----------------------------
DROP TABLE IF EXISTS `t_tmpl`;
CREATE TABLE `t_tmpl` (
  `tmplId` int(11) NOT NULL AUTO_INCREMENT,
  `tmplName` varchar(20) DEFAULT NULL,
  `totalSeat` int(4) DEFAULT NULL,
  `seatMap` text,
  PRIMARY KEY (`tmplId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tmpl
-- ----------------------------
INSERT INTO `t_tmpl` VALUES ('3', '大影厅', '83', 'aaaaaaaaaa,aaaaaaaaaa,__________,aaaaaaaa__,aaaaaaaaaa,aaaaaaaaaa,aaaaaaaaaa,aaaaaaaaaa,aaaaaaaaaa,aa__aa__aa');
INSERT INTO `t_tmpl` VALUES ('4', '小影厅', '20', 'aaa_a,a_aaa,aa_aa,a_aaa,_aaaa');
INSERT INTO `t_tmpl` VALUES ('5', '中影厅', '30', 'aaaaaaaaaa,aaaaabaaaa,____aaa___,aaaaaaaa__');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userId` varchar(32) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1bd1369c4ddb4ad0976eb09298b1b3ec', 'admin', 'passwd', '15160209126', 'ADMIN');
INSERT INTO `t_user` VALUES ('215fca6823d64707a96f996ee3e3d2b4', 'test111', 'testeateaega', '15160293847', 'USER');
INSERT INTO `t_user` VALUES ('60b93d4d173b431d908f9d89f15bdbda', 'aaa', 'bbb', '1516', 'USER');
INSERT INTO `t_user` VALUES ('7105cabbaf874d6286ab5bac150563d0', 'teste1', 'testeaegagea', '12345678904', 'ADMIN');
INSERT INTO `t_user` VALUES ('9fb4943bef9843afa730e4f65476cd3a', 'admin888', 'admin888', '15163728462', 'USER');
INSERT INTO `t_user` VALUES ('a04dc18151dc42de8f9cb0e46eebed16', 'bbb', 'admin888', '15160209126', 'USER');
INSERT INTO `t_user` VALUES ('b350a92d77964970a07fe45b541e19f6', 'test123', 'test123', '15160209126', 'USER');
INSERT INTO `t_user` VALUES ('d5d3e9afa0ea46359edfde053632aa66', 'test', 'test123456', '15160209126', 'USER');
INSERT INTO `t_user` VALUES ('e7a1d745dbd14d789c052712ed036054', 'tttt', 'admin888', '15160209126', 'USER');
