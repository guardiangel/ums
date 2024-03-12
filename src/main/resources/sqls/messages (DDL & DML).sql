SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema messages
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `messages` ;

-- -----------------------------------------------------
-- Schema messages
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `messages` DEFAULT CHARACTER SET utf8 ;

-- -----------------------------------------------------
-- Table `messages`.`producers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `messages`.`producers` ;

CREATE TABLE IF NOT EXISTS `messages`.`producers` (
  `id` varchar(45) NOT NULL,
  `comment` VARCHAR(128) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `messages`.`messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `messages`.`messages` ;

CREATE TABLE IF NOT EXISTS `messages`.`messages` (
  `id` varchar(45) NOT NULL,
  `content` VARCHAR(140) NULL,
  `created` INT(15) NOT NULL,
  `producer_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`, `producer_id`),
  INDEX `fk_messages_producers1_idx` (`producer_id` ASC) VISIBLE,
  CONSTRAINT `fk_messages_producers1`
  FOREIGN KEY (`producer_id`)
  REFERENCES `messages`.`producers` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `messages`.`subscribers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `messages`.`subscribers` ;

CREATE TABLE IF NOT EXISTS `messages`.`subscribers` (
  `id` varchar(45) NOT NULL,
  `comment` VARCHAR(128) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `messages`.`subscriptions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `messages`.`subscriptions` ;

CREATE TABLE IF NOT EXISTS `messages`.`subscriptions` (
  `producers_id` varchar(45) NOT NULL,
  `subscribers_id` varchar(45) NOT NULL,
  PRIMARY KEY (`producers_id`, `subscribers_id`),
  INDEX `fk_producers_has_subscribers_subscribers1_idx` (`subscribers_id` ASC) VISIBLE,
  INDEX `fk_producers_has_subscribers_producers_idx` (`producers_id` ASC) VISIBLE,
  CONSTRAINT `fk_producers_has_subscribers_producers`
  FOREIGN KEY (`producers_id`)
  REFERENCES `messages`.`producers` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_producers_has_subscribers_subscribers1`
  FOREIGN KEY (`subscribers_id`)
  REFERENCES `messages`.`subscribers` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- DML for table `messages`.`producers`
-- -----------------------------------------------------
INSERT INTO `messages`.`producers` (`id`, `comment`) VALUES
('554e47aa-6abd-42c3-8363-2163250e78a6', 'Deutschland'),
('bcc9cd9e-207c-44f9-842e-ec90421fdab2', 'France'),
('fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc', 'The U.S.A.');

-- -----------------------------------------------------
-- DML for table `messages`.`subscribers`
-- -----------------------------------------------------
INSERT INTO `messages`.`subscribers` (`id`, `comment`) VALUES
('eda55c5a-9552-46ed-945d-a19d4c5d0cfe', 'Canada'),
('ABB04B9F5D1040DD9076EB27CA76891A', 'Russia');

-- -----------------------------------------------------
-- DML for table `messages`.`subscriptions`
-- -----------------------------------------------------
INSERT INTO `messages`.`subscriptions` (`producers_id`, `subscribers_id`) VALUES
('554e47aa-6abd-42c3-8363-2163250e78a6', 'ABB04B9F5D1040DD9076EB27CA76891A'),
('bcc9cd9e-207c-44f9-842e-ec90421fdab2', 'eda55c5a-9552-46ed-945d-a19d4c5d0cfe'),
('fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc', 'ABB04B9F5D1040DD9076EB27CA76891A');

-- -----------------------------------------------------
-- DML for table `messages`.`messages`
-- -----------------------------------------------------
INSERT INTO `messages`.`messages` (`id`, `content`, `created`, `producer_id`) VALUES
('5832e707-c6ca-4039-ac42-d4e204cfa126', 'Guten Tag von Angela Merkel! ', '1605579881', '554e47aa-6abd-42c3-8363-2163250e78a6'),
('a6930c21-acbc-4a8f-83e3-36bd1c016c31', 'Who is subscribed to Mr. Trump?', '1605194769', 'fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc'),
('42d6040c-93ed-42a6-a265-5eb50ff7c5a2', 'And here Mr. Trump come again', '1605194747', 'fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc'),
('0104ee0b-0db0-4e88-a710-03b683672793', 'Mr. Macron would like to say hello!', '1605197637', 'bcc9cd9e-207c-44f9-842e-ec90421fdab2'),
('4519a1dc-3dc5-40e8-91f9-b47ea6a40e0c', 'Donald Trump posted his first message', '1605194709', 'fce5dd59-aa53-4f1a-87a5-77b9fe98f4fc'),
('989e5d46-1663-4d45-8e56-5120b602db2a', 'Now France President is here as well', '1605195323', 'bcc9cd9e-207c-44f9-842e-ec90421fdab2');
