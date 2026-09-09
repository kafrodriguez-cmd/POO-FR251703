-- =====================================================
-- EJERCICIOS COMPLEMENTARIOS - GUÍA 7 (JDBC)
-- Base de datos + tablas alumno, materia, alumno_materia
-- =====================================================

CREATE DATABASE IF NOT EXISTS academiabdd
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE academiabdd;

-- Tabla alumno
CREATE TABLE IF NOT EXISTS alumno (
    cod_alumno   INT          NOT NULL PRIMARY KEY,
    nombre       VARCHAR(80)  NOT NULL,
    apellido     VARCHAR(80)  NOT NULL,
    edad         INT          NOT NULL,
    direccion    VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabla materia
CREATE TABLE IF NOT EXISTS materia (
    cod_materia  INT          NOT NULL PRIMARY KEY,
    nombre       VARCHAR(25)  NOT NULL,
    descripcion  VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabla intermedia (relación muchos a muchos)
CREATE TABLE IF NOT EXISTS alumno_materia (
    cod_alumno   INT NOT NULL,
    cod_materia  INT NOT NULL,
    PRIMARY KEY (cod_alumno, cod_materia),
    CONSTRAINT fk_am_alumno  FOREIGN KEY (cod_alumno)  REFERENCES alumno(cod_alumno)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_am_materia FOREIGN KEY (cod_materia) REFERENCES materia(cod_materia)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Datos de ejemplo
INSERT INTO alumno (cod_alumno, nombre, apellido, edad, direccion) VALUES
(1, 'Ana',     'López',    20, 'Col. Escalón'),
(2, 'Carlos',  'Martínez', 22, 'Col. San Benito'),
(3, 'María',   'García',   19, 'Col. Centro');

INSERT INTO materia (cod_materia, nombre, descripcion) VALUES
(101, 'POO',      'Programación Orientada a Objetos'),
(102, 'BD',       'Bases de Datos'),
(103, 'Redes',    'Redes de Computadoras'),
(104, 'Cálculo',  'Cálculo Diferencial e Integral');

INSERT INTO alumno_materia (cod_alumno, cod_materia) VALUES
(1, 101), (1, 102),
(2, 101), (2, 103),
(3, 102), (3, 104);

SELECT 'Base de datos academiabdd creada e inicializada correctamente' AS mensaje;
