/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.5.8-log : Database - visitor
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;`report_covid_alert`

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`visitor` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `visitor`;

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `eid` INT(30) NOT NULL AUTO_INCREMENT,
  `sid` INT(30) DEFAULT NULL,
  `name` VARCHAR(30) DEFAULT NULL,
  `email` VARCHAR(30) DEFAULT NULL,
  `phone` VARCHAR(30) DEFAULT NULL,
  `address` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

INSERT  INTO `employee`(`eid`,`sid`,`name`,`email`,`phone`,`address`) VALUES (11,12,'ajin','ajin@gmail.com','1245454545','ajin address'),(12,12,'priya','priya@gmail.com','2356888888','Priya aluve address'),(13,12,'xhxhhhxhxj','b@xbb','95959','bxjx');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `fid` INT(11) NOT NULL AUTO_INCREMENT,
  `uid` INT(11) DEFAULT NULL,
  `subject` VARCHAR(30) DEFAULT NULL,
  `details` VARCHAR(200) DEFAULT NULL,
  `date` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

INSERT  INTO `feedback`(`fid`,`uid`,`subject`,`details`,`date`) VALUES (1,1,'necessary app','This was a very informative course. It explains well how contract tracing is so vitally important in the controlling of the COVID-19 virus. Thank you for this opportunity.','2021-03-04'),(2,2,'refresh app','No way to refresh app, shows last check was days ago. Tried a force quit and reopened and it still won\'t update. I assumed the app was passively working but what good is it if it doesn\'t refresh? I\'m ','2021-03-04');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `logid` INT(11) NOT NULL AUTO_INCREMENT,
  `uid` INT(20) DEFAULT NULL,
  `username` VARCHAR(30) DEFAULT NULL,
  `password` VARCHAR(30) DEFAULT NULL,
  `type` VARCHAR(30) DEFAULT NULL,
  `status` VARCHAR(30) DEFAULT 'approved',
  PRIMARY KEY (`logid`)
) ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

INSERT  INTO `login`(`logid`,`uid`,`username`,`password`,`type`,`status`) VALUES (0,0,'admin@gmail.com','admin','admin','approved'),(1,1,'athul@gmail.com','233','user','approved'),(4,5,'asd@gmail.com','321','shop','pending'),(5,6,'bismigroup@gmail.com','123','shop','approved'),(6,7,'lccjavateam@gmail.com','jfghu','shop','pending'),(7,12,'kr@hotmail.com','123','shop','approved'),(8,2,'hiran@gmail.com','123456','user','approved');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `nid` INT(11) NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(50) DEFAULT NULL,
  `details` VARCHAR(500) DEFAULT NULL,
  `date` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`nid`)
) ENGINE=INNODB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `notification` */

INSERT  INTO `notification`(`nid`,`subject`,`details`,`date`) VALUES (3,'Administrative Services','Preventive measures to contain the spread of COVID-19 - guidelines regarding attendance of government officials.','2021-01-27'),(11,'determine COVID-19 positive','After the people you had close contact with while you were contagious have been identified, a contact tracer will typically reach out and notify each of them of their possible exposure as soon as possible. The contact tracer will also provide education','2021-01-06');

/*Table structure for table `report_covid_alert` */

DROP TABLE IF EXISTS `report_covid_alert`;

CREATE TABLE `report_covid_alert` (
  `aid` INT(30) NOT NULL AUTO_INCREMENT,
  `sid` INT(30) DEFAULT NULL,
  `name` VARCHAR(30) DEFAULT NULL,
  `address` VARCHAR(200) DEFAULT NULL,
  `details` VARCHAR(500) DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  `submited_date` DATE DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `report_covid_alert` */

INSERT  INTO `report_covid_alert`(`aid`,`sid`,`name`,`address`,`details`,`date`,`submited_date`) VALUES (2,6,'Athul','RK ARCADE, JUNCTION, Kaloor - Kadavanthara Rd, near KALOOR','At a time when many pharmacies are charging exorbitant prices for the face masks and hand sanitisers in the backdrop of high demand due to the coronavirus pandemic','2021-03-01','2021-03-03'),(3,12,'Athul','RK ARCADE, JUNCTION, Kaloor - Kadavanthara Rd, near KALOOR','Thasleem PK, co-owner of Cochin Surgicals, said: \"We have been selling masks at Rs 2 for the last 8 years. But now, the rate has gone up everywhere. We bought the masks at Rs 8 or Rs 10 and are selling at Rs 2, while others are selling at Rs 25.','2021-01-03','2021-03-03');

/*Table structure for table `shop_registration` */

DROP TABLE IF EXISTS `shop_registration`;

CREATE TABLE `shop_registration` (
  `sid` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) DEFAULT NULL,
  `address` VARCHAR(200) DEFAULT NULL,
  `location` VARCHAR(30) DEFAULT NULL,
  `phone` VARCHAR(30) DEFAULT NULL,
  `email` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=INNODB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `shop_registration` */

INSERT  INTO `shop_registration`(`sid`,`name`,`address`,`location`,`phone`,`email`) VALUES (5,'asd shop','3rd Floor, Darmodhayam Building,, Shanmugham Road, Marine Drive, Kochi, Kerala 682031','location','1234567899','asd@gmail.com'),(6,'Bismi Appliances','Shop No.2-1, Gcda Complex, Kaloor, Ernakulam - 682017, Near International Stadium (Map)','Kaloor','8976453125','bismigroup@gmail.com'),(7,'Lulu','Old NH 47, Edappally Junction, Nethaji Nagar, Edappally, Kochi','near bus stand','9865423184','lulu@gmail.com'),(12,'KR Bakes',' Elamkulam, Ernakulam, Kerala 682020, Kadavanthara, Ernakulam, Kerala 682020','Kadavanthara Junction','1452145214','kr@hotmail.com');

/*Table structure for table `user_registration` */

DROP TABLE IF EXISTS `user_registration`;

CREATE TABLE `user_registration` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `user_registration` */

insert  into `user_registration`(`uid`,`name`,`address`,`phone`,`email`) values (1,'Athul','d ghh g to','2346534','athul@gmail.com'),(2,'hiran','vkg','6558085368','hiran@gmail.com');

/*Table structure for table `visitor_details` */

DROP TABLE IF EXISTS `visitor_details`;

CREATE TABLE `visitor_details` (
  `vid` int(30) NOT NULL AUTO_INCREMENT,
  `uid` int(30) DEFAULT NULL,
  `sid` int(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `status` varchar(30) DEFAULT 'enterd',
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `visitor_details` */

insert  into `visitor_details`(`vid`,`uid`,`sid`,`date`,`time`,`status`) values (2,1,12,'2021-01-03','11:55:51','enterd'),(3,1,6,'2021-03-01','8:06:01','enterd');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
