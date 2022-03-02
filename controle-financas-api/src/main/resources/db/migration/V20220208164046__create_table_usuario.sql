CREATE TABLE usuarios (

 id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
                         email varchar(50) NOT NULL,
                         senha varchar (50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;