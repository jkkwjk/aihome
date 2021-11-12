/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : aihome

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 12/11/2021 17:20:25
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hardware
-- ----------------------------
INSERT INTO `hardware` VALUES (2, 'test', '后端测试', 'icon', '155.155.155.1', '2021-11-12 15:03:35', '2021-11-12 15:03:35');

-- ----------------------------
-- Table structure for hardware_state
-- ----------------------------
DROP TABLE IF EXISTS `hardware_state`;
CREATE TABLE `hardware_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态id',
  `type` tinyint(1) NOT NULL COMMENT '状态类型',
  `can_control` tinyint(1) NOT NULL COMMENT '能否控制',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态标题',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_dev_id`(`dev_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hardware_state
-- ----------------------------

-- ----------------------------
-- Table structure for mode_icon
-- ----------------------------
DROP TABLE IF EXISTS `mode_icon`;
CREATE TABLE `mode_icon`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态id',
  `mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模式',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标类名',
  `color` char(7) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标颜色',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mode_icon
-- ----------------------------

-- ----------------------------
-- Table structure for mode_option
-- ----------------------------
DROP TABLE IF EXISTS `mode_option`;
CREATE TABLE `mode_option`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态id',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '内部表示',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '友好显示',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mode_option
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mode_state
-- ----------------------------

-- ----------------------------
-- Table structure for mode_text
-- ----------------------------
DROP TABLE IF EXISTS `mode_text`;
CREATE TABLE `mode_text`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态id',
  `mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模式',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_state_id`(`state_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mode_text
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of on_off_state
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of overview
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of value_state
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
