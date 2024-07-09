USE QuizDatabase;

-- Drop tables if they exist
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Messages;
DROP TABLE IF EXISTS Friend_requests;
DROP TABLE IF EXISTS Quizzes;
DROP TABLE IF EXISTS Fill_blank_questions;
DROP TABLE IF EXISTS Multiple_choice_questions;
DROP TABLE IF EXISTS Multiple_choice_answers;
DROP TABLE IF EXISTS Picture_questions;
DROP TABLE IF EXISTS Scores;
DROP TABLE IF EXISTS Textbox_questions;
DROP TABLE IF EXISTS Quiz_questions;
drop table if exists Challenge;
drop table if exists Multi_answer_questions;
drop table if exists Multi_answer_answers;
drop table if exists checkbox_questions;
drop table if exists checkbox_answers;
drop table if exists Matching_questions;
drop table if exists Matching_answers;
drop table if exists Quiz_ratings;
drop table if exists Announcements;
drop table if exists Achievements;
Create table Quiz_ratings(
                             rating_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                             rating INT(2),
                             quiz_id int(6)not null,
                             user_id INT(10) not null
);

Create table Challenge(
                          challenge_id INT(6) AUTO_INCREMENT PRIMARY KEY, -- Primary and Foreign Key with Friends table
                          from_id INT(6) not null,
                          to_id INT(6)not null,
                          quiz_id int(6)not null,
                          notification INT(1) Default 0 -- 0- not seen, 1-accepted, 2 - declined
);

INSERT INTO Challenge(from_id, to_id, quiz_id, notification) VALUES
                                                                 (3,1,1,0),
                                                                 (2,1,1,0),
                                                                 (4,1,1,0),
                                                                 (5,1,1,0);
-- Create Users table
CREATE TABLE Users (
                       user_id INT(10) AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(150)not null,
                       password VARCHAR(130)not null,
                       admin_status INT(1)not null, -- Boolean: 0 or 1
                       quizzes_taken INT(5) default 0,
                       quizzes_created INT(5)default 0,
                       highest_scorer INT(1)default 0, -- Boolean: 0 or 1
                       practice_mode INT(1)default 0, -- Boolean: 0 or 1
                       profile_pic_url VARCHAR(300),
                       activeAccount INT(1) DEFAULT 1
);

INSERT INTO Users ( username, password, admin_status, quizzes_taken,
                    quizzes_created, highest_scorer, practice_mode, profile_pic_url) VALUES
                                                                                         ('john_doe', 'cbfdac6008f9cab4083784cbd1874f76618d2a97', 1, 10, 5, 1, 0, 'http://example.com/images/john.jpg'),
                                                                                         ('jane_smith', 'aa6ae8c005b9048b03f6059224c858650d9e52d5', 0, 8, 3, 0, 1, 'http://example.com/images/jane.jpg'),
                                                                                         ('alice_jones', 'cbfdac6008f9cab4083784cbd1874f76618d2a97', 0, 15, 7, 0, 0, 'http://example.com/images/alice.jpg'),
                                                                                         ('bob_brown', 'cbfdac6008f9cab4083784cbd1874f76618d2a97', 0, 5, 2, 1, 1, 'http://example.com/images/bob.jpg'),
                                                                                         ('charlie_black', 'cbfdac6008f9cab4083784cbd1874f76618d2a97', 1, 20, 10, 1, 1, 'http://example.com/images/charlie.jpg');
-- password123
-- password234
-- password123
-- password123
-- password123



-- Create Messages table
CREATE TABLE Messages (
                          message_id INT(6) AUTO_INCREMENT PRIMARY KEY, -- Primary and Foreign Key with Friends table
                          from_id INT(6) not null,
                          to_id INT(6) not null,
                          text VARCHAR(300), -- text message
                          notification INT(1)default 0 -- Boolean: 0 or 1
);

-- Create Friend_Requests table
CREATE TABLE Friend_requests (
                                 request_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                 from_id INT(6)not null,
                                 to_id INT(6)not null,
                                 notification INT(1) Default 0 -- 0 - not seen, 1-accepted, 2 - declined
);

INSERT INTO Friend_requests(from_id, to_id, notification) VALUES
                                                              (1,3,1),
                                                              (1,2,1),
                                                              (1,4,1),
                                                              (1,5,1),
                                                              (3,1,1),
                                                              (2,1,1),
                                                              (4,1,1),
                                                              (5,1,1);

