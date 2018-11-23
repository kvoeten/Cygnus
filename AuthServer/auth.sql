-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.14 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for accounts
CREATE DATABASE IF NOT EXISTS `accounts` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `accounts`;

-- Dumping structure for table accounts.outh_client_details
CREATE TABLE IF NOT EXISTS `priviliges` (
  `id` INT(12)  NOT NULL,
  `name` VARCHAR(256)  NOT NULL,
  `description` VARCHAR(256)  NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table accounts.accounts
CREATE TABLE IF NOT EXISTS `accounts` (
  `nAccountID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nNexonCash` int(11) unsigned NOT NULL DEFAULT '0',
  `nMaplePoint` int(11) unsigned NOT NULL DEFAULT '0',
  `nMileage` int(11) unsigned NOT NULL DEFAULT '0',
  `bVerified` tinyint(1) NOT NULL DEFAULT '0',
  `sAccountName` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sPassword` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sToken` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `sEmail` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sIP` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0.0.0.0',
  `sSecondPW` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `nGradeCode` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `nState` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `nGender` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `nBanned` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pCreateDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pLastLoadDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBirthDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `gender` tinyint(2) unsigned NOT NULL DEFAULT '10',
  `nLastWorldID` tinyint(2) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`nAccountID`),
  UNIQUE KEY `sAccountName_UNIQUE` (`sAccountName`),
  UNIQUE KEY `sEmail_UNIQUE` (`sEmail`),
  UNIQUE KEY `nAccountID_UNIQUE` (`nAccountID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.
-- Dumping structure for table accounts.hwidban
CREATE TABLE IF NOT EXISTS `hwidban` (
  `sHWID` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBanEndDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  PRIMARY KEY (`sHWID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.
-- Dumping structure for table accounts.ipban
CREATE TABLE IF NOT EXISTS `ipban` (
  `sIP` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBanEndDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  PRIMARY KEY (`sIP`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.
-- Dumping structure for table accounts.macban
CREATE TABLE IF NOT EXISTS `macban` (
  `sMac` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBanEndDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  PRIMARY KEY (`sMac`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
