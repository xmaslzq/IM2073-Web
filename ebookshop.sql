-- MySQL dump 10.13  Distrib 8.4.4, for Win64 (x86_64)
--
-- Host: localhost    Database: ebookshop
-- ------------------------------------------------------
-- Server version	8.4.4

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
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `author_id` int NOT NULL AUTO_INCREMENT,
  `author_name` varchar(100) NOT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'Seymour Simon'),(2,'Dr. Patrizia Collard'),(3,'Brad Stone'),(4,'Barack Obama'),(5,'Adult Coloring Books'),(6,'Maggie Testa'),(7,'Lisa Fipps'),(8,'Holly Webb'),(9,'Mark Manson'),(10,'Morgan Housel'),(11,'James Clear'),(12,'Peter Brown'),(13,'Amy C. Edmondson'),(14,'Michael Becraft'),(15,'Mel Robbins'),(16,'Harper Lee'),(17,'George Orwell'),(18,'F. Scott Fitzgerald'),(19,'Jane Austen'),(20,'J.K. Rowling'),(21,'Isaac Asimov'),(22,'Agatha Christie'),(23,'Stephen King'),(24,'Walter Isaacson'),(25,'Brené Brown');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `author_id` int NOT NULL,
  `genre_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `stock` int DEFAULT '100',
  `description` varchar(1000) DEFAULT 'No description available',
  `published_date` date DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `author_id` (`author_id`),
  KEY `genre_id` (`genre_id`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`),
  CONSTRAINT `book_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,1,3,'Solar System',10.99,85,'A compelling story with unforgettable characters and a powerful message.','1950-01-22','https://m.media-amazon.com/images/I/81wkQsjbo5L._AC_UL320_.jpg'),(2,2,10,'The Little Book of Mindfulness',8.99,184,'A compelling story with unforgettable characters and a powerful message.','2020-05-28','https://m.media-amazon.com/images/I/71bt4N3tzAL._AC_UL320_.jpg'),(3,3,9,'Amazon Unbound',9.99,181,'A thrilling journey through a world of adventure and mystery.','1984-09-18','https://m.media-amazon.com/images/I/71cYdt99HxL._AC_UL320_.jpg'),(4,4,9,'BARACK OBAMA',7.99,186,'An inspiring tale that leaves a lasting impact on readers.','2006-08-12','https://m.media-amazon.com/images/I/91+NBrXG-PL._AC_UL320_.jpg'),(5,5,3,'ADULT COLORING BOOK',11.49,187,'A fascinating read that explores the depths of human understanding.','1938-03-24','https://m.media-amazon.com/images/I/71R1dKjpyoL._AC_UL320_.jpg'),(6,6,6,'THE ADOPTED DRAGON',12.99,112,'A must-read for anyone looking for motivation and personal growth.','1991-11-04','https://m.media-amazon.com/images/I/71x9AlE-OrL._AC_UL320_.jpg'),(7,7,2,'BOOK CLUB',9.49,89,'A gripping narrative that keeps readers engaged from start to finish.','1940-04-20','https://m.media-amazon.com/images/I/712uz1sSWAL._AC_UL320_.jpg'),(8,8,6,'A DRAGON HAS TO PERSEVERE',10.49,56,'A masterpiece of literature that stands the test of time.','1957-02-12','https://m.media-amazon.com/images/I/71taowAjG+L._AC_UL320_.jpg'),(9,9,10,'The Subtle Art of Not Giving a F*ck',8.99,96,'A well-researched book that provides deep insights into its subject.','1948-12-10','https://m.media-amazon.com/images/I/71QKQ9mwV7L._AC_UL320_.jpg'),(10,10,3,'The Psychology of Money',9.99,33,'A well-researched book that provides deep insights into its subject.','1966-02-06','https://m.media-amazon.com/images/I/81wZXiu4OiL._AC._SR360,460.jpg'),(11,11,10,'Atomic Habits',9.49,50,'An inspiring tale that leaves a lasting impact on readers.','1964-04-10','https://m.media-amazon.com/images/I/81F90H7hnML._AC._SR360,460.jpg'),(12,12,5,'THE WILD ROBOT PROTECTS',7.99,160,'A fascinating read that explores the depths of human understanding.','1997-06-14','https://m.media-amazon.com/images/I/71gCPEf-MiL._AC._SR360,460.jpg'),(13,13,9,'THE FEARLESS ORGANIZATION',15.99,29,'An inspiring tale that leaves a lasting impact on readers.','1947-07-06','https://m.media-amazon.com/images/I/716QNCYUMAL._AC._SR360,460.jpg'),(14,14,9,'BILL GATES',8.49,163,'A gripping narrative that keeps readers engaged from start to finish.','2006-02-18','https://m.media-amazon.com/images/I/71yR+jQLqXL._AC._SR360,460.jpg'),(15,15,10,'THE LET THEM',8.99,46,'A thrilling journey through a world of adventure and mystery.','2013-07-06','https://m.media-amazon.com/images/I/91I1KDnK1kL._AC._SR360,460.jpg'),(16,16,1,'To Kill a Mockingbird',12.99,48,'An insightful book filled with knowledge and wisdom.','2003-12-21','https://m.media-amazon.com/images/I/91REf0GGuiL._AC_UL480_FMwebp_QL65_.jpg'),(17,17,2,'1984',9.99,102,'A must-read for anyone looking for motivation and personal growth.','1990-02-02','https://m.media-amazon.com/images/I/71rpa1-kyvL._AC_UL480_FMwebp_QL65_.jpg'),(18,19,4,'Pride and Prejudice',8.99,133,'A gripping narrative that keeps readers engaged from start to finish.','1949-03-02','https://m.media-amazon.com/images/I/81a3sr-RgdL._AC_UL480_FMwebp_QL65_.jpg'),(19,18,1,'The Great Gatsby',10.99,76,'A must-read for anyone looking for motivation and personal growth.','2021-07-09','https://m.media-amazon.com/images/I/91yg5rniqwL._AC_UL480_FMwebp_QL65_.jpg'),(20,20,6,'Harry Potter and the Sorcerer\'s Stone',14.99,191,'A must-read for anyone looking for motivation and personal growth.','1953-05-11','https://m.media-amazon.com/images/I/91pI+R+GE7L._AC_UL480_FMwebp_QL65_.jpg'),(21,21,5,'Foundation',11.99,188,'An inspiring tale that leaves a lasting impact on readers.','1996-09-16','https://m.media-amazon.com/images/I/81LT+V9G4IL._AC_UL480_FMwebp_QL65_.jpg'),(22,22,7,'Murder on the Orient Express',9.49,87,'An inspiring tale that leaves a lasting impact on readers.','2021-05-25','https://m.media-amazon.com/images/I/61S4V6tYq6L._AC_UL480_FMwebp_QL65_.jpg'),(23,23,8,'The Shining',13.99,144,'A well-researched book that provides deep insights into its subject.','1953-12-20','https://m.media-amazon.com/images/I/91U7HNa2NQL._AC_UL480_FMwebp_QL65_.jpg'),(24,24,9,'Steve Jobs',15.99,57,'An inspiring tale that leaves a lasting impact on readers.','1922-06-22','https://m.media-amazon.com/images/I/71sVQDj0SCL._AC_UL480_FMwebp_QL65_.jpg'),(25,25,10,'Daring Greatly',12.49,103,'A compelling story with unforgettable characters and a powerful message.','1956-01-17','https://m.media-amazon.com/images/I/81GOZ+-+yiL._AC_UL480_FMwebp_QL65_.jpg'),(26,16,1,'Go Set a Watchman',11.99,167,'A gripping narrative that keeps readers engaged from start to finish.','2022-06-26','https://m.media-amazon.com/images/I/91YXvPqn5jL._AC_UL480_FMwebp_QL65_.jpg'),(27,17,2,'Animal Farm',8.49,120,'A groundbreaking work that challenges conventional thinking.','1975-11-27','https://m.media-amazon.com/images/I/71je3-DsQEL._AC_UL480_FMwebp_QL65_.jpg'),(28,20,6,'Harry Potter and the Chamber of Secrets',14.99,108,'A well-researched book that provides deep insights into its subject.','1945-01-12','https://m.media-amazon.com/images/I/818umIdoruL._AC_UL480_FMwebp_QL65_.jpg'),(29,20,6,'Harry Potter and the Prisoner of Azkaban',15.49,103,'A must-read for anyone looking for motivation and personal growth.','1959-05-01','https://m.media-amazon.com/images/I/81NQA1BDlnL._AC_UL480_FMwebp_QL65_.jpg'),(30,20,2,'The Casual Vacancy',12.99,26,'A gripping narrative that keeps readers engaged from start to finish.','1989-11-09','https://m.media-amazon.com/images/I/71joZzjLECL._AC_UL480_FMwebp_QL65_.jpg'),(31,21,5,'Foundation and Empire',11.49,38,'An insightful book filled with knowledge and wisdom.','2008-07-19','https://m.media-amazon.com/images/I/81Iah3aWyXL._AC_UL480_FMwebp_QL65_.jpg'),(32,21,5,'Second Foundation',12.29,171,'A thrilling journey through a world of adventure and mystery.','1923-08-14','https://m.media-amazon.com/images/I/91A8bRquC1L._AC_UL480_FMwebp_QL65_.jpg'),(33,22,7,'The Murder of Roger Ackroyd',9.99,180,'An insightful book filled with knowledge and wisdom.','1927-02-27','https://m.media-amazon.com/images/I/41qI8kU+ORL._SY445_SX342_.jpg'),(34,22,7,'And Then There Were None',10.99,108,'A groundbreaking work that challenges conventional thinking.','2006-07-20','https://m.media-amazon.com/images/I/41Fdor5q0yL._SY445_SX342_.jpg'),(35,23,8,'Carrie',13.49,94,'A compelling story with unforgettable characters and a powerful message.','1952-08-02','https://m.media-amazon.com/images/I/51NoAejrmsL._SY445_SX342_.jpg'),(36,23,8,'Misery',14.29,100,'No description available',NULL,'https://m.media-amazon.com/images/I/51vx8M956VL._SY445_SX342_.jpg'),(37,24,9,'Becoming Steve Jobs',16.99,100,'No description available',NULL,'https://m.media-amazon.com/images/I/41ZaPyVkzpL._SY342_.jpg'),(38,25,10,'The Gifts of Imperfection',11.99,48,'An insightful book filled with knowledge and wisdom.','2007-09-11','https://m.media-amazon.com/images/I/61pEljvVxcL._SY342_.jpg'),(39,25,10,'Atlas of the Heart',13.49,100,'No description available',NULL,'https://m.media-amazon.com/images/I/91UtMl1tBBL._SY342_.jpg');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cart_item_id` int NOT NULL AUTO_INCREMENT,
  `cart_id` int NOT NULL,
  `book_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`cart_item_id`),
  KEY `cart_id` (`cart_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `shopping_cart` (`cart_id`) ON DELETE CASCADE,
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  CONSTRAINT `cart_item_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `genre_id` int NOT NULL AUTO_INCREMENT,
  `genre_name` varchar(100) NOT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Classic'),(2,'Fiction'),(3,'Non-Fiction'),(4,'Romance'),(5,'Science Fiction'),(6,'Fantasy'),(7,'Mystery'),(8,'Thriller'),(9,'Biography'),(10,'Self-Help');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `book_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `ordertable` (`order_id`) ON DELETE CASCADE,
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  CONSTRAINT `order_item_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordertable`
--

DROP TABLE IF EXISTS `ordertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordertable` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `total_price` decimal(10,2) DEFAULT NULL,
  `status` enum('finished','shipping','cancelled') NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ordertable_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordertable`
--

LOCK TABLES `ordertable` WRITE;
/*!40000 ALTER TABLE `ordertable` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordertable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `payment_method` enum('credit','debit','paynow') NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `ordertable` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `book_id` (`book_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `review_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopping_cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `shopping_cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=672488 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart`
--

LOCK TABLES `shopping_cart` WRITE;
/*!40000 ALTER TABLE `shopping_cart` DISABLE KEYS */;
INSERT INTO `shopping_cart` VALUES (672487,1),(447335,2),(602898,3);
/*!40000 ALTER TABLE `shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` char(15) DEFAULT NULL,
  `password` char(60) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'tath0001','tath0001@gmail.com',NULL,'lalalala',NULL),(2,'helloworld','hello@gmail.com',NULL,'qwertyuiop',NULL),(3,'minimickey','mckinsey@gmail.com',NULL,'123456789',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-10  2:48:34
