/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 127.0.0.1:3306
 Source Schema         : micro_system

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 22/10/2021 16:53:35
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
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路径',
  `icon` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `enabled` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否启用（0：未启用，1：启用）',
  `parent_id` bigint(20) NOT NULL COMMENT '父菜单ID',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型（1：菜单；2：权限）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '主页', '/home', 'el-icon-s-home', 1, '1', 0, '1');
INSERT INTO `menu` VALUES (2, '系统管理', '/system', 'el-icon-s-tools', 2, '1', 0, '1');
INSERT INTO `menu` VALUES (3, '权限管理', '/system/menu', 'el-icon-menu', 1, '1', 2, '1');
INSERT INTO `menu` VALUES (11, '角色管理', '/system/role', 'fa fa-chain', 2, '1', 2, '1');
INSERT INTO `menu` VALUES (12, '用户管理', '/system/user', 'el-icon-user-solid', 3, '1', 2, '1');
INSERT INTO `menu` VALUES (13, '增加子节点', '/system/menu/add', 'el-icon-plus', 1, '1', 3, '2');
INSERT INTO `menu` VALUES (14, '删除子节点', '/system/menu/delete', 'el-icon-remove-outline', 2, '1', 3, '2');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `enabled` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级用户', '超级用户', '1');
INSERT INTO `role` VALUES (5, '系统管理用户', '负责系统管理功能', '1');

-- ----------------------------
-- Table structure for role_relation_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_relation_menu`;
CREATE TABLE `role_relation_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_relation_menu
-- ----------------------------
INSERT INTO `role_relation_menu` VALUES (21, 7, 2);
INSERT INTO `role_relation_menu` VALUES (22, 7, 3);
INSERT INTO `role_relation_menu` VALUES (23, 7, 1);
INSERT INTO `role_relation_menu` VALUES (122, 5, 1);
INSERT INTO `role_relation_menu` VALUES (123, 5, 2);
INSERT INTO `role_relation_menu` VALUES (124, 5, 3);
INSERT INTO `role_relation_menu` VALUES (125, 5, 13);
INSERT INTO `role_relation_menu` VALUES (126, 5, 14);
INSERT INTO `role_relation_menu` VALUES (127, 5, 11);
INSERT INTO `role_relation_menu` VALUES (128, 5, 12);

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
  `enabled` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '启用状态（0：未启用；1：启用）',
  `avatar` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456', 'admin', '18888888888', '18888888888@micro.com', 1, '1', NULL);
INSERT INTO `user` VALUES (5, 'swt', '123456', 'swt', '188345839847', '389458@qq.com', 5, '1', 1);

SET FOREIGN_KEY_CHECKS = 1;
