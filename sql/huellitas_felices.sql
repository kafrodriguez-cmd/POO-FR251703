-- ============================================================
-- CENTRO DE ADOPCIÓN DE MASCOTAS "HUELLITAS FELICES"
-- Script de creación de base de datos + datos de prueba
-- Motor: MySQL 8.x
-- ============================================================

DROP DATABASE IF EXISTS huellitas_felices;
CREATE DATABASE huellitas_felices CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE huellitas_felices;

-- ------------------------------------------------------------
-- 1. USUARIOS (para autenticación y control de sesión)
-- ------------------------------------------------------------
CREATE TABLE usuario (
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(50)  NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,          -- en producción usar hash (BCrypt)
    nombre_completo VARCHAR(120) NOT NULL,
    rol             ENUM('ADMIN','EMPLEADO') NOT NULL DEFAULT 'EMPLEADO',
    activo          TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_registro  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 2. ADOPTANTES
-- ------------------------------------------------------------
CREATE TABLE adoptante (
    id_adoptante    INT AUTO_INCREMENT PRIMARY KEY,
    nombres         VARCHAR(80)  NOT NULL,
    apellidos       VARCHAR(80)  NOT NULL,
    dui             VARCHAR(15)  NOT NULL UNIQUE,
    telefono        VARCHAR(20),
    email           VARCHAR(100),
    direccion       VARCHAR(200),
    fecha_registro  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 3. MASCOTAS
-- ------------------------------------------------------------
CREATE TABLE mascota (
    id_mascota      INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(60)  NOT NULL,
    especie         ENUM('Perro','Gato','Conejo','Ave','Otro') NOT NULL,
    raza            VARCHAR(60),
    edad_aprox      INT,                            -- en meses
    sexo            ENUM('Macho','Hembra') NOT NULL,
    color           VARCHAR(40),
    tamano          ENUM('Pequeño','Mediano','Grande') DEFAULT 'Mediano',
    estado          ENUM('Disponible','En proceso','Adoptada','No disponible') NOT NULL DEFAULT 'Disponible',
    descripcion     TEXT,
    fecha_ingreso   DATE NOT NULL,
    foto_url        VARCHAR(255) DEFAULT 'default_mascota.png'
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 4. ADOPCIONES  (relación 1-N entre Mascota y Adoptante)
-- ------------------------------------------------------------
CREATE TABLE adopcion (
    id_adopcion     INT AUTO_INCREMENT PRIMARY KEY,
    id_mascota      INT NOT NULL,
    id_adoptante    INT NOT NULL,
    fecha_adopcion  DATE NOT NULL,
    estado          ENUM('Pendiente','Aprobada','Rechazada','Finalizada') NOT NULL DEFAULT 'Pendiente',
    observaciones   TEXT,
    id_usuario      INT,                            -- empleado que registró
    fecha_registro  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_adopcion_mascota   FOREIGN KEY (id_mascota)   REFERENCES mascota(id_mascota)   ON DELETE RESTRICT,
    CONSTRAINT fk_adopcion_adoptante FOREIGN KEY (id_adoptante) REFERENCES adoptante(id_adoptante) ON DELETE RESTRICT,
    CONSTRAINT fk_adopcion_usuario   FOREIGN KEY (id_usuario)   REFERENCES usuario(id_usuario)   ON DELETE SET NULL
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 5. VACUNAS  + tabla intermedia (relación muchos-a-muchos)
-- ------------------------------------------------------------
CREATE TABLE vacuna (
    id_vacuna       INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(80) NOT NULL,
    descripcion     VARCHAR(200)
) ENGINE=InnoDB;

CREATE TABLE mascota_vacuna (
    id_mascota      INT NOT NULL,
    id_vacuna       INT NOT NULL,
    fecha_aplicacion DATE NOT NULL,
    proxima_dosis   DATE,
    PRIMARY KEY (id_mascota, id_vacuna, fecha_aplicacion),
    CONSTRAINT fk_mv_mascota FOREIGN KEY (id_mascota) REFERENCES mascota(id_mascota) ON DELETE CASCADE,
    CONSTRAINT fk_mv_vacuna  FOREIGN KEY (id_vacuna)  REFERENCES vacuna(id_vacuna)  ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================================
-- DATOS DE PRUEBA
-- ============================================================

-- Usuarios (password en texto plano solo para desarrollo académico)
INSERT INTO usuario (username, password, nombre_completo, rol) VALUES
('admin',   'admin123',  'María Elena Rodríguez', 'ADMIN'),
('empleado','emp123',    'Carlos Antonio Méndez', 'EMPLEADO');

-- Adoptantes
INSERT INTO adoptante (nombres, apellidos, dui, telefono, email, direccion) VALUES
('Ana Sofía',   'López García',   '01234567-8', '7000-1111', 'ana.lopez@email.com',   'Col. Escalón, San Salvador'),
('Luis Miguel', 'Pérez Hernández','02345678-9', '7000-2222', 'luis.perez@email.com',  'Santa Tecla, La Libertad'),
('Carmen',      'Ramírez Díaz',   '03456789-0', '7000-3333', 'carmen.r@email.com',    'Soyapango, San Salvador'),
('José',        'Martínez Flores','04567890-1', '7000-4444', 'jose.mf@email.com',     'Mejicanos, San Salvador');

-- Mascotas
INSERT INTO mascota (nombre, especie, raza, edad_aprox, sexo, color, tamano, estado, descripcion, fecha_ingreso) VALUES
('Firulais',  'Perro', 'Labrador',     24, 'Macho',  'Dorado',   'Grande',  'Disponible',   'Muy juguetón y sociable con niños.', '2025-11-15'),
('Michi',     'Gato',  'Siamés',       18, 'Hembra', 'Crema',    'Pequeño', 'Disponible',   'Tranquila, ideal para apartamento.', '2025-12-01'),
('Rocky',     'Perro', 'Pastor Alemán',36, 'Macho',  'Negro',    'Grande',  'En proceso',   'Protector y leal.',                 '2025-10-20'),
('Luna',      'Gato',  'Criollo',      12, 'Hembra', 'Gris',     'Mediano', 'Disponible',   'Cariñosa y curiosa.',               '2026-01-10'),
('Toby',      'Perro', 'Beagle',       30, 'Macho',  'Tricolor', 'Mediano', 'Adoptada',     'Energético y amigable.',            '2025-09-05'),
('Coco',      'Conejo','Holandés',      8, 'Macho',  'Blanco',   'Pequeño', 'Disponible',   'Dócil y limpio.',                   '2026-02-14'),
('Max',       'Perro', 'Golden',       20, 'Macho',  'Dorado',   'Grande',  'Disponible',   'Ideal para familias activas.',      '2026-03-01'),
('Nina',      'Gato',  'Persa',        15, 'Hembra', 'Blanco',   'Pequeño', 'No disponible','Requiere cuidados especiales de pelaje.','2025-12-20');

-- Adopciones
INSERT INTO adopcion (id_mascota, id_adoptante, fecha_adopcion, estado, observaciones, id_usuario) VALUES
(5, 1, '2025-11-20', 'Finalizada', 'Adopción exitosa. Seguimiento a los 30 días.', 1),
(3, 2, '2026-01-15', 'Pendiente',  'En espera de visita domiciliaria.',            2);

-- Vacunas
INSERT INTO vacuna (nombre, descripcion) VALUES
('Antirrábica',     'Vacuna contra la rabia'),
('Múltiple (DHPP)',  'Distemper, Hepatitis, Parvovirus, Parainfluenza'),
('Triple Felina',    'Panleucopenia, Calicivirus, Rinotraqueítis'),
('Leucemia Felina',  'Prevención de leucemia en gatos');

-- Relación muchos-a-muchos (mascota - vacuna)
INSERT INTO mascota_vacuna (id_mascota, id_vacuna, fecha_aplicacion, proxima_dosis) VALUES
(1, 1, '2025-11-20', '2026-11-20'),
(1, 2, '2025-11-20', '2026-05-20'),
(2, 3, '2025-12-05', '2026-06-05'),
(4, 3, '2026-01-15', '2026-07-15'),
(7, 1, '2026-03-05', '2027-03-05'),
(7, 2, '2026-03-05', '2026-09-05');

-- ============================================================
-- VISTA ÚTIL PARA REPORTES (JOIN)
-- ============================================================
CREATE OR REPLACE VIEW v_adopciones_detalle AS
SELECT 
    a.id_adopcion,
    a.fecha_adopcion,
    a.estado AS estado_adopcion,
    a.observaciones,
    m.id_mascota,
    m.nombre AS nombre_mascota,
    m.especie,
    m.raza,
    ad.id_adoptante,
    CONCAT(ad.nombres, ' ', ad.apellidos) AS nombre_adoptante,
    ad.dui,
    ad.telefono,
    u.nombre_completo AS registrado_por
FROM adopcion a
INNER JOIN mascota   m  ON a.id_mascota   = m.id_mascota
INNER JOIN adoptante ad ON a.id_adoptante = ad.id_adoptante
LEFT  JOIN usuario   u  ON a.id_usuario   = u.id_usuario;

SELECT 'Base de datos huellitas_felices creada correctamente' AS mensaje;
