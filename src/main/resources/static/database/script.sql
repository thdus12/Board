CREATE DATABASE IF NOT EXISTS `board_study` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE IF NOT EXISTS `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `title` varchar(200) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `read_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '조회수',
  `register_id` VARCHAR(100) NOT NULL COMMENT '작성자',
  `register_time` DATETIME NULL DEFAULT NULL COMMENT '작성일',
  `update_time` DATETIME NULL DEFAULT NULL COMMENT '수정일',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='게시판';