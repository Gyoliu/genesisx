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
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('4', 'doc', '', b'0', b'1', b'1', '文档', 'ios-book', '@/components/main', NULL, NULL, 'https://lison16.github.io/iview-admin-doc/#/', '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('5', 'user', '/user', b'0', b'1', b'1', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('6', 'userOnline', '/user/online', b'0', b'1', b'1', '在线用户', 'md-people', '@/view/user/userOnline.vue', '5', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('7', 'message', '/message', b'1', b'1', b'1', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('8', 'message_page', 'message_page', b'0', b'0', b'0', '消息中心', 'md-notifications', '@/view/single-page/message/index.vue', NULL, NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('9', 'components', '/components', b'0', b'0', b'0', '组件', 'logo-buffer', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('10', 'count_to_page', 'count_to_page', b'0', b'0', b'0', '数字渐变', 'md-trending-up', '@/view/components/count-to/count-to.vue', '9', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('11', 'drag_list_page', 'drag_list_page', b'0', b'0', b'0', '拖拽列表', 'ios-infinite', '@/view/components/drag-list/drag-list.vue', '9', NULL, NULL, '2', '1', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('12', 'tree_table_page', 'tree_table_page', b'0', b'0', b'0', '树状表格', 'md-git-branch', '@/view/components/tree-table/index.vue', '9', NULL, NULL, '2', '2', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('13', 'cropper_page', 'cropper_page', b'0', b'0', b'0', '图片裁剪', 'md-crop', '@/view/components/cropper/cropper.vue', '9', NULL, NULL, '2', '3', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('14', 'tables_page', 'tables_page', b'0', b'0', b'0', '多功能表格', 'md-grid', '@/view/components/tables/tables.vue', '9', NULL, NULL, '2', '4', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('15', 'split_pane_page', 'split_pane_page', b'0', b'0', b'0', '分割窗口', 'md-pause', '@/view/components/split-pane/split-pane.vue', '9', NULL, NULL, '2', '5', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('16', 'markdown_page', 'markdown_page', b'0', b'0', b'0', 'Markdown编辑器', 'logo-markdown', '@/view/components/markdown/markdown.vue', '9', NULL, NULL, '2', '6', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('17', 'editor_page', 'editor_page', b'0', b'0', b'0', '富文本编辑器', 'ios-create', '@/view/components/editor/editor.vue', '9', NULL, NULL, '2', '7', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('18', 'icons_page', 'icons_page', b'0', b'0', b'0', '自定义图标', '_bear', '@/view/components/icons/icons.vue', '9', NULL, NULL, '2', '8', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('19', 'update', '/update', b'0', b'0', b'0', '数据上传', 'md-cloud-upload', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('20', 'update_table_page', 'update_table_page', b'0', b'0', b'0', '上传Csv', 'ios-document', '@/view/update/update-table.vue', '19', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('21', 'update_paste_page', 'update_paste_page', b'0', b'0', b'0', '粘贴表格数据', 'md-clipboard', '@/view/update/update-paste.vue', '19', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('22', 'excel', '/excel', b'0', b'0', b'0', 'EXCEL导入导出', 'ios-stats', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('23', 'upload-excel', 'upload-excel', b'0', b'0', b'0', '导入EXCEL', 'md-add', '@/view/excel/upload-excel.vue', '22', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('24', 'export-excel', 'export-excel', b'0', b'0', b'0', '导出EXCEL', 'md-download', '@/view/excel/export-excel.vue', '22', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('25', 'tools_methods', '/tools_methods', b'0', b'1', b'0', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('26', 'tools_methods_page', 'tools_methods_page', b'0', b'0', b'0', '工具方法', 'ios-hammer', '@/view/tools-methods/tools-methods.vue', '25', NULL, NULL, '2', '0', 'before_close_normal', NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('27', 'i18n', '/i18n', b'0', b'1', b'0', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('28', 'i18n_page', 'i18n_page', b'0', b'0', b'0', 'i18n - {{ i18n_page }}', 'md-planet', '@/view/i18n/i18n-page.vue', '27', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('29', 'error_store', '/error_store', b'0', b'1', b'0', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('30', 'error_store_page', 'error_store_page', b'0', b'0', b'0', '错误收集', 'ios-bug', '@/view/error-store/error-store.vue', '29', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('31', 'error_logger', '/error_logger', b'1', b'1', b'0', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('32', 'error_logger_page', 'error_logger_page', b'0', b'0', b'0', '错误收集', 'ios-bug', '@/view/single-page/error-logger.vue', '31', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('33', 'directive', '/directive', b'0', b'1', b'0', '', '', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('34', 'directive_page', 'directive_page', b'0', b'0', b'0', '指令', 'ios-navigate', '@/view/directive/directive.vue', '33', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('35', 'multilevel', '/multilevel', b'0', b'0', b'0', '多级菜单', 'md-menu', '@/components/main', NULL, NULL, NULL, '1', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('36', 'level_2_1', 'level_2_1', b'0', b'0', b'0', '二级-1', 'md-funnel', '@/view/multilevel/level-2-1.vue', '35', NULL, NULL, '2', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('37', 'level_2_2', 'level_2_2', b'0', b'0', b'0', '二级-2', 'md-funnel', '@/components/parent-view', '35', NULL, NULL, '2', '0', NULL, 'admin');
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('38', 'level_2_2_1', 'level_2_2_1', b'0', b'0', b'0', '三级', 'md-funnel', '@/view/multilevel/level-2-2/level-3-1.vue', '37', NULL, NULL, '3', '0', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `hide_in_menu`, `hide_in_bread`, `not_cache`, `title`, `icon`, `component`, `parent`, `redirect`, `href`, `level`, `order`, `before_close_name`, `access`) VALUES ('39', 'level_2_3', 'level_2_3', b'0', b'0', b'0', '二级-3', 'md-funnel', '@/view/multilevel/level-2-3.vue', '35', NULL, NULL, '2', '0', NULL, NULL);
