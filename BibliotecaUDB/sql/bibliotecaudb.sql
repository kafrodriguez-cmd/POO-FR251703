-- =====================================================
-- Script de Base de Datos: bibliotecaudb
-- Desafío Práctico #03 - POO404
-- Universidad Don Bosco
-- =====================================================

CREATE DATABASE IF NOT EXISTS bibliotecaudb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE bibliotecaudb;

-- -----------------------------------------------------
-- Tabla: categorias
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id_categoria INT(11) NOT NULL AUTO_INCREMENT,
    nombre_categoria VARCHAR(80) NOT NULL,
    PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- Tabla: libros
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS libros (
    id_libro INT(11) NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    id_categoria INT(11) NOT NULL,
    cantidad_disponible INT(11) DEFAULT 1,
    PRIMARY KEY (id_libro),
    CONSTRAINT fk_libro_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categorias (id_categoria)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- Tabla: estudiantes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS estudiantes (
    id_estudiante INT(11) NOT NULL AUTO_INCREMENT,
    carnet VARCHAR(10) NOT NULL UNIQUE,
    nombre_estudiante VARCHAR(100) NOT NULL,
    carrera VARCHAR(80) NOT NULL,
    telefono VARCHAR(9),
    PRIMARY KEY (id_estudiante)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- Tabla: prestamos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS prestamos (
    id_prestamo INT(11) NOT NULL AUTO_INCREMENT,
    id_estudiante INT(11) NOT NULL,
    id_libro INT(11) NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'Activo',
    PRIMARY KEY (id_prestamo),
    CONSTRAINT fk_prestamo_estudiante
        FOREIGN KEY (id_estudiante)
        REFERENCES estudiantes (id_estudiante)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_prestamo_libro
        FOREIGN KEY (id_libro)
        REFERENCES libros (id_libro)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- DATOS DE PRUEBA
-- =====================================================

INSERT INTO categorias (nombre_categoria) VALUES
('Ingeniería de Software'),
('Bases de Datos'),
('Programación Orientada a Objetos'),
('Redes de Computadoras'),
('Inteligencia Artificial'),
('Matemáticas Discretas');

INSERT INTO libros (titulo, autor, isbn, id_categoria, cantidad_disponible) VALUES
('Clean Code', 'Robert C. Martin', '978-0132350884', 1, 3),
('Design Patterns', 'Erich Gamma', '978-0201633610', 1, 2),
('Database System Concepts', 'Abraham Silberschatz', '978-0073523323', 2, 4),
('Java: The Complete Reference', 'Herbert Schildt', '978-1260463552', 3, 5),
('Computer Networking: A Top-Down Approach', 'James Kurose', '978-0133594140', 4, 2),
('Artificial Intelligence: A Modern Approach', 'Stuart Russell', '978-0134610993', 5, 3),
('Discrete Mathematics and Its Applications', 'Kenneth Rosen', '978-0073383095', 6, 4),
('Effective Java', 'Joshua Bloch', '978-0134685991', 3, 2);

INSERT INTO estudiantes (carnet, nombre_estudiante, carrera, telefono) VALUES
('GB25001', 'Ana María López', 'Ingeniería en Computación', '7854-1234'),
('GB25002', 'Carlos Eduardo Pérez', 'Ingeniería en Sistemas', '7890-5678'),
('GB25003', 'María José Hernández', 'Ingeniería en Computación', '7012-3456'),
('GB25004', 'José Luis Martínez', 'Ingeniería Industrial', '7234-8901'),
('GB25005', 'Sofía Alejandra Ramírez', 'Ingeniería en Sistemas', '7456-7890');

INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, estado) VALUES
(1, 1, '2026-09-15', '2026-09-30', 'Devuelto'),
(2, 4, '2026-10-01', '2026-10-15', 'Activo'),
(3, 2, '2026-10-05', '2026-10-20', 'Activo'),
(1, 7, '2026-10-10', '2026-10-25', 'Activo');

-- Verificar datos
SELECT 'Categorías:' AS info;
SELECT * FROM categorias;
SELECT 'Libros:' AS info;
SELECT * FROM libros;
SELECT 'Estudiantes:' AS info;
SELECT * FROM estudiantes;
SELECT 'Préstamos:' AS info;
SELECT * FROM prestamos;
