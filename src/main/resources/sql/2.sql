INSERT INTO Users (login, password, first_name, last_name, country) VALUES ('admin', 'admin', 'Phil', 'Kuzmin', 'RUSSIA');
INSERT INTO Users (login, password, first_name, last_name, country) VALUES ('Jagger', 'qwe', 'Mick', 'Jagger', 'USA');
INSERT INTO Users (login, password, first_name, last_name, country) VALUES ('Bono', 'qwe', 'Bono', 'U2', 'USA');
INSERT INTO Users (login, password, first_name, last_name, country) VALUES ('JustinBieber', 'qwe', 'Justin', 'Bieber', 'RUSSIA');
INSERT INTO Users (login, password, first_name, last_name, country) VALUES ('ringo', 'qwe', 'Ringo', 'Starr', 'SPAIN');

INSERT INTO Instruments (instrument_name) VALUES ('guitar');
INSERT INTO Instruments (instrument_name) VALUES ('bass');
INSERT INTO Instruments (instrument_name) VALUES ('vocals');
INSERT INTO Instruments (instrument_name) VALUES ('piano');
INSERT INTO Instruments (instrument_name) VALUES ('drums');

INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (1 , 1);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (1 , 2);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (2 , 2);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (2 , 3);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (3 , 3);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (3 , 4);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (4 , 4);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (4 , 5);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (5 , 5);
INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (5 , 1);

INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (1, 1);
INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (1, 2);
INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (1, 3);
INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (1, 5);
INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (2, 5);
INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (5, 2);

INSERT INTO Messages (user_id, message_date, message_text) VALUES (2, '2010-01-01', 'This is my first music tweet!!');
INSERT INTO Messages (user_id, message_date, message_text) VALUES (2, '2011-02-01', 'Second tweet! Nice service you''ve got here');
INSERT INTO Messages (user_id, message_date, message_text) VALUES (3, '2012-03-07', 'What''s up?');
INSERT INTO Messages (user_id, message_date, message_text) VALUES (4, '2013-04-07', 'What''s up? What''s up? What''s up?');
INSERT INTO Messages (user_id, message_date, message_text) VALUES (5, '2015-04-07', 'This is Ringo!');
INSERT INTO Messages (user_id, message_date, message_text) VALUES (5, '2015-04-08', 'All you need is love!');
INSERT INTO Messages (user_id, message_date, message_text) VALUES (3, '2017-03-08', 'All you need is peace!');
