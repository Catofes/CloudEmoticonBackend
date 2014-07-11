-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 11, 2014 at 11:37 AM
-- Server version: 5.5.37-MariaDB-log
-- PHP Version: 5.5.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `CloudEmoticon`
--

-- --------------------------------------------------------

--
-- Table structure for table `AccessKey`
--

CREATE TABLE IF NOT EXISTS `AccessKey` (
  `Key` varchar(64) NOT NULL,
  `UserId` int(11) NOT NULL,
  `GenerateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Expired` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Key`),
  UNIQUE KEY `Key_2` (`Key`),
  KEY `Key` (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `AccessKey`
--

INSERT INTO `AccessKey` (`Key`, `UserId`, `GenerateTime`, `Expired`) VALUES
('8YYB8bZwkALoknzBPMmmyXFyo7laX3zkqrDCKd3e40Nyql3nlUkBy4GpZgnOMYyS', 1, '2014-07-11 11:32:30', 1),
('Grn6beabZyo3y3veUjCuDJB1YmWUsPRp189qk0f7Z1saFJb0LgFtTDcGqkz9DYmP', 1, '2014-05-11 07:18:49', 1);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(32) NOT NULL,
  `Password` varchar(32) NOT NULL,
  `EmailAddress` varchar(128) NOT NULL,
  `RegistDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Level` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`Id`, `Username`, `Password`, `EmailAddress`, `RegistDate`, `Level`) VALUES
(1, 'test', 'b444ac06613fc8d63795be9ad0beaf55', 'test@catofes.com', '0000-00-00 00:00:00', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
