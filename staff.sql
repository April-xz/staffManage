/*
Navicat MySQL Data Transfer

Source Server         : db
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : staff

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2020-01-03 21:30:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', 'admin');

-- ----------------------------
-- Table structure for clockln
-- ----------------------------
DROP TABLE IF EXISTS `clockln`;
CREATE TABLE `clockln` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `staffName` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clockln
-- ----------------------------
INSERT INTO `clockln` VALUES ('10', '李雪', '12/10/2019', '13:22:00', '运维部');
INSERT INTO `clockln` VALUES ('11', '李雪', '12/10/2019', '23:22:02', '运维部');
INSERT INTO `clockln` VALUES ('12', '李雪', '12/10/2019', '23:22:03', '运维部');
INSERT INTO `clockln` VALUES ('13', '李雪', '12/10/2019', '23:22:05', '运维部');
INSERT INTO `clockln` VALUES ('14', '张曦', '12/11/2019', '7:22:06', '运维部');
INSERT INTO `clockln` VALUES ('15', '张曦', '12/11/2019', '23:22:08', '运维部');
INSERT INTO `clockln` VALUES ('16', '张曦', '12/12/2019', '10:44:46', '运维部');
INSERT INTO `clockln` VALUES ('17', '张曦', '12/12/2019', '22:44:48', '运维部');
INSERT INTO `clockln` VALUES ('18', '李雪', '12/12/2019', '6:00:00', '运维部');
INSERT INTO `clockln` VALUES ('19', '李雪', '12/12/2019', '20:00:00', '运维部');
INSERT INTO `clockln` VALUES ('20', '藏戏', '12/12/2019', '7:00:00', '研发部');
INSERT INTO `clockln` VALUES ('21', '藏戏', '12/12/2019', '20:00:00', '研发部');
INSERT INTO `clockln` VALUES ('22', '张曦', '12/14/2019', '23:28:53', '运维部');
INSERT INTO `clockln` VALUES ('23', '张曦', '12/14/2019', '23:49:28', '运维部');
INSERT INTO `clockln` VALUES ('24', 'admin', '01/02/2020', '20:51:16', '研发部');
INSERT INTO `clockln` VALUES ('25', 'admin', '01/02/2020', '20:51:27', '研发部');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `departmentId` varchar(255) DEFAULT NULL,
  `departmentName` varchar(255) DEFAULT NULL,
  `departmentActiv` varchar(255) DEFAULT NULL,
  `departmentPer` varchar(255) DEFAULT NULL,
  `departmentLeader` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('2', '运维部', '线上维护', '50', 'admin');
INSERT INTO `department` VALUES ('3', '研发部', '软件开发', '20', '李雯');

-- ----------------------------
-- Table structure for leaves
-- ----------------------------
DROP TABLE IF EXISTS `leaves`;
CREATE TABLE `leaves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `staffName` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `imgPath` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leaves
-- ----------------------------
INSERT INTO `leaves` VALUES ('5', '张曦', '病假', '12356', 'img/O1CN014ACcds1MHp1qJbXzq_!!2549841410-0-sm.jpg', '待审核', '12/14/2019 - 12/15/2019');
INSERT INTO `leaves` VALUES ('6', '张曦', '事假', '222', 'img/O1CN01eGJYlw2DGbbuIC3Jc_!!2978398582-0-sm.jpg', '待审核', '12/26/2019 - 01/26/2020');
INSERT INTO `leaves` VALUES ('7', '张曦', '病假', '生病', 'img/O1CN014ACcds1MHp1qJbXzq_!!2549841410-0-sm.jpg', '待审核', '12/27/2019 - 12/28/2019');
INSERT INTO `leaves` VALUES ('8', '赵丽', '事假', '123', 'img/新建文本文档.txt', '拒绝', '01/02/2020 - 01/02/2020');
INSERT INTO `leaves` VALUES ('9', 'admin', '事假', '789', 'img/electron.asar', '通过', '01/02/2020 - 01/02/2020');

-- ----------------------------
-- Table structure for regist
-- ----------------------------
DROP TABLE IF EXISTS `regist`;
CREATE TABLE `regist` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of regist
-- ----------------------------
INSERT INTO `regist` VALUES ('001', '李雪', '25', '1555555555', '女', '123456', '运维部');
INSERT INTO `regist` VALUES ('002', '藏戏', '25', '165541545', '男', '123456', '研发部');
INSERT INTO `regist` VALUES ('003', '张曦', '25', '16554155345', '女', '123456', '运维部');
INSERT INTO `regist` VALUES ('005', '赵丽', '27', '166588888', '女', '123456', '运维部');
INSERT INTO `regist` VALUES ('admin', 'admin', '25', '18845794454', '男', 'admin', '研发部');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` varchar(255) DEFAULT NULL,
  `staff` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '2');
