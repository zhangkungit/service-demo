/*
Navicat MySQL Data Transfer
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

-- ----------------------------
-- Table structure for `qsource_worker_node`
-- ----------------------------
DROP TABLE IF EXISTS `qsource_worker_node`;
CREATE TABLE `qsource_worker_node` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `host_name` varchar(64) NOT NULL DEFAULT '' COMMENT '机器host名',
  `port` varchar(64) NOT NULL DEFAULT '' COMMENT '端口号',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '2' COMMENT '节点类型: CONTAINER(1), ACTUAL(2)',
  `launch_date` date NOT NULL COMMENT '启动日期',
  `create_date` timestamp COMMENT '创建时间',
  `last_update_date` timestamp COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IDGenerator WorkerID配置表';



-- ----------------------------
-- Table structure for `qsource_code`
-- ----------------------------
DROP TABLE IF EXISTS `qsource_code`;
CREATE TABLE `qsource_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(40) NOT NULL DEFAULT '' COMMENT '业务类型',
  `current` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '当前值',
  `code_length` int(20) NOT NULL DEFAULT '0',
  `create_date` timestamp COMMENT '创建时间',
  `last_update_date` timestamp COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_index` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统code发号表';
