-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.26 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table clearbnb.booking
CREATE TABLE IF NOT EXISTS `booking` (
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

-- Dumping data for table clearbnb.booking: ~0 rows (approximately)
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;

-- Dumping structure for table clearbnb.listing
CREATE TABLE IF NOT EXISTS `listing` (
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

-- Dumping data for table clearbnb.listing: ~0 rows (approximately)
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT IGNORE INTO `listing` (`listingId`, `version`, `auditedDatetime`, `ownerId`, `title`, `description`, `imageUrl`, `location`, `numberGuests`, `price`, `listingStartDate`, `listingEndDate`) VALUES
	(1, 1, '2021-09-28 17:31:00', 1, 'My House', 'haha', 'haha', 'haha', 5, 500, '2021-09-29', '2021-10-10');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;

-- Dumping structure for table clearbnb.message
CREATE TABLE IF NOT EXISTS `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `written_by_id` int NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`),
  KEY `written_by_id` (`written_by_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`written_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.message: ~0 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- Dumping structure for table clearbnb.review
CREATE TABLE IF NOT EXISTS `review` (
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

-- Dumping data for table clearbnb.review: ~0 rows (approximately)
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT IGNORE INTO `review` (`reviewId`, `version`, `timestamp`, `author_id`, `comment`, `rating`, `postedToListingId`, `reviewsUserIdOf`) VALUES
	(1, 1, '2021-09-28 23:14:32', 1, 'haha', 3, 1, NULL),
	(2, 1, '2021-09-29 15:11:01', 1, 'haha', 3, NULL, 1),
	(3, 1, '2021-09-29 15:16:43', 1, 'haha', 3, NULL, 1),
	(4, 1, '2021-09-29 15:41:41', 1, 'haha', 3, NULL, 1),
	(5, 1, '2021-09-29 15:45:26', 1, 'hehe', 3, NULL, 1),
	(6, 1, '2021-09-29 15:46:47', 1, 'NEW REVIEW', 3, NULL, 1),
	(7, 1, '2021-09-29 15:49:48', 1, 'haha', 3, NULL, 1),
	(8, 1, '2021-09-29 16:06:37', 2, 'haha', 3, NULL, 1),
	(9, 1, '2021-09-29 16:29:55', 2, 'hehee', 3, NULL, 1),
	(10, 1, '2021-09-29 16:31:58', 2, 'hehehe', 3, NULL, 1),
	(11, 1, '2021-09-29 16:33:15', 2, 'HEHEHEHE', 3, NULL, 1);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;

-- Dumping structure for table clearbnb.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`,`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.user: ~0 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`user_id`, `username`, `password`, `email`, `balance`) VALUES
	(1, 'mikael', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'haha', 1),
	(2, 'Guy', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'hahaha', 55);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
