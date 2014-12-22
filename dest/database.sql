CREATE DATABASE wt_mail;

USE wt_mail;


CREATE  TABLE `wt_mail`.`user` (
  `username` VARCHAR(30) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`username`) );


CREATE  TABLE `wt_mail`.`mail_info` (
  `mail_id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(30) NOT NULL ,
  `role` INT NOT NULL ,
  `from` VARCHAR(45) NOT NULL ,
  `to` VARCHAR(45) NOT NULL ,
  `header` TEXT NULL ,
  `bytes` BIGINT NULL ,
  PRIMARY KEY (`mail_id`) ,
  INDEX `username` (`username` ASC) ,
  CONSTRAINT `username`
    FOREIGN KEY (`username` )
    REFERENCES `wt_mail`.`user` (`username` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


CREATE  TABLE `wt_mail`.`message` (
  `message_id` INT NOT NULL AUTO_INCREMENT ,
  `mail_id` INT NOT NULL ,
  `content` TEXT NULL ,
  `readed` BIT NOT NULL DEFAULT b'0',
  PRIMARY KEY (`message_id`) ,
  INDEX `mail_id` (`mail_id` ASC) ,
  CONSTRAINT `mail_id`
    FOREIGN KEY (`mail_id` )
    REFERENCES `wt_mail`.`mail_info` (`mail_id` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


