-- Кестені жасау
CREATE TABLE games (
    id INTEGER PRIMARY KEY,
    type TEXT,
    name TEXT,
    weight INTEGER,
    age INTEGER,
    duration INTEGER
);

-- Ойындарды қосу
INSERT INTO games (type, name, weight, age, duration)
VALUES ('Puzzle', 'Лего мини-пазл', 300, 6, 20);

INSERT INTO games (type, name, weight, age, duration)
VALUES ('Board', 'Ходилки', 500, 7, 40);

INSERT INTO games (type, name, weight, age, duration)
VALUES ('Action', 'Пистолетик', 250, 5, 10);
