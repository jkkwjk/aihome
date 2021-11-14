/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : aihome

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 14/11/2021 23:18:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hardware
-- ----------------------------
DROP TABLE IF EXISTS `hardware`;
CREATE TABLE `hardware`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备名称',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备图标',
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备ip',
  `discover_time` datetime(0) NULL DEFAULT NULL COMMENT '设备发现时间',
  `heart_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次心跳时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_dev_id`(`dev_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hardware
-- ----------------------------
INSERT INTO `hardware` VALUES (2, 'test', '后端测试', 'icon', '155.155.155.1', '2021-11-12 15:03:35', '2021-11-12 15:03:35');
INSERT INTO `hardware` VALUES (3, 'add', '状态测试22', 'icon', '155.155.155.1', '2021-11-14 14:32:58', '2021-11-14 14:33:00');

-- ----------------------------
-- Table structure for hardware_state
-- ----------------------------
DROP TABLE IF EXISTS `hardware_state`;
CREATE TABLE `hardware_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态id',
  `type` int(11) NOT NULL COMMENT '状态类型',
  `can_control` tinyint(1) NOT NULL COMMENT '能否控制',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态标题',
  `report_time` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE,
  INDEX `index_dev_id`(`dev_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hardware_state
-- ----------------------------
INSERT INTO `hardware_state` VALUES (1, 'test', 'test-1', 0, 1, 'test-name', '2021-11-12 21:30:43');
INSERT INTO `hardware_state` VALUES (2, 'add', 'add-0', 0, 0, '程序新添加的', '2021-11-12 23:21:07');
INSERT INTO `hardware_state` VALUES (4, 'add', 'add-1', 0, 0, '程序新添加的', '2021-11-12 23:22:24');
INSERT INTO `hardware_state` VALUES (5, 'add', 'add-2', 2, 0, '程序新添加的数值', '2021-11-13 12:39:22');
INSERT INTO `hardware_state` VALUES (6, 'add', 'add-3', 1, 0, '程序新添加的模式2', '2021-11-14 13:13:19');

-- ----------------------------
-- Table structure for mode_option
-- ----------------------------
DROP TABLE IF EXISTS `mode_option`;
CREATE TABLE `mode_option`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态id',
  `mode_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '内部表示',
  `mode_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '友好模式显示',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主要显示文字',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `color` char(7) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标显示颜色',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mode_option
-- ----------------------------
INSERT INTO `mode_option` VALUES (1, 'add-3', 'eco', '节能', '节能模式', 'ico-jieneng', '#jienen');
INSERT INTO `mode_option` VALUES (2, 'add-3', 'auto', '自动', '自动模式', 'ico-zidong', '#zidong');
INSERT INTO `mode_option` VALUES (3, 'add-3', 'power', '强力', '强力模式', 'ico-qiangli', '#power');

-- ----------------------------
-- Table structure for mode_state
-- ----------------------------
DROP TABLE IF EXISTS `mode_state`;
CREATE TABLE `mode_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态id',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mode_state
-- ----------------------------
INSERT INTO `mode_state` VALUES (1, 'add-3', 'eco');

-- ----------------------------
-- Table structure for on_off_state
-- ----------------------------
DROP TABLE IF EXISTS `on_off_state`;
CREATE TABLE `on_off_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态id',
  `text_active` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '被激活的文本',
  `text_un_active` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '不被激活的文本',
  `state` tinyint(1) NOT NULL COMMENT '状态',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `icon_active_color` char(7) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '激活图标的颜色',
  `icon_un_active_color` char(7) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '不激活图标的颜色',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of on_off_state
-- ----------------------------
INSERT INTO `on_off_state` VALUES (1, 'test-1', 'test-1', 'test-1', 0, 'icon', 'ac', 'unac');
INSERT INTO `on_off_state` VALUES (4, 'add-0', 'add-text', 'add-text-un', 0, 'add-icon', '#add', '#add-un');
INSERT INTO `on_off_state` VALUES (6, 'add-1', 'add-text', 'add-text-un', 0, 'add-icon', '#add', '#add-un');

-- ----------------------------
-- Table structure for overview
-- ----------------------------
DROP TABLE IF EXISTS `overview`;
CREATE TABLE `overview`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `before_id` int(11) NULL DEFAULT NULL COMMENT '前一个id',
  `state_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `after_id` int(11) NULL DEFAULT NULL COMMENT '后一个id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_dev_id`(`state_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for value_state
-- ----------------------------
DROP TABLE IF EXISTS `value_state`;
CREATE TABLE `value_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态id',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '显示文本',
  `state` int(11) NOT NULL COMMENT '状态',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `icon_color_for_max` char(7) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标值最大时的颜色',
  `min` int(11) NOT NULL COMMENT '最小的值',
  `max` int(11) NOT NULL COMMENT '最大的值',
  `step` int(11) NOT NULL COMMENT '每次的步长',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of value_state
-- ----------------------------
INSERT INTO `value_state` VALUES (1, 'add-2', 'add-text', 50, 'add-icon', '#max', 0, 100, 1);

SET FOREIGN_KEY_CHECKS = 1;
