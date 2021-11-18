-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: k5a506.p.ssafy.io    Database: blockai
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `certification`
--

DROP TABLE IF EXISTS `certification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification` (
  `certification_id` int NOT NULL AUTO_INCREMENT,
  `certified_at` datetime DEFAULT NULL,
  `certified_by` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`certification_id`),
  KEY `FKjmhx0ociffsvq09ibfocsv15w` (`user_id`),
  CONSTRAINT `FKjmhx0ociffsvq09ibfocsv15w` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification`
--

LOCK TABLES `certification` WRITE;
/*!40000 ALTER TABLE `certification` DISABLE KEYS */;
INSERT INTO `certification` VALUES (12,'2018-01-15 19:19:20','SSAFY주식회사',23),(14,'2019-09-21 19:19:20','인천공항',23),(15,'2021-11-18 19:19:20','이마트24',23),(16,'2021-11-18 19:44:12','GS25',25),(17,'2021-11-18 20:10:26','GS25',25);
/*!40000 ALTER TABLE `certification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `birth` date NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `did_address` varchar(255) DEFAULT NULL,
  `issued_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_nr8v18mx1pi5r42jpqwttj5s6` (`did_address`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (22,'1997-03-31','2021-11-18 18:37:13','0x83FAD5A576E3EB523C17B9C57C9FC92230577FC7','2021-11-18 19:16:23','2021-11-18 19:16:23','ssafy@naver.com','김예슬','$2a$10$zegtp2pkFOAnqsFGZTZnHuynmrxFgoUQL46hOJaE.arbANAxtNPru','010-2518-2575'),(23,'1997-07-20','2021-11-18 18:39:46','0xC7D3CE29E8DE7B7892718E89AF61E5933A42C0E2','2021-11-18 18:47:48','2021-11-18 18:47:48','sr5871@naver.com','박세령','$2a$10$Zz42Lb4PTYk0x0R56GAsTeh.zqWBB3lRmYDVog3wWdlsBmJf2thH.','010-5812-5871'),(24,'2000-01-01','2021-11-18 18:44:03',NULL,NULL,NULL,'ssafy@ssafy.com','김싸피','$2a$10$RsjNLHtpKR9EbB3U49fxY.KEOEtVgXav1lyUGOSIwtIk2QIdxArxC','010-1234-5678'),(25,'1995-01-23','2021-11-18 19:38:32','0xC1A298446DA4152084FD5A1C400D93C1D39AE859','2021-11-18 19:41:18','2021-11-18 19:41:18','rldnd22@naver.com','권기웅','$2a$10$gFUzJ5skDPM7sjtErr1OGOEDbdx49mqS69ggVvHxXXfYUJN87c.Hy','010-7557-5836');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_user_id` int NOT NULL,
  `roles` int DEFAULT NULL,
  KEY `FKkv46dn3qakjvsk7ra33nd5sns` (`user_user_id`),
  CONSTRAINT `FKkv46dn3qakjvsk7ra33nd5sns` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (22,0),(23,0),(24,0),(25,0);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'blockai'
--

--
-- Dumping routines for database 'blockai'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-19  5:22:36
