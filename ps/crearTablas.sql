DROP TABLE IF EXISTS Formar;
DROP TABLE IF EXISTS Reproduccion;
DROP TABLE IF EXISTS Cancion;
DROP TABLE IF EXISTS Sesion;
DROP TABLE IF EXISTS Seguir;
DROP TABLE IF EXISTS ListaReproduccion;
DROP TABLE IF EXISTS Usuario;

CREATE TABLE Usuario(
	nombre varchar(32) PRIMARY KEY,
	hashPass varchar(128) NOT NULL,
	imagenPerfil varchar(128) default NULL
);

-- Codigo hash de 'Gracehopper1'
INSERT INTO Usuario values('Admin', 'fd119e3d4ab1344a55ba8567cb33ce9d', NULL);

CREATE TABLE Cancion(
	titulo varchar(64),
	nombreArtista varchar(32),
	nombreAlbum varchar(32) default 'Desconocido',
	genero varchar(32) default 'Desconocido',
	uploader varchar(32),
	ruta varchar(128) UNIQUE NOT NULL DEFAULT 'nada',
	next_id int unique auto_increment,
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, uploader),
	FOREIGN KEY (uploader) references Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Sesion(
	hashSesion varchar(128),
	nombreUsuario varchar(32),
	PRIMARY KEY (hashSesion, nombreUsuario),
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 'nombreSeguidor' se refiere a ti como persona que sigue a otra persona,
-- NO las personas que te siguen a ti
-- 'nombreSeguido' se refiere a la persona a la que sigues
CREATE TABLE Seguir(
	nombreSeguidor varchar(32),
	nombreSeguido varchar(32),
	PRIMARY KEY (nombreSeguidor, nombreSeguido),
	FOREIGN KEY (nombreSeguidor) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (nombreSeguido) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ListaReproduccion(
	nombre varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (nombre, nombreUsuario),
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Formar(
	ruta varchar(128),
	nombreLista varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (ruta, nombreLista, nombreUsuario),
	FOREIGN KEY (nombreLista, nombreUsuario) REFERENCES ListaReproduccion(nombre, nombreUsuario) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(ruta) REFERENCES Cancion(ruta) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reproduccion(
	numReproduccion BIGINT AUTO_INCREMENT,
	ruta varchar(128) not null,
	nombreUsuario varchar(32),
	fecha TIMESTAMP default CURRENT_TIMESTAMP,
	PRIMARY KEY(numReproduccion),
	FOREIGN KEY (ruta) REFERENCES Cancion(ruta) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Compartir(
	ruta varchar(128),
	usuarioOrigen varchar(32),
	usuarioDestino varchar(32),
	fecha TIMESTAMP default CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (usuarioOrigen, usuarioDestino, ruta),
	FOREIGN KEY(usuarioOrigen) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(usuarioDestino) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(ruta) REFERENCES Cancion(ruta) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Trigger que evite que se borre el usuario Admin
DELIMITER //
CREATE OR REPLACE TRIGGER no_borrar_admin
BEFORE DELETE ON Usuario
FOR EACH ROW
BEGIN
	IF OLD.nombre = 'Admin' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Prohibido borrar el usuario Admin';
	END IF;
END; //
DELIMITER ;

-- Trigger que genere automáticamente una lista 'Favoritos' para cada usuario
CREATE OR REPLACE TRIGGER crear_favoritos
AFTER INSERT ON Usuario
FOR EACH ROW
INSERT INTO ListaReproduccion(nombre,nombreUsuario) VALUES ('Favoritos',NEW.nombre);

-- Trigger que evite borrar las listas de nombre 'Favoritos'
DELIMITER //
CREATE OR REPLACE TRIGGER no_borrar_favoritos
BEFORE DELETE ON ListaReproduccion
FOR EACH ROW
BEGIN
	IF OLD.nombre = 'Favoritos' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Prohibido borrar lista de favoritos';
	END IF;
END; //
DELIMITER ;

DELIMITER //
CREATE OR REPLACE TRIGGER introducirCancion
BEFORE INSERT ON Cancion
FOR EACH ROW
BEGIN
	set NEW.titulo = CONCAT('Cancion', (select max(next_id) from Cancion));
END; //
DELIMITER ;