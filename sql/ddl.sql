CREATE DATABASE IF NOT EXISTS shorturl;

USE shorturl;

DROP TABLE IF EXISTS `url_mapping`;
CREATE TABLE IF NOT EXISTS `url_mapping`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `url`         TEXT                     DEFAULT NULL COMMENT '长链接',
    `url_key`     VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '长链接key',
    `url_hash`    VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '长链接hash值',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY idx_url_hash (`url_hash`) USING BTREE,
    UNIQUE KEY uk_url_key (`url_key`) USING BTREE
) ENGINE = INNODB COMMENT = 'URL映射';