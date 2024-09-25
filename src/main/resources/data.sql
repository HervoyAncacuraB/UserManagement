-- Create User table
CREATE TABLE IF NOT EXISTS users (
 id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 authorities VARCHAR(255),
 phone_number VARCHAR(255),
 city_code_phone VARCHAR(255),
 country_code_phone VARCHAR(255),
 created DATETIME NOT NULL,
 modified DATETIME,
 last_login DATETIME,
 token VARCHAR(255),
 is_active BOOLEAN NOT NULL);

-- PRELOAD DATA --
-- Insertar datos en la tabla user
INSERT INTO users (name, email, password, authorities, phone_number, city_code_phone, country_code_phone, created, is_active)
VALUES ('John Doe', 'johndoe@example.com', 'password123!', 'ROLE_USER', '1234567', '1', '57', CURRENT_TIMESTAMP, true);