-- Create Quizzes table
CREATE TABLE Quizzes (
                         quiz_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                         quiz_name VARCHAR(300),
                         quiz_description VARCHAR(300),
                         quiz_creator_id INT(6)not null, -- Foreign Key
                         random_question INT(1)default 0, -- Boolean: 0 or 1
                         one_page INT(1)default 0, -- Boolean: 0 or 1
                         immediate INT(1)default 1, -- Boolean: 0 or 1
                         practice INT(1)default 0, -- Boolean: 0 or 1
                         creation_date timestamp default current_timestamp,
                         times_taken INT(6)
);
-- Insert data into Quizzes table with creation_date as TIMESTAMP
INSERT INTO Quizzes (quiz_name, quiz_description, quiz_creator_id, random_question, one_page, immediate, practice, creation_date, times_taken)
VALUES
    ('General Knowledge', 'A basic general knowledge quiz.', 1, 0, 1, 1, 0, '2024-06-25 00:00:00', 10),
    ('Science Quiz', 'Test your science knowledge.', 2, 1, 0, 0, 1, '2024-06-20 00:00:00', 5),
    ('Math Quiz', 'Challenge yourself with math problems.', 3, 0, 0, 1, 0, '2024-06-18 00:00:00', 8),
    ('History Quiz', 'How well do you know history?', 4, 1, 1, 1, 1, '2024-06-22 00:00:00', 12),
    ('q1', 'Test your literature knowledge.', 5, 0, 0, 0, 0, '2024-06-24 00:00:00', 3),
    ('q2', 'test you skills', 5, 0, 0, 0, 0, '2023-05-24 00:00:00', 4),
    ('q3', 'do you really care', 5, 0, 0, 0, 0, '2023-06-24 00:00:00', 5);



-- Create questions table
CREATE TABLE Quiz_questions(
                               question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                               quiz_id INT(6) not null,
                               sub_id INT(3) not null,
                               type INT(2) not null
);

-- Create textbox question table
CREATE TABLE Textbox_questions(
                                  question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                  quiz_id INT(6)not null,
                                  sub_id INT(3)not null,
                                  question VARCHAR(300),
                                  answer VARCHAR(300)
);

-- Create Fill_blank_questions table
CREATE TABLE Fill_blank_questions (
                                      question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                      quiz_id INT(6)not null, -- Foreign Key with Quizzes
                                      sub_id INT(3)not null, -- Numeric order of the question in the quiz
                                      text_before VARCHAR(300),
                                      text_after VARCHAR(300),
                                      answer VARCHAR(300)
);

-- Create Multiple_choice_questions table
CREATE TABLE Multiple_choice_questions (
                                           question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                           quiz_id INT(6)not null, -- Foreign Key with Quizzes
                                           sub_id INT(3)not null, -- Numeric order of the question in the quiz
                                           question VARCHAR(300),
                                           ordered int(1) default 0
);

-- Create Multiple_choice_answers table
CREATE TABLE Multiple_choice_answers (
                                         answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                         quiz_id INT(6)not null, -- Foreign Key with Quizzes
                                         sub_id INT(3)not null, -- Numeric order of the question in the quiz
                                         order_number int(3) not null,
                                         answer VARCHAR(300),
                                         correct INT(1) default 0-- Boolean: 0 or 1
);

-- Create Picture_questions table
CREATE TABLE Picture_questions (
                                   answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                   quiz_id INT(6) not null, -- Foreign Key with Quizzes
                                   sub_id INT(3) not null, -- Numeric order of the question in the quiz
                                   question VARCHAR(300),
                                   answer VARCHAR(300),
                                   image_url VARCHAR(300)
);

-- Create Scores table
CREATE TABLE Scores (
                        score_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                        quiz_id int(6) not null,
                        user_id INT(6) not null, -- Foreign Key
                        score INT(6),
                        time INT(10),
                        date_scored timestamp default current_timestamp
);
INSERT INTO Scores (quiz_id, user_id, score, time, date_scored)
VALUES
    (1, 1, 80, 1200, '2024-06-30 10:15:00'),
    (1, 1, 60, 1100, '2024-06-29 10:15:00'),
    (1, 1, 70, 1000, '2024-06-28 10:15:00'),
    (1, 2, 85, 1100, '2024-06-29 14:30:00'),
    (2, 1, 75, 1500, '2024-06-28 09:45:00'),
    (2, 3, 90, 1000, '2024-06-27 11:20:00'),
    (1, 4, 70, 1300, '2024-06-26 16:00:00'),
    (3, 2, 95, 800, '2024-06-25 18:10:00'),
    (3, 4, 65, 1700, '2024-06-24 12:45:00'),
    (2, 3, 82, 1250, '2024-06-22 15:30:00'),
    (3, 2, 78, 1400, '2024-06-21 08:20:00');



CREATE TABLE Multi_answer_questions(
                                           question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                           quiz_id INT(6) not null, -- Foreign Key with Quizzes
                                           sub_id INT(3) not null, -- Numeric order of the question in the quiz
                                           question VARCHAR(300),
                                           ordered int(1) default 1
);

