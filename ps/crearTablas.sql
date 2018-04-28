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

-- Codigo hash de 'gracehopper'
INSERT INTO Usuario values('Admin', '1d6868c84f4ed1ee6d5f34116ab14ddb', NULL);

CREATE TABLE Cancion(
	titulo varchar(64),
	nombreArtista varchar(32),
	nombreAlbum varchar(32) default 'Desconocido',
	genero varchar(32) default 'Desconocido',
	uploader varchar(32),
	ruta varchar(128) UNIQUE NOT NULL,
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, uploader),
	FOREIGN KEY (uploader) references Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Cancion values('Deltoya', 'Extremoduro', 'Iros todos a tomar por culo','Rock', 'Admin', 'Sin ruta');

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
	titulo varchar(64),
	nombreArtista varchar(32),
	nombreAlbum varchar(32),
	nombreLista varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, nombreLista, nombreUsuario),
	FOREIGN KEY (titulo, nombreArtista, nombreAlbum) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum) ON DELETE CASCADE,
	FOREIGN KEY (nombreLista, nombreUsuario) REFERENCES ListaReproduccion(nombre, nombreUsuario) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reproduccion(
	nombreUsuario varchar(32) REFERENCES Usuario(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
	titulo varchar(64),
	nombreAlbum varchar(32),
	nombreArtista varchar(32),
	uploader varchar(32),
	fecha TIMESTAMP default CURRENT_TIMESTAMP,
	FOREIGN KEY (titulo, nombreArtista, nombreAlbum, uploader) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum, uploader) ON DELETE CASCADE,
	PRIMARY KEY (nombreUsuario, titulo, nombreAlbum, nombreArtista, uploader, fecha)
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