USE `rest_with_springboot_matheus`;

CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(80) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  `address` varchar(100) NOT NULL,
  `gender` varchar(6) NOT NULL, 
  PRIMARY KEY (`id`)
)


INSERT INTO `person` VALUES 
(1,'São Paulo','Ayrton','Male','Senna'),
(2,'Anchiano - Italy','Leonardo','Male','da Vinci'),
(4,'Kentucky  - US','Muhammed','Male','Ali'),
(5,'RJ  - Brazil','William','Male','Bonner'),
(7,'Vezo  - Africa do Sul','Nelson','Male','Mandela'),
(8,'Vezo  - Africa do Sul','Nelson','Male','Mandela'),
(9,'Smijan  - Croacia','Nikola','Male','Tesla');