CREATE TABLE Multi_answer_answers(
                                         answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                         quiz_id INT(6) not null, -- Foreign Key with Quizzes
                                         sub_id INT(3) not null, -- Numeric order of the question in the quiz
                                         answer VARCHAR(300),
                                         order_num int(3) not null
);

CREATE TABLE checkbox_questions(
                                                question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                                quiz_id INT(6) not null, -- Foreign Key with Quizzes
                                                sub_id INT(3) not null, -- Numeric order of the question in the quiz
                                                question VARCHAR(300),
                                                ordered int(1) default 1

);

CREATE TABLE checkbox_answers(
          answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
          quiz_id INT(6) not null, -- Foreign Key with Quizzes
          sub_id INT(3) not null, -- Numeric order of the question in the quiz
          answer VARCHAR(300),
          correct INT(1) default 0, -- Boolean: 0 or 1
          order_num int(3) not null
);

CREATE TABLE Matching_questions(
                                   question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                   quiz_id INT(6) not null, -- Foreign Key with Quizzes
                                   sub_id INT(3) not null, -- Numeric order of the question in the quiz
                                   question VARCHAR(300)

);

CREATE TABLE Matching_answers(
                                 answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                 quiz_id INT(6) not null, -- Foreign Key with Quizzes
                                 sub_id INT(3) not null, -- Numeric order of the question in the quiz
                                 word VARCHAR(300),
                                 matching_word VARCHAR(300)
);

Create table Announcements (
                               announcement_id INT(6)AUTO_INCREMENT PRIMARY KEY,
                               announcement_title VARCHAR(300),
                               announcement_text VARCHAR(300),
                               announcer_id INT(6),
                               announcement_date TIMESTAMP default current_timestamp
);
CREATE TABLE Achievements ( achievement_id INT(6)AUTO_INCREMENT PRIMARY KEY,
                            achievement_title VARCHAR(300),
                            user_id INT(6),
                            achievement_date TIMESTAMP default current_timestamp
);

INSERT INTO Announcements(announcement_id, announcement_title, announcement_text, announcer_id, announcement_date)
VALUES
    (1401, 'Announcement 1', 'This is announcement 1', 1, '2022-06-25 00:00:00'),
    (1402, 'Announcement 2', 'This is announcement 2', 5, '2022-07-25 00:00:00');

-- -------------------------------------------------------------
-- Add a new quiz
-- Add a new quiz
INSERT INTO Quizzes (quiz_name, quiz_description, quiz_creator_id, random_question, one_page, immediate, practice, creation_date, times_taken)
VALUES
    ('Sample Quiz', 'This is a sample quiz to demonstrate table relationships.', 1, 0, 1, 1, 0, '2023-06-18 00:00:00', 0);

-- Retrieve the quiz ID for the new quiz (Assume the quiz_id is 6 for the following inserts)
SELECT quiz_id FROM Quizzes WHERE quiz_name = 'Sample Quiz';

-- Add questions to the Quiz_questions table
INSERT INTO Quiz_questions (quiz_id, sub_id, type) VALUES
   (6, 1, 1), -- Textbox question
   (6, 2, 2), -- Fill-in-the-blank question
   (6, 3, 3), -- Multiple-choice question
   (6, 4, 4), -- Picture question
   (6, 5, 6), -- CheckBox question
   (6, 6, 6), -- CheckBox question
   (6, 7, 5), -- multiAnswer question
   (6, 8, 5), -- multiAnswer question
   (6, 9, 5); -- multiAnswer question
-- Add a textbox question to the Textbox_questions table
INSERT INTO Textbox_questions (quiz_id, sub_id, question, answer) VALUES
    (6, 1, 'What is the capital of France?', 'Paris');

-- Add a fill-in-the-blank question to the Fill_blank_questions table
INSERT INTO Fill_blank_questions (quiz_id, sub_id, text_before, text_after, answer) VALUES
    (6, 2, 'The chemical symbol for water is ', '.', 'H2O');

-- Add a multiple-choice question to the Multiple_choice_questions table
INSERT INTO Multiple_choice_questions (quiz_id, sub_id, question, ordered) VALUES
    (6, 3, 'Which planet is known as the Red Planet?', 1);

-- Add answers to the Multiple_choice_answers table
INSERT INTO Multiple_choice_answers (quiz_id, sub_id, order_number, answer, correct) VALUES
     (6, 3, 1, 'Mars', 1),
     (6, 3, 2, 'Venus', 0),
     (6, 3, 3, 'Jupiter', 0),
     (6, 3, 4, 'Saturn', 0);

