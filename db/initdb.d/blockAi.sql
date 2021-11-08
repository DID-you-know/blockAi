CREATE TABLE `User` (
	`id`	int	NOT NULL auto_increment PRIMARY KEY,
	`name`	varchar(30)	NOT NULL,
	`email`	varchar(50)	NOT NULL,
    `password` varchar(50) NOT NULL,
	`birth`	datetime	NOT NULL,
	`phone`	varchar(30)	NOT NULL,
	`key`	varchar(50)	NULL,
	`created_at`	timestamp	NOT NULL
);

CREATE TABLE `Certification` (
	`id`	int	NOT NULL auto_increment PRIMARY KEY,
	`user_id`	int	NOT NULL,
	`from`	varchar(50)	NOT NULL,
	`certified_date`	timestamp	NOT NULL,
  KEY `FK_User_TO_Certification` (`user_id`),
  CONSTRAINT `FK_User_TO_Certification` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE `DID` (
	`id`	int	NOT NULL auto_increment PRIMARY KEY,
	`user_id`	int	NOT NULL,
	`did_address`	varchar(50)	NOT NULL,
	`issued_date`	timestamp	NOT NULL,
  KEY `FK_User_TO_DID` (`user_id`),
  CONSTRAINT `FK_User_TO_DID` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
);