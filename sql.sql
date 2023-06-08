/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - project
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`project` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `project`;

/*Table structure for table `answers` */

DROP TABLE IF EXISTS `answers`;

CREATE TABLE `answers` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `qanswer_id` int(11) DEFAULT NULL,
  `mark_awd` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `answers` */

/*Table structure for table `apply` */

DROP TABLE IF EXISTS `apply`;

CREATE TABLE `apply` (
  `apply_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` varchar(100) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `qualification` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `apply` */

insert  into `apply`(`apply_id`,`post_id`,`user_id`,`details`,`qualification`) values 
(3,'2',NULL,'asdf','bca'),
(4,'3',NULL,'rgbn','bsc'),
(5,'',NULL,'rgbn','bsc'),
(6,'3',NULL,'asfv','bca'),
(7,'3',NULL,'dfg','bsc');

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `chat` */

insert  into `chat`(`chat_id`,`sender_id`,`receiver_id`,`message`,`date`) values 
(1,1,5,'fghj','16:37:51'),
(2,1,3,'cvbn','16:37:51'),
(3,1,5,'gshj','11:10:07'),
(4,1,16,'hii','10:33:23'),
(5,7,3,'asdf','11:21:58'),
(6,1,5,'asdd','11:22:21');

/*Table structure for table `enquiry` */

DROP TABLE IF EXISTS `enquiry`;

CREATE TABLE `enquiry` (
  `enquiry_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `enquiry` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`enquiry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `enquiry` */

insert  into `enquiry`(`enquiry_id`,`user_id`,`enquiry`,`reply`,`date`) values 
(1,NULL,'daagh','pending','2023-02-23');

/*Table structure for table `exam` */

DROP TABLE IF EXISTS `exam`;

CREATE TABLE `exam` (
  `exam_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) DEFAULT NULL,
  `exam` varchar(100) DEFAULT NULL,
  `examdate` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`exam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `exam` */

insert  into `exam`(`exam_id`,`post_id`,`exam`,`examdate`,`date`,`status`) values 
(1,2,'asdfg','29/3/2023','2023-03-01','pending'),
(2,3,'asdf','30/3/2023','2023-03-01','pending');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'Asd','jjdnd','student'),
(3,'Kme12cs78','1234','student'),
(4,'08','1234','student'),
(5,'20ubca4304','12345','student'),
(6,'Santhwana','123456','student'),
(7,'anuu','anu','student'),
(8,'asdf','asdd','student'),
(9,'asdf','asdd','student'),
(10,'asdf','asdd','student'),
(11,'asdf','asdd','student'),
(12,'asdf','asdd','student'),
(13,'asdf','asdd','student'),
(14,'asdf','asdd','student'),
(15,'asdf','asdd','student'),
(16,'asdf','asdd','student');

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `post` varchar(100) DEFAULT NULL,
  `qualification` varchar(100) DEFAULT NULL,
  `postfor` varchar(100) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `enddate` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `post` */

insert  into `post`(`post_id`,`post`,`qualification`,`postfor`,`details`,`date`,`enddate`,`image`) values 
(2,'fgsj','rhjk','hjk','gik','2023-02-26','16/2/2023','26_02_2023_12_33_04.jpg'),
(3,'dfg','ghh','fgg','fgh','2023-02-28','dfgh','28_02_2023_12_53_13.jpg');

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` int(11) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `question` */

insert  into `question`(`question_id`,`exam_id`,`question`) values 
(1,1,'red'),
(2,1,'blue');

/*Table structure for table `questionanswer` */

DROP TABLE IF EXISTS `questionanswer`;

CREATE TABLE `questionanswer` (
  `qanswer_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `option` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`qanswer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `questionanswer` */

insert  into `questionanswer`(`qanswer_id`,`question_id`,`option`,`status`) values 
(1,1,'3','Yes'),
(2,1,'4','No'),
(3,1,'1','No'),
(4,2,'4','Yes'),
(5,2,'2','No');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`fname`,`lname`,`phone`,`email`,`image`,`status`) values 
(1,2,'Ghsjs','Hhhs','aswathyalen@gmail.com','+449605684334',NULL,NULL),
(2,3,'Anju','K','anju@gmail.com','9966332255',NULL,NULL),
(3,4,'Aswathy','Alen Joby','aswathyalen@gmail.com','+449605684334',NULL,NULL),
(4,5,'Shweta','V','shweta@gmail.com','9747502705',NULL,NULL),
(5,6,'Santh','Jubet','aswathyalenjoby@gmail.com','+449605684334',NULL,NULL),
(6,11,'sdg','sfh','9956869576','srg@gmail.com','26_02_2023_09_58_37.jpg','pending'),
(7,16,'sdg','sfh','9956869576','srg@gmail.com','26_02_2023_10_06_01.jpg','pending');

/*Table structure for table `working_details` */

DROP TABLE IF EXISTS `working_details`;

CREATE TABLE `working_details` (
  `working_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `work` varchar(100) DEFAULT NULL,
  `resume` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`working_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `working_details` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