-- Add a picture question to the Picture_questions table
INSERT INTO Picture_questions (quiz_id, sub_id, question, answer, image_url) VALUES
    (6, 4, 'What is the name of this famous landmark?', 'Eiffel Tower', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg/500px-Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg');
-- -----------------------------
INSERT INTO checkbox_questions (quiz_id, sub_id, question) VALUES
    (6, 5, 'What are the primary colors?'),
    (6, 6, 'Select the programming languages.');
INSERT INTO Multi_answer_questions (quiz_id, sub_id, question, ordered) VALUES
    (6, 7, 'What are the primary colors?', 0),
    (6, 8, 'What are the planets in the Solar System in order?', 1),
    (6, 9, 'What are the components of a computer?', 0);

INSERT INTO Multi_answer_answers (quiz_id, sub_id, answer, order_num) VALUES
-- For question "What are the primary colors?"
(6, 7, 'Red', 1),
(6, 7, 'Blue', 2),
(6, 7, 'Green', 3),
(6, 7, 'Yellow', 4),

-- For question "What are the planets in the Solar System?"
(6, 8, 'Mercury', 1),
(6, 8, 'Venus', 2),
(6, 8, 'Earth', 3),
(6, 8, 'Mars', 4),
(6, 8, 'Jupiter', 5),
(6, 8, 'Saturn', 6),
(6, 8, 'Uranus', 7),
(6, 8, 'Neptune', 8),

-- For question "What are the components of a computer?"
(6, 9, 'CPU', 1),
(6, 9, 'RAM', 2),
(6, 9, 'Motherboard', 3),
(6, 9, 'Power Supply', 4),
(6, 9, 'Storage', 5);


-- Insert checkbox answers
INSERT INTO checkbox_answers (quiz_id,sub_id, answer, correct, order_num) VALUES
  (6,5, 'Red', 1, 1),
  (6,5, 'Blue', 1 ,2),
  (6,5, 'Green', 1 ,3 ),
  (6,5, 'Yellow', 0, 4),
  (6,6, 'Java', 1, 1),
  (6,6, 'Python', 1,2 ),
  (6,6, 'C++', 1, 3),
  (6,6, 'HTML', 0, 4);

-- --------------------------
INSERT INTO Messages (from_id, to_id, text, notification)
VALUES
    (1, 2, 'Hello, how are you?', 1),
    (2, 1, 'Great, what about you?', 0),
    (1, 2, 'i am fine!!', 1),
    (2, 1, 'are you done writin oop?', 0),
    (1, 2, 'not yet 😞 wby', 1),
    (2, 1, 'same', 0);

-- ---------------------

INSERT INTO Scores (quiz_id, user_id, score, time, date_scored)
VALUES
    (4, (SELECT user_id FROM Users WHERE username = 'bob_brown'), 80, 1200, CURRENT_TIMESTAMP - INTERVAL 8 HOUR),
    (4, (SELECT user_id FROM Users WHERE username = 'bob_brown'), 85, 1100, CURRENT_TIMESTAMP - INTERVAL 7 HOUR),
    (4, (SELECT user_id FROM Users WHERE username = 'bob_brown'), 95, 1000, CURRENT_TIMESTAMP - INTERVAL 6 HOUR),
    (4, (SELECT user_id FROM Users WHERE username = 'bob_brown'), 70, 1300, CURRENT_TIMESTAMP - INTERVAL 5 HOUR);
-- ----------------
SELECT u.user_id,
       u.username,
       u.password,
       u.admin_status,
       u.quizzes_taken,
       u.quizzes_created,
       u.highest_scorer,
       u.practice_mode,
       u.profile_pic_url,
       u.activeAccount,
       s.score,
       s.date_scored,
       s.score_id,
       s.quiz_id,
       s.user_id,
       s.time

FROM Users u
         JOIN Scores s ON u.user_id = s.user_id
WHERE s.quiz_id = 4
  AND s.date_scored >= NOW() - INTERVAL 1 DAY
ORDER BY s.score DESC
    LIMIT 2;
-- -----------------------------------------------
INSERT INTO Achievements (achievement_title, user_id, achievement_date) VALUES
            ('Practice makes perfect', 1, NOW() - INTERVAL 1 DAY),
            ('Quiz machine', 1, NOW() - INTERVAL 2 DAY),
            ('Amateur author', 2, NOW() - INTERVAL 3 DAY),
            ('I am the greatest', 4, NOW() - INTERVAL 4 DAY),
            ('Practice makes perfect', 4, NOW() - INTERVAL 5 HOUR),
            ('Quiz machine', 4, NOW() - INTERVAL 6 HOUR);


-- -------------------------------------------
select * from messages;
SELECT * FROM Announcements ORDER BY announcement_date DESC LIMIT 2;

Insert into Quiz_ratings(rating, quiz_id, user_id) values
(6, 1, 1);
