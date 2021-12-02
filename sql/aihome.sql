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

 Date: 02/12/2021 22:24:32
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
  `mac` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备mac',
  `discover_time` datetime(0) NULL DEFAULT NULL COMMENT '设备发现时间',
  `heart_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次心跳时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_dev_id`(`dev_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hardware
-- ----------------------------
INSERT INTO `hardware` VALUES (10, 'yPvWSUQc', '测试硬件过期', 'el-icon-switch-button', '48:3f:da:a3:bb:6c', '2021-11-28 13:50:57', '2021-11-28 13:53:16');
INSERT INTO `hardware` VALUES (16, 'wLshAPDd', '测试硬件', 'el-icon-switch-button', '48:3f:da:a3:bb:6c', '2021-11-28 14:33:55', '2021-11-28 22:34:50');

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hardware_state
-- ----------------------------
INSERT INTO `hardware_state` VALUES (10, 'yPvWSUQc', 'yPvWSUQc-0', 0, 1, '状态-开关过期', '2021-11-28 13:53:16');
INSERT INTO `hardware_state` VALUES (16, 'wLshAPDd', 'wLshAPDd-0', 0, 1, '状态-开关', '2021-11-28 22:34:50');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of on_off_state
-- ----------------------------
INSERT INTO `on_off_state` VALUES (9, 'yPvWSUQc-0', '打开', '关闭', 0, 'el-icon-switch-button', '#000000', '#aabbcc');
INSERT INTO `on_off_state` VALUES (15, 'wLshAPDd-0', '打开', '关闭', 0, 'el-icon-switch-button', '#000000', '#aabbcc');

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
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of overview
-- ----------------------------
INSERT INTO `overview` VALUES (38, NULL, 'wLshAPDd-0', 39);
INSERT INTO `overview` VALUES (39, 38, 'yPvWSUQc-0', NULL);

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

SET FOREIGN_KEY_CHECKS = 1;
