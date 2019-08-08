-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: system_engineering
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `service_registry`
--

DROP TABLE IF EXISTS `service_registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `service_registry` (
  `tinyurl.service` varchar(100) NOT NULL,
  `url` varchar(250) DEFAULT NULL,
  `running_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `health_check_url` varchar(250) DEFAULT NULL,
  `deregistered` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `connected` varchar(1) DEFAULT 't',
  PRIMARY KEY (`tinyurl.service`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_registry`
--

LOCK TABLES `service_registry` WRITE;
/*!40000 ALTER TABLE `service_registry` DISABLE KEYS */;
INSERT INTO `service_registry` VALUES ('dnot yet','testing_ip','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('iniddsdir','l','2019-07-14 00:41:35',NULL,'2019-07-14 04:38:50','f'),('inifdddddsdir','l','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('inifir','l','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('j','l','2019-07-14 00:41:25',NULL,'2019-07-14 04:36:53','f'),('jova','testing','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('macbookPro',' http://192.168.1.168:8001/','2019-07-14 03:37:00',' http://192.168.1.168:8001/system_engineering','2019-07-14 04:38:50','f'),('macbookPro11',' http://192.168.1.168:8001/','2019-07-14 04:17:44',' http://192.168.1.168:8001/system_engineering/ping','2019-07-14 04:38:50','f'),('macbookPro11xuux',' http://192.168.1.168:8001/','2019-07-14 04:52:37','http://192.168.1.168:8001/system_engineering/service_registry/ping','2019-07-14 23:28:20','f'),('macbookPro11xxx',' http://192.168.1.168:8001/','2019-07-14 04:26:56',' 192.168.1.168:8001/system_engineering/ping','2019-07-14 04:38:50','f'),('pinger','localhost','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('repeatable read','testing_repeatable_read','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('repeatable readeee332222','testing_repeatable_read_3','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('repeatable readeeee444','testing_repeatable_read_3','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('testign2333','loppp','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('testindddwew33333g','testing_ip','2019-07-14 00:41:25',NULL,'2019-07-14 04:38:50','f'),('whats the ti,e','l','2019-07-14 00:42:08',NULL,'2019-07-14 04:38:50','f');
/*!40000 ALTER TABLE `service_registry` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-29  0:32:40
