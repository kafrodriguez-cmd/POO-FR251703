-- ============================================================
-- Script de creación de base de datos: bibliotecaudb
-- Sistema de Gestión de Biblioteca Universitaria
-- Universidad Don Bosco - Programación Orientada a Objetos
-- ============================================================

CREATE DATABASE IF NOT EXISTS bibliotecaudb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE bibliotecaudb;

-- ------------------------------------------------------------
-- Tabla: categorias
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id_categoria      INT(11)      NOT NULL AUTO_INCREMENT,
    nombre_categoria  VARCHAR(80)  NOT NULL,
    PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Tabla: libros
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS libros (
    id_libro              INT(11)       NOT NULL AUTO_INCREMENT,
    titulo                VARCHAR(150)  NOT NULL,
    autor                 VARCHAR(100)  NOT NULL,
    isbn                  VARCHAR(20)   UNIQUE,
    id_categoria          INT(11),
    cantidad_disponible   INT(11)       DEFAULT 1,
    PRIMARY KEY (id_libro),
    CONSTRAINT fk_libro_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categorias (id_categoria)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Tabla: estudiantes
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS estudiantes (
    id_estudiante       INT(11)       NOT NULL AUTO_INCREMENT,
    carnet              VARCHAR(10)   NOT NULL UNIQUE,
    nombre_estudiante   VARCHAR(100)  NOT NULL,
    carrera             VARCHAR(80)   NOT NULL,
    telefono            VARCHAR(9),
    PRIMARY KEY (id_estudiante)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Tabla: prestamos
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS prestamos (
    id_prestamo      INT(11)      NOT NULL AUTO_INCREMENT,
    id_estudiante    INT(11)      NOT NULL,
    id_libro         INT(11)      NOT NULL,
    fecha_prestamo   DATE         NOT NULL,
    fecha_devolucion DATE         NOT NULL,
    estado           VARCHAR(20)  DEFAULT 'Activo',
    PRIMARY KEY (id_prestamo),
    CONSTRAINT fk_prestamo_estudiante
        FOREIGN KEY (id_estudiante)
        REFERENCES estudiantes (id_estudiante)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_prestamo_libro
        FOREIGN KEY (id_libro)
        REFERENCES libros (id_libro)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Datos de prueba (mínimo 3 registros por tabla)
-- ============================================================

-- Categorías
INSERT INTO categorias (nombre_categoria) VALUES
('Informática'),
('Matemáticas'),
('Literatura'),
('Ciencias'),
('Ingeniería');

-- Libros
INSERT INTO libros (titulo, autor, isbn, id_categoria, cantidad_disponible) VALUES
('Programación Orientada a Objetos con Java', 'Herbert Schildt', '978-0071808552', 1, 5),
('Estructuras de Datos y Algoritmos', 'Mark Allen Weiss', '978-0132576277', 1, 3),
('Cálculo de una Variable', 'James Stewart', '978-1285740621', 2, 4),
('Cien años de soledad', 'Gabriel García Márquez', '978-0307474728', 3, 2),
('Física Universitaria', 'Young y Freedman', '978-0321973610', 4, 3),
('Ingeniería de Software', 'Ian Sommerville', '978-0133943030', 5, 2);

-- Estudiantes
INSERT INTO estudiantes (carnet, nombre_estudiante, carrera, telefono) VALUES
('AB123456', 'Ana María López', 'Ingeniería en Computación', '7890-1234'),
('CD789012', 'Carlos Eduardo Ramírez', 'Ingeniería Industrial', '7123-4567'),
('EF345678', 'Elena Patricia Martínez', 'Ingeniería en Sistemas', '7456-7890'),
('GH901234', 'Gabriel Andrés Hernández', 'Ingeniería Eléctrica', '7789-0123'),
('IJ567890', 'Isabel Sofía Castillo', 'Ingeniería en Computación', '7012-3456');

-- Préstamos (algunos activos, uno vencido de ejemplo)
INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, estado) VALUES
(1, 1, '2026-09-01', '2026-09-15', 'Activo'),
(2, 3, '2026-09-05', '2026-09-20', 'Activo'),
(3, 2, '2026-08-10', '2026-08-25', 'Activo'),
(4, 4, '2026-09-10', '2026-09-25', 'Activo'),
(1, 5, '2026-07-01', '2026-07-15', 'Devuelto');

-- ============================================================
-- Fin del script
-- ============================================================
