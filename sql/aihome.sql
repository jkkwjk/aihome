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

 Date: 03/06/2022 11:24:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auto
-- ----------------------------
DROP TABLE IF EXISTS `auto`;
CREATE TABLE `auto`  (
  `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `type` integer(4),
  `name` text(255),
  `enable` integer(4),
  `code` blob,
  `cron` text(40),
  `events` text(1000),
)

-- ----------------------------
-- Table structure for hardware
-- ----------------------------
DROP TABLE IF EXISTS `hardware`;
CREATE TABLE `hardware`  (
  `dev_id` text(10) NOT NULL,
  `name` text(255) NOT NULL,
  `icon` text(255),
  `mac` text(20),
  `discover_time` text,
  `heart_time` text
)

-- ----------------------------
-- Table structure for hardware_state
-- ----------------------------
DROP TABLE IF EXISTS `hardware_state`;
CREATE TABLE `hardware_state`  (
  `state_id` text(13) NOT NULL,
  `dev_id` text(10) NOT NULL,
  `type` integer(11) NOT NULL,
  `can_control` integer(1) NOT NULL,
  `name` text(255),
  `report_time` text
)

-- ----------------------------
-- Table structure for mode_option
-- ----------------------------
DROP TABLE IF EXISTS `mode_option`;
CREATE TABLE `mode_option`  (
  `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `state_id` text(13),
  `mode_value` text(255),
  `mode_text` text(255),
  `text` text(255),
  `icon` text(255),
  `color` text(7)
)

-- ----------------------------
-- Table structure for mode_state
-- ----------------------------
DROP TABLE IF EXISTS `mode_state`;
CREATE TABLE `mode_state`  (
  `state_id` text(13) NOT NULL,
  `state` text(255) NOT NULL
)

-- ----------------------------
-- Table structure for on_off_state
-- ----------------------------
DROP TABLE IF EXISTS `on_off_state`;
CREATE TABLE `on_off_state`  (
  `state_id` text(13) NOT NULL,
  `text_active` text(255),
  `text_un_active` text(255),
  `state` integer(1) NOT NULL,
  `icon` text(255),
  `icon_active_color` text(7),
  `icon_un_active_color` text(7)
)

-- ----------------------------
-- Table structure for overview
-- ----------------------------
DROP TABLE IF EXISTS `overview`;
CREATE TABLE `overview`  (
  `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `before_id` integer(11),
  `state_id` text(32) NOT NULL,
  `after_id` integer(11)
)

-- ----------------------------
-- Table structure for value_state
-- ----------------------------
DROP TABLE IF EXISTS `value_state`;
CREATE TABLE `value_state`  (
  `state_id` text(13) NOT NULL,
  `text` text(255),
  `state` integer(11) NOT NULL,
  `icon` text(255),
  `icon_color_for_max` text(7),
  `min` integer(11) NOT NULL,
  `max` integer(11) NOT NULL,
  `step` integer(11) NOT NULL
)

SET FOREIGN_KEY_CHECKS = 1;
