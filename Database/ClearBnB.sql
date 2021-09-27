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
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`listing_booked`) REFERENCES `listing` (`listing_id`),
  CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`booked_by_user`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.booking: ~0 rows (approximately)
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT IGNORE INTO `booking` (`booking_id`, `listing_booked`, `booked_by_user`, `amount_paid`, `booking_start_date`, `booking_end_date`, `cancelled`) VALUES
	(1, 1, 1, 1000, '2021-09-22', '2021-09-29', 0);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;

-- Dumping structure for table clearbnb.listing
CREATE TABLE IF NOT EXISTS `listing` (
  `listing_id` int NOT NULL,
  `version` int NOT NULL,
  `audited_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `owner_id` int NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `location` varchar(50) NOT NULL DEFAULT '',
  `number_guests` int NOT NULL DEFAULT '1',
  `price` double NOT NULL DEFAULT '0',
  `listing_start_date` date DEFAULT NULL,
  `listing_end_date` date DEFAULT NULL,
  PRIMARY KEY (`listing_id`,`version`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `listing_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.listing: ~2 rows (approximately)
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT IGNORE INTO `listing` (`listing_id`, `version`, `audited_datetime`, `owner_id`, `title`, `description`, `image_url`, `location`, `number_guests`, `price`, `listing_start_date`, `listing_end_date`) VALUES
	(1, 1, '2021-09-24 00:29:49', 1, 'ha', 'ha', 'ha', 'ha', 1, 1, '2021-09-24', '2021-09-24'),
	(5, 1, '2021-09-24 16:45:23', 1, 'Special', 'ha', 'https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2020/02/home-banner-2020-02-min.jpg', 'special', 1, 1000, '2021-09-23', '2021-09-27'),
	(5, 2, '2021-09-24 18:40:13', 1, 'Kansas City Flat', 'A small flat', 'https://i2.wp.com/samhouseplans.com/wp-content/uploads/2021/01/Small-House-Plans-6.5x6-Meter-1.jpg?fit=1920%2C1080&ssl=1', 'Kansas, Arkansas', 1, 1000, '2021-09-21', '2021-09-30');
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

-- Dumping data for table clearbnb.message: ~1 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT IGNORE INTO `message` (`message_id`, `written_by_id`, `content`, `timestamp`) VALUES
	(1, 1, 'Hello all', '2021-09-25 14:15:07');
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
  `target` int DEFAULT NULL,
  PRIMARY KEY (`reviewId`) USING BTREE,
  KEY `author_id` (`author_id`),
  KEY `refers_to_listing_id` (`postedToListingId`) USING BTREE,
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `review_ibfk_3` FOREIGN KEY (`postedToListingId`) REFERENCES `listing` (`listing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.review: ~7 rows (approximately)
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT IGNORE INTO `review` (`reviewId`, `version`, `timestamp`, `author_id`, `comment`, `rating`, `postedToListingId`, `target`) VALUES
	(1, 1, '2021-09-26 03:49:11', 1, '2222222', 1, 5, NULL),
	(2, 1, '2021-09-26 04:02:43', 1, '1', 5, 5, NULL),
	(3, 1, '2021-09-26 04:02:47', 1, '12', 5, 5, NULL),
	(4, 1, '2021-09-26 04:02:51', 1, '13', 5, 5, NULL),
	(5, 1, '2021-09-26 04:03:11', 1, '1414444', 5, 5, NULL),
	(6, 1, '2021-09-26 04:03:39', 1, '1515151515', 2, 5, NULL),
	(7, 1, '2021-09-26 16:12:45', 1, 'NOOOO', 2, 1, NULL);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;

-- Dumping structure for table clearbnb.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`,`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`user_id`, `username`, `password`, `email`, `balance`) VALUES
	(1, 'Matt', '-', 'matt@yahoo.com', 1000),
	(20, 'spieleGut', 'he', 'he', 222),
	(21, 'spieleGut2', 'he', 'he', 222);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
