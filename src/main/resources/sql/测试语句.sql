CREATE DATABASE `db_jss` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE db_jss;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminid` int(11) NOT NULL AUTO_INCREMENT,
  `adminname` varchar(25) NOT NULL,
  `adminpassword` varchar(32) NOT NULL,
  PRIMARY KEY (`adminid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Data for table "admin"
#

/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'aaa','aaa'),(2,'bbb','bbb');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
