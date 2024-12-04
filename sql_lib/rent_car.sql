-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 01, 2024 at 11:51 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rent_car`
--

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(10) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `manage_cars`
--

CREATE TABLE `manage_cars` (
  `regis_num` varchar(20) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  `status` varchar(150) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manage_cars`
--

INSERT INTO `manage_cars` (`regis_num`, `brand`, `model`, `status`, `price`) VALUES
('1', '1', '1', 'Available', 0),
('A001', 'Car', 'Toyota', 'Available', 400),
('B002', 'Toyota', 'Tomas', 'Available', 50);

-- --------------------------------------------------------

--
-- Table structure for table `manage_customers`
--

CREATE TABLE `manage_customers` (
  `id` varchar(10) NOT NULL,
  `name` varchar(150) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manage_customers`
--

INSERT INTO `manage_customers` (`id`, `name`, `address`, `phone`) VALUES
('1', 'A', 'HN', '0123456789'),
('2', 'B', 'HD', '0987654321');

-- --------------------------------------------------------

--
-- Table structure for table `manage_rent`
--

CREATE TABLE `manage_rent` (
  `id` varchar(20) NOT NULL,
  `regis_num` varchar(100) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `rent_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `fees` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manage_rent`
--

INSERT INTO `manage_rent` (`id`, `regis_num`, `customer_name`, `rent_date`, `return_date`, `fees`) VALUES
('1', 'B002', 'A', '2024-04-01', '2024-04-07', 50);

-- --------------------------------------------------------

--
-- Table structure for table `manage_return`
--

CREATE TABLE `manage_return` (
  `id` varchar(10) NOT NULL,
  `regis_num` varchar(150) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `return_date` date DEFAULT NULL,
  `delay` int(11) NOT NULL,
  `fine` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manage_return`
--

INSERT INTO `manage_return` (`id`, `regis_num`, `customer_name`, `return_date`, `delay`, `fine`) VALUES
('1', 'A001', 'A', '2024-04-19', 13, 520),
('2', 'B002', 'A', '2024-04-13', 0, 0),
('3', 'B002', 'A', '2024-04-08', 1, 355);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `manage_cars`
--
ALTER TABLE `manage_cars`
  ADD PRIMARY KEY (`regis_num`);

--
-- Indexes for table `manage_customers`
--
ALTER TABLE `manage_customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `manage_return`
--
ALTER TABLE `manage_return`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
