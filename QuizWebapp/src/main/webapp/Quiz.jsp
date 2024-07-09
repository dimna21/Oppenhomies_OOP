<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="DBpackage.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="DBpackage.Questions.*" %>
<%@ page import="DBpackage.DAOpackage.QuizDAO" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Arrays" %>

<%
    int practice = 0;
    if (request.getParameter("practice") != null) {
        practice = Integer.parseInt( request.getParameter("practice"));
    }

    session.setAttribute("practice", practice);
    //DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    long startTimeMillis = System.currentTimeMillis();
    // Store in session
    session.setAttribute("quizStartTime", startTimeMillis);

    int quizId = Integer.parseInt(request.getParameter("quizId"));
    Quiz quiz = QuizDAO.getQuizInfo(quizId);
    ArrayList<Question> questions = QuizDAO.getQuizQuestions(quizId);
    ArrayList<Integer> originalIndices = new ArrayList<Integer>();
    for (int i = 0; i < questions.size(); i++) {
        originalIndices.add(i);
    }
    if(quiz.getRandomQuestion() == 1) {
        Collections.shuffle(originalIndices);
        ArrayList<Question> shuffledQuestions = new ArrayList<Question>(questions.size());
        for(int i=0; i<questions.size(); i++) {
            shuffledQuestions.add(questions.get(originalIndices.get(i)));
        }
        questions = shuffledQuestions;
    }

    int onePage = quiz.getOnePage();
    int immediate = quiz.getImmediate();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=quiz.getName()%></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/QuizPage.css">

    <script>
        let currentQuestion = 0;
        const totalQuestions = <%=questions.size()%>;
        const originalIndices = <%=Arrays.toString(originalIndices.toArray())%>;
        const immediate = <%=immediate%>;

        function updateQuestionOrder() {
            var order = [];
            $('.question').each(function() {
                order.push($(this).data('original-index'));
            });
            $('#questionOrder').val(JSON.stringify(order));
        }

        function showQuestion(index) {
            $('.question').hide();
            $('#question' + index).show();
            updateNavButtons();
        }

        function checkAndNextQuestion() {
            if (currentQuestion < totalQuestions - 1) {
                checkAnswer(currentQuestion, originalIndices[currentQuestion])
                    .then((response) => {
                        showFeedbackPopup(response);
                        setTimeout(() => {
                            currentQuestion++;
                            showQuestion(currentQuestion);
                        }, 2000); // Wait 2 seconds before moving to the next question
                    })
                    .catch(error => {
                        console.error("Error checking answer:", error);
                    });
            } else {
                // If it's the last question, check the answer and submit the quiz
                checkAnswer(currentQuestion, originalIndices[currentQuestion])
                    .then((response) => {
                        showFeedbackPopup(response);
                        setTimeout(() => {
                            $('#quizForm').submit();
                        }, 2000); // Wait 2 seconds before submitting the quiz
                    })
                    .catch(error => {
                        console.error("Error checking answer:", error);
                    });
            }
        }

        function nextQuestion() {
            if (currentQuestion < totalQuestions - 1) {
                currentQuestion++;
                showQuestion(currentQuestion);
            }
        }

        function prevQuestion() {
            if (currentQuestion > 0) {
                currentQuestion--;
                showQuestion(currentQuestion);
            }
        }



        function updateNavButtons() {
            if (immediate == 1) {
                $('#nextBtn').prop('disabled', currentQuestion === totalQuestions - 1);
                $('#nextBtn').text(currentQuestion === totalQuestions - 1 ? 'Submit' : 'Next');
            } else {
                $('#prevBtn').prop('disabled', currentQuestion === 0);
                $('#nextBtn').prop('disabled', currentQuestion === totalQuestions - 1);
            }
        }

        function checkAnswer(questionIndex, originalIndex) {
            return new Promise((resolve, reject) => {
                let answer;
                const questionElement = document.getElementById('question' + questionIndex);
                const questionType = questionElement.getAttribute('data-question-type');

                // Get the answer based on the question type
                switch (parseInt(questionType)) {
                    case <%=DatabaseAccess.QUESTION_TEXTBOX%>:
                    case <%=DatabaseAccess.QUESTION_FILL_BLANK%>:
                    case <%=DatabaseAccess.QUESTION_PICTURE%>:
                        answer = document.getElementById('question' + originalIndex + '-answer').value;
                        break;
                    case <%=DatabaseAccess.QUESTION_MULTIPLE_CHOICE%>:
                        answer = $('input[name="answer[' + originalIndex + ']"]:checked').val();
                        break;
                    case <%=DatabaseAccess.QUESTION_MULTIANSWER%>:
                        // Collect all text inputs for multi-answer questions
                        answer = $('input[name^="answer[' + originalIndex + ']"]').map(function() {
                            return this.value.trim();
                        }).get();
                        break;
                    case <%=DatabaseAccess.QUESTION_CHECKBOX%>:
                        // Collect values of checked checkboxes
                        answer = $('input[name^="answer[' + originalIndex + ']"]:checked').map(function() {
                            return this.value;
                        }).get();
                        break;
                    case <%=DatabaseAccess.QUESTION_MATCHING%>:
                        answer = $('#question' + originalIndex + '-answer').val();
                        break;
                }

                $.ajax({
                    url: 'CheckAnswerServlet',
                    type: 'POST',
                    data: {
                        quizId: <%=quizId%>,
                        questionIndex: originalIndex,
                        answer: JSON.stringify(answer),
                        questionType: questionType
                    },
                    success: function(response) {
                        resolve(response);
                    },
                    error: function(xhr, status, error) {
                        console.error("Error checking answer:", error);
                        reject(error);
                    }
                });
            });
        }

        function showFeedbackPopup(feedback) {
            // Create a popup element
            const popup = $('<div>', {
                class: 'feedback-popup',
                html: feedback
            }).appendTo('body');

            // Style the popup
            popup.css({
                position: 'fixed',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                padding: '20px',
                background: 'white',
                border: '1px solid black',
                borderRadius: '5px',
                boxShadow: '0 0 10px rgba(0,0,0,0.5)',
                zIndex: 1000
            });

            // Remove the popup after 2 seconds
            setTimeout(() => {
                popup.remove();
            }, 2000);
        }

        $(document).ready(function() {
            <% if (onePage != 1) { %>
            showQuestion(0);
            <% } %>

            setupDragAndDrop();
            updateQuestionOrder();
        });

        function setupDragAndDrop() {
            const draggables = document.querySelectorAll('.matching-item[draggable="true"]');
            const dropZones = document.querySelectorAll('.drop-zone');

            draggables.forEach(draggable => {
                draggable.addEventListener('dragstart', dragStart);
                draggable.addEventListener('dragend', dragEnd);
            });

            dropZones.forEach(zone => {
                zone.addEventListener('dragover', dragOver);
                zone.addEventListener('dragenter', dragEnter);
                zone.addEventListener('dragleave', dragLeave);
                zone.addEventListener('drop', drop);
            });
        }

        function dragStart(e) {
            e.dataTransfer.setData('text/plain', e.target.dataset.itemId);
            setTimeout(() => e.target.classList.add('dragging'), 0);
        }

        function dragEnd(e) {
            e.target.classList.remove('dragging');
        }

        function dragOver(e) {
            e.preventDefault();
        }

        function dragEnter(e) {
            e.preventDefault();
            e.target.classList.add('drag-over');
        }

        function dragLeave(e) {
            e.target.classList.remove('drag-over');
        }

        function drop(e) {
            e.preventDefault();
            const itemId = e.dataTransfer.getData('text/plain');
            const draggableElement = document.querySelector(`[data-item-id="${itemId}"]`);
            const dropZone = e.target.closest('.drop-zone');

            if (!dropZone) return;

            const matchingSlot = dropZone.closest('.matching-slot');

            // Check if there's already an item in this slot
            const existingItem = dropZone.querySelector('.matching-item');
            if (existingItem) {
                // Move the existing item back to the left column
                document.querySelector('.matching-column-left').appendChild(existingItem);
            }

            // Remove the dragged item from its previous position
            if (draggableElement.parentElement.classList.contains('drop-zone')) {
                draggableElement.parentElement.innerHTML = '';
            }

            dropZone.innerHTML = ''; // Clear the drop zone
            dropZone.appendChild(draggableElement);

            dropZone.classList.remove('drag-over');
            updateMatchingAnswers(matchingSlot.closest('.question').id);
        }

        function updateMatchingAnswers(questionId) {
            let matchings = [];
            $(`#${questionId} .matching-slot`).each(function() {
                let rightItem = $(this).find('.slot-text').text();
                let leftItem = $(this).find('.matching-item').text();
                if (leftItem) {
                    matchings.push({left: leftItem, right: rightItem});
                }
            });
            $(`#${questionId}-answer`).val(JSON.stringify(matchings));
        }

        function resetMatches(questionId) {
            const leftColumn = document.querySelector(`#${questionId} .matching-column-left`);
            const dropZones = document.querySelectorAll(`#${questionId} .drop-zone`);

            dropZones.forEach(zone => {
                const item = zone.querySelector('.matching-item');
                if (item) {
                    leftColumn.appendChild(item);
                }
                zone.innerHTML = '';
            });

            updateMatchingAnswers(questionId);
        }

        $('.matching-container').each(function() {
            const questionId = $(this).closest('.question').attr('id');
            $('<button>', {
                text: 'Reset Matches',
                class: 'reset-matches',
                click: function() { resetMatches(questionId); }
            }).insertAfter(this);
        });

        $('#quizForm').submit(function(e) {
            updateQuestionOrder();
            console.log($('#questionOrder').val());
            return true;
        });
    </script>
