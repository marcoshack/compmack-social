-- MySQL dump 10.11
--
-- Host: localhost    Database: csocial
-- ------------------------------------------------------
-- Server version	5.0.75-0ubuntu10.2

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
-- Table structure for table `friendship_requests`
--

DROP TABLE IF EXISTS `friendship_requests`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `friendship_requests` (
  `id` bigint(20) NOT NULL auto_increment,
  `message` varchar(255) default NULL,
  `requestDate` date default NULL,
  `status` int(11) default NULL,
  `fromUser_id` bigint(20) default NULL,
  `toUser_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8AE72089DC39F93D` (`toUser_id`),
  KEY `FK8AE7208942F50D2E` (`fromUser_id`),
  CONSTRAINT `FK8AE7208942F50D2E` FOREIGN KEY (`fromUser_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK8AE72089DC39F93D` FOREIGN KEY (`toUser_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `friendship_requests`
--

LOCK TABLES `friendship_requests` WRITE;
/*!40000 ALTER TABLE `friendship_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendship_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendships`
--

DROP TABLE IF EXISTS `friendships`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `friendships` (
  `id` bigint(20) NOT NULL auto_increment,
  `startDate` date default NULL,
  `friend_id` bigint(20) default NULL,
  `owner_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK53D355D965E94465` (`friend_id`),
  KEY `FK53D355D9E7BE830` (`owner_id`),
  CONSTRAINT `FK53D355D965E94465` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK53D355D9E7BE830` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `friendships`
--

LOCK TABLES `friendships` WRITE;
/*!40000 ALTER TABLE `friendships` DISABLE KEYS */;
INSERT INTO `friendships` VALUES (1,'2009-05-14',2,1),(2,'2009-05-14',1,2);
/*!40000 ALTER TABLE `friendships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL auto_increment,
  `postDate` date NOT NULL,
  `status` int(11) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `text` text NOT NULL,
  `author_id` bigint(20) default NULL,
  `owner_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKE475014C34E2A58` (`author_id`),
  KEY `FKE475014CE7BE830` (`owner_id`),
  CONSTRAINT `FKE475014C34E2A58` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKE475014CE7BE830` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL auto_increment,
  `birthday` date default NULL,
  `email` varchar(255) NOT NULL,
  `nickName` varchar(255) default NULL,
  `password` varchar(255) NOT NULL,
  `realName` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'1981-08-07','marcoshack@gmail.com','Hack','123mudar','Marcos Hack','mhack'),(2,'1982-01-02','carol_giudice@yahoo.com.br','Carol','123mudar','Maria Carololina Giudice de Castro e Silva Hack','cgiudice'),(3,NULL,'gabriel.saraiva@gmail.com',NULL,'123mudar','Gabriel Saraiva','gsaraiva'),(4,NULL,'dlustosa@gmail.com',NULL,'123mudar','Daniel Lustosa','dlustosa'),(5,NULL,'antonioams@gmail.com',NULL,'123mudar','Antonio Anderson','asouza'),(6,NULL,'joaquim@mackenzie',NULL,'123mudar','Joaquim','joaquim');
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

-- Dump completed on 2009-05-21 17:05:28
