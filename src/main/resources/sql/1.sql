CREATE TABLE Users (
  user_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
  login      VARCHAR(15)  NOT NULL UNIQUE,
  password   VARCHAR(256) NOT NULL,
  first_name VARCHAR(32),
  last_name  VARCHAR(32),
  country    VARCHAR(32),
);

CREATE TABLE Instruments (
  instrument_id   BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  instrument_name VARCHAR(32)           NOT NULL UNIQUE
);

CREATE TABLE Messages (
  message_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id      BIGINT    NOT NULL,
  message_date TIMESTAMP NOT NULL,
  message_text VARCHAR(1024),
  FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE Likes (
  like_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id    BIGINT NOT NULL,
  message_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (user_id),
  FOREIGN KEY (message_id) REFERENCES Messages (message_id)
);

CREATE TABLE Subscriptions (
  subscription_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id             BIGINT NOT NULL,
  subscripted_user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (user_id),
  FOREIGN KEY (subscripted_user_id) REFERENCES Users (user_id)
);

CREATE TABLE Users_Instruments (
  user_id       BIGINT NOT NULL,
  instrument_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (user_id),
  FOREIGN KEY (instrument_id) REFERENCES Instruments (instrument_id)
);