CREATE DATABASE cricket_stats;

USE cricket_stats;

CREATE TABLE team_stats (
    country VARCHAR(50),
    odi INT,
    t20 INT,
    test INT
);
 INSERT INTO team_stats (country, odi, t20, test) VALUES
('AUS', 14, 4, 6),
('IND', 27, 15, 3),
('NZ', 15, 10, 4),
('SA', 16, 2, 3),
('PAK', 14, 4, 2),
('ENG', 11, 4, 4),
('AFG', 7, 7, 0),
('SL', 16, 1, 2),
('WI', 10, 8, 1),
('BAN', 11, 10, 3);
