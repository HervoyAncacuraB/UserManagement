-- Create User table
CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE Phones (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        number VARCHAR(50) NOT NULL,
                        citycode VARCHAR(10) NOT NULL,
                        contrycode VARCHAR(10) NOT NULL,
                        user_id INT,
                        FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- PRELOAD DATA --
INSERT INTO Users (name, email, password)
VALUES ('pepito', 'pepito@asd.cl', 'alberca');

INSERT INTO Phones (number, citycode, contrycode, user_id)
VALUES ('123456789', '3', '56', (SELECT id FROM Users WHERE email = 'pepito@asd.cl'));