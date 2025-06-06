DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
                          `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                          `name` VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
                          `password` VARCHAR(32) NOT NULL COMMENT '加密后的密码',
                          `salt` VARCHAR(32) NOT NULL COMMENT '加密使用的盐',
                          `email` VARCHAR(32) NOT NULL UNIQUE COMMENT '邮箱',
                          `phone_number` VARCHAR(15) NOT NULL UNIQUE COMMENT '手机号码',
                          `status` INT(2) NOT NULL DEFAULT 1 COMMENT '状态，-1：逻辑删除，0：禁用，1：启用',
                          `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
                          `last_login_time` DATETIME DEFAULT NULL COMMENT '上次登录时间',
                          `last_update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '上次更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Spring Boot Demo t_user 系列示例表';

DROP TABLE IF EXISTS `t_id_card`;
CREATE TABLE `t_id_card` (
                             `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                             `id_card_no` VARCHAR(18) NOT NULL UNIQUE COMMENT '身份证号码',
                             `user_id` INT(11) NOT NULL UNIQUE COMMENT '用户id',
                             `status` INT(2) NOT NULL DEFAULT 1 COMMENT '状态，-1：逻辑删除，0：禁用，1：启用',
                             `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
                             `last_login_time` DATETIME DEFAULT NULL COMMENT '上次登录时间',
                             `last_update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '上次更新时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='t_id_card 身份证表';

DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
                                `dept_id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                                `dept_name` VARCHAR(50) NOT NULL COMMENT '部门名称',
                                `dept_no` VARCHAR(20) NOT NULL COMMENT '部门编号',
                                `status` INT(2) NOT NULL DEFAULT 1 COMMENT '状态，-1：逻辑删除，0：禁用，1：启用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='t_department 部门表';

DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
                              `emp_id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                              `emp_name` VARCHAR(50) NOT NULL COMMENT '员工名字',
                              `emp_no` VARCHAR(20) NOT NULL COMMENT '员工编号',
                              `dept_id` INT(11) COMMENT '部门ID',
                              `status` INT(2) NOT NULL DEFAULT 1 COMMENT '状态，-1：逻辑删除，0：禁用，1：启用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='t_employee 员工表';
