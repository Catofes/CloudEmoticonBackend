-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 15, 2014 at 01:30 PM
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
  `GenerateTime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Expired` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Key`),
  UNIQUE KEY `Key_2` (`Key`),
  KEY `Key` (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `AccessKey`
--

INSERT INTO `AccessKey` (`Key`, `UserId`, `GenerateTime`, `Expired`) VALUES
('8YYB8bZwkALoknzBPMmmyXFyo7laX3zkqrDCKd3e40Nyql3nlUkBy4GpZgnOMYyS', 1, '2014-07-11 11:32:30.000000', 1),
('Grn6beabZyo3y3veUjCuDJB1YmWUsPRp189qk0f7Z1saFJb0LgFtTDcGqkz9DYmP', 1, '2014-05-11 07:18:49.663471', 1),
('l16a4GH9nE9lFUugucfSUJK6GMBomtsiy6ZBumItVIP1NbUlPw8b7H66ooCD4AJj', 1, '2014-07-11 16:55:41.000000', 1),
('qVUkFcbhAhuyBJO2elaInCKRCXtnr0n7QXqdjJiB3Vl91odruLZ2UdXSSStBAx2i', 1, '2014-07-11 16:56:10.000000', 1),
('Sul6D67qbADlq5gz1BjFcVBowF7mGxEfDK4tjvKhrRMuUcdpzlHg0rmwoAyfht5p', 1, '2014-07-11 16:57:09.000000', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Favor`
--

CREATE TABLE IF NOT EXISTS `Favor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `Value` text NOT NULL,
  `IfLove` tinyint(4) NOT NULL DEFAULT '1',
  `AddOn` mediumtext NOT NULL,
  `LastModified` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CheckCode` varchar(4) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `Favor`
--

INSERT INTO `Favor` (`Id`, `UserId`, `Value`, `IfLove`, `AddOn`, `LastModified`, `CheckCode`) VALUES
(1, 1, 'sadceww', 0, 'sdafeww', '2014-07-15 13:18:03.204867', 'QRs8'),
(2, 1, 'sadceww', 0, 'sdafeww', '2014-07-15 13:18:54.845229', 'pcK4'),
(3, 1, 'sadceww:s', 0, 'sdafeww', '2014-07-15 13:18:59.987506', 'RYYw'),
(4, 1, 'sadceww:s@#$%你好）——×&），xzcvm“～‘" ''', 0, 'sdafeww', '2014-07-15 13:19:22.160385', 'I96t'),
(5, 1, 'sadceww:s@#$%你好）——×&），xzcvm“～‘" ''', 0, 'sdafeww', '2014-07-15 13:20:03.761519', 'KCtN'),
(6, 1, 'sadceww:s@#$%你好 \\n ）——×&），xzcvm“～‘" ''', 0, 'sdafeww', '2014-07-15 13:20:09.508338', '9Ymg'),
(7, 1, 'sadceww:s@#$%你好\r\n\r\n \\n ）——×&），xzcvm“～‘" ''', 0, 'sdf', '2014-07-15 13:21:24.468993', 'gbAf'),
(8, 1, 'sadceww:s@#$%你好  ）——×&），xzcvm“～‘" ''', 0, 'sdf', '2014-07-15 13:20:55.731134', 'Bsva'),
(9, 1, 'sadceww:s@#$%你好 \\r\\n ）——×&），xzcvm“～‘" ''', 0, 'sdf', '2014-07-15 13:21:58.637392', '0bso'),
(10, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 0, 'sdf', '2014-07-15 13:22:10.405660', 'ET7e'),
(11, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''sdfa', 0, 'sdafeww', '2014-07-15 13:22:24.775287', '8Ed6'),
(12, 1, 'sfda', 0, '', '2014-07-15 13:24:25.422417', '2gvF'),
(13, 1, 'sfdaAA]]-)_(', 0, '', '2014-07-15 13:24:43.541170', 'zGx6'),
(14, 1, 'sadceww', 1, 'sdafeww', '2014-07-15 13:27:14.307870', 'uGHM'),
(15, 1, 'sadcewwsdfaf34545y745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:27:27.218725', 'PVPn'),
(16, 1, 'sadcewwsdfaf34545y \\n 745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:27:45.703783', 'Lltg'),
(17, 1, 'sadcewwsdfaf34545y  745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:27:57.456362', 'ujWx'),
(18, 1, 'sadcewwsdfaf34545y  745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:27:59.727145', '92bF'),
(19, 1, 'sadcewwsdfaf34545y  745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:28:01.751402', '0JaV'),
(20, 1, 'sadcewwsdfaf34545y  745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:28:02.772988', 'AKRy'),
(21, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:28:17.786779', 'BJPz'),
(22, 1, 'sadcewwsdfaf34545y  745y 地方v第三方'';,../0123780.s,c$%^&*()@#^&%!^', 1, 'sdafeww', '2014-07-15 13:28:22.626874', 'A8OO'),
(23, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:28:24.449222', 'qJi4'),
(24, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:28:42.543818', 'j3jZ'),
(25, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:03.351514', 'Syzc'),
(26, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:11.120885', 'tdet'),
(27, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:11.782379', 'i7Zt'),
(28, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:12.287623', '755k'),
(29, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:12.717917', 'I2oy'),
(30, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:12.918970', 'qfUF'),
(31, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:13.062007', 'vFtk'),
(32, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:13.245533', 'jr1m'),
(33, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:13.413342', 'WIk3'),
(34, 1, 'sadceww:s@#$%你好 \\r\\n <br> ）——×&），xzcvm“～‘" ''', 1, 'sdf', '2014-07-15 13:30:13.597147', 'uxNt');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(32) NOT NULL,
  `Password` varchar(32) NOT NULL,
  `EmailAddress` varchar(128) NOT NULL,
  `RegistDate` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Level` tinyint(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`Id`, `Username`, `Password`, `EmailAddress`, `RegistDate`, `Level`) VALUES
(1, 'test', 'b444ac06613fc8d63795be9ad0beaf55', 'test@catofes.com', '0000-00-00 00:00:00.000000', 1),
(2, 'ppp', 'b3054ff0797ff0b2bbce03ec897fe63e', 'pp@catofes.com', '2014-07-11 16:37:13.000000', 0),
(3, 'pppp', 'c3ae457bb31ea0b0df811cf615e81cb4', 'pppp@catofes.com', '2014-07-11 16:41:55.000000', 0),
(4, 'pppsdfa', '177f2d4a88f96bd793a2fd133cb7e009', 'pps@catofes.com', '2014-07-11 17:17:45.000000', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
