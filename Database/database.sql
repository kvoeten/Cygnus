CREATE DATABASE IF NOT EXISTS `accounts` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `accounts`;

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `nAccountID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nNexonCash` int(11) unsigned NOT NULL default '0',
  `nMaplePoint` int(11) unsigned NOT NULL default '0',
  `nMileage` int(11) unsigned NOT NULL default '0',
  `bVerified` BOOL NOT NULL DEFAULT false,
  `sAccountName` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sPassword` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sToken` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL default "",
  `sEmail` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sIP` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL default "0.0.0.0",
  `sSecondPW` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT "",
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

DROP TABLE IF EXISTS `ipban`;
CREATE TABLE IF NOT EXISTS `ipban` (
  `sIP` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBanEndDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  PRIMARY KEY (`sIP`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `macban`;
CREATE TABLE IF NOT EXISTS `macban` (
  `sMac` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBanEndDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  PRIMARY KEY (`sMac`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `hwidban`;
CREATE TABLE IF NOT EXISTS `hwidban` (
  `sHWID` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pBanDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  `pBanEndDate` timestamp NOT NULL DEFAULT '1971-01-01 00:00:01',
  PRIMARY KEY (`sHWID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping database structure for center
DROP DATABASE IF EXISTS `center`;
CREATE DATABASE IF NOT EXISTS `center` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `center`;