</head>
<body>
<h1><%=quiz.getName()%></h1>

<form id="quizForm" action="SubmitQuizServlet" method="post">
    <input type="hidden" name="quizId" value="<%=quizId%>">
    <input type="hidden" name="questionOrder" id="questionOrder" value="">

    <% for (int i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        int originalIndex = originalIndices.get(i);
        int type = question.getType();
    %>
    <div id="question<%=i%>" class="question" data-original-index="<%=originalIndex%>" data-question-type="<%=type%>" <%=onePage != 1 ? "style='display:none;'" : ""%>>
        <h3>Question <%=i+1%></h3>

        <% if (type == QuestionTypeConstants.QUESTION_TEXTBOX) { %>
        <p><%=((QuestionTextbox)question).getQuestion()%></p>
        <input type="text" name="answer[<%=originalIndex%>]" id="question<%=originalIndex%>-answer">

        <% } else if (type == QuestionTypeConstants.QUESTION_FILL_BLANK) { %>
        <p>
            <%=((QuestionFillBlank)question).getTextBefore()%>
            <input type="text" name="answer[<%=originalIndex%>]" id="question<%=originalIndex%>-answer">
            <%=((QuestionFillBlank)question).getTextAfter()%>
        </p>

        <% } else if (type == QuestionTypeConstants.QUESTION_MULTIPLE_CHOICE) { %>
        <p><%=((QuestionMultipleChoice)question).getQuestion()%></p>
        <%ArrayList<String> mcAnswers = ((QuestionMultipleChoice)question).getAnswerList();%>
        <% for(int j = 0; j < mcAnswers.size(); j++) { %>
        <div class="option-group">
            <input type="radio" name="answer[<%=originalIndex%>]" id="question<%=originalIndex%>-answer<%=j%>" value="<%=j%>">
            <label for="question<%=originalIndex%>-answer<%=j%>"><%=mcAnswers.get(j)%></label>
        </div>
        <% } %>

        <% } else if (type == QuestionTypeConstants.QUESTION_PICTURE) { %>
        <p><%=((QuestionPicture)question).getQuestion()%></p>
        <img src="<%=((QuestionPicture)question).getImageURL()%>" alt="Question Image">
        <input type="text" name="answer[<%=originalIndex%>]" id="question<%=originalIndex%>-answer" placeholder="Answer">

        <% } else if (type == QuestionTypeConstants.QUESTION_MULTIANSWER) { %>
        <p><%=((QuestionMultiAnswer)question).getQuestion()%></p>
        <% for(int j = 0; j < ((QuestionMultiAnswer)question).getAnswerList().size(); j++) { %>
        <input type="text" name="answer[<%=originalIndex%>][<%=j%>]" id="question<%=originalIndex%>-answer<%=j%>" placeholder="Answer <%=j+1%>">
        <% } %>

        <% } else if (type == QuestionTypeConstants.QUESTION_CHECKBOX) { %>
        <%ArrayList<String> cbAnswers = ((QuestionCheckbox)question).getAnswerList();%>
        <% for(int j = 0; j < cbAnswers.size(); j++) { %>
        <div class="option-group">
            <input type="checkbox" name="answer[<%=originalIndex%>][<%=j%>]" id="question<%=originalIndex%>-answer<%=j%>" value="<%=j%>">
            <label for="question<%=originalIndex%>-answer<%=j%>"><%=cbAnswers.get(j)%></label>
        </div>
        <% } %>

        <% } else if (type == QuestionTypeConstants.QUESTION_MATCHING) { %>
        <div class="matching-container">
            <div class="matching-column matching-column-left">
                <% ArrayList<String> leftItems = ((QuestionMatching)question).getWords(); %>
                <% for(int j = 0; j < leftItems.size(); j++) { %>
                <div class="matching-item" draggable="true" data-item-id="left<%=j%>"><%=leftItems.get(j)%></div>
                <% } %>
            </div>
            <div class="matching-column matching-column-right">
                <% ArrayList<String> rightItems = ((QuestionMatching)question).getMatchingWords(); %>
                <% for(int j = 0; j < rightItems.size(); j++) { %>
                <div class="matching-slot" data-slot-id="right<%=j%>">
                    <div class="slot-text"><%=rightItems.get(j)%></div>
                    <div class="drop-zone"></div>
                </div>
                <% } %>
            </div>
        </div>
        <input type="hidden" name="answer[<%=originalIndex%>]" id="question<%=originalIndex%>-answer">
        <% } %>

        <% if (immediate == 1 && onePage == 1) { %>
        <button type="button" onclick="checkAnswer(<%=question.getQuestionID()%>,  <%=originalIndex%>)">Check Answer</button>
        <div id="result<%=question.getQuestionID()%>" class="feedback"></div>
        <% } %>

    </div>
    <% } %>

    <% if (onePage != 1) { %>
        <% if (immediate == 1) { %>
            <button type="button" id="nextBtn" onclick="checkAndNextQuestion()">Next</button>
        <% } else { %>
            <button type="button" id="prevBtn" onclick="prevQuestion()">Previous</button>
            <button type="button" id="nextBtn" onclick="nextQuestion()">Next</button>
        <% } %>
    <% } %>

    <input type="submit" value="Submit Quiz">
</form>
</body>
</html>
<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>