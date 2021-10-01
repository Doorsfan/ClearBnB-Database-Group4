# ************************************************************
# Sequel Ace SQL dump
# Version 3041
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: 127.0.0.1 (MySQL 8.0.26)
# Database: ClearBnB
# Generation Time: 2021-10-01 16:36:59 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table booking
# ------------------------------------------------------------

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `listing_booked` int NOT NULL,
  `booked_by_user` int NOT NULL,
  `amount_paid` double NOT NULL DEFAULT '0',
  `booking_start_date` date NOT NULL,
  `booking_end_date` date NOT NULL,
  `cancelled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`),
  KEY `listing_booked` (`listing_booked`),
  KEY `booked_by_user` (`booked_by_user`),
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`listing_booked`) REFERENCES `listing` (`listingId`),
  CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`booked_by_user`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table listing
# ------------------------------------------------------------

DROP TABLE IF EXISTS `listing`;

CREATE TABLE `listing` (
  `listingId` int NOT NULL,
  `version` int NOT NULL DEFAULT '1',
  `auditedDatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ownerId` int NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `imageUrl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `location` varchar(50) NOT NULL DEFAULT '',
  `numberGuests` int NOT NULL DEFAULT '1',
  `price` double NOT NULL DEFAULT '0',
  `listingStartDate` date NOT NULL DEFAULT '0000-00-00',
  `listingEndDate` date DEFAULT NULL,
  PRIMARY KEY (`listingId`,`version`) USING BTREE,
  KEY `owner_id` (`ownerId`) USING BTREE,
  CONSTRAINT `listing_ibfk_1` FOREIGN KEY (`ownerId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `written_by_id` int NOT NULL,
  `recipient_id` int DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`),
  KEY `written_by_id` (`written_by_id`),
  KEY `recipient_id` (`recipient_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`written_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`recipient_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table review
# ------------------------------------------------------------

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `reviewId` int NOT NULL AUTO_INCREMENT,
  `version` int DEFAULT '1',
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `author_id` int NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `postedToListingId` int DEFAULT NULL,
  `reviewsUserIdOf` int DEFAULT NULL,
  PRIMARY KEY (`reviewId`) USING BTREE,
  KEY `author_id` (`author_id`),
  KEY `refers_to_listing_id` (`postedToListingId`) USING BTREE,
  KEY `relatesToUserIdOf` (`reviewsUserIdOf`),
  CONSTRAINT `relatesToUserIdOf` FOREIGN KEY (`reviewsUserIdOf`) REFERENCES `user` (`user_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `review_ibfk_3` FOREIGN KEY (`postedToListingId`) REFERENCES `listing` (`listingId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`,`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
