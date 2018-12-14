/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : world

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-12-12 10:39:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu_rel`;
CREATE TABLE `sys_user_menu_rel` (
  `user_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  UNIQUE KEY `unique_user_menu_key` (`user_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_menu_rel
-- ----------------------------
INSERT INTO `sys_user_menu_rel` VALUES ('1', '1');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '2');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '3');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '4');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '5');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '6');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '7');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '8');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '9');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '10');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '11');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '12');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '13');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '14');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '15');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '16');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '17');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '18');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '19');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '20');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '21');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '22');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '23');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '24');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '25');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '26');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '27');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '28');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '29');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '30');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '31');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '32');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '33');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '34');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '35');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '36');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '37');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '38');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '39');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '40');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '41');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '42');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '43');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '44');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '45');
INSERT INTO `sys_user_menu_rel` VALUES ('1', '46');
