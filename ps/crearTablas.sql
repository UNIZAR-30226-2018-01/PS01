CREATE TABLE ArtistaAlbum(
	nombreArtista varchar(32) default 'Desconocido',
	nombreAlbum varchar(32) default 'Desconocido',
	anyooAlbum varchar(32) NOT NULL default 'XXXX',
	PRIMARY KEY (nombreArtista, nombreAlbum)
);

CREATE TABLE Cancion(
	titulo varchar(32),
	nombreArtista varchar(32),
	nombreAlbum varchar(32),
	genero varchar(32) default 'Desconocido',
	uploader varchar(32) REFERENCES Usuario(nombre),
	ruta varchar UNIQUE NOT NULL,
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, uploader),
	FOREIGN KEY (nombreArtista, nombreAlbum) REFERENCES ArtistaAlbum(nombreArtista, nombreAlbum)
);

CREATE TABLE Usuario(
	nombre varchar(32) PRIMARY KEY,
	hashPass varchar(128) NOT NULL
);

CREATE TABLE Seguir(
	nombreSeguidor varchar(32),
	nombreSeguido varchar(32),
	PRIMARY KEY (nombreSeguidor, nombreSeguido),
	FOREIGN KEY (nombreSeguidor) REFERENCES Usuario(nombre) ON DELETE CASCADE,
	FOREIGN KEY (nombreSeguido) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE ListaReproduccion(
	nombre varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (nombre, nombreUsuario),
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE Formar(
	titulo varchar(32),
	nombreArtista varchar(32),
	nombreAlbum varchar(32),
	nombreLista varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, nombreLista, nombreUsuario),
	FOREIGN KEY (titulo, nombreArtista, nombreAlbum) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum) ON DELETE CASCADE,
	FOREIGN KEY (nombreLista, nombreUsuario) REFERENCES ListaReproduccion(nombre, nombreUsuario) ON DELETE CASCADE
);

CREATE TABLE Acceder(
	nombreLista varchar(32),
	nombreCreador varchar(32),
	nombreListener varchar(32),
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (nombreLista, nombreCreador, nombreListener, fecha),
	FOREIGN KEY (nombreCreador) REFERENCES Usuario(nombre) ON DELETE CASCADE,
	FOREIGN KEY (nombreListener) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE Sesion(
	hashSesion varchar(128),
	nombreUsuario varchar(32),
	PRIMARY KEY (hashSesion, nombreUsuario),
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE Reproducir(
	nombreUsuario varchar(32) REFERENCES Usuario(nombre) ON DELETE CASCADE,
	titulo varchar(32),
	nombreAlbum varchar(32),
	nombreArtista varchar(32),
	FOREIGN KEY (titulo, nombreAlbum, nombreArtista) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum) ON DELETE CASCADE,
	PRIMARY KEY (nombreUsuario, titulo, nombreAlbum, nombreArtista)
);

CREATE TABLE Gustar(
	nombreUsuario varchar(32) REFERENCES Usuario(nombre),
	titulo varchar(32),
	nombreAlbum varchar(32),
	nombreArtista varchar(32),
	uploader varchar(32),
	PRIMARY KEY (nombreUsuario, titulo, nombreAlbum, nombreArtista),
	FOREIGN KEY (titulo, nombreArtista, nombreAlbum, uploader) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum, uploader) ON DELETE CASCADE
);