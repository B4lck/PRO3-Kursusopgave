DROP SCHEMA IF EXISTS slaughter_house CASCADE;

CREATE SCHEMA slaughter_house;
SET SCHEMA 'slaughter_house';

GRANT ALL PRIVILEGES ON DATABASE pro3_slagteri TO pro3_slagteri;
GRANT ALL PRIVILEGES ON SCHEMA slaughter_house TO pro3_slagteri;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA slaughter_house TO pro3_slagteri;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA slaughter_house TO pro3_slagteri;

CREATE DOMAIN animal_type AS VARCHAR(7) CHECK (VALUE IN ('beef', 'pork', 'lamb', 'chicken', 'fish', 'other'));
CREATE DOMAIN weight_domain AS NUMERIC(6,2);

CREATE TABLE Package (
                         package_no SERIAL PRIMARY KEY,
                         expire_date DATE NOT NULL
);

CREATE TABLE Tray (
                      max_weight weight_domain NOT NULL,
                      type_of_animal animal_type NOT NULL,
                      package_no INT REFERENCES Package (package_no) NOT NULL,
                      tray_no SERIAL PRIMARY KEY
);


CREATE TABLE Animal (
                        weight weight_domain NOT NULL,
                        type_of_animal animal_type NOT NULL,
                        animal_no SERIAL PRIMARY KEY,
                        origin VARCHAR NOT NULL
);

CREATE TABLE AnimalPart (
                            weight weight_domain NOT NULL,
                            tray_no INT REFERENCES Tray (tray_no) NOT NULL,
                            from_animal INT REFERENCES Animal (animal_no) NOT NULL,
                            part_id SERIAL PRIMARY KEY,
                            description VARCHAR
);

CREATE OR REPLACE FUNCTION check_animalpart_type_match()
    RETURNS TRIGGER AS $$
DECLARE
    animal_type_from_animal animal_type;
    animal_type_from_tray animal_type;
BEGIN
    SELECT type_of_animal INTO animal_type_from_animal
    FROM Animal
    WHERE animal_no = NEW.from_animal;

    SELECT type_of_animal INTO animal_type_from_tray
    FROM Tray
    WHERE tray_no = NEW.tray_no;

    IF animal_type_from_animal <> animal_type_from_tray THEN
        RAISE EXCEPTION 'Animal type % does not match tray type % for tray %',
            animal_type_from_animal, animal_type_from_tray, NEW.tray_no;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_animalpart_type
    BEFORE INSERT OR UPDATE ON AnimalPart
    FOR EACH ROW
EXECUTE FUNCTION check_animalpart_type_match();


CREATE OR REPLACE FUNCTION is_overweight()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF (SELECT sum(AnimalPart.weight) FROM AnimalPart WHERE tray_no = NEW.tray_no) + NEW.weight > (SELECT max_weight FROM Tray WHERE tray_no = NEW.tray_no) THEN
        RAISE EXCEPTION 'Bakken kan ikke holde til vægten';
    END IF;
    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER ensure_weight
    BEFORE INSERT OR UPDATE
    ON AnimalPart
    FOR EACH ROW
EXECUTE FUNCTION is_overweight();

-- Måske også laver en trigger som tjekker om der er mere vægt tilbage på dyret hver gang man tilføjer et animal_part

------------------------------------------------------------------------
--------------------------Dummy Data------------------------------------
------------------------------------------------------------------------

INSERT INTO Package (expire_date)
VALUES  ('2025-10-22'),
        ('2025-10-23'),
        ('2025-10-24'),
        ('2025-10-25');

INSERT INTO Tray (max_weight, type_of_animal, package_no)      -- Tray ID
VALUES  (20.00, 'pork', 1), -- 1
        (5.00, 'chicken', 2),  -- 2
        (12.00, 'lamb', 2), -- 3
        (7.50, 'pork', 3),  -- 4
        (20.00, 'beef', 4); -- 5

INSERT INTO Animal (weight, type_of_animal, origin)   -- Animal ID
VALUES  (390.00, 'beef', 'Jens Hansens Bondegård'),  -- 1
        (110.00, 'pork', 'Jens Hansens Bondegård'), -- 2
        (1.7, 'chicken', 'Jens Hansens Bondegård'), -- 3
        (1.7, 'chicken', 'Jens Hansens Bondegård'), -- 4
        (25.00, 'lamb', 'Olsens Herregård');  -- 5

INSERT INTO AnimalPart (weight, tray_no, from_animal, description)
VALUES  (20.00, 5, 1, 'Rib eyes'),
        (20.00, 1, 2, 'Flæskesteg'),
        (1.7, 2, 3, 'Hel kylling'),
        (1.7, 2, 4, 'Hel kylling');