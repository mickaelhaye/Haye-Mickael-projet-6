-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: pay_my_buddy_test
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `balance` float DEFAULT NULL,
  `account_name` varchar(255) DEFAULT NULL,
  `user_id2` int DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `FKsu21wnlwg0jsojk1lhrm60qu7` (`user_id2`),
  CONSTRAINT `FKsu21wnlwg0jsojk1lhrm60qu7` FOREIGN KEY (`user_id2`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,5200,'moncomptelily',2),(3,300,'mon compte John',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_banking_operation`
--

DROP TABLE IF EXISTS `account_banking_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_banking_operation` (
  `account_id2` int NOT NULL,
  `banking_operation_id2` int NOT NULL,
  KEY `FKa0f8wgdqcf2925gxbhrb8tr7h` (`banking_operation_id2`),
  KEY `FK83ux42em5r35lhqwdq6hk02yf` (`account_id2`),
  CONSTRAINT `FK83ux42em5r35lhqwdq6hk02yf` FOREIGN KEY (`account_id2`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FKa0f8wgdqcf2925gxbhrb8tr7h` FOREIGN KEY (`banking_operation_id2`) REFERENCES `banking_operation` (`banking_operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_banking_operation`
--

LOCK TABLES `account_banking_operation` WRITE;
/*!40000 ALTER TABLE `account_banking_operation` DISABLE KEYS */;
INSERT INTO `account_banking_operation` VALUES (3,2),(3,3),(3,4),(1,1),(1,4);
/*!40000 ALTER TABLE `account_banking_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banking_operation`
--

DROP TABLE IF EXISTS `banking_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banking_operation` (
  `banking_operation_id` int NOT NULL AUTO_INCREMENT,
  `amount` float DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hour` varchar(255) DEFAULT NULL,
  `type_transaction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`banking_operation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banking_operation`
--

LOCK TABLES `banking_operation` WRITE;
/*!40000 ALTER TABLE `banking_operation` DISABLE KEYS */;
INSERT INTO `banking_operation` VALUES (1,5000,'19/07/2023','add 5000 to my account','09:06:00','add money'),(2,200,'19/07/2023','','09:14:37','add money'),(3,300,'19/07/2023','add money 300','09:14:50','add money'),(4,200,'19/07/2023','send money to lily','09:15:23','send money to lily.cooper@hotmail.fr from John.boyd@gmail.com');
/*!40000 ALTER TABLE `banking_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `birthdate` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'39 rue de bel air, 49300 Cholet','1990-05-29','John.boyd@gmail.com','John','Boyd','$2a$10$tSl/CREV0RkvrlXl77xyuuXFXzHa8rzuTco3Gp9Rr6n1sUVkjYzeC','ROLE_ADMIN'),(2,'19 squere de begrolles,49300 cholet','1983-05-02','lily.cooper@hotmail.fr','Lily','Cooper','$2a$10$OgKoPd7jlXXR/c680sPaHujE7NUtusAfIYWe6ztmak53n5u6IiODC','ROLE_USER'),(6,'','2021-05-04','UserWithoutCount@gmail.com','','UserWithoutCount','$2a$10$UfhLJ.q9yGsnYKYz6bgvRuSp5t5XA3B5D7OudxVBiwsIYQlgwxj1C','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_user`
--

DROP TABLE IF EXISTS `user_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_user` (
  `user_id` int NOT NULL,
  `user2_id` int NOT NULL,
  KEY `FKe3eh4miqtjbv5p7o8opvq3but` (`user2_id`),
  KEY `FKewu8a78thkqv37l3ww3e94oqv` (`user_id`),
  CONSTRAINT `FKe3eh4miqtjbv5p7o8opvq3but` FOREIGN KEY (`user2_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKewu8a78thkqv37l3ww3e94oqv` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_user`
--

LOCK TABLES `user_user` WRITE;
/*!40000 ALTER TABLE `user_user` DISABLE KEYS */;
INSERT INTO `user_user` VALUES (1,2);
/*!40000 ALTER TABLE `user_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-21 11:57:13
