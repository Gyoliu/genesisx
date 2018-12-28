CREATE TABLE `sys_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL COMMENT '生日',
  `personal_signature` varchar(500) DEFAULT NULL COMMENT '个性签名',
  `head_portrait` longblob COMMENT '头像',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `creator_id` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modifier_id` int(11) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `world`.`sys_user_info` (`id`, `first_name`, `last_name`, `phone_number`, `email`, `birth_date`, `personal_signature`, `head_portrait`, `create_date`, `creator_id`, `modify_date`, `modifier_id`, `remarks`) VALUES ('1', 'admin', 'admin', '111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `world`.`sys_user_info` (`id`, `first_name`, `last_name`, `phone_number`, `email`, `birth_date`, `personal_signature`, `head_portrait`, `create_date`, `creator_id`, `modify_date`, `modifier_id`, `remarks`) VALUES ('2', 'xing', 'liu', '13417*****', '1111@163.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `locking` bit(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `creator_id` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modifier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `world`.`sys_user` (`id`, `user_id`, `role_id`, `username`, `password`, `salt`, `enable`, `locking`, `create_date`, `creator_id`, `modify_date`, `modifier_id`) VALUES ('1', '1', '1', 'admin', '$2a$10$Oyp8PRtsE7F105mdgI6bYOODnT.tO3YV5dFJC7tS7hKTqovLogfRu', 'admin', b'1', b'0', '2018-11-15 15:31:54', '1', '2018-12-05 15:34:24', '1');
INSERT INTO `world`.`sys_user` (`id`, `user_id`, `role_id`, `username`, `password`, `salt`, `enable`, `locking`, `create_date`, `creator_id`, `modify_date`, `modifier_id`) VALUES ('2', '2', '1', 'liuxing', '$2a$10$BliDjhy71PxRHofP6VYt/ecmPdX/G.s6S.TWn.lGfUHDA9Asqs/ee', '123456', b'1', b'0', '2018-12-05 16:07:04', '1', '2018-12-05 16:07:43', '1');


CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `world`.`sys_role` (`id`, `role`) VALUES ('1', 'admin');


CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `before_data` json DEFAULT NULL,
  `after_data` json DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `creator` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL ,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `sys_user`
ADD COLUMN `enable`  bit(1) NULL AFTER `salt`;
