-- ============================================================
-- Script de creación de la base de datos biblioteca_db
-- Desafío Práctico #02 - POO404 Universidad Don Bosco
-- Ejecutar en phpMyAdmin, MySQL Workbench o consola MySQL
-- ============================================================

CREATE DATABASE IF NOT EXISTS biblioteca_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE biblioteca_db;

-- Tabla de autores
CREATE TABLE IF NOT EXISTS autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50)
) ENGINE=InnoDB;

-- Tabla de categorías
CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(80) NOT NULL
) ENGINE=InnoDB;

-- Tabla de libros (relación con autor y categoría)
CREATE TABLE IF NOT EXISTS libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    anio_publicacion INT NOT NULL,
    id_autor INT NOT NULL,
    id_categoria INT NOT NULL,
    CONSTRAINT fk_libro_autor FOREIGN KEY (id_autor) REFERENCES autor(id_autor)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_libro_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ==================== DATOS DE EJEMPLO ====================

INSERT INTO autor (nombre, nacionalidad) VALUES
('Gabriel García Márquez', 'Colombiana'),
('Mario Vargas Llosa', 'Peruana'),
('Isabel Allende', 'Chilena'),
('Jorge Luis Borges', 'Argentina'),
('Julio Cortázar', 'Argentina');

INSERT INTO categoria (nombre_categoria) VALUES
('Novela'),
('Cuento'),
('Poesía'),
('Ensayo'),
('Drama');

INSERT INTO libro (titulo, anio_publicacion, id_autor, id_categoria) VALUES
('Cien años de soledad', 1967, 1, 1),
('El amor en los tiempos del cólera', 1985, 1, 1),
('La ciudad y los perros', 1963, 2, 1),
('La casa de los espíritus', 1982, 3, 1),
('Ficciones', 1944, 4, 2),
('Rayuela', 1963, 5, 1);

-- Verificar
SELECT 'Base de datos biblioteca_db creada correctamente' AS mensaje;
SELECT COUNT(*) AS total_autores FROM autor;
SELECT COUNT(*) AS total_categorias FROM categoria;
SELECT COUNT(*) AS total_libros FROM libro;
