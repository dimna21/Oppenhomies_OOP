# UPDATE Users SET profile_pic_url = 'hello.jpg' WHERE username = 'john_doe';
# select * from users;
#SELECT * FROM quizzes ORDER BY creation_date desc LIMIT 3;
USE QuizDatabase;

-- Drop tables if they exist
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Friends;
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
-- Create Users table
Create table Challenge(
                          challenge_id INT(6) AUTO_INCREMENT PRIMARY KEY, -- Primary and Foreign Key with Friends table
                          from_id INT(6),
                          to_id INT(6),
                          quiz_id int(6),
                          notification INT(1)
);
CREATE TABLE Users (
                       user_id INT(10) AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(150),
                       password VARCHAR(130),
                       admin_status INT(1), -- Boolean: 0 or 1
                       quizzes_taken INT(5),
                       quizzes_created INT(5),
                       highest_scorer INT(1), -- Boolean: 0 or 1
                       practice_mode INT(1), -- Boolean: 0 or 1
                       profile_pic_url VARCHAR(300)
);

INSERT INTO Users ( username, password, admin_status, quizzes_taken,
                   quizzes_created, highest_scorer, practice_mode, profile_pic_url) VALUES
                                                                                        ( 'john_doe', 'password123', 1, 10, 5, 1, 0, 'http://example.com/images/john.jpg'),
                                                                                        ('jane_smith', 'password456', 0, 8, 3, 0, 1, 'http://example.com/images/jane.jpg'),
                                                                                        ('alice_jones', 'password789', 0, 15, 7, 0, 0, 'http://example.com/images/alice.jpg'),
                                                                                        ('bob_brown', 'password321', 0, 5, 2, 1, 1, 'http://example.com/images/bob.jpg'),
                                                                                        ('charlie_black', 'password654', 1, 20, 10, 1, 1, 'http://example.com/images/charlie.jpg');


-- Create Friends table
CREATE TABLE Friends (
                         friendship_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                         friendOne_id INT(6),
                         friendTwo_id INT(6),
                         friendship_status INT(1) -- Boolean: 0 or 1
);

-- Create Messages table
CREATE TABLE Messages (
                          message_id INT(6) AUTO_INCREMENT PRIMARY KEY, -- Primary and Foreign Key with Friends table
                          from_id INT(6),
                          to_id INT(6),
                          notification INT(1) -- Boolean: 0 or 1
);

-- Create Friend_Requests table
CREATE TABLE Friend_requests (
                                 request_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                 from_id INT(6),
                                 to_id INT(6),
                                 notification INT(1) -- Boolean: 0 or 1
);

-- Create Quizzes table
CREATE TABLE Quizzes (
                         quiz_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                         quiz_name VARCHAR(300),
                         quiz_description VARCHAR(300),
                         quiz_creator_id INT(6), -- Foreign Key
                         random_question INT(1), -- Boolean: 0 or 1
                         one_page INT(1), -- Boolean: 0 or 1
                         immediate INT(1), -- Boolean: 0 or 1
                         practice INT(1), -- Boolean: 0 or 1
                         creation_date DATE,
                         times_taken INT(6)
);
INSERT INTO Quizzes (quiz_name, quiz_description, quiz_creator_id, random_question, one_page, immediate, practice, creation_date, times_taken)
VALUES
    ('General Knowledge', 'A basic general knowledge quiz.', 1, 0, 1, 1, 0, '2024-06-25', 10),
    ('Science Quiz', 'Test your science knowledge.', 2, 1, 0, 0, 1, '2024-06-20', 5),
    ('Math Quiz', 'Challenge yourself with math problems.', 3, 0, 0, 1, 0, '2024-06-18', 8),
    ('History Quiz', 'How well do you know history?', 4, 1, 1, 1, 1, '2024-06-22', 12),
    ('Literature Quiz', 'Test your literature knowledge.', 5, 0, 0, 0, 0, '2024-06-24', 3);

-- Create questions table
CREATE TABLE Quiz_questions(
                               question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                               quiz_id INT(6),
                               sub_id INT(3),
                               type INT(2)
);

-- Create textbox question table
CREATE TABLE Textbox_questions(
                                  question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                  quiz_id INT(6),
                                  sub_id INT(3),
                                  question VARCHAR(300),
                                  answer VARCHAR(300)
);

-- Create Fill_blank_questions table
CREATE TABLE Fill_blank_questions (
                                      question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                      quiz_id INT(6), -- Foreign Key with Quizzes
                                      sub_id INT(3), -- Numeric order of the question in the quiz
                                      text_before VARCHAR(300),
                                      text_after VARCHAR(300),
                                      answer VARCHAR(300)
);

-- Create Multiple_choice_questions table
CREATE TABLE Multiple_choice_questions (
                                           question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                           quiz_id INT(6), -- Foreign Key with Quizzes
                                           sub_id INT(3), -- Numeric order of the question in the quiz
                                           question VARCHAR(300)
);

-- Create Multiple_choice_answers table
CREATE TABLE Multiple_choice_answers (
                                         answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                         quiz_id INT(6), -- Foreign Key with Quizzes
                                         sub_id INT(3), -- Numeric order of the question in the quiz
                                         answer VARCHAR(300),
                                         correct INT(1) -- Boolean: 0 or 1
);

-- Create Picture_questions table
CREATE TABLE Picture_questions (
                                   answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                                   quiz_id INT(6), -- Foreign Key with Quizzes
                                   sub_id INT(3), -- Numeric order of the question in the quiz
                                   question VARCHAR(300),
                                   answer VARCHAR(300),
                                   image_url VARCHAR(300)
);

-- Create Scores table
CREATE TABLE Scores (
                        score_id INT(6) AUTO_INCREMENT PRIMARY KEY,
                        quiz_id int(6),
                        user_id INT(6), -- Foreign Key
                        score INT(6),
                        time INT(10),
                        date_scored DATE
);

CREATE TABLE Multi_textbox_questions(
    question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT(6), -- Foreign Key with Quizzes
    sub_id INT(3), -- Numeric order of the question in the quiz
    question VARCHAR(300)
);

CREATE TABLE Multi_textbox_answers(
    answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
    multifill_id INT(6), -- foreign key with question_id of questions
    answer VARCHAR(300)
);

CREATE TABLE Multi_multiple_choice_questions(
      question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
      quiz_id INT(6), -- Foreign Key with Quizzes
      sub_id INT(3), -- Numeric order of the question in the quiz
      question VARCHAR(300)

);

CREATE TABLE Multi_multiple_choice_answers(
     answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
     multichoice_id INT(6), -- foreign key with question_id of questions
     answer VARCHAR(300),
     correct INT(1) -- Boolean: 0 or 1
);

CREATE TABLE Matching_questions(
     question_id INT(6) AUTO_INCREMENT PRIMARY KEY,
     quiz_id INT(6), -- Foreign Key with Quizzes
     sub_id INT(3), -- Numeric order of the question in the quiz
     question VARCHAR(300)

);

CREATE TABLE Matching_answers(
     answer_id INT(6) AUTO_INCREMENT PRIMARY KEY,
     match_id INT(6), -- foreign key with question_id of questions
     word VARCHAR(300),
     matching_word VARCHAR(300)
);