-- Dumping structure for table center.avatardata
DROP TABLE IF EXISTS `avatardata`;
CREATE TABLE IF NOT EXISTS `avatardata` (
  `dwCharacterID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nAccountID` int(11) unsigned NOT NULL,
  `nCharlistPos` int(11) NOT NULL DEFAULT '0',
  `nWorld` int(11) NOT NULL DEFAULT '0',
  `nRank` int(11) NOT NULL DEFAULT '0',
  `nRankMove` int(11) NOT NULL DEFAULT '0',
  `nOverallRank` int(11) NOT NULL DEFAULT '0',
  `nOverallRankMove` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dwCharacterID`),
  UNIQUE KEY `dwCharacterID` (`dwCharacterID`),
  KEY `nAccountID` (`nAccountID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.avatardata: 0 rows
DELETE FROM `avatardata`;
/*!40000 ALTER TABLE `avatardata` DISABLE KEYS */;
/*!40000 ALTER TABLE `avatardata` ENABLE KEYS */;

-- Dumping structure for table center.avatarlook
DROP TABLE IF EXISTS `avatarlook`;
CREATE TABLE IF NOT EXISTS `avatarlook` (
  `dwCharacterID` int(11) unsigned NOT NULL,
  `nGender` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nSkin` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nFace` int(11) NOT NULL DEFAULT '0',
  `nHair` int(11) NOT NULL DEFAULT '0',
  `nJob` int(11) NOT NULL DEFAULT '0',
  `nWeaponsStickerID` int(11) NOT NULL DEFAULT '0',
  `nWeaponID` int(11) NOT NULL DEFAULT '0',
  `nSubWeaponID` int(11) NOT NULL DEFAULT '0',
  `bDrawElfEar` tinyint(1) NOT NULL DEFAULT '0',
  `nXenonDefFaceAcc` int(11) NOT NULL DEFAULT '0',
  `nDemonSlayerDefFaceAcc` int(11) NOT NULL DEFAULT '0',
  `nBeastDefFaceAcc` int(11) NOT NULL DEFAULT '0',
  `nBeastEars` int(11) NOT NULL DEFAULT '0',
  `nBeastTail` int(11) NOT NULL DEFAULT '0',
  `nMixedHairColor` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nMixHairPercent` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.avatarlook: 0 rows
DELETE FROM `avatarlook`;
/*!40000 ALTER TABLE `avatarlook` DISABLE KEYS */;
/*!40000 ALTER TABLE `avatarlook` ENABLE KEYS */;

-- Dumping structure for table center.gw_cashitemoption
DROP TABLE IF EXISTS `gw_cashitemoption`;
CREATE TABLE IF NOT EXISTS `gw_cashitemoption` (
  `liCashItemSN` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nGrade` int(11) NOT NULL DEFAULT '0',
  `ftEquippedLow` int(11) NOT NULL DEFAULT '0',
  `ftEquippedHigh` int(11) NOT NULL DEFAULT '0',
  `aCashStats1` int(11) NOT NULL DEFAULT '0',
  `aCashStats2` int(11) NOT NULL DEFAULT '0',
  `aCashStats3` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`liCashItemSN`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.gw_cashitemoption: 0 rows
DELETE FROM `gw_cashitemoption`;
/*!40000 ALTER TABLE `gw_cashitemoption` DISABLE KEYS */;
/*!40000 ALTER TABLE `gw_cashitemoption` ENABLE KEYS */;

-- Dumping structure for table center.gw_characterstat
DROP TABLE IF EXISTS `gw_characterstat`;
CREATE TABLE IF NOT EXISTS `gw_characterstat` (
  `dwCharacterID` int(11) unsigned NOT NULL,
  `dwCharacterIDForLog` int(11) NOT NULL,
  `dwWorldIDForLog` int(11) NOT NULL,
  `sCharacterName` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `nGender` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nSkin` int(11) unsigned NOT NULL DEFAULT '0',
  `nFace` int(11) NOT NULL DEFAULT '0',
  `nHair` int(11) NOT NULL DEFAULT '0',
  `nMixBaseHairColor` tinyint(3) NOT NULL DEFAULT '-1',
  `nMixAddHairColor` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nMixHairBaseProb` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nLevel` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nJob` int(6) unsigned NOT NULL DEFAULT '0',
  `nSTR` int(6) NOT NULL DEFAULT '0',
  `nDEX` int(6) NOT NULL DEFAULT '0',
  `nINT` int(6) NOT NULL DEFAULT '0',
  `nLUK` int(6) NOT NULL DEFAULT '0',
  `nHP` int(11) NOT NULL DEFAULT '0',
  `nMHP` int(11) NOT NULL DEFAULT '0',
  `nMP` int(11) NOT NULL DEFAULT '0',
  `nMMP` int(11) NOT NULL DEFAULT '0',
  `nAP` int(6) NOT NULL DEFAULT '0',
  `nSP` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nExp64` int(11) NOT NULL DEFAULT '0',
  `nPop` int(11) NOT NULL DEFAULT '0',
  `nWP` int(11) NOT NULL DEFAULT '0',
  `dwPosMap` int(11) NOT NULL DEFAULT '0',
  `nPortal` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nSubJob` int(6) NOT NULL DEFAULT '0',
  `nDefFaceAcc` int(11) NOT NULL DEFAULT '0',
  `nFatigue` int(6) unsigned NOT NULL DEFAULT '0',
  `nLastFatigureUpdateTime` int(11) NOT NULL DEFAULT '0',
  `nCharismaEXP` int(11) NOT NULL DEFAULT '0',
  `nInsightExp` int(11) NOT NULL DEFAULT '0',
  `nWillExp` int(11) NOT NULL DEFAULT '0',
  `nCraftExp` int(11) NOT NULL DEFAULT '0',
  `nSenseExp` int(11) NOT NULL DEFAULT '0',
  `nCharmExp` int(11) NOT NULL DEFAULT '0',
  `DayLimit` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nPvPExp` int(11) NOT NULL DEFAULT '0',
  `nPVPGrade` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nPvpPoint` int(11) NOT NULL DEFAULT '0',
  `nPvpModeLevel` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nPvpModeType` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nEventPoint` int(11) NOT NULL DEFAULT '0',
  `ftLastLogoutTimeHigh` int(11) NOT NULL DEFAULT '0',
  `ftLastLogoutTimeLow` int(11) NOT NULL DEFAULT '0',
  `bBurning` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.gw_characterstat: 0 rows
DELETE FROM `gw_characterstat`;
/*!40000 ALTER TABLE `gw_characterstat` DISABLE KEYS */;
/*!40000 ALTER TABLE `gw_characterstat` ENABLE KEYS */;

-- Dumping structure for table center.gw_dressupinfo
DROP TABLE IF EXISTS `gw_dressupinfo`;
CREATE TABLE IF NOT EXISTS `gw_dressupinfo` (
  `dwCharacterID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nFace` int(11) unsigned NOT NULL,
  `nHair` int(11) NOT NULL DEFAULT '0',
  `nClothes` int(11) NOT NULL DEFAULT '0',
  `nSkin` int(11) NOT NULL DEFAULT '0',
  `nMixBaseHairColor` int(11) NOT NULL DEFAULT '0',
  `nMixAddHairColor` int(11) NOT NULL DEFAULT '0',
  `nMixHairBaseProb` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.gw_dressupinfo: 0 rows
DELETE FROM `gw_dressupinfo`;
/*!40000 ALTER TABLE `gw_dressupinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `gw_dressupinfo` ENABLE KEYS */;

-- Dumping structure for table center.gw_itemslotbundle
DROP TABLE IF EXISTS `gw_itemslotbundle`;
CREATE TABLE IF NOT EXISTS `gw_itemslotbundle` (
  `dwCharacterID` int(11) NOT NULL,
  `nItemID` int(11) NOT NULL DEFAULT '0',
  `nPos` int(11) NOT NULL DEFAULT '0',
  `dateExpireLow` int(11) NOT NULL DEFAULT '0',
  `dateExpireHigh` int(11) NOT NULL DEFAULT '0',
  `nBagIndex` int(11) NOT NULL DEFAULT '0',
  `nNumber` int(6) NOT NULL DEFAULT '1',
  `nAttribute` int(6) NOT NULL DEFAULT '1',
  `sTitle` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.gw_itemslotbundle: 0 rows
DELETE FROM `gw_itemslotbundle`;
/*!40000 ALTER TABLE `gw_itemslotbundle` DISABLE KEYS */;
/*!40000 ALTER TABLE `gw_itemslotbundle` ENABLE KEYS */;

-- Dumping structure for table center.gw_itemslotequip
DROP TABLE IF EXISTS `gw_itemslotequip`;
CREATE TABLE IF NOT EXISTS `gw_itemslotequip` (
  `liSN` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dwCharacterID` int(11) NOT NULL,
  `nItemID` int(11) NOT NULL DEFAULT '0',
  `nPos` int(11) NOT NULL DEFAULT '-1',
  `dateExpireLow` int(11) NOT NULL DEFAULT '0',
  `dateExpireHigh` int(11) NOT NULL DEFAULT '0',
  `nBagIndex` int(11) NOT NULL DEFAULT '-1',
  `nPrevBonusExpRate` int(11) NOT NULL DEFAULT '-1',
  `sTitle` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT "",
  `vSlot` BOOL NOT NULL DEFAULT false,
  `nSlot` int(11) NOT NULL DEFAULT '0',
  `ftEquippedLow` int(11) NOT NULL DEFAULT '0',
  `ftEquippedHigh` int(11) NOT NULL DEFAULT '0',
  `nGrade` int(11) NOT NULL DEFAULT '0',
  `nCHUC` int(11) NOT NULL DEFAULT '0',
  `nOption1` int(6) NOT NULL DEFAULT '0',
  `nOption2` int(6) NOT NULL DEFAULT '0',
  `nOption3` int(6) NOT NULL DEFAULT '0',
  `nOption4` int(6) NOT NULL DEFAULT '0',
  `nOption5` int(6) NOT NULL DEFAULT '0',
  `nOption6` int(6) NOT NULL DEFAULT '0',
  `nOption7` int(6) NOT NULL DEFAULT '0',
  `nSocketState` int(6) NOT NULL DEFAULT '0',
  `nSocket1` int(6) NOT NULL DEFAULT '0',
  `nSocket2` int(6) NOT NULL DEFAULT '0',
  `nSocket3` int(6) NOT NULL DEFAULT '0',
  `nSoulOptionID` int(6) NOT NULL DEFAULT '0',
  `nSoulSocketID` int(6) NOT NULL DEFAULT '0',
  `nSoulOption` int(6) NOT NULL DEFAULT '0',
  `nRUC` int(11) NOT NULL DEFAULT '0',
  `nCUC` int(11) NOT NULL DEFAULT '0',
  `niSTR` int(11) NOT NULL DEFAULT '0',
  `niDEX` int(11) NOT NULL DEFAULT '0',
  `niINT` int(11) NOT NULL DEFAULT '0',
  `niLUK` int(11) NOT NULL DEFAULT '0',
  `niMaxHP` int(11) NOT NULL DEFAULT '0',
  `niMaxMP` int(11) NOT NULL DEFAULT '0',
  `niPAD` int(11) NOT NULL DEFAULT '0',
  `niMAD` int(11) NOT NULL DEFAULT '0',
  `niPDD` int(11) NOT NULL DEFAULT '0',
  `niMDD` int(11) NOT NULL DEFAULT '0',
  `niACC` int(11) NOT NULL DEFAULT '0',
  `niEVA` int(11) NOT NULL DEFAULT '0',
  `niCraft` int(11) NOT NULL DEFAULT '0',
  `niSpeed` int(11) NOT NULL DEFAULT '0',
  `niJump` int(11) NOT NULL DEFAULT '0',
  `nAttribute` int(11) NOT NULL DEFAULT '0',
  `nLevelUpType` int(11) NOT NULL DEFAULT '0',
  `nLevel` int(11) NOT NULL DEFAULT '0',
  `nEXP64` int(11) NOT NULL DEFAULT '0',
  `nDurability` int(11) NOT NULL DEFAULT '0',
  `nIUC` int(11) NOT NULL DEFAULT '0',
  `niPVPDamage` int(11) NOT NULL DEFAULT '0',
  `iReduceReq` int(11) NOT NULL DEFAULT '0',
  `nSpecialAttribute` int(11) NOT NULL DEFAULT '0',
  `nDurabilityMax` int(11) NOT NULL DEFAULT '0',
  `niIncReq` int(11) NOT NULL DEFAULT '0',
  `nGrowthEnchant` int(11) NOT NULL DEFAULT '0',
  `nPsEnchant` int(11) NOT NULL DEFAULT '0',
  `nBDR` int(11) NOT NULL DEFAULT '0',
  `niMDR` int(11) NOT NULL DEFAULT '0',
  `nDamR` int(11) NOT NULL DEFAULT '0',
  `nStatR` int(11) NOT NULL DEFAULT '0',
  `nCuttable` int(11) NOT NULL DEFAULT '0',
  `nExGradeOption` int(11) NOT NULL DEFAULT '0',
  `nItemState` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`liSN`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.gw_itemslotequip: 0 rows
DELETE FROM `gw_itemslotequip`;
/*!40000 ALTER TABLE `gw_itemslotequip` DISABLE KEYS */;
/*!40000 ALTER TABLE `gw_itemslotequip` ENABLE KEYS */;

-- Dumping structure for table center.keymap
DROP TABLE IF EXISTS `keymap`;
CREATE TABLE IF NOT EXISTS `keymap` (
  `dwCharacterID` int(11) NOT NULL,
  `mapping` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table center.rankings
DROP TABLE IF EXISTS `rankings`;
CREATE TABLE IF NOT EXISTS `rankings` (
  `dwCharacterID` int(11) NOT NULL,
  `nRank` int(10) unsigned NOT NULL DEFAULT '0',
  `jobrank` int(10) unsigned NOT NULL DEFAULT '0',
  `rankmove` int(10) unsigned NOT NULL DEFAULT '0',
  `jobrankmove` int(10) unsigned NOT NULL DEFAULT '0',
  `finalrank` int(10) unsigned NOT NULL DEFAULT '0',
  `finaljobrank` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table center.telerock
DROP TABLE IF EXISTS `telerock`;
CREATE TABLE IF NOT EXISTS `telerock` (
  `dwCharacterID` int(10) unsigned NOT NULL,
  `rock` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '999999999;999999999;999999999;999999999;999999999;',
  `vip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '999999999;999999999;999999999;999999999;999999999;999999999;999999999;999999999;999999999;999999999;',
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.telerock: 0 rows
DELETE FROM `telerock`;
/*!40000 ALTER TABLE `telerock` DISABLE KEYS */;
/*!40000 ALTER TABLE `telerock` ENABLE KEYS */;

-- Dumping structure for table center.zeroinfo
DROP TABLE IF EXISTS `zeroinfo`;
CREATE TABLE IF NOT EXISTS `zeroinfo` (
  `dwCharacterID` int(11) unsigned NOT NULL,
  `nSubHP` int(11) unsigned NOT NULL,
  `nSubMP` int(11) NOT NULL DEFAULT '0',
  `nSubSkin` int(11) NOT NULL DEFAULT '0',
  `nSubHair` int(11) NOT NULL DEFAULT '0',
  `nSubFace` int(11) NOT NULL DEFAULT '0',
  `nSubMHP` int(11) NOT NULL DEFAULT '0',
  `nSubMMP` int(11) NOT NULL DEFAULT '0',
  `dbcharZeroLinkCashPart` int(11) NOT NULL DEFAULT '0',
  `nMixBaseHairColor` int(11) NOT NULL DEFAULT '0',
  `nMixAddHairColor` int(11) NOT NULL DEFAULT '0',
  `nMixHairBaseProb` int(11) NOT NULL DEFAULT '0',
  `bIsBeta` tinyint(1) NOT NULL DEFAULT '0',
  `nLapis` int(11) NOT NULL DEFAULT '0',
  `nLazuli` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dwCharacterID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table center.zeroinfo: 0 rows
DELETE FROM `zeroinfo`;
/*!40000 ALTER TABLE `zeroinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `zeroinfo` ENABLE KEYS */;

