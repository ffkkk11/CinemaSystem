/*
Navicat MySQL Data Transfer

Source Server         : LocalHost
Source Server Version : 50551
Source Host           : localhost:3306
Source Database       : cinema

Target Server Type    : MYSQL
Target Server Version : 50551
File Encoding         : 65001

Date: 2017-04-20 17:32:34
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

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
INSERT INTO `t_order` VALUES ('0d0043e31a0d4c4e96f12a5d8eed303f', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-04-03 16:52:30', '1_2', '13', '12.00', null);
INSERT INTO `t_order` VALUES ('0e6b0759da3441c28646c514cb78283f', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-14 15:48:40', '1_3', '13', '12.00', 'ch_OCeTmDn9iLeDLOGaT8fPK4K4');
INSERT INTO `t_order` VALUES ('2586869ccf8a41e6845f0cf936186038', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-03 17:02:45', '1_1', '13', '12.00', 'ch_qLyXHSfP0KW9zjzjz5qjfrL0');
INSERT INTO `t_order` VALUES ('5a368b8bcf264d16a2eb822ac8cc0b33', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-04-18 16:28:46', '2_4', '13', '12.00', 'ch_qvnfbHeHOK8Sennf9GLibLq5');
INSERT INTO `t_order` VALUES ('64dce5e61a0c4228baa4980757341e60', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-18 16:29:19', '2_4', '13', '12.00', 'ch_1mr5qP9izvjDyTmvXPGKuX9K');
INSERT INTO `t_order` VALUES ('73771599d79347d1b47faa9f3a0c9905', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-14 15:48:58', '1_5', '13', '12.00', 'ch_HeT4W94OarPCbfD0eLzLq5iL');
INSERT INTO `t_order` VALUES ('8a23d2c546e0453a856fcae5868b4b11', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-18 16:35:47', '3_5', '13', '12.00', 'ch_rbz50OWLW9iPiXr9qD9eLizH');
INSERT INTO `t_order` VALUES ('af896fab7eff4aaaa5a4a608733976c2', '9fb4943bef9843afa730e4f65476cd3a', '3', '2017-04-03 17:27:09', '1_2', '13', '12.00', null);
INSERT INTO `t_order` VALUES ('be90c49379eb4b618c1c9e3b7dada64b', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-05 23:50:48', '1_2', '13', '12.00', 'ch_aHqPq9XXTunTKGCu9OrDGib9');
INSERT INTO `t_order` VALUES ('c8645fe9e980474880b97042b3477659', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-04-05 23:30:09', '1_2,1_3', '13', '24.00', null);
INSERT INTO `t_order` VALUES ('c9a8bf405d6f40a48d45fadad2ed8311', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-18 16:44:57', '4_4', '13', '12.00', 'ch_Tq9CyHqDOOmH8ivPW5OOWfDS');
INSERT INTO `t_order` VALUES ('cf0f928e4b7a47d3a71ba9d9beceb553', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-04-03 16:30:07', '1_1', '13', '12.00', null);
INSERT INTO `t_order` VALUES ('d2a6bd4a4d774ccebb05cd6b301cd85f', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-04-03 17:02:40', '1_1', '13', '12.00', 'ch_0mbXf954qDK4T8azH05KaDa9');
INSERT INTO `t_order` VALUES ('d8b82be02ac34c86bbc7bd2973b29c28', '1bd1369c4ddb4ad0976eb09298b1b3ec', '3', '2017-04-18 16:29:12', '2_4', '13', '12.00', 'ch_HqTW18zz1SSCqvHWn5aDGCSC');
INSERT INTO `t_order` VALUES ('eebf261e332243469d8d8902fdde598e', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-18 16:39:08', '3_4', '13', '12.00', 'ch_14iTOSWDifTCW5uzfHubrnv5');
INSERT INTO `t_order` VALUES ('fd9714203f3844329e6b4dbfcf5adfc3', '1bd1369c4ddb4ad0976eb09298b1b3ec', '2', '2017-04-18 16:33:00', '2_5', '13', '12.00', 'ch_avLG0SXXbvb9WfDmn95qTirD');

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_schedule
-- ----------------------------
INSERT INTO `t_schedule` VALUES ('7', '2', '3', '2017-04-03 16:39:00', '23.00', '0');
INSERT INTO `t_schedule` VALUES ('8', '4', '3', '2017-11-13 11:12:00', '11.11', '1');
INSERT INTO `t_schedule` VALUES ('10', '7', '6', '2017-08-11 11:11:00', '11.00', '1');
INSERT INTO `t_schedule` VALUES ('11', '5', '4', '2017-03-22 22:22:00', '11.00', '0');
INSERT INTO `t_schedule` VALUES ('12', '2', '4', '2017-04-03 11:11:00', '11.00', '0');
INSERT INTO `t_schedule` VALUES ('13', '4', '3', '2017-05-03 11:11:00', '12.00', '1');

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
