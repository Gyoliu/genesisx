/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : world

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-12-12 09:54:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `hide_in_menu` bit(1) NOT NULL DEFAULT b'0',
  `hide_in_bread` bit(1) NOT NULL DEFAULT b'0',
  `not_cache` bit(1) NOT NULL DEFAULT b'0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `icon` varchar(255) NOT NULL DEFAULT '',
  `component` varchar(255) NOT NULL DEFAULT '',
  `parent` int(11) DEFAULT NULL,
  `redirect` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  `order` int(11) NOT NULL DEFAULT '0',
  `before_close_name` varchar(255) DEFAULT NULL,
  `access` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', 'login', '/login', '', '', '', 'Login - 登录', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('2', '_home', '/', '', '\0', '', '', '', '', null, '/home', null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('3', 'home', '/home', '', '\0', '', '首页', 'md-home', '@/view/single-page/home', '2', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('4', 'doc', '', '\0', '', '', '文档', 'ios-book', '', null, null, 'https://lison16.github.io/iview-admin-doc/#/', '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('5', 'user', '/user', '\0', '\0', '', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('6', 'userOnline', '/user/online', '\0', '\0', '', '在线用户', 'md-people', '@/view/user/userOnline.vue', '5', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('7', 'message', '/message', '', '', '', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('8', 'message_page', 'message_page', '\0', '\0', '\0', '消息中心', 'md-notifications', '@/view/single-page/message/index.vue', null, null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('9', 'components', '/components', '\0', '\0', '\0', '组件', 'logo-buffer', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('10', 'count_to_page', 'count_to_page', '\0', '\0', '\0', '数字渐变', 'md-trending-up', '@/view/components/count-to/count-to.vue', '9', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('11', 'drag_list_page', 'drag_list_page', '\0', '\0', '\0', '拖拽列表', 'ios-infinite', '@/view/components/drag-list/drag-list.vue', '9', null, null, '2', '1', null, null);
INSERT INTO `sys_menu` VALUES ('12', 'tree_table_page', 'tree_table_page', '\0', '\0', '\0', '树状表格', 'md-git-branch', '@/view/components/tree-table/index.vue', '9', null, null, '2', '2', null, null);
INSERT INTO `sys_menu` VALUES ('13', 'cropper_page', 'cropper_page', '\0', '\0', '\0', '图片裁剪', 'md-crop', '@/view/components/cropper/cropper.vue', '9', null, null, '2', '3', null, null);
INSERT INTO `sys_menu` VALUES ('14', 'tables_page', 'tables_page', '\0', '\0', '\0', '多功能表格', 'md-grid', '@/view/components/tables/tables.vue', '9', null, null, '2', '4', null, null);
INSERT INTO `sys_menu` VALUES ('15', 'split_pane_page', 'split_pane_page', '\0', '\0', '\0', '分割窗口', 'md-pause', '@/view/components/split-pane/split-pane.vue', '9', null, null, '2', '5', null, null);
INSERT INTO `sys_menu` VALUES ('16', 'markdown_page', 'markdown_page', '\0', '\0', '\0', 'Markdown编辑器', 'logo-markdown', '@/view/components/markdown/markdown.vue', '9', null, null, '2', '6', null, null);
INSERT INTO `sys_menu` VALUES ('17', 'editor_page', 'editor_page', '\0', '\0', '\0', '富文本编辑器', 'ios-create', '@/view/components/editor/editor.vue', '9', null, null, '2', '7', null, null);
INSERT INTO `sys_menu` VALUES ('18', 'icons_page', 'icons_page', '\0', '\0', '\0', '自定义图标', '_bear', '@/view/components/icons/icons.vue', '9', null, null, '2', '8', null, null);
INSERT INTO `sys_menu` VALUES ('19', 'update', '/update', '\0', '\0', '\0', '数据上传', 'md-cloud-upload', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('20', 'update_table_page', 'update_table_page', '\0', '\0', '\0', '上传Csv', 'ios-document', '@/view/update/update-table.vue', '19', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('21', 'update_paste_page', 'update_paste_page', '\0', '\0', '\0', '粘贴表格数据', 'md-clipboard', '@/view/update/update-paste.vue', '19', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('22', 'excel', '/excel', '\0', '\0', '\0', 'EXCEL导入导出', 'ios-stats', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('23', 'upload-excel', 'upload-excel', '\0', '\0', '\0', '导入EXCEL', 'md-add', '@/view/excel/upload-excel.vue', '22', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('24', 'export-excel', 'export-excel', '\0', '\0', '\0', '导出EXCEL', 'md-download', '@/view/excel/export-excel.vue', '22', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('25', 'tools_methods', '/tools_methods', '\0', '', '\0', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('26', 'tools_methods_page', 'tools_methods_page', '\0', '\0', '\0', '工具方法', 'ios-hammer', '@/view/tools-methods/tools-methods.vue', '25', null, null, '2', '0', 'before_close_normal', null);
INSERT INTO `sys_menu` VALUES ('27', 'i18n', '/i18n', '\0', '', '\0', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('28', 'i18n_page', 'i18n_page', '\0', '\0', '\0', 'i18n - {{ i18n_page }}', 'md-planet', '@/view/i18n/i18n-page.vue', null, null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('29', 'error_store', '/error_store', '\0', '', '\0', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('30', 'error_store_page', 'error_store_page', '\0', '\0', '\0', '错误收集', 'ios-bug', '@/view/error-store/error-store.vue', '29', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('31', 'error_logger', '/error_logger', '', '', '\0', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('32', 'error_logger_page', 'error_logger_page', '\0', '\0', '\0', '错误收集', 'ios-bug', '@/view/single-page/error-logger.vue', '31', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('33', 'directive', '/directive', '\0', '', '\0', '', '', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('34', 'directive_page', 'directive_page', '\0', '\0', '\0', '指令', 'ios-navigate', '@/view/directive/directive.vue', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('35', 'multilevel', '/multilevel', '\0', '\0', '\0', '多级菜单', 'md-menu', '', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('36', 'level_2_1', 'level_2_1', '\0', '\0', '\0', '二级-1', 'md-funnel', '@/view/multilevel/level-2-1.vue', '35', null, null, '2', '0', null, null);
INSERT INTO `sys_menu` VALUES ('37', 'level_2_2', 'level_2_2', '\0', '\0', '\0', '二级-2', 'md-funnel', 'parentView', '35', null, null, '1', '0', null, 'admin');
INSERT INTO `sys_menu` VALUES ('38', 'level_2_2_1', 'level_2_2_1', '\0', '\0', '\0', '三级', 'md-funnel', '@/view/multilevel/level-2-2/level-3-1.vue', '37', null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('39', 'level_2_3', 'level_2_3', '\0', '\0', '\0', '二级-3', 'md-funnel', '@/view/multilevel/level-2-3.vue', '35', null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('40', 'error_401', '/401', '', '\0', '\0', '', '', '@/view/error-page/401.vue', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('41', 'error_500', '/500', '', '\0', '\0', '', '', '@/view/error-page/500.vue', null, null, null, '1', '0', null, null);
INSERT INTO `sys_menu` VALUES ('42', 'error_404', '*', '', '\0', '\0', '', '', '@/view/error-page/404.vue', null, null, null, '1', '0', null, null);
