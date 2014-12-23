-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 23, 2014 at 01:04 PM
-- Server version: 10.0.15-MariaDB-log
-- PHP Version: 5.6.3

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
  `GenerateTime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `Expired` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `AccessKey`
--

INSERT INTO `AccessKey` (`Key`, `UserId`, `GenerateTime`, `Expired`) VALUES
('8YYB8bZwkALoknzBPMmmyXFyo7laX3zkqrDCKd3e40Nyql3nlUkBy4GpZgnOMYyS', 1, '2014-07-11 11:32:30.000000', 1),
('Cs2s9gYF5fi0SFV7jmpIZxAZSeSWJVkaErAQMkKO8lyKZS4QAjggJISiBDM7NH3E', 1, '2014-07-16 13:12:47.998877', 1),
('Grn6beabZyo3y3veUjCuDJB1YmWUsPRp189qk0f7Z1saFJb0LgFtTDcGqkz9DYmP', 1, '2014-05-11 07:18:49.663471', 1),
('HJ5MEoHKmF8gqawi3eZucvGo2No2glutopX9y1HQcHUMII1vfKyvCyUXMiubegak', 1, '2014-07-16 13:13:53.717511', 0),
('l16a4GH9nE9lFUugucfSUJK6GMBomtsiy6ZBumItVIP1NbUlPw8b7H66ooCD4AJj', 1, '2014-07-11 16:55:41.000000', 1),
('nt0Yv3DL2Z5iQvHjb11evm6PHuyDOFRU2wxi50AoGqYcaJK3fyVWaRj344UXZDMM', 1, '2014-07-25 04:29:20.758400', 1),
('plGEbofoTlbCQbDjfoM7yRzmgO16WsRlzo1emK4BsDmy1fEFmb7OAmm0FUeAeZdM', 1, '2014-07-24 14:06:47.187412', 1),
('qVUkFcbhAhuyBJO2elaInCKRCXtnr0n7QXqdjJiB3Vl91odruLZ2UdXSSStBAx2i', 1, '2014-07-11 16:56:10.000000', 1),
('Sul6D67qbADlq5gz1BjFcVBowF7mGxEfDK4tjvKhrRMuUcdpzlHg0rmwoAyfht5p', 1, '2014-07-11 16:57:09.000000', 1),
('uKbHThUL3cDbc5IFHLTnjI8BMCBsR3ZFs9ai0GHO6yh8e0GxwAuFsVDjO7kqMTGH', 1, '2014-07-24 14:45:36.198160', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Device`
--

CREATE TABLE IF NOT EXISTS `Device` (
  `DeviceId` char(64) NOT NULL,
  `UserId` int(11) NOT NULL,
  `LastSync` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `MainKey` int(11) NOT NULL,
  PRIMARY KEY (`DeviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Device`
--

INSERT INTO `Device` (`DeviceId`, `UserId`, `LastSync`, `MainKey`) VALUES
('nPhmUJQ9P85CVGe1RXq2CrkREsenast41Mm475kI27coBPF8wUczAV9uy6wWmnVP', 1, '2014-07-16 14:12:54.424814', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Favor`
--

CREATE TABLE IF NOT EXISTS `Favor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `Value` text NOT NULL,
  `Type` int(11) NOT NULL DEFAULT '1',
  `AddOn` mediumtext NOT NULL,
  `Group` text NOT NULL,
  `LastModified` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `CheckCode` varchar(4) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=40 ;

--
-- Dumping data for table `Favor`
--

INSERT INTO `Favor` (`Id`, `UserId`, `Value`, `Type`, `AddOn`, `Group`, `LastModified`, `CheckCode`) VALUES
(1, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(2, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(3, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(4, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(5, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(6, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(7, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(8, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(9, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(10, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(11, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(12, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(13, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(14, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(15, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(16, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(17, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(18, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(19, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(20, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(21, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(22, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(23, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(24, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(25, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(26, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(27, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(28, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(29, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(30, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(31, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(32, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(33, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(34, 1, '223', 1, '433', '', '2014-07-16 10:58:06.904583', 'E5L2'),
(35, 1, '223erws', 1, '433', '', '2014-07-16 12:02:58.260074', 'eI0Q'),
(36, 2, '223erws', 1, '433', '', '2014-07-16 12:02:58.260074', 'dHRA'),
(37, 1, 'sadceww', 1, 'sdafewwds', '', '2014-07-16 13:15:18.647206', 'Xmtp'),
(38, 1, 'sadcewwewr', 1, 'sdafewwds', '', '2014-07-16 13:15:24.529607', 'ndJl'),
(39, 1, 'sadceww', 1, 'sdafeww', '', '2014-07-25 04:50:26.891917', 'tsmB');

-- --------------------------------------------------------

--
-- Table structure for table `Log`
--

CREATE TABLE IF NOT EXISTS `Log` (
  `Time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `Log` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(32) NOT NULL,
  `Password` varchar(32) NOT NULL,
  `EmailAddress` varchar(128) NOT NULL,
  `RegistDate` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `Level` tinyint(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`Id`, `Username`, `Password`, `EmailAddress`, `RegistDate`, `Level`) VALUES
(1, 'test', 'b444ac06613fc8d63795be9ad0beaf55', 'test@catofes.com', '0000-00-00 00:00:00.000000', 1),
(2, 'ppp', 'b3054ff0797ff0b2bbce03ec897fe63e', 'pp@catofes.com', '2014-07-11 16:37:13.000000', 1),
(3, 'pppp', 'c3ae457bb31ea0b0df811cf615e81cb4', 'pppp@catofes.com', '2014-07-11 16:41:55.000000', 1),
(4, 'pppsdfa', '177f2d4a88f96bd793a2fd133cb7e009', 'pps@catofes.com', '2014-07-11 17:17:45.000000', 1),
(5, 'qiaohao', 'a94a8fe5ccb19ba61c4c0873d391e987', 's@gmail.com', '2014-07-25 06:54:57.329808', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
