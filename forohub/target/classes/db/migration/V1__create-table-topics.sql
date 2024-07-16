create table topics(
    id bigint not null auto_increment,
    activo tinyint,
    titulo varchar(100) not null unique,
    mensaje varchar(400) not null unique,
    fecha datetime not null,
    usuario bigint not null,
    curso varchar(100) not null,

    primary key (id)
);