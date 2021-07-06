/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : micro_user

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 06/07/2021 15:01:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `icon` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标',
  `sort` int(11) NOT NULL COMMENT '排序',
  `enabled` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否启用（0：未启用，1：启用）',
  `p_id` bigint(20) NOT NULL COMMENT '父菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '主页', '/home', 'el-icon-s-home', 1, '1', 0);
INSERT INTO `menu` VALUES (2, '统计分析', '/analysis', 'el-icon-s-marketing', 2, '1', 0);
INSERT INTO `menu` VALUES (3, '活跃分析', '/analysis/living', 'el-icon-place', 3, '1', 2);
INSERT INTO `menu` VALUES (4, '时段分析', '/analysis/time', 'el-icon-bicycle', 4, '1', 2);
INSERT INTO `menu` VALUES (5, '菜单管理', '/menu', 'el-icon-s-tools', 5, '1', 0);
INSERT INTO `menu` VALUES (6, '人员管理', '/menu/person', 'el-icon-user', 6, '1', 5);
INSERT INTO `menu` VALUES (7, '角色管理', '/menu/role', 'el-icon-key', 7, '1', 5);
INSERT INTO `menu` VALUES (8, '权限管理', '/menu/power', 'el-icon-lock', 8, '1', 5);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `role_relation_menu_id` bigint(20) NOT NULL COMMENT '角色关联菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 1);

-- ----------------------------
-- Table structure for role_relation_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_relation_menu`;
CREATE TABLE `role_relation_menu`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_relation_menu
-- ----------------------------
INSERT INTO `role_relation_menu` VALUES (1, 1, 1);
INSERT INTO `role_relation_menu` VALUES (2, 1, 2);
INSERT INTO `role_relation_menu` VALUES (3, 1, 3);
INSERT INTO `role_relation_menu` VALUES (4, 1, 4);
INSERT INTO `role_relation_menu` VALUES (5, 1, 5);
INSERT INTO `role_relation_menu` VALUES (6, 1, 6);
INSERT INTO `role_relation_menu` VALUES (7, 1, 7);
INSERT INTO `role_relation_menu` VALUES (8, 1, 8);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456', 'admin', '18888888888', '18888888888@micro.com', 1);

SET FOREIGN_KEY_CHECKS = 1;
