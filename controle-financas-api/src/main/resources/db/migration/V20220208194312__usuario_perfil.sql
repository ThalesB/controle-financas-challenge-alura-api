CREATE TABLE usuario_perfil(

    id_usuario BIGINT(20) NOT NULL,
    id_perfil BIGINT(20) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_perfil) REFERENCES perfis(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;