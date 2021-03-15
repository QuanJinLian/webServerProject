# WeTalk Server  
#### 1. tomcat 실행  
#### 2. ChatServer 클래스 실행  

### Connection pool설정 -(Servers -> context.xml)  
	   <Resource 
	    name="jdbc/oracle" auth="Container" 
	    type="javax.sql.DataSource"
	    maxActive="10" maxWait="-1" 
	    username="root" 
	    password="java" 
	    driverClassName="com.mysql.cj.jdbc.Driver"
	    url="jdbc:mysql://localhost:3306/wetalkdb?serverTimezone=UTC"	    
	    closeMethod="close"/>

### MySQL 테이블 및 초기 데이터  
  
	CREATE DATABASE  IF NOT EXISTS `wetalkdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;  
	USE `wetalkdb`;  
	-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)  
	--  
	-- Host: localhost    Database: wetalkdb  
	-- ------------------------------------------------------  
	-- Server version	8.0.22  

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
	-- Table structure for table `friendlist`  
	--  

	DROP TABLE IF EXISTS `friendlist`;  
	/*!40101 SET @saved_cs_client     = @@character_set_client */;  
	/*!50503 SET character_set_client = utf8mb4 */;  
	CREATE TABLE `friendlist` (
	  `id` varchar(100) DEFAULT NULL,
	  `friendlist` longtext
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	/*!40101 SET character_set_client = @saved_cs_client */;

	--
	-- Dumping data for table `friendlist`  
	--

	LOCK TABLES `friendlist` WRITE;  
	/*!40000 ALTER TABLE `friendlist` DISABLE KEYS */;  
	INSERT INTO `friendlist` VALUES ('eeee','iiii&ddd@dd.com&wwww&uuu@uu.com'),('iiii','eeee&wwww&sss@ss.com'),('ddd@dd.com','eeee'),('sss@ss.com','iiii&wwww'),('iii@ii.com',''),('yyyy@yy.com',''),('wwww','iiii&sss@ss.com&eeee'),('uuu@uu.com','eeee');  
	/*!40000 ALTER TABLE `friendlist` ENABLE KEYS */;  
	UNLOCK TABLES;  

	--  
	-- Table structure for table `user`  
	--  

	DROP TABLE IF EXISTS `user`;  
	/*!40101 SET @saved_cs_client     = @@character_set_client */;  
	/*!50503 SET character_set_client = utf8mb4 */;  
	CREATE TABLE `user` (
	  `id` varchar(100) NOT NULL,
	  `pwd` varchar(16) DEFAULT NULL,
	  `nikname` varchar(10) DEFAULT NULL,
	  `photo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
	  `indate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;  
	/*!40101 SET character_set_client = @saved_cs_client */;  

	--
	-- Dumping data for table `user`
	--

	LOCK TABLES `user` WRITE;  
	/*!40000 ALTER TABLE `user` DISABLE KEYS */;  
	INSERT INTO `user` VALUES ('ddd@dd.com','dddddd','디비디비딥','tjekdsjk','2021-01-14 14:26:13'),('eeee','8585','pikapika','피카츄20210124050608','2021-01-06 01:53:26'),('iii@ii.com','iiiiii','아이언맨2','pikachu','2021-01-10 08:24:35'),('iiii','ion','아이언맨','85sefsdf','2021-01-06 05:36:17'),('sss@ss.com','ssssss','ssssss','pika','2021-01-10 08:07:38'),('uuu@uu.com','uuuuuu','imUUU','uuuuu20210220130309','2021-01-15 03:27:05'),('wwww','qjsro','번개맨','45sde1d','2021-01-06 05:37:29'),('yyyy@yy.com','yyyyyyy','yyyyy','yyyyy20210114004645','2021-01-13 15:46:46');  
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

	-- Dump completed on 2021-02-22 10:08:58  
