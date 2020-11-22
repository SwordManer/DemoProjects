DROP TABLE IF EXISTS USER_INFO;
 
CREATE TABLE USER_INFO (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) DEFAULT NULL
);

drop table if exists `interestpoint`;
create table `interestpoint` (

	`id` varchar(50) NOT NULL COMMENT 'primary id',
	`channel` varchar(20) NOT NULL COMMENT 'channel, eg: baidumap',
	`id_in_channel` varchar(50) NOT NULL COMMENT 'id in channel',
	`poi_type` varchar(30) COMMENT 'type of poi',
	`location` varchar(30) NOT NULL COMMENT 'location, include latitude & longitude',
	`address` varchar(100) COMMENT 'address',
	`province` varchar(30) COMMENT 'province',
	`city` varchar(30) COMMENT 'city',
	`area` varchar(30) COMMENT 'district of city',
	`telephone` varchar(30) COMMENT 'telephone',
	`detail` tinyint(1) COMMENT 'is there detail page: 0 - not, 1 - yes ',
	`street_id` varchar(50) COMMENT 'street id',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
	`update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
	PRIMARY KEY (`id`)
);

drop table if exists `LOCATION_INTERESTPOINT`;
create table `LOCATION_INTERESTPOINT` (
	`id` varchar(50) NOT NULL COMMENT 'primary id',
	`loc_latitude` double(20) NOT NULL COMMENT 'latitude of location',
	`loc_logitude` double(20) NOT NULL COMMENT 'logitude of location',
	`location` varchar(30) NOT NULL COMMENT 'location with latitude and logitude',
	`poi_id` varchar(50) COMMENT 'id in interest_point',
	`poi_type` varchar(30) COMMENT 'type of poi',
	`poi_location` varchar(30) COMMENT 'location of poi with latitude and logitude',
	`poi_latitude` double(20) COMMENT 'latitude of interestPoint',
	`poi_logitude` double(20) COMMENT 'logitude of interestPoint',
	`radius` double(20) COMMENT 'radius within',
	`distance` double(20) COMMENT 'distance between location and interestPoint',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
	`update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
	PRIMARY KEY (`id`)
);