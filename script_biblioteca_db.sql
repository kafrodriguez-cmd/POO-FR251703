-- ============================================================
-- Script limpio y listo para el Desafío de Biblioteca Digital
-- Compatible con el proyecto Java (JFrame + JDBC)
-- ============================================================

CREATE DATABASE IF NOT EXISTS biblioteca_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE biblioteca_db;

-- Eliminar tablas si ya existen (para poder re-ejecutar el script)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS libro;
DROP TABLE IF EXISTS autor;
DROP TABLE IF EXISTS categoria;
SET FOREIGN_KEY_CHECKS = 1;

-- ===================== TABLA AUTOR =====================
CREATE TABLE autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ===================== TABLA CATEGORIA =====================
CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ===================== TABLA LIBRO =====================
CREATE TABLE libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    ano_publicacion INT NOT NULL,
    id_autor INT NOT NULL,
    id_categoria INT NOT NULL,
    CONSTRAINT fk_libro_autor 
        FOREIGN KEY (id_autor) REFERENCES autor(id_autor)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_libro_categoria 
        FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ===================== DATOS DE EJEMPLO =====================

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

INSERT INTO libro (titulo, ano_publicacion, id_autor, id_categoria) VALUES
('Cien años de soledad', 1967, 1, 1),
('El amor en los tiempos del cólera', 1985, 1, 1),
('La ciudad y los perros', 1963, 2, 1),
('La casa de los espíritus', 1982, 3, 1),
('Ficciones', 1944, 4, 2),
('Rayuela', 1963, 5, 1);

-- Verificar que todo se creó bien
SELECT 'Base de datos lista' AS mensaje;
SELECT COUNT(*) AS total_autores FROM autor;
SELECT COUNT(*) AS total_categorias FROM categoria;
SELECT COUNT(*) AS total_libros FROM libro;
