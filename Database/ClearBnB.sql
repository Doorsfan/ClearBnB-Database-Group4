# ************************************************************
# Sequel Ace SQL dump
# Version 3041
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: 127.0.0.1 (MySQL 8.0.26)
# Database: ClearBnB
# Generation Time: 2021-09-17 09:09:49 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Booking
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Booking`;

CREATE TABLE `Booking` (
  `booking_id` int unsigned NOT NULL AUTO_INCREMENT,
  `listing_booked` int unsigned NOT NULL,
  `booked_by_user` int unsigned NOT NULL,
  `amount_paid` double NOT NULL DEFAULT '0',
  `booking_start_date` date NOT NULL,
  `booking_end_date` date NOT NULL,
  `cancelled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`),
  KEY `listing_booked` (`listing_booked`),
  KEY `booked_by_user` (`booked_by_user`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`listing_booked`) REFERENCES `Listing` (`listing_id`),
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`booked_by_user`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table Listing
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Listing`;

CREATE TABLE `Listing` (
  `listing_id` int unsigned NOT NULL AUTO_INCREMENT,
  `version` int NOT NULL,
  `audited_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `owner_id` int unsigned NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `image_url` varchar(100) DEFAULT NULL,
  `location` varchar(50) NOT NULL DEFAULT '',
  `number_guests` int NOT NULL DEFAULT '1',
  `price` double NOT NULL DEFAULT '0',
  `listing_start_date` date DEFAULT NULL,
  `listing_end_date` date DEFAULT NULL,
  PRIMARY KEY (`listing_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `listing_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table Message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Message`;

CREATE TABLE `Message` (
  `message_id` int unsigned NOT NULL AUTO_INCREMENT,
  `written_by_id` int unsigned NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`message_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table Review
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Review`;

CREATE TABLE `Review` (
  `review_id` int unsigned NOT NULL AUTO_INCREMENT,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `author_id` int unsigned NOT NULL,
  `target_id` int unsigned NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `author_id` (`author_id`),
  KEY `target_id` (`target_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`target_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
