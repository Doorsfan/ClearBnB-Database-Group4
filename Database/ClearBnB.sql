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


-- Dumping database structure for clearbnb
CREATE DATABASE IF NOT EXISTS `clearbnb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `clearbnb`;

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

-- Dumping data for table clearbnb.listing: ~17 rows (approximately)
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT IGNORE INTO `listing` (`listingId`, `version`, `auditedDatetime`, `ownerId`, `title`, `description`, `imageUrl`, `location`, `numberGuests`, `price`, `listingStartDate`, `listingEndDate`) VALUES
	(1, 1, '2021-10-04 16:01:00', 4, 'Small Suburban Housing', 'A small fine suburban housing ', 'https://i.ytimg.com/vi/35wYjQRnOZA/maxresdefault.jpg', 'Detroit', 3, 200, '2021-10-05', '2021-10-30'),
	(2, 1, '2021-10-04 16:02:00', 4, 'Small House with Garden', 'A small house with a garden outside', 'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/tiny-houses-1579284305.png?crop=1.00xw:0.788xh;0,0.189xh&resize=640:*', 'Kansas', 4, 300, '2021-11-14', '2021-11-26'),
	(3, 1, '2021-10-04 16:04:00', 5, 'House in Kalmar', 'State of the art house in Kalmar', 'https://a0.muscache.com/pictures/miso/Hosting-45109815/original/30e7bd0b-fa69-4f07-b6dc-541cc2509ebe.jpeg', 'Kalmar', 2, 500, '2021-11-24', '2021-12-15'),
	(4, 1, '2021-10-04 16:18:00', 5, 'House near the ocean', 'A house near the ocean', 'https://www.theplancollection.com/Upload/Designers/123/1042/Plan1231042MainImage_21_8_2017_8.jpg', 'Göteborg', 3, 300, '2021-10-04', '2021-10-28'),
	(5, 1, '2021-10-04 16:21:00', 6, 'Small White House', 'Small house in the Fields', 'https://cdn.houseplansservices.com/content/iipv49rhgtuoiqmn4dvmphqpcl/w991x660.jpg?v=9', 'Istanbul, Turkey', 2, 560, '2021-11-24', '2021-11-30'),
	(6, 1, '2021-10-04 16:24:00', 6, 'Luxury Mansion', 'A luxury mansion', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAznDIRkbkjl9u35p7MXAgCZ-ZjYpNuD9pFzLzx2IbE9RodzgKXediGzFjNv1NjgnW7r0&usqp=CAU', 'France, Paris', 6, 5000, '2021-11-16', '2021-12-10'),
	(7, 1, '2021-10-04 16:28:00', 7, 'Mansion in Birmingham', 'A lovely mansion in Birmingham', 'https://media-cldnry.s-nbcnews.com/image/upload/newscms/2019_24/1448814/how-size-doesnt-make-you-happier-today-main-190614.jpg', 'UK, Birmingham', 4, 1500, '2021-11-24', '2021-12-28'),
	(8, 1, '2021-10-04 16:31:00', 8, 'Luxury Resort', 'A luxury resort located in central Tokyo, Japan', 'https://i.pinimg.com/originals/bb/91/72/bb9172d16db39835f06f7493aa02a741.jpg', 'Tokyo, Japan', 7, 3000, '2021-10-04', '2021-12-24'),
	(9, 1, '2021-10-04 16:35:00', 8, 'Gated Community House', 'A small gated community housing', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMUIxJXtxxHeX-LGu40M-fRDGadxGE6-sf0tAjfC-Py9642WdH_7Z9Js1IbtxpXH-6mMc&usqp=CAU', 'Berlin, Germany', 3, 300, '2021-10-04', '2021-10-29'),
	(10, 1, '2021-10-04 16:37:00', 9, 'A small lakeside house', 'A small lakeside housing', 'https://rdcnewscdn.realtor.com/wp-content/uploads/2018/08/Indianapolis-IN-home.jpg', 'Stockholm, Sweden', 5, 500, '2021-10-04', '2021-12-01'),
	(11, 1, '2021-10-04 16:39:00', 10, 'A countryside House', 'A small countryside housing', 'https://static01.nyt.com/images/2019/06/25/realestate/25domestic-zeff/a1c1a1a36c9e4ff8adcb958c4276f28d-jumbo.jpg', 'Alabama, USA', 5, 700, '2021-10-07', '2021-10-29'),
	(12, 1, '2021-10-04 16:42:00', 11, 'Small house in Sweden', 'A small housing in Sweden', 'https://s3.amazonaws.com/homelink/media/images/listingsrescale_stage/610_385/606c93985a5fd_958_508.JPG', 'Helsingborg, Sweden', 2, 200, '2021-10-16', '2021-10-28'),
	(13, 1, '2021-10-04 16:45:00', 12, 'Suburban Getaway', 'A small housing in a Suburban getaway', 'https://www.retirementliving.com/wp-content/uploads/2018/04/how-much-house.jpg', 'Utah, USA', 2, 500, '2021-11-09', '2021-12-02'),
	(14, 1, '2021-10-04 16:48:00', 13, 'A small luxury housing', 'A small luxury housing', 'https://st.depositphotos.com/1029202/1601/i/950/depositphotos_16014789-stock-photo-modern-luxury-house.jpg', 'Amsterdam, Netherlands', 6, 4000, '2021-10-12', '2021-10-28'),
	(15, 1, '2021-10-04 16:50:00', 14, 'Winter getaway', 'A small winter housing', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/319187435.jpg?k=9b8b0f9b3accfec60fa4b3a4d18dc468ad179efd08076c344640edb977ac3d2f&o=&hp=1', 'Luleå, Sweden', 3, 500, '2021-10-19', '2021-10-30'),
	(16, 1, '2021-10-04 16:52:00', 15, 'House with a Pool', 'A housing with a Pool', 'https://media.gettyimages.com/photos/large-house-with-swimming-pool-picture-id94474127?s=612x612', 'Bangkok, Thailand', 2, 600, '2021-10-11', '2021-10-30'),
	(17, 1, '2021-10-04 16:54:00', 16, 'Enormous Mansion', 'A huge luxury Mansion', 'https://i.pinimg.com/originals/d0/c0/4b/d0c04be7f982a0753cb6dc0c565ea661.jpg', 'Paris, France', 10, 5000, '2021-10-04', '2021-11-30'),
	(18, 1, '2021-10-04 17:05:00', 17, 'Mountainside House', 'A house on the mountainside', 'https://empire-s3-production.bobvila.com/articles/wp-content/uploads/2021/08/When-Buying-a-Bigger-House-Could-Be-a-Bad-Idea_1.jpg', 'England, Yorkshire', 2, 1000, '2021-10-06', '2021-10-28');
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

-- Dumping data for table clearbnb.message: ~0 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table clearbnb.user: ~22 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`userId`, `username`, `password`, `email`, `balance`) VALUES
	(1, 'mikael', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'haha', 0),
	(3, 'support', '60ebf6e22afe105f975ddc810e6cac4599034f40cc25c16da279c36c2b4bcbbe', 'haha', 5),
	(4, 'kstobart0', '1b8d638bda34c12ac55072387cbf018e4562b303574ca3efe3dfa37b65503c48', 'cortler0@etsy.com', 70115),
	(5, 'tpaget1', '30df4ed4506dd6aee153dd5c95d000df78d8c77e17c758d4fed7c2a3670e0485', 'fmcgrey1@si.edu', 30208),
	(6, 'mheardman2', 'ac79b5773df94209ec15590ba4dd62b84f830966bd4f8821fb7bd2f2cfb11557', 'sdehm2@discovery.com', 72115),
	(7, 'elivoir3', '4d2c8bf350457aca96cf5bfd2a89e7f950af6e311eed495dd5dbe3b06e545a7a', 'pbarke3@phpbb.com', 89961),
	(8, 'tweighell4', 'a6c7cf72c78ec52ffb9f59988172ac20081ff40bcc93bc174b920eeabeed9681', 'alangstrath4@chicagotribune.com', 38328),
	(9, 'bflade5', '1c26f6036288ca71803b0d9cd5e92d9e66fb5d6cc30d207610237eeed9da3ef6', 'kderell5@samsung.com', 59014),
	(10, 'gblencoe6', 'de9a0d0a16d695a2b21a353edf4afa1e8981a77793627429a3debea35ab32258', 'egomme6@oakley.com', 51200),
	(11, 'onetherwood7', 'b084c2c2ee44e3230af5d3ee047b31d910b2999004135d2671028997b2c98c63', 'koxburgh7@facebook.com', 68529),
	(12, 'scawcutt8', '1a35a2fad4449f36986bf1ba4d538d797cb1faa62b4c286b7a9fbc1aef23a112', 'astonestreet8@usa.gov', 21716),
	(13, 'pnossent9', '2778f90150bda58bc3c82139bcde00da21e0c1bd744cc25f4bb41b4f6ad81f3d', 'ushave9@irs.gov', 27657),
	(14, 'rscurrya', '5bdbe0cd1175c149f288bc802cef2fed5c548c58baa5ca23eb13449cd6ecccde', 'kvanzona@weibo.com', 12418),
	(15, 'aishchenkob', 'cf28e273c9d0c56e6eeec18f7dfc84a247f8567875f3e1e857a7b35fe7904a3c', 'bhamblenb@squarespace.com', 73312),
	(16, 'ffredyc', '68a49034e1fac7c5f145f22161f67a1ce7c9baa26464cac7d6661805d22f4779', 'tstoffelsc@myspace.com', 28727),
	(17, 'eharbrond', 'ab4e2d00232ef5ba23f964cae14f803a0f3968a2977b7567173e35ce3e46dca9', 'hhudspethd@indiatimes.com', 87304),
	(18, 'emeltetale', 'e19a9cf4599c22576e07f35a2f93f2a85425e08b486dec0142f162604a11d053', 'lpollye@jugem.jp', 59823),
	(19, 'hjollifff', '384434d2cc108e2eabe197692336f7c5308130dadd7b80e24d0f9dd725cbc89b', 'garchardf@yahoo.co.jp', 17164),
	(20, 'bklempkeg', '1a7c054e6f801ee83875f8404e27a7996072ab47dd373d3cc666b664c494051a', 'lpriseg@creativecommons.org', 9701),
	(21, 'spossah', '94aff63533a7261484181f5e0af918f4207c9ef7f9632c48696f6e4e1c4dbc18', 'kkynderh@soundcloud.com', 31936),
	(22, 'aaleksandrovi', 'a6f1988cb187b406b50187f2fd32fb3785cedc4cc3dbb51e2bee998fdd1fc617', 'rdumbarei@cargocollective.com', 10168),
	(23, 'lfaberj', '6421ffdd964525202bb0a258f2b42ae58a2a4be4acc0c5199f046f12b02719ca', 'hheildsj@vimeo.com', 42937);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
