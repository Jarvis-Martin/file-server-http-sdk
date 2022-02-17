/*
Navicat MySQL Data Transfer

Source Server         : VMmysql
Source Server Version : 80021
Source Host           : 192.168.193.129:3306
Source Database       : fileserver

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2022-02-17 18:32:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `file`
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `file_name` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '涓婁紶鍚庢枃浠舵柊鍚?',
  `file_size` int NOT NULL COMMENT '文件大小',
  `file_type` varchar(50) NOT NULL COMMENT '文件类型',
  `file_orign_name` varchar(512) NOT NULL COMMENT '文件原名称',
  `create_time` bigint NOT NULL COMMENT '文件创建时间，13位时间戳',
  `file_directory` char(8) NOT NULL COMMENT '文件存放目录',
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_name` (`file_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('8', '12e2c051-143c-49df-84a9-45b120295ec2', '6490', 'image/png', 'jinbi.png', '1644988023619', '20220216');
INSERT INTO `file` VALUES ('9', '1ce5d35f-4dda-46eb-9fcf-d12a94cf3aae', '9', 'text/plain', '123.txt', '1644995475573', '20220216');
INSERT INTO `file` VALUES ('10', '23c5492f-5f7c-47a2-b2fd-5164150e42e2', '6490', 'image/png', 'downloadfile.png', '1645086750834', '20220217');
INSERT INTO `file` VALUES ('11', 'd79b83bb-b25d-4b67-bf58-f88b772d0569', '6490', 'image/png', 'downloadfile.png', '1645088386921', '20220217');
