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
-- Create Users table
CREATE TABLE Users (
                       user_id INT(10) PRIMARY KEY,
                       username VARCHAR(15),
                       password VARCHAR(130),
                       admin_status INT(1), -- Boolean: 0 or 1
                       quizzes_taken INT(5),
                       quizzes_created INT(5),
                       highest_scorer INT(1), -- Boolean: 0 or 1
                       practice_mode INT(1), -- Boolean: 0 or 1
                       profile_pic_url VARCHAR(300)
);

INSERT INTO Users (user_id, username, password, admin_status, quizzes_taken,
                   quizzes_created, highest_scorer, practice_mode, profile_pic_url) VALUES
                                                                                        (1, 'john_doe', 'password123', 1, 10, 5, 1, 0, 'http://example.com/images/john.jpg'),
                                                                                        (2, 'jane_smith', 'password456', 0, 8, 3, 0, 1, 'http://example.com/images/jane.jpg'),
                                                                                        (3, 'alice_jones', 'password789', 0, 15, 7, 0, 0, 'http://example.com/images/alice.jpg'),
                                                                                        (4, 'bob_brown', 'password321', 0, 5, 2, 1, 1, 'http://example.com/images/bob.jpg'),
                                                                                        (5, 'charlie_black', 'password654', 1, 20, 10, 1, 1, 'http://example.com/images/charlie.jpg');


-- Create Friends table
CREATE TABLE Friends (
                         friendship_id INT(6) PRIMARY KEY,
                         friendOne_id INT(6),
                         friendTwo_id INT(6),
                         friendship_status INT(1) -- Boolean: 0 or 1
);

-- Create Messages table
CREATE TABLE Messages (
                          message_id INT(6) PRIMARY KEY, -- Primary and Foreign Key with Friends table
                          from_id INT(6),
                          to_id INT(6),
                          notification INT(1) -- Boolean: 0 or 1
);

-- Create Friend_Requests table
CREATE TABLE Friend_requests (
                                 request_id INT(6) PRIMARY KEY,
                                 from_id INT(6),
                                 to_id INT(6),
                                 notification INT(1) -- Boolean: 0 or 1
);

-- Create Quizzes table
CREATE TABLE Quizzes (
                         quiz_id INT(6) PRIMARY KEY,
                         quiz_name VARCHAR(25),
                         quiz_description VARCHAR(50),
                         quiz_creator_id INT(6), -- Foreign Key
                         random_question INT(1), -- Boolean: 0 or 1
                         one_page INT(1), -- Boolean: 0 or 1
                         immediate INT(1), -- Boolean: 0 or 1
                         practice INT(1), -- Boolean: 0 or 1
                         creation_date DATE,
                         times_taken INT(6)
);

-- Create questions table
CREATE TABLE Quiz_questions(
                               question_id INT(6) PRIMARY KEY,
                               quiz_id INT(6),
                               sub_id INT(3),
                               type INT(2)
);

-- Create textbox question table
CREATE TABLE Textbox_questions(
                                  question_id INT(6) PRIMARY KEY,
                                  quiz_id INT(6),
                                  sub_id INT(3),
                                  question VARCHAR(100),
                                  answer VARCHAR(30)
);

-- Create Fill_blank_questions table
CREATE TABLE Fill_blank_questions (
                                      question_id INT(6) PRIMARY KEY,
                                      quiz_id INT(6), -- Foreign Key with Quizzes
                                      sub_id INT(3), -- Numeric order of the question in the quiz
                                      text_before VARCHAR(300),
                                      text_after VARCHAR(300),
                                      answer VARCHAR(50)
);

-- Create Multiple_choice_questions table
CREATE TABLE Multiple_choice_questions (
                                           question_id INT(6) PRIMARY KEY,
                                           quiz_id INT(6), -- Foreign Key with Quizzes
                                           sub_id INT(3), -- Numeric order of the question in the quiz
                                           question VARCHAR(300)
);

-- Create Multiple_choice_answers table
CREATE TABLE Multiple_choice_answers (
                                         answer_id INT(6) PRIMARY KEY,
                                         quiz_id INT(6), -- Foreign Key with Quizzes
                                         sub_id INT(3), -- Numeric order of the question in the quiz
                                         answer VARCHAR(30),
                                         correct INT(1) -- Boolean: 0 or 1
);

-- Create Picture_questions table
CREATE TABLE Picture_questions (
                                   answer_id INT(6) PRIMARY KEY,
                                   quiz_id INT(6), -- Foreign Key with Quizzes
                                   sub_id INT(3), -- Numeric order of the question in the quiz
                                   question VARCHAR(300),
                                   answer VARCHAR(30),
                                   image_url VARCHAR(100)
);

-- Create Scores table
CREATE TABLE Scores (
                        score_id INT(6) PRIMARY KEY,
                        user_id INT(6), -- Foreign Key
                        score INT(6),
                        time INT(10),
                        date_scored DATE
);
