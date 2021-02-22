CREATE DATABASE  IF NOT EXISTS `cinema_app`;
USE `cinema_app`;

DROP TABLE IF EXISTS `film`;
DROP TABLE IF EXISTS `room`;
DROP TABLE IF EXISTS `presentation`;
DROP TABLE IF EXISTS `seat`;
DROP TABLE IF EXISTS `booking`;
DROP TABLE IF EXISTS `booking_seat`;

CREATE TABLE `film` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `room` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `seat` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `seat_row` INT(11) NOT NULL UNIQUE,
  `number_in_seat_row` INT(11) NOT NULL UNIQUE,
  `room_id` INT(11) NOT NULL UNIQUE,
  PRIMARY KEY (`id`),
  
  FOREIGN KEY (`room_id`) 
  REFERENCES `room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `presentation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `start_time` DATETIME DEFAULT NULL,
  `price` DECIMAL(5,2) DEFAULT NULL,
  `film_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`), 
  
  FOREIGN KEY (`film_id`) 
  REFERENCES `film` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  FOREIGN KEY (`room_id`) 
  REFERENCES `room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `booking` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) DEFAULT NULL,
  `credit_card_no` VARCHAR(45) DEFAULT NULL,
  `presentation_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  
  FOREIGN KEY (`presentation_id`) 
  REFERENCES `presentation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `booking_seat` (
  `booking_id` int(11) NOT NULL,
  `seat_id` int(11) NOT NULL,
  
  PRIMARY KEY (`booking_id`,`seat_id`),
  
  FOREIGN KEY (`booking_id`) 
  REFERENCES `booking` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  FOREIGN KEY (`seat_id`) 
  REFERENCES `seat` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;





