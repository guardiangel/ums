/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP SCHEMA IF EXISTS `ums`;

CREATE SCHEMA IF NOT EXISTS `ums` DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `ums`.`last_visit`;
CREATE TABLE `ums`.`last_visit` (
  `id` varchar(45) NOT NULL,
  `in` BIGINT DEFAULT NULL,
  `out` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ums`.`roles`;
CREATE TABLE `ums`.`roles` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ums`.`users`;
CREATE TABLE `ums`.`users` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `created` BIGINT NOT NULL,
  `last_visit_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_key` (`email`) USING BTREE,
  KEY `fk_users_last_visit1_idx` (`last_visit_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`last_visit_id`) REFERENCES `last_visit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ums`.`users_has_roles`;
CREATE TABLE `ums`.`users_has_roles` (
  `users_id` varchar(45) NOT NULL,
  `roles_id` varchar(45) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `fk_users_has_roles_roles1_idx` (`roles_id`),
  KEY `fk_users_has_roles_users_idx` (`users_id`),
  CONSTRAINT `fk_users_has_roles_roles1` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_roles_users` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ums`.`user_token`;
CREATE TABLE `ums`.`user_token` (
    `user_id` varchar(50) NOT NULL,
    `token` varchar(300) NOT NULL,
    `duration` bigint NOT NULL,
    `issue_at` bigint NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `userId_UNIQUE` (`user_id`),
    UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ums`.`last_visit` (`id`, `in`, `out`) VALUES
('64099be3-d57a-4e1d-8aed-6bd82cb6fc95', '1604249194', '1604249224'),
('a30aa2c2-74fc-4450-824c-844ca66d36a6', '1604249181', '1604249209'),
('3e961eb5-284d-4351-b48b-f7dbf2a5ef07', '1604249171', '1604249203'),
('26389567-d1e8-44b9-a7b2-6231749912e0', '1604249188', '1604249217'),
('6f43d5f4-b371-4ae5-b837-8962242f7bc1', '1604130098', '1604130106');

INSERT INTO `ums`.`roles` (`id`, `name`, `description`) VALUES
('ec869c52-4012-4524-980b-49e1840870eb', 'ADMIN', 'Admistrative toles for the system'),
('4554e62a-670c-4ccd-a4e1-4900a5ed5911', 'SUBSCRIBER', 'Message content consumer'),
('7d07aa13-4a35-48a4-a316-127ed46d65d0', 'PRODUCER', 'Message content producer');

INSERT INTO `ums`.`users` (`id`, `name`, `email`, `password`, `created`, `last_visit_id`) VALUES
('554e47aa-6abd-42c3-8363-2163250e78a6', 'Angela Merkel', 'angela@merkel.de', 'password', '1504249224', '26389567-d1e8-44b9-a7b2-6231749912e0'),
('bcc9cd9e-207c-44f9-842e-ec90421fdab2', 'Emmanuel Macron', 'emmanuel@macron.fr', 'password', '1504249224', '6f43d5f4-b371-4ae5-b837-8962242f7bc1'),
('fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc', 'Donald Trump', 'donalt@trump.us', 'password', '1604129987', '6f43d5f4-b371-4ae5-b837-8962242f7bc1'),
('eda55c5a-9552-46ed-945d-a19d4c5d0cfe', 'Guiquan Sun', 'felix.sgq@gmail.com', 'password', '123456', 'a30aa2c2-74fc-4450-824c-844ca66d36a6'),
('339282f1-8c05-4356-90b6-d5b10c4aeb55', 'Vladimir Putin', 'vladimir@putin.tu', 'password', '1504249224', '3e961eb5-284d-4351-b48b-f7dbf2a5ef07');

INSERT INTO `ums`.`users_has_roles` (`users_id`, `roles_id`) VALUES
('554e47aa-6abd-42c3-8363-2163250e78a6', '4554e62a-670c-4ccd-a4e1-4900a5ed5911'),
('bcc9cd9e-207c-44f9-842e-ec90421fdab2', '4554e62a-670c-4ccd-a4e1-4900a5ed5911'),
('bcc9cd9e-207c-44f9-842e-ec90421fdab2', '7d07aa13-4a35-48a4-a316-127ed46d65d0'),
('fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc', '4554e62a-670c-4ccd-a4e1-4900a5ed5911'),
('fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc', '7d07aa13-4a35-48a4-a316-127ed46d65d0'),
('eda55c5a-9552-46ed-945d-a19d4c5d0cfe', '4554e62a-670c-4ccd-a4e1-4900a5ed5911'),
('339282f1-8c05-4356-90b6-d5b10c4aeb55', '4554e62a-670c-4ccd-a4e1-4900a5ed5911');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;