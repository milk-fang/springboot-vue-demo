/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : springboot-vue

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-10-05 20:37:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) unsigned zerofill DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '明朝那些事', '00000050.99', '当年明月', '2009-12-27 00:00:00', 'http://localhost:9090/files/f806544b7f174cc7be97deee43acb065');
INSERT INTO `book` VALUES ('2', '菊与刀', '00000031.98', 'xxx', '1995-12-31 00:00:00', 'http://localhost:9090/files/b1128bc0e9fd4cc9811f8e4f17e7734d');
INSERT INTO `book` VALUES ('3', '毛泽东选集', '00000110.35', '毛泽东', '1944-12-31 01:00:00', 'http://localhost:9090/files/f2ea293af7134f248d943b62ccb6cd03');
INSERT INTO `book` VALUES ('4', '忘', '00000012.78', 'xxx', '2019-12-29 00:00:00', 'http://localhost:9090/files/c64370c873af432b80eda88a179d8228');
INSERT INTO `book` VALUES ('5', '他', '00000031.56', 'yyy', '2020-12-27 00:00:00', 'http://localhost:9090/files/19c9f0e131ca452d9dd33b3d30ec43ac');
INSERT INTO `book` VALUES ('6', '丰乳肥臀', '00000035.21', '莫言', '2020-12-27 00:00:00', 'http://localhost:9090/files/5328b145e0cf42a49ad6258af949532d');
INSERT INTO `book` VALUES ('7', '酒国', '00000031.24', '莫言', '2020-12-27 00:00:00', 'http://localhost:9090/files/1f069fa7258f4274be76e7cdc34fe6e3');
INSERT INTO `book` VALUES ('8', '三四', '00000041.78', 'abc', '2020-12-27 00:00:00', 'http://localhost:9090/files/336b2149827144c7b84c9d4631c0060a');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `author` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('2', '111a', '<p>?<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">?</span><span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">?</span><br/></p>', null, null);
INSERT INTO `news` VALUES ('3', '123ab', '', null, null);
INSERT INTO `news` VALUES ('4', '4', null, null, null);
INSERT INTO `news` VALUES ('5', '4', '<p>牛逼plus</p>', null, null);
INSERT INTO `news` VALUES ('6', '6', '<p>hahaha<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">?</span></p>', null, null);
INSERT INTO `news` VALUES ('7', 'a', '<p>fdadfasa</p>', null, '2021-09-25 16:22:43');
INSERT INTO `news` VALUES ('8', '8', '<p>?<br/></p>', null, '2021-09-25 16:23:22');
INSERT INTO `news` VALUES ('9', '10', '<p>?<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">?</span><span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">?</span><span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">?</span><br/></p>', '张三', '2021-09-25 16:24:21');
INSERT INTO `news` VALUES ('10', 'a', '<p><img src=\"http://localhost:9090/files/7f67ed31a54741579e09ed9179057489\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '张三', '2021-09-25 17:20:55');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4', 'admin', 'admin', 'abcd', '18', '女', 'abcde', '1', 'http://localhost:9090/files/8c59af78578b49229d8f49ebddd7e50c');
INSERT INTO `user` VALUES ('13', 'san', '123456', 'milk', '29', '男', 'avcd', '2', null);
INSERT INTO `user` VALUES ('5', 'wang', '123456', 'zhao', '19', '男', 'China', '2', 'http://localhost:9090/files/b1128bc0e9fd4cc9811f8e4f17e7734d');
INSERT INTO `user` VALUES ('6', 'heihei', '123456', '1234', '40', '未知', 'China', '2', null);
INSERT INTO `user` VALUES ('7', 'hijk', '123456', 'HAHA', '12', '男', 'anhui', '2', null);
INSERT INTO `user` VALUES ('8', 'ldsk', '123456', 'lkd', '23', '女', 'QINGDAO', '2', null);
INSERT INTO `user` VALUES ('9', 'yiji', '123456', 'hahaha', '31', '女', 'hebei', '2', null);
INSERT INTO `user` VALUES ('10', 'xial', '123456', 'si', '28', '男', 'sichuan', '2', null);
INSERT INTO `user` VALUES ('11', 'xixi', '123456·', 'sabi', '21', '男', 'hunan', '2', null);
INSERT INTO `user` VALUES ('14', 'hahah', '123456', 'wangzhao123', '29', '男', 'heiheihei', '2', null);
