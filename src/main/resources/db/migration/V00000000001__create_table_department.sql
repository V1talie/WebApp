CREATE TABLE Department
(
    department_id LONG auto_increment PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    location      VARCHAR(255) NOT NULL,
    CONSTRAINT name_len CHECK (LENGTH(RTRIM(name)) > 1),
    CONSTRAINT location__len CHECK (LENGTH(RTRIM(location)) > 1),
    CONSTRAINT name_location_uniq UNIQUE (name, location)
);