<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create Quiz</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <link rel="stylesheet" type="text/css" href="CreateQuiz.css?v=3.0">
</head>
<body>
<h1>Create Quiz</h1>
<form id="quizForm" action="CreateQuizServlet" method="post">
  <input type="text" id="quizTitle" placeholder="Quiz Title" required><br>
  <textarea id="quizDescription" placeholder="Quiz Description" required></textarea><br>
  <div class="checkbox-container">
    <input type="checkbox" id="randomCheckbox" name="randomCheckbox">
    <label for="randomCheckbox">Random Order of Questions</label>
    <input type="checkbox" id="onePageCheckbox" name="onePageCheckbox">
    <label for="onePageCheckbox">Display on One Page</label>
    <input type="checkbox" id="immediateCheckbox" name="immediateCheckbox">
    <label for="immediateCheckbox">Immediate Answers to Questions</label>
  </div>
  <div id="questions"></div>

  <button type="button" id="addQuestionBtn">Add Question</button>

  <div id="questionContainer" style="display:none;">
    <select id="questionType">
      <option value="">Select Question Type</option>
      <option value="questionResponse">Question Response</option>
      <option value="fillBlank">Fill in the Blank</option>
      <option value="multipleChoice">Multiple Choice</option>
      <option value="pictureResponse">Picture Response</option>
    </select>
  </div>

  <button type="submit" id="createQuizBtn" style="display:none;">Create Quiz</button>
</form>

