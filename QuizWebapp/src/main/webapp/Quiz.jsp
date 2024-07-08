<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="DBpackage.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="DBpackage.Questions.*" %>

<%
    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    int quizId = Integer.parseInt(request.getParameter("quizId"));
    System.out.println(quizId);
    Quiz quiz = dbAccess.getQuizInfo(quizId);
    ArrayList<Question> questions = dbAccess.getQuizQuestions(quizId);
    System.out.println(questions.size());
    if(quiz.getRandomQuestion() == 1) Collections.shuffle(questions);
    int questionIndex = 0;
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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/QuizPage.css">

    <script>
        let currentQuestion = 0;
        const totalQuestions = <%=questions.size()%>;

        function showQuestion(index) {
            $('.question').hide();
            $('#question' + index).show();
            updateNavButtons();
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
            $('#prevBtn').prop('disabled', currentQuestion === 0);
            $('#nextBtn').prop('disabled', currentQuestion === totalQuestions - 1);
        }

        function checkAnswer(questionId) {
            // AJAX call to check answer
            // Show result if immediate == 1
        }

        $(document).ready(function() {
            <% if (onePage != 1) { %>
            showQuestion(0);
            <% } %>

            $(".matching-column").sortable({
                connectWith: ".matching-column",
                update: function(event, ui) {
                    updateMatchingAnswers($(this).closest('.question').attr('id'));
                }
            }).disableSelection();
        });

        function updateMatchingAnswers(questionId) {
            let leftColumn = $(`#${questionId} .matching-column-left`).sortable('toArray');
            let rightColumn = $(`#${questionId} .matching-column-right`).sortable('toArray');
            let matchings = [];
            for (let i = 0; i < leftColumn.length; i++) {
                matchings.push({left: leftColumn[i], right: rightColumn[i]});
            }
            $(`#${questionId}-answer`).val(JSON.stringify(matchings));
        }
    </script>
</head>
<body>
<h1><%=quiz.getName()%></h1>

<form id="quizForm" action="submitQuiz" method="post">
    <% for (int i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        int type = question.getType();
    %>
    <div id="question<%=i%>" class="question" <%=onePage != 1 ? "style='display:none;'" : ""%>>
        <h3>Question <%=i+1%></h3>
        <% if (type == QuestionTypeConstants.QUESTION_TEXTBOX) { %>
        <p><%=((QuestionTextbox)question).getQuestion()%></p>
        <input type="text" name="answer[<%=i%>]" id="question<%=i%>-answer">
        <% } else if (type == QuestionTypeConstants.QUESTION_FILL_BLANK) { %>
        <p>
            <%=((QuestionFillBlank)question).getTextBefore()%>
            <input type="text" name="answer[<%=i%>]" id="question<%=i%>-answer">
            <%=((QuestionFillBlank)question).getTextAfter()%>
        </p>
        <% } else if (type == QuestionTypeConstants.QUESTION_MULTIPLE_CHOICE) { %>
        <p><%=((QuestionMultipleChoice)question).getQuestion()%></p>
        <%ArrayList<String> mcAnswers = ((QuestionMultipleChoice)question).getAnswerList();%>
        <% for(int j = 0; j < mcAnswers.size(); j++) { %>
        <div class="option-group">
            <input type="radio" name="answer[<%=i%>]" id="question<%=i%>-answer<%=j%>" value="<%=j%>">
            <label for="question<%=i%>-answer<%=j%>"><%=mcAnswers.get(j)%></label>
        </div>
        <% } %>
        <% } else if (type == QuestionTypeConstants.QUESTION_PICTURE) { %>
        <p><%=((QuestionPicture)question).getQuestion()%></p>
        <img src="<%=((QuestionPicture)question).getImageURL()%>" alt="Question Image">
        <input type="text" name="answer[<%=i%>]" id="question<%=i%>-answer" placeholder="Answer">
        <% } else if (type == QuestionTypeConstants.QUESTION_MULTIANSWER) { %>
        <p><%=((QuestionMultiAnswer)question).getQuestion()%></p>
        <% for(int j = 0; j < ((QuestionMultiAnswer)question).getAnswerList().size(); j++) { %>
        <input type="text" name="answer[<%=i%>][<%=j%>]" id="question<%=i%>-answer<%=j%>" placeholder="Answer <%=j+1%>">
        <% } %>
        <% } else if (type == QuestionTypeConstants.QUESTION_CHECKBOX) { %>
        <%ArrayList<String> cbAnswers = ((QuestionCheckbox)question).getAnswerList();%>
        <% for(int j = 0; j < cbAnswers.size(); j++) { %>
        <div class="option-group">
            <input type="checkbox" name="answer[<%=i%>][]" id="question<%=i%>-answer<%=j%>" value="<%=j%>">
            <label for="question<%=i%>-answer<%=j%>"><%=cbAnswers.get(j)%></label>
        </div>
        <% } %>
        <% } else if (type == QuestionTypeConstants.QUESTION_MATCHING) { %>
        <div class="matching-container">
            <div class="matching-column matching-column-left">
                <% ArrayList<String> leftItems = ((QuestionMatching)question).getWords(); %>
                <% for(int j = 0; j < leftItems.size(); j++) { %>
                <div class="matching-item" id="left<%=j%>"><%=leftItems.get(j)%></div>
                <% } %>
            </div>
            <div class="matching-column matching-column-right">
                <% ArrayList<String> rightItems = ((QuestionMatching)question).getMatchingWords(); %>
                <% for(int j = 0; j < rightItems.size(); j++) { %>
                <div class="matching-item" id="right<%=j%>"><%=rightItems.get(j)%></div>
                <% } %>
            </div>
        </div>
        <input type="hidden" name="answer[<%=i%>]" id="question<%=i%>-answer">
        <% } %>

        <% if (immediate == 1 && onePage == 1) { %>
        <button type="button" onclick="checkAnswer(<%=question.getQuestionID()%>)">Check Answer</button>
        <div id="result<%=question.getQuestionID()%>" class="feedback"></div>
        <% } %>
    </div>
    <% } %>

    <% if (onePage != 1) { %>
    <button type="button" id="prevBtn" onclick="prevQuestion()">Previous</button>
    <button type="button" id="nextBtn" onclick="nextQuestion()">Next</button>
    <% } %>

    <input type="submit" value="Submit Quiz">
</form>
</body>
</html>