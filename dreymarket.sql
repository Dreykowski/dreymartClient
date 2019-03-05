-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2019 at 03:46 AM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dreymarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `banner`
--

CREATE TABLE `banner` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `banner`
--

INSERT INTO `banner` (`ID`, `Name`, `Link`) VALUES
(2, 'Promo Terbesar', 'https://firebasestorage.googleapis.com/v0/b/clientdreymart.appspot.com/o/geometric-sale-banners-with-text-space_1017-11175.jpg?alt=media&token=81dba893-3d0a-4699-b32f-fbed56ef9604'),
(3, 'Diskon hingga 25%', 'https://firebasestorage.googleapis.com/v0/b/clientdreymart.appspot.com/o/hand-drawn-sale-banners_23-2147740310.jpg?alt=media&token=da360e14-e3f1-4dd2-84cd-4af81869c6b9'),
(4, 'Selamat Tahun Baru 2019', 'https://firebasestorage.googleapis.com/v0/b/clientdreymart.appspot.com/o/modern-banners-for-new-year-2018_23-2147731494.jpg?alt=media&token=1474412d-a62a-4dcf-812c-05cc962c6700'),
(5, 'Promo Spesial', 'https://firebasestorage.googleapis.com/v0/b/clientdreymart.appspot.com/o/sale-banner-template-design-in-memphis-style_1199-161.jpg?alt=media&token=adcdc0f2-a73c-4d30-a4d6-d91dc58b51dd');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL,
  `Price` float NOT NULL,
  `MenuId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`ID`, `Name`, `Link`, `Price`, `MenuId`) VALUES
(53, 'Lotion Vampire ', 'http://192.168.43.10/dreymarket/server/product/product_img/fd3e36f7-52d9-4bdb-88b2-7f1f341221f0.jpg', 15000, 17),
(54, 'Baygon One Push', 'http://192.168.43.10/dreymarket/server/product/product_img/4c8caf10-e4a9-41c4-aa34-fee682c99c8a.jpg', 10000, 17),
(55, 'Baygon 270ml', 'http://192.168.43.10/dreymarket/server/product/product_img/a75ec0a7-e761-4790-914c-e92ebf0c6920.jpg', 13000, 17),
(56, 'Bakmi Mewah free Mangkuk', 'http://192.168.43.10/dreymarket/server/product/product_img/851cba22-674b-4648-996c-bd688a5da52c.jpg', 35000, 18),
(57, 'Indomie Soto Spesial', 'http://192.168.43.10/dreymarket/server/product/product_img/9eaaa29b-d3a1-41ba-90f5-646123d3ba78.jpg', 3000, 18),
(58, 'Indomie Soto Padang', 'http://192.168.43.10/dreymarket/server/product/product_img/d40170db-404c-4d1e-8187-86bc61e0f972.jpg', 3000, 18),
(59, 'Indomie Soto Mie', 'http://192.168.43.10/dreymarket/server/product/product_img/3d7bb8b3-06e3-4596-9df9-cb23420026bf.jpg', 2500, 18),
(60, 'Ensure Vanilla', 'http://192.168.43.10/dreymarket/server/product/product_img/0c7eae23-3ae8-4746-809e-38b76e974594.jpg', 45000, 19),
(61, 'Teh Pucuk Harum', 'http://192.168.43.10/dreymarket/server/product/product_img/37d8faff-e6af-46f5-b75f-508750c45129.jpg', 5000, 19),
(62, 'Teh Kotak', 'http://192.168.43.10/dreymarket/server/product/product_img/abc0844e-c32a-4d9b-b1e3-bd149dfcd4a3.jpg', 5500, 19),
(63, 'Nescafe Gold Blend ', 'http://192.168.43.10/dreymarket/server/product/product_img/436a06da-20a5-4c30-aaf1-336cd3d80ba0.jpg', 6000, 19),
(64, 'Bimoli', 'http://192.168.43.10/dreymarket/server/product/product_img/3a3d60ff-f169-4d92-923a-495eeecceeb5.jpg', 20000, 20),
(65, 'Tropical Minyak Goreng ', 'http://192.168.43.10/dreymarket/server/product/product_img/6ff06be1-6a57-4988-a963-38133a60b1d5.jpg', 26000, 20),
(66, 'Sania Premium ', 'http://192.168.43.10/dreymarket/server/product/product_img/c0217818-3939-4a6e-8576-c1fd575f29f5.jpg', 33000, 20),
(67, 'Rose Brand Minyak Goreng ', 'http://192.168.43.10/dreymarket/server/product/product_img/7ce06ac2-7233-409b-b1a3-a095d7dce9d4.jpg', 29000, 20),
(68, 'Yupi Ice Cream', 'http://192.168.43.10/dreymarket/server/product/product_img/62891e75-f872-4681-9195-c2f2ca9ed0da.jpg', 15000, 21),
(69, 'Tamarin', 'http://192.168.43.10/dreymarket/server/product/product_img/68bb352d-2d86-4eac-871f-d51b8e93e26d.jpg', 24000, 21),
(70, 'Kis Mint Grape ', 'http://192.168.43.10/dreymarket/server/product/product_img/80c72b1c-4179-47e8-9a11-23c28f0de177.jpg', 21500, 21),
(71, 'Koplak coffee Shot classic', 'http://192.168.43.10/dreymarket/server/product/product_img/1dc1e6df-6bed-4754-b2ec-09262ee4f6ee.jpg', 22000, 21),
(72, 'Djarum Super Filter', 'http://192.168.43.10/dreymarket/server/product/product_img/ca9e63c6-76d0-4d48-83a5-39bd12f2968f.jpg', 21000, 22),
(73, 'Umild', 'http://192.168.43.10/dreymarket/server/product/product_img/3f0a4dbc-77e3-4a85-a7bf-b20399cbdcd2.jpg', 19000, 22),
(74, 'Sampoerna Mild', 'http://192.168.43.10/dreymarket/server/product/product_img/59ff61ba-a86e-4ae5-8729-1e8c45677f7c.jpg', 24000, 22),
(75, 'Marlboro', 'http://192.168.43.10/dreymarket/server/product/product_img/c839017e-e234-4f8f-b3ac-27c39e178fe9.jpg', 25000, 22),
(76, 'Yuri Hand Soap Grape', 'http://192.168.43.10/dreymarket/server/product/product_img/959990ec-3b4e-4b45-b89e-196613edb32f.jpg', 14000, 23),
(77, 'Shinzui Ume Keiko', 'http://192.168.43.10/dreymarket/server/product/product_img/1760a98c-d6c1-4c05-ab14-811a81193fb5.jpg', 4000, 23),
(78, 'Velvy Goat', 'http://192.168.43.10/dreymarket/server/product/product_img/b2499cf5-54d6-4b2d-9b7d-0944b12f33aa.jpg', 14000, 23),
(79, 'Nuvo Family', 'http://192.168.43.10/dreymarket/server/product/product_img/a1f4d6c0-1d56-480e-aeea-8a2e533de82d.jpg', 3500, 23),
(80, 'Clear Super Fresh 170ml', 'http://192.168.43.10/dreymarket/server/product/product_img/50610f02-0630-42e3-b6b8-40eb3b2d2e2c.jpg', 14000, 24),
(81, 'Dove 70ml', 'http://192.168.43.10/dreymarket/server/product/product_img/b570c1f2-ca39-48d8-81ef-108e515e4e57.jpg', 10000, 24),
(82, 'Zinc Gold Premium', 'http://192.168.43.10/dreymarket/server/product/product_img/35ab90cc-9bfd-41cd-8ed8-047d9b3bc8cf.jpg', 15000, 24),
(83, 'Clear Men Shampoo 170ml', 'http://192.168.43.10/dreymarket/server/product/product_img/12b9c01f-c9e9-49fa-b880-2948a43bbe70.jpg', 15000, 24),
(84, 'Dreymart ', 'http://192.168.43.10/dreymarket/server/product/product_img/2a2230f1-6672-4728-8fe0-0595a809060f.jpg', 20000, 18);

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`ID`, `Name`, `Link`) VALUES
(17, 'Pengusir Serangga', 'http://192.168.43.10/dreymarket/server/category/category_img/5417de5a-59f1-4cb8-b643-a3c216939ac9.jpg'),
(18, 'Mie Instan', 'http://192.168.43.10/dreymarket/server/category/category_img/f9c4a927-c7ba-411f-aa15-b99ac3c2ef1f.jpg'),
(19, 'Minuman', 'http://192.168.43.10/dreymarket/server/category/category_img/cef20119-ecfd-464f-96d8-505a8d650a09.jpg'),
(20, 'Minyak Goreng', 'http://192.168.43.10/dreymarket/server/category/category_img/a11d18fc-a45b-494d-983c-d8d8fca5ed45.jpg'),
(21, 'Permen', 'http://192.168.43.10/dreymarket/server/category/category_img/8c74b718-d162-41bf-a3fa-d15c7133b17a.jpg'),
(22, 'Rokok', 'http://192.168.43.10/dreymarket/server/category/category_img/677c07df-68d4-41e6-a21b-e311c47056b5.jpg'),
(23, 'Sabun', 'http://192.168.43.10/dreymarket/server/category/category_img/73bd1da2-5046-4e0d-8749-9eb86f89a1e5.jpg'),
(24, 'Sampo', 'http://192.168.43.10/dreymarket/server/category/category_img/165627cd-3dc9-42c2-b7d7-176d9cff9284.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `OrderId` bigint(11) NOT NULL,
  `OrderDate` datetime NOT NULL,
  `OrderStatus` tinyint(4) NOT NULL,
  `OrderPrice` float NOT NULL,
  `OrderDetail` text NOT NULL,
  `OrderComment` text NOT NULL,
  `OrderAddress` text NOT NULL,
  `UserPhone` varchar(20) NOT NULL,
  `PaymentMethod` varchar(11) NOT NULL DEFAULT 'Braintree'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`OrderId`, `OrderDate`, `OrderStatus`, `OrderPrice`, `OrderDetail`, `OrderComment`, `OrderAddress`, `UserPhone`, `PaymentMethod`) VALUES
(27, '2019-01-09 06:04:49', -1, 60000, '[{\"amount\":3,\"ice\":-1,\"id\":9,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/09057caa-8d8b-4977-98ed-ee0f56805333.jpg\",\"name\":\"Electronic Mosquito\",\"price\":60000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', '', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(28, '2019-01-09 06:39:02', -1, 228000, '[{\"amount\":1,\"ice\":-1,\"id\":10,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/9eaaa29b-d3a1-41ba-90f5-646123d3ba78.jpg\",\"name\":\"Indomie Soto Spesial\",\"price\":3000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"},{\"amount\":5,\"ice\":-1,\"id\":11,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/0c7eae23-3ae8-4746-809e-38b76e974594.jpg\",\"name\":\"Ensure Vanilla\",\"price\":225000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Pengiriman cepat sukses truss.. â™¥ï¸', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(29, '2019-01-09 06:43:28', -1, 14000, '[{\"amount\":1,\"ice\":-1,\"id\":12,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/959990ec-3b4e-4b45-b89e-196613edb32f.jpg\",\"name\":\"Yuri Hand Soap Grape\",\"price\":14000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Huh', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(30, '2019-01-15 09:06:45', -1, 185000, '[{\"amount\":1,\"ice\":-1,\"id\":13,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/4c8caf10-e4a9-41c4-aa34-fee682c99c8a.jpg\",\"name\":\"Baygon One Push\",\"price\":10000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"},{\"amount\":5,\"ice\":-1,\"id\":14,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/851cba22-674b-4648-996c-bd688a5da52c.jpg\",\"name\":\"Bakmi Mewah free Mangkuk\",\"price\":175000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Tolong dikirim secepat nya. ', 'Jalan. S. Parman Gg tomat. ', '+6289689035998', 'COD'),
(31, '2019-01-15 09:07:39', -1, 20000, '[{\"amount\":1,\"ice\":-1,\"id\":15,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/09057caa-8d8b-4977-98ed-ee0f56805333.jpg\",\"name\":\"Electronic Mosquito\",\"price\":20000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', '', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(32, '2019-01-15 09:07:57', -1, 20000, '[{\"amount\":1,\"ice\":-1,\"id\":16,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/09057caa-8d8b-4977-98ed-ee0f56805333.jpg\",\"name\":\"Electronic Mosquito\",\"price\":20000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Jwhah', 'Hahaha', '+6289689035998', 'COD'),
(33, '2019-01-15 22:46:16', -1, 175000, '[{\"amount\":5,\"ice\":-1,\"id\":17,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/851cba22-674b-4648-996c-bd688a5da52c.jpg\",\"name\":\"Bakmi Mewah free Mangkuk\",\"price\":175000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', '', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(34, '2019-01-15 22:47:19', -1, 3000, '[{\"amount\":1,\"ice\":-1,\"id\":18,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/9eaaa29b-d3a1-41ba-90f5-646123d3ba78.jpg\",\"name\":\"Indomie Soto Spesial\",\"price\":3000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', '', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(35, '2019-01-16 07:02:59', -1, 175000, '[{\"amount\":5,\"ice\":-1,\"id\":19,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/851cba22-674b-4648-996c-bd688a5da52c.jpg\",\"name\":\"Bakmi Mewah free Mangkuk\",\"price\":175000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Kirimkan cepetann.. ', 'Jln. S. Parman Gg tomat ', '+6289689035998', 'COD'),
(36, '2019-01-16 08:09:40', -1, 20000, '[{\"amount\":1,\"ice\":-1,\"id\":20,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/09057caa-8d8b-4977-98ed-ee0f56805333.jpg\",\"name\":\"Electronic Mosquito\",\"price\":20000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', '', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(37, '2019-01-16 09:19:24', -1, 20000, '[{\"amount\":1,\"ice\":-1,\"id\":21,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/09057caa-8d8b-4977-98ed-ee0f56805333.jpg\",\"name\":\"Electronic Mosquito\",\"price\":20000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Nope ', 'Jln S. parman Gg tomat ', '+6289689035998', 'COD'),
(38, '2019-01-16 09:57:53', 3, 140000, '[{\"amount\":4,\"ice\":-1,\"id\":22,\"link\":\"http://192.168.43.10/dreymarket/server/product/product_img/851cba22-674b-4648-996c-bd688a5da52c.jpg\",\"name\":\"Bakmi Mewah free Mangkuk\",\"price\":140000.0,\"size\":-1,\"sugar\":-1,\"toppingExtras\":\"\"}]', 'Tolong kirim secepatnya ', 'Jln s. Parman ', '+6289689035998', 'COD');

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`id`, `name`, `lat`, `lng`) VALUES
(1, 'Dreymart 1st', -1.834352, 109.97861),
(2, 'Dreymarket 2nd', -1.845118, 109.980278);

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `phone` varchar(20) NOT NULL,
  `token` text NOT NULL,
  `isServerToken` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`phone`, `token`, `isServerToken`) VALUES
('+6289689035998', 'c7i0rXdkHBQ:APA91bGRSW3nM66X8rpl_fLcowERd5hOFWIAT_6QgB4x6fpp3_E34zSoWhhUcuefim2jkm6P1d1F1gh9luyWmR3fXo1SsBg5oahJBuzXrEdMW0dPpff5FV6mPJvXHZXq7RiDyBv3ItyR', 0),
('server_app_01', 'cAB_j14zOBE:APA91bGBKovkJ2udZZ1C15pkIkCprt2bVkQaPFln6NmrkHx0nDYu7HLchBnq7_4vgtkSn-HITR8vY7c4WLi4chBdMgdrf8fllyUrob4I73OPkSdtEgSXUpXU5Qn1zeAs34x1EKrwUl1Q', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Phone` varchar(20) NOT NULL,
  `avatarUrl` text NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Birthdate` date NOT NULL,
  `Address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Phone`, `avatarUrl`, `Name`, `Birthdate`, `Address`) VALUES
('+6289689035998', '+6289689035998.jpg', 'Rey', '1997-07-10', 'Jln S. parman Gg tomat ');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banner`
--
ALTER TABLE `banner`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `MenuId` (`MenuId`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`OrderId`),
  ADD KEY `UserPhone` (`UserPhone`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`phone`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banner`
--
ALTER TABLE `banner`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=85;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `OrderId` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `barang_ibfk_1` FOREIGN KEY (`MenuId`) REFERENCES `menu` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`UserPhone`) REFERENCES `user` (`Phone`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
