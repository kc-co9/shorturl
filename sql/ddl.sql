CREATE DATABASE IF NOT EXISTS shorturl;

USE shorturl;

# DROP TABLE IF EXISTS `code_gen`;
CREATE TABLE IF NOT EXISTS `code_gen`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `code_start`  BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '长链接key',
    `code_end`    BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '长链接key',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB COMMENT = 'key生成器';

# DROP TABLE IF EXISTS `url_mapping`;
CREATE TABLE IF NOT EXISTS `url_mapping`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `short_id`    BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '短链ID',
    `url`         TEXT                     DEFAULT NULL COMMENT '原始链接',
    `code`        VARCHAR(45)     NOT NULL DEFAULT '' COMMENT '原始链接code',
    `hash`        VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '原始链接hash值',
    `status`      TINYINT         NOT NULL DEFAULT 0 COMMENT '状态 0-未知 1-激活 2-失效',
    `valid_start` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '有效期-开始时间',
    `valid_end`   DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '有效期-结束时间',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY idx_hash_url (`hash`, `url`(45)) USING BTREE,
    UNIQUE KEY uk_short_id (`short_id`) USING BTREE,
    UNIQUE KEY uk_code (`code`) USING BTREE
) ENGINE = INNODB COMMENT = 'url标识符';

# DROP TABLE IF EXISTS `url_blocklist`;
CREATE TABLE IF NOT EXISTS `url_blocklist`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `block_id`    BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '黑名单ID',
    `url`         TEXT                     DEFAULT NULL COMMENT '链接',
    `hash`        VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '链接hash值',
    `remark`      VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '备注',
    `status`      TINYINT         NOT NULL DEFAULT 0 COMMENT '状态 0-未知 1-激活 2-失效',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY idx_hash_url (`hash`, `url`(45)) USING BTREE,
    UNIQUE KEY uk_block_id (`block_id`) USING BTREE
) ENGINE = INNODB COMMENT = 'url黑名单';

# DROP TABLE IF EXISTS `administrator`;
CREATE TABLE IF NOT EXISTS `administrator`
(
    `id`               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `administrator_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '管理者ID',
    `email`            VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '管理者邮箱',
    `username`         VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '管理者用户名',
    `password`         VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '管理者密码',
    `create_time`      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE idx_email (`email`) USING BTREE,
    UNIQUE idx_administrator_id (`administrator_id`) USING BTREE
) ENGINE = INNODB COMMENT = '管理者';