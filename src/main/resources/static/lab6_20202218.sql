-- =========================================
-- CREACIÓN DE TABLAS
-- =========================================
CREATE DATABASE lab_06;
USE lab_06;
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id BIGINT NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE heroes_navales (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    pais VARCHAR(50),
    rango VARCHAR(50),
    fecha_nacimiento DATE
    
    
);

CREATE TABLE intenciones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE canciones_criollas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    letra TEXT
);

CREATE TABLE asignaciones_cancion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    cancion_id BIGINT  NULL,
    intentos INT DEFAULT 0,
    adivinada BOOLEAN DEFAULT FALSE,
    solicitud BOOLEAN DEFAULT FALSE,
    fecha_adivinada DATETIME NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (cancion_id) REFERENCES canciones_criollas(id)
);

CREATE TABLE numeros_casa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    numero_objetivo INT NOT NULL,
    intentos INT DEFAULT 0,
    adivinado BOOLEAN DEFAULT FALSE,
    solicitud BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE mesas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL DEFAULT 4,
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE reservas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    mesa_id BIGINT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (mesa_id) REFERENCES mesas(id)
);

-- =========================================
-- SPRING SESSION
-- =========================================


CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
);


CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` 
      FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) 
      ON DELETE CASCADE
);


-- =========================================
-- INSERCIÓN DE DATOS BASE
-- =========================================


-- Roles
INSERT INTO roles (nombre) VALUES 
('ADMIN'),
('USUARIO'),
('VISITANTE');

INSERT INTO usuarios (nombre, correo, password, rol_id) VALUES
-- Hombres
('Carlos Vargas', 'carlos.vargas@example.com', '$2a$08$7AmTJUjeEKbPSvHVzgdD1.m3BS9g8BzDIxJFZxZYnVeY5RN5j7mbW', 2), -- contraseña : devPASS
('Xavier Ruiz', 'xavier.ruiz@example.com', '$2a$08$rut1LGTnIjkqCNvBp1KVVuCCq8m5d1XB.IKqk23hsxPi1YpAXjHfW', 2), -- contraeña: poPASS
('Administrador','admin@example.com','$2a$08$4NQtBasKpnOOdqEZIf9wX.qelUHa.FkY/HSKQJCsCrKSo3K4ho4eS',1), -- contraseña: adminPASS
('Test Usuario','test@ejemplo.com','$2a$08$IHENpfDGkX8M0hNZKhdZ9eU3e1NJCGv4D.fCP2LUoWgpZF3CA2FH.',2); -- Contraseña: qaPASS

INSERT INTO heroes_navales (nombre, descripcion, pais) VALUES
('Miguel Grau', 'El Caballero de los Mares, héroe máximo del Perú', 'Perú'),
('Arturo Prat', 'Héroe naval chileno en la Guerra del Pacífico', 'Chile'),
('Almirante Brown', 'Fundador de la Armada Argentina', 'Argentina'),
('Simón Bolívar', 'Libertador y líder naval en el Caribe', 'Venezuela');

INSERT INTO intenciones (usuario_id, descripcion) VALUES
(1, 'Quiero participar en la campaña de mensajes.'),
(2, 'Deseo enviar flores amarillas a mis amigos.'),
(3, 'Me gustaría sumarme con un carrito de amistad.');

INSERT INTO canciones_criollas (titulo, letra) VALUES
('La Flor de la Canela', 'Yo perdí el corazón'),
('Contigo Perú', 'Cuando despiertan mis ojos'),
('La flor de la canela', 'Olga'),
('Valicha', 'Esta es mi tierra'),
('Ritmo, color y sabor', 'Jipi jay'),
('Y se llama Perú', 'Toro Mata');

INSERT INTO asignaciones_cancion (usuario_id, cancion_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO numeros_casa (usuario_id, numero_objetivo) VALUES
(1, 7),
(2, 15),
(3, 23);

INSERT INTO mesas (numero, capacidad, disponible) VALUES
(1, 4, TRUE),
(2, 4, TRUE),
(3, 4, TRUE),
(4, 4, TRUE),
(5, 4, TRUE);

INSERT INTO reservas (usuario_id, mesa_id) VALUES
(1, 1);