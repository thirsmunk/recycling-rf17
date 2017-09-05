-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: pantsystem
-- ------------------------------------------------------
-- Server version	5.7.17-log

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pantsystem
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `pantsystem` DEFAULT CHARACTER SET utf8 ;
USE `pantsystem` ;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(60) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pantid_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Lucas Weje','lucasweje','supermand1',2),(2,'Daniel Gutfeld','danielgutfeld','dannygut3',2),(3,'Iben Foldager','ibenfoldager','cbs',2),(4,'Lasse Munk','lassemunk','munk2',2),(5,'Mads Nyborg','admin','admin',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

--
-- Table structure for table `pant`
--

DROP TABLE IF EXISTS `pant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pant` (
  `idpant` int(11) NOT NULL AUTO_INCREMENT,
  `panttype` varchar(45) NOT NULL,
  `value` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `users_id` int(11) NOT NULL,
  PRIMARY KEY (`idpant`,`users_id`),
  UNIQUE KEY `idpant_UNIQUE` (`idpant`),
  KEY `fk_pant_brugere_idx` (`users_id`),
  CONSTRAINT `fk_pant_brugere` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pant`
--

LOCK TABLES `pant` WRITE;
/*!40000 ALTER TABLE `pant` DISABLE KEYS */;
INSERT INTO `pant` VALUES (1,'B',12,'2017-03-30 12:38:35',1),(2,'C',34,'2017-03-30 12:38:35',1),(3,'A',10,'2017-03-30 12:38:35',1),(4,'B',55,'2017-03-30 12:38:35',2),(5,'A',72,'2017-03-30 12:38:35',2),(6,'B',35,'2017-03-30 12:38:35',3),(7,'C',11,'2017-03-30 12:38:35',4),(9,'A',78,'2017-04-01 11:53:51',4),(10,'B',5,'2017-04-01 11:53:51',4),(11,'A',2,'2017-04-01 11:53:51',4),(12,'B',28,'2017-04-01 11:53:51',1),(13,'B',15,'2017-04-01 11:53:51',1),(14,'C',23,'2017-04-01 11:53:51',2),(15,'A',11,'2017-04-01 11:53:51',2),(16,'B',72,'2017-04-01 11:53:51',3),(17,'B',45,'2017-04-01 11:53:51',4),(18,'C',14,'2017-04-01 11:53:51',1),(19,'A',11,'2017-04-01 11:53:51',1),(20,'B',34,'2017-04-01 11:53:51',3),(21,'A',6,'2017-04-01 11:53:51',2),(22,'B',4,'2017-04-01 13:29:46',4),(23,'B',6,'2017-04-01 13:32:03',4),(25,'A',39,'2017-04-01 17:32:20',1),(36,'c',12,'2017-04-25 11:12:35',1),(42,'C',15,'2017-04-25 13:54:34',4),(43,'A',11,'2017-04-25 13:54:44',4),(45,'A',3,'2017-04-27 12:12:11',4),(46,'A',2,'2017-04-27 12:12:30',4),(47,'B',6,'2017-04-27 12:12:41',4),(48,'A',6,'2017-04-27 12:12:49',4),(49,'B',3,'2017-04-27 12:12:53',4),(50,'C',9,'2017-04-27 12:12:58',4),(51,'B',8,'2017-04-27 12:31:14',4),(52,'A',3,'2017-04-27 12:38:31',4);
/*!40000 ALTER TABLE `pant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump complete!
