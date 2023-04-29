CREATE DATABASE IF NOT EXISTS `board_study` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE IF NOT EXISTS `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `title` varchar(200) NOT NULL COMMENT '����',
  `content` text NOT NULL COMMENT '����',
  `read_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '��ȸ��',
  `register_id` VARCHAR(100) NOT NULL COMMENT '�ۼ���',
  `register_time` DATETIME NULL DEFAULT NULL COMMENT '�ۼ���',
  `update_time` DATETIME NULL DEFAULT NULL COMMENT '������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='�Խ���';