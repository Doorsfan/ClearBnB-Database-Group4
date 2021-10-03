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
  `bookingId` int NOT NULL AUTO_INCREMENT,
  `listingBooked` int NOT NULL,
  `bookedByUser` int NOT NULL,
  `amountPaid` double NOT NULL DEFAULT '0',
  `bookingStartDate` date NOT NULL,
  `bookingEndDate` date NOT NULL,
  `cancelled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`bookingId`) USING BTREE,
  KEY `listing_booked` (`listingBooked`) USING BTREE,
  KEY `booked_by_user` (`bookedByUser`) USING BTREE,
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`listingBooked`) REFERENCES `listing` (`listingId`),
  CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`bookedByUser`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.booking: ~0 rows (approximately)
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT IGNORE INTO `booking` (`bookingId`, `listingBooked`, `bookedByUser`, `amountPaid`, `bookingStartDate`, `bookingEndDate`, `cancelled`) VALUES
	(1, 6, 1, 1150, '2021-10-03', '2021-10-05', 0);
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
  CONSTRAINT `listing_ibfk_1` FOREIGN KEY (`ownerId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.listing: ~2 rows (approximately)
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT IGNORE INTO `listing` (`listingId`, `version`, `auditedDatetime`, `ownerId`, `title`, `description`, `imageUrl`, `location`, `numberGuests`, `price`, `listingStartDate`, `listingEndDate`) VALUES
	(6, 1, '2021-10-02 02:46:00', 2, 'Specific House', 'Test', 'https://bloximages.newyork1.vip.townnews.com/republicaneagle.com/content/tncms/assets/v3/editorial/0/4d/04d915ae-c565-11eb-85dc-5392e2d4f280/60ba74c2d6077.image.png', 'test', 1, 500, '2021-10-02', '2021-10-15'),
	(7, 1, '2021-10-02 03:14:00', 1, 'Another house', 'haha', 'haha', 'haha', 1, 100, '2021-10-24', '2021-11-10');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;

-- Dumping structure for table clearbnb.message
CREATE TABLE IF NOT EXISTS `message` (
  `messageId` int NOT NULL AUTO_INCREMENT,
  `writtenById` int NOT NULL,
  `recipientId` int DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`messageId`) USING BTREE,
  KEY `written_by_id` (`writtenById`) USING BTREE,
  KEY `recipient_id` (`recipientId`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`writtenById`) REFERENCES `user` (`userId`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`recipientId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.message: ~2 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT IGNORE INTO `message` (`messageId`, `writtenById`, `recipientId`, `content`, `timestamp`) VALUES
	(2, 2, NULL, 'WHAT IS UP DUDE', '2021-10-01 18:43:11'),
	(3, 3, 2, 'heyyy', '2021-10-01 18:47:50'),
	(4, 2, NULL, 'eyyyy', '2021-10-01 18:47:55'),
	(5, 2, NULL, '<ruuuu', '2021-10-02 23:10:10'),
	(6, 2, NULL, 'EYYY', '2021-10-02 23:10:17'),
	(7, 3, 2, 'HELLO', '2021-10-02 23:10:38'),
	(8, 2, NULL, 'EYY', '2021-10-02 23:10:56'),
	(9, 2, NULL, 'HERRR', '2021-10-02 23:11:09'),
	(10, 3, 2, 'RRRRR', '2021-10-02 23:11:25');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- Dumping structure for table clearbnb.review
CREATE TABLE IF NOT EXISTS `review` (
  `reviewId` int NOT NULL AUTO_INCREMENT,
  `version` int DEFAULT '1',
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `authorId` int NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `postedToListingId` int DEFAULT NULL,
  `reviewsUserIdOf` int DEFAULT NULL,
  PRIMARY KEY (`reviewId`) USING BTREE,
  KEY `refers_to_listing_id` (`postedToListingId`) USING BTREE,
  KEY `relatesToUserIdOf` (`reviewsUserIdOf`),
  KEY `author_id` (`authorId`) USING BTREE,
  CONSTRAINT `relatesToUserIdOf` FOREIGN KEY (`reviewsUserIdOf`) REFERENCES `user` (`userId`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`authorId`) REFERENCES `user` (`userId`),
  CONSTRAINT `review_ibfk_3` FOREIGN KEY (`postedToListingId`) REFERENCES `listing` (`listingId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.review: ~0 rows (approximately)
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT IGNORE INTO `review` (`reviewId`, `version`, `timestamp`, `authorId`, `comment`, `rating`, `postedToListingId`, `reviewsUserIdOf`) VALUES
	(1, 1, '2021-10-02 23:59:31', 1, 'WHAT A SHITTY HOUSE', 5, 6, NULL),
	(2, 1, '2021-10-03 00:02:24', 2, 'NUH UH', 5, 6, NULL);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;

-- Dumping structure for table clearbnb.user
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE KEY `username` (`username`,`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`userId`, `username`, `password`, `email`, `balance`) VALUES
	(1, 'mikael', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'haha', 0),
	(2, 'Guy', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'haha', 5),
	(3, 'support', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'haha', 5);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
