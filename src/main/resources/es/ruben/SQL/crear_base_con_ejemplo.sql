create table if not exists DNI
(
    id       int         not null,
    Nombre    varchar(20) null,
    Apellidos varchar(20) null
    );

insert into DNI.DNI (id, Nombre, Apellidos)
values ("100","Ruben","Luna"),
       ("99","Mikel","Boada");
