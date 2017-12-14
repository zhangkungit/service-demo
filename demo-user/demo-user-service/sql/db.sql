/*
Navicat MySQL Data Transfer

Source Server         : 192.168.30.37_mycat
Source Server Version : 50629
Source Host           : 192.168.30.37:8066
Source Database       : service-user

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2017-12-08 14:34:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_primary_key`
-- ----------------------------
DROP TABLE IF EXISTS `t_primary_key`;
CREATE TABLE `t_primary_key` (
  `primary_name` varchar(128) NOT NULL COMMENT '主键名',
  `current_value` bigint(20) NOT NULL COMMENT '当前值',
  `step` int(11) NOT NULL COMMENT '步长',
  UNIQUE KEY `idx_primary_key` (`primary_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主键id生成表';

-- ----------------------------
-- Records of t_primary_key
-- ----------------------------
INSERT INTO `t_primary_key` VALUES ('primary_order', '100000', '100');