<script>
  $(document).ready(function() {
    let questionId = 0;
    let questionCount = 0;

    $('#addQuestionBtn').click(function() {
      $('#questionContainer').toggle();
      $('#questionType').val(''); // Reset selection
    });

    $('#questionType').change(function() {
      let type = $(this).val();
      if (type === 'questionResponse') {
        addQuestionResponseQuestion();
      }
      if (type === 'fillBlank') {
        addFillBlankQuestion();
      }
      if (type === 'multipleChoice') {
        addMultipleChoiceQuestion();
      }
      if (type === 'pictureResponse') {
        addPictureResponseQuestion();
      }
      $('#questionContainer').hide();
    });

    $('#onePageCheckbox').change(function() {
      if ($(this).is(':checked')) {
        $('#immediateCheckbox').prop('checked', false);
        $('#immediateCheckbox').prop('disabled', true);
      } else {
        $('#immediateCheckbox').prop('disabled', false);
      }
    });

    function showCreateQuizButton() {
      if (questionCount > 0) {
        $('#createQuizBtn').show();
      }
    }

    function afterAddingQuestion() {
      questionCount++;
      showCreateQuizButton();
    }

    function addQuestionResponseQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}">
          <input type="text" placeholder="Question" required>
          <div class="answers">
            <input type="text" placeholder="Answer" required>
          </div>
          <button type="button" class="saveBtn">Save</button>
          <button type="button" class="editBtn" style="display:none;">Edit</button>
          <button type="button" class="deleteBtn">Delete</button>
        </div>
      `;
      $('#questions').append(questionHtml);

      $(`#question${questionId} .saveBtn`).click(function() {
        $(this).closest('.question').find('input').prop('disabled', true);
        $(this).hide();
        $(this).siblings('.editBtn').show();
      });

      $(`#question${questionId} .editBtn`).click(function() {
        $(this).closest('.question').find('input').prop('disabled', false);
        $(this).hide();
        $(this).siblings('.saveBtn').show();
      });

      $(`#question${questionId} .deleteBtn`).click(function() {
        $(this).closest('.question').remove();
        questionCount--;
        if (questionCount === 0) {
          $('#createQuizBtn').hide();
        }
      });

      afterAddingQuestion();
    }

    function addFillBlankQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}">
          <input type="text" placeholder="Before">
          <div class="answers">
            <input type="text" placeholder="Answer" required>
          </div>
          <input type="text" placeholder="After">
          <button type="button" class="saveBtn">Save</button>
          <button type="button" class="editBtn" style="display:none;">Edit</button>
          <button type="button" class="deleteBtn">Delete</button>
        </div>
      `;
      $('#questions').append(questionHtml);

      $(`#question${questionId} .saveBtn`).click(function() {
        $(this).closest('.question').find('input').prop('disabled', true);
        $(this).hide();
        $(this).siblings('.editBtn').show();
      });

      $(`#question${questionId} .editBtn`).click(function() {
        $(this).closest('.question').find('input').prop('disabled', false);
        $(this).hide();
        $(this).siblings('.saveBtn').show();
      });

      $(`#question${questionId} .deleteBtn`).click(function() {
        $(this).closest('.question').remove();
        questionCount--;
        if (questionCount === 0) {
          $('#createQuizBtn').hide();
        }
      });

      afterAddingQuestion();
    }

    function addMultipleChoiceQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}">
          <input type="text" placeholder="Question" required>
          <select class="answerCount">
            <option value="2">2 Answers</option>
            <option value="3">3 Answers</option>
            <option value="4">4 Answers</option>
            <option value="5">5 Answers</option>
            <option value="6">6 Answers</option>
          </select>
          <div class="answers"></div>
          <button type="button" class="saveBtn">Save</button>
          <button type="button" class="editBtn" style="display:none;">Edit</button>
          <button type="button" class="deleteBtn">Delete</button>
        </div>
      `;
      $('#questions').append(questionHtml);

      $(`#question${questionId} .answerCount`).change(function() {
        let answerCount = $(this).val();
        let answersHtml = '';
        for (let i = 0; i < answerCount; i++) {
          answersHtml += `
            <div>
              <input type="text" placeholder="Answer ${i + 1}" required>
              <input type="radio" name="correct${questionId}" value="${i}"> Correct
            </div>
          `;
        }
        $(`#question${questionId} .answers`).html(answersHtml);
      });

      $(`#question${questionId} .saveBtn`).click(function() {
        $(this).closest('.question').find('input, select').prop('disabled', true);
        $(this).hide();
        $(this).siblings('.editBtn').show();
      });

      $(`#question${questionId} .editBtn`).click(function() {
        $(this).closest('.question').find('input, select').prop('disabled', false);
        $(this).hide();
        $(this).siblings('.saveBtn').show();
      });

      $(`#question${questionId} .deleteBtn`).click(function() {
        $(this).closest('.question').remove();
        questionCount--;
        if (questionCount === 0) {
          $('#createQuizBtn').hide();
        }
      });

      afterAddingQuestion();
    }

    function addPictureResponseQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}">
          <input type="file" accept="image/*">
          <div class="answers">
            <input type="text" placeholder="Answer" required>
          </div>
          <button type="button" class="saveBtn">Save</button>
          <button type="button" class="editBtn" style="display:none;">Edit</button>
          <button type="button" class="deleteBtn">Delete</button>
        </div>
      `;
      $('#questions').append(questionHtml);

      $(`#question${questionId} .saveBtn`).click(function() {
        $(this).closest('.question').find('input, select').prop('disabled', true);
        $(this).hide();
        $(this).siblings('.editBtn').show();
      });

      $(`#question${questionId} .editBtn`).click(function() {
        $(this).closest('.question').find('input, select').prop('disabled', false);
        $(this).hide();
        $(this).siblings('.saveBtn').show();
      });

      $(`#question${questionId} .deleteBtn`).click(function() {
        $(this).closest('.question').remove();
        questionCount--;
        if (questionCount === 0) {
          $('#createQuizBtn').hide();
        }
      });

      afterAddingQuestion();
    }

    function serializeQuiz() {
      let quiz = {
        title: $('#quizTitle').val(),
        description: $('#quizDescription').val(),
        randomOrder: $('#randomCheckbox').is(':checked'),
        onePage: $('#onePageCheckbox').is(':checked'),
        immediateCorrection: $('#immediateCheckbox').is(':checked'),
        questions: []
      };

      $('.question').each(function() {
        let $question = $(this);
        let type = $question.data('type');
        let questionData = {
          type: type,
          question: $question.find('input[placeholder="Question"]').val()
        };

        switch(type) {
          case 'questionResponse':
          case 'fillBlank':
          case 'pictureResponse':
            questionData.answer = $question.find('.answers input').val();
            break;
          case 'multipleChoice':
            questionData.answers = [];
            $question.find('.answers input[type="text"]').each(function(index) {
              questionData.answers.push({
                text: $(this).val(),
                isCorrect: $question.find(`input[name="correct${$question.attr('id').replace('question', '')}"][value="${index}"]`).is(':checked')
              });
            });
            break;
        }

        if (type === 'fillBlank') {
          questionData.before = $question.find('input[placeholder="Before"]').val();
          questionData.after = $question.find('input[placeholder="After"]').val();
        }

        if (type === 'pictureResponse') {
          // Note: File upload will require additional handling
          questionData.image = $question.find('input[type="file"]').val();
        }

        quiz.questions.push(questionData);
      });

      return JSON.stringify(quiz);
    }

    $('#quizForm').submit(function(e) {
      e.preventDefault();
      let quizData = serializeQuiz();

      $.ajax({
        url: 'CreateQuizServlet',
        type: 'POST',
        contentType: 'application/json',
        data: quizData,
        success: function(response) {
          alert('Quiz created successfully!');
          // Optionally, redirect or clear the form
        },
        error: function(xhr, status, error) {
          alert('Error creating quiz: ' + error);
        }
      });
    });

  });
</script>
</body>
</html>
