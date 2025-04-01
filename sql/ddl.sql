CREATE DATABASE IF NOT EXISTS shorturl;

USE shorturl;

DROP TABLE IF EXISTS `key_gen`;
CREATE TABLE IF NOT EXISTS `key_gen`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `key_start`   BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '长链接key',
    `key_end`     BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '长链接key',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB COMMENT = 'key生成器';

DROP TABLE IF EXISTS `url_key`;
CREATE TABLE IF NOT EXISTS `url_key`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `url`         TEXT                     DEFAULT NULL COMMENT '原始链接',
    `key`         VARCHAR(45)     NOT NULL DEFAULT '' COMMENT '原始链接key',
    `hash`        VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '原始链接hash值',
    `status`      TINYINT         NOT NULL DEFAULT 0 COMMENT '状态 0-未知 1-激活 2-失效',
    `valid_start` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '有效期-开始时间',
    `valid_end`   DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '有效期-结束时间',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY idx_hash_url (`hash`, `url`(45)) USING BTREE,
    UNIQUE KEY uk_key (`key`) USING BTREE
) ENGINE = INNODB COMMENT = 'url标识符';

DROP TABLE IF EXISTS `url_blocklist`;
CREATE TABLE IF NOT EXISTS `url_blocklist`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `url`         TEXT                     DEFAULT NULL COMMENT '链接',
    `hash`        VARCHAR(90)     NOT NULL DEFAULT '' COMMENT '链接hash值',
    `remark`      VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '备注',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY idx_hash_url (`hash`, `url`(45)) USING BTREE
) ENGINE = INNODB COMMENT = 'url黑名单';