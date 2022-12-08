INSERT INTO USER (EMAIL, FIRST_NAME, LAST_NAME, USER_NAME)
Values   ('s.cepeda.ortegon@gmail.com', 'Sergio', 'Cepeda', 'sergio.cepeda'),
         ('alonson@gmail.com', 'Alonso', 'Ortegon', 'alonso00'),
         ('gerardo@gmail.com', 'Gerardo', 'Cepeda', 'gerardo99'),
         ('gustavo@gmail.com', 'Gustavo', 'Torres', 'torres');


INSERT INTO EVENT (TITLE, DATE)
Values ('FORMULA 1', '2022-10-10'),
       ('CHIRSTMAS PARTY', '2022-01-01');

INSERT INTO TICKET ( USER_ID,  CATEGORY, PLACE, EVENT_ID)
VALUES ( '1' , 'STANDARD' , '23' , '1' ),
       ( '3' , 'STANDARD' , '26' , '1' ),
       ( '4' , 'STANDARD' , '200' , '1' );





