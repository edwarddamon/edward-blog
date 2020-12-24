/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : edwardblog

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 24/12/2020 21:54:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_advice
-- ----------------------------
DROP TABLE IF EXISTS `blog_advice`;
CREATE TABLE `blog_advice`  (
  `ad_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ad_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `ad_time` datetime(0) NULL DEFAULT NULL,
  `ad_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ad_id`) USING BTREE,
  INDEX `ad_user_id`(`ad_user_id`) USING BTREE,
  CONSTRAINT `blog_advice_ibfk_1` FOREIGN KEY (`ad_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article`  (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `a_createtime` datetime(0) NULL DEFAULT NULL,
  `a_likecount` int(11) NULL DEFAULT NULL,
  `a_visitcount` int(11) NULL DEFAULT NULL,
  `a_status` tinyint(1) NOT NULL,
  `a_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_cate_id` int(11) NULL DEFAULT NULL,
  `a_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`a_id`) USING BTREE,
  INDEX `a_cate_id`(`a_cate_id`) USING BTREE,
  INDEX `a_user_id`(`a_user_id`) USING BTREE,
  CONSTRAINT `blog_article_ibfk_1` FOREIGN KEY (`a_cate_id`) REFERENCES `blog_category` (`cate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `blog_article_ibfk_2` FOREIGN KEY (`a_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_book
-- ----------------------------
DROP TABLE IF EXISTS `blog_book`;
CREATE TABLE `blog_book`  (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `b_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b_discrible` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b_booktime` datetime(0) NULL DEFAULT NULL,
  `b_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`b_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_bug
-- ----------------------------
DROP TABLE IF EXISTS `blog_bug`;
CREATE TABLE `blog_bug`  (
  `bug_id` int(11) NOT NULL AUTO_INCREMENT,
  `bug_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `bug_time` datetime(0) NULL DEFAULT NULL,
  `bug_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`bug_id`) USING BTREE,
  INDEX `bug_user_id`(`bug_user_id`) USING BTREE,
  CONSTRAINT `blog_bug_ibfk_1` FOREIGN KEY (`bug_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category`  (
  `cate_id` int(11) NOT NULL AUTO_INCREMENT,
  `cate_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment`  (
  `com_id` int(11) NOT NULL AUTO_INCREMENT,
  `com_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `com_comtime` datetime(0) NULL DEFAULT NULL,
  `com_likecount` int(11) NULL DEFAULT NULL,
  `com_com_id` int(11) NULL DEFAULT NULL,
  `com_article_id` int(11) NULL DEFAULT NULL,
  `com_target_id` int(11) NULL DEFAULT NULL,
  `com_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`com_id`) USING BTREE,
  INDEX `com_article_id`(`com_article_id`) USING BTREE,
  INDEX `com_user_id`(`com_user_id`) USING BTREE,
  INDEX `com_com_id`(`com_com_id`) USING BTREE,
  INDEX `com_target_id`(`com_target_id`) USING BTREE,
  CONSTRAINT `blog_comment_ibfk_1` FOREIGN KEY (`com_article_id`) REFERENCES `blog_article` (`a_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `blog_comment_ibfk_3` FOREIGN KEY (`com_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `blog_comment_ibfk_4` FOREIGN KEY (`com_com_id`) REFERENCES `blog_comment` (`com_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `blog_comment_ibfk_5` FOREIGN KEY (`com_target_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_diary
-- ----------------------------
DROP TABLE IF EXISTS `blog_diary`;
CREATE TABLE `blog_diary`  (
  `d_id` int(11) NOT NULL AUTO_INCREMENT,
  `d_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `d_diarytime` datetime(0) NULL DEFAULT NULL,
  `d_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`d_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_friendblog
-- ----------------------------
DROP TABLE IF EXISTS `blog_friendblog`;
CREATE TABLE `blog_friendblog`  (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_status` int(11) NULL DEFAULT NULL,
  `f_backinfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_friendblogtime` datetime(0) NULL DEFAULT NULL,
  `f_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  INDEX `f_user_id`(`f_user_id`) USING BTREE,
  CONSTRAINT `blog_friendblog_ibfk_1` FOREIGN KEY (`f_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_inform
-- ----------------------------
DROP TABLE IF EXISTS `blog_inform`;
CREATE TABLE `blog_inform`  (
  `in_id` int(11) NOT NULL AUTO_INCREMENT,
  `in_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `in_informtime` datetime(0) NULL DEFAULT NULL,
  `in_read` tinyint(1) NULL DEFAULT NULL,
  `in_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`in_id`) USING BTREE,
  INDEX `in_user_id`(`in_user_id`) USING BTREE,
  CONSTRAINT `blog_inform_ibfk_1` FOREIGN KEY (`in_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_message
-- ----------------------------
DROP TABLE IF EXISTS `blog_message`;
CREATE TABLE `blog_message`  (
  `mes_id` int(11) NOT NULL AUTO_INCREMENT,
  `mes_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mes_likecount` int(11) NULL DEFAULT NULL,
  `mes_mestime` datetime(0) NULL DEFAULT NULL,
  `mes_top` tinyint(1) NULL DEFAULT NULL,
  `mes_user_id` int(11) NULL DEFAULT NULL,
  `mes_target_id` int(11) NULL DEFAULT NULL,
  `mes_parent_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`mes_id`) USING BTREE,
  INDEX `mes_user_id`(`mes_user_id`) USING BTREE,
  INDEX `mes_parent_id`(`mes_parent_id`) USING BTREE,
  INDEX `mes_target_id`(`mes_target_id`) USING BTREE,
  CONSTRAINT `blog_message_ibfk_1` FOREIGN KEY (`mes_user_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `blog_message_ibfk_2` FOREIGN KEY (`mes_parent_id`) REFERENCES `blog_message` (`mes_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `blog_message_ibfk_3` FOREIGN KEY (`mes_target_id`) REFERENCES `blog_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_nav
-- ----------------------------
DROP TABLE IF EXISTS `blog_nav`;
CREATE TABLE `blog_nav`  (
  `nav_id` int(11) NOT NULL AUTO_INCREMENT,
  `nav_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nav_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`nav_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_updatelog
-- ----------------------------
DROP TABLE IF EXISTS `blog_updatelog`;
CREATE TABLE `blog_updatelog`  (
  `up_id` int(11) NOT NULL AUTO_INCREMENT,
  `up_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `up_updatetime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`up_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user`  (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `u_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `u_nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `u_headpicture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `u_sex` tinyint(1) NULL DEFAULT NULL,
  `u_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `u_birthday` datetime(0) NULL DEFAULT NULL,
  `u_admin` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
