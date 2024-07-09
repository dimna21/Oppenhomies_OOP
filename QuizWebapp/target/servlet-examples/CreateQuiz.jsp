<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create Quiz</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <link rel="stylesheet" type="text/css" href="CreateQuiz.css?v=2.2">
</head>
<body>
<h1>Create Quiz</h1>
<form id="quizForm" action="CreateQuizServlet" method="post">
  <input type="text" id="quizTitle" placeholder="Quiz Title" required><br>
  <textarea id="quizDescription" placeholder="Quiz Description" required></textarea><br>
  <div class="checkbox-container">
    <label>
      <span>Random Order of Questions</span>
      <input type="checkbox" id="randomCheckbox" name="randomCheckbox">
    </label>
    <label>
      <span>Display on One Page</span>
      <input type="checkbox" id="onePageCheckbox" name="onePageCheckbox">
    </label>
    <label>
      <span>Immediate Answers to Questions</span>
      <input type="checkbox" id="immediateCheckbox" name="immediateCheckbox">
    </label>
    <label>
      <span>Practice Mode</span>
      <input type="checkbox" id="practiceCheckbox" name="practiceCheckbox">
    </label>
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
      <option value="multiAnswer">Multi-Answer Questions</option>
      <option value="multiChoiceMultiAnswer">Multiple Choice-Multiple Answers</option>
      <option value="matching">Matching Question</option>
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
      if(type == 'multiAnswer') {
        addMultiAnswerQuestion();
      }
      if(type == 'multiChoiceMultiAnswer') {
        addMultiChoiceMultiAnswerQuestion();
      }
      if(type == 'matching') {
        addMatchingQuestion();
      }
      $('#questionContainer').hide();
    });

    $('#onePageCheckbox').change(function() {
      if ($(this).is(':checked')) {
        $('#immediateCheckbox').prop('checked', false);
        //$('#immediateCheckbox').prop('disabled', true);
      }
    });

    $('#immediateCheckbox').change(function (){
      if($(this).is(':checked')) {
        $('#onePageCheckbox').prop('checked',false);
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
        <div class="question" id="question${questionId}" data-type="questionResponse">
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

      setupQuestionButtons(questionId);

      afterAddingQuestion();
    }

    function addFillBlankQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}" data-type="fillBlank">
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

      setupQuestionButtons(questionId);

      afterAddingQuestion();
    }

    function addMultipleChoiceQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}" data-type="multipleChoice">
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

      setupQuestionButtons(questionId);

      afterAddingQuestion();
    }

    function addPictureResponseQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}" data-type="pictureResponse">
          <input type="text" placeholder="Question" required>
          <input type="text" placeholder="Image URL" required>
          <div class="answers">
            <input type="text" placeholder="Answer" required>
          </div>
          <button type="button" class="saveBtn">Save</button>
          <button type="button" class="editBtn" style="display:none;">Edit</button>
          <button type="button" class="deleteBtn">Delete</button>
        </div>
      `;
      $('#questions').append(questionHtml);

      setupQuestionButtons(questionId);

      afterAddingQuestion();
    }

    function addMultiAnswerQuestion() {
      questionId++;
      let questionHtml = `
    <div class="question" id="question${questionId}" data-type="multiAnswer">
      <input type="text" placeholder="Question" required>
      <input class="answerCount" type="number" min="2" placeholder="Number of answers" required>
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
                </div>
              `;
          }
        $(`#question${questionId} .answers`).html(answersHtml);
      });

      setupQuestionButtons(questionId);

      afterAddingQuestion();
    }

    function addMultiChoiceMultiAnswerQuestion() {
      questionId++;
      let questionHtml = `
        <div class="question" id="question${questionId}" data-type="multiChoiceMultiAnswer">
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
              <input type="checkbox" name="correct${questionId}" value="${i}"> Correct
            </div>
          `;
        }
        $(`#question${questionId} .answers`).html(answersHtml);
      });

      setupQuestionButtons(questionId);

      afterAddingQuestion();
    }

    function addMatchingQuestion() {
        questionId++;
        let questionHtml = `
          <div class="question" id="question${questionId}" data-type="matching">
              <input type="text" placeholder="Question" required>
              <input class="answerCount" type="number" min="2" placeholder="Number of answers" required>
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
                    <input type="text" placeholder="Match ${i + 1}-a" required>
                    <input type="text" placeholder="Match ${i + 1}-b" required>
                  </div>
                `;
          }
          $(`#question${questionId} .answers`).html(answersHtml);
        });

        setupQuestionButtons(questionId);

        afterAddingQuestion();
    }

    function serializeQuiz() {
      let quiz = {
        title: $('#quizTitle').val(),
        description: $('#quizDescription').val(),
        randomOrder: $('#randomCheckbox').is(':checked'),
        onePage: $('#onePageCheckbox').is(':checked'),
        immediateCorrection: $('#immediateCheckbox').is(':checked'),
        practice: $('#practiceCheckbox').is(':checked'),
        questions: []
      };

      $('.question').each(function() {
        let $question = $(this);
        let type = $question.data('type');
        let questionData = { type: type };

        switch(type) {
          case 'questionResponse':
          case 'multipleChoice':
          case 'pictureResponse':
          case 'multiAnswer':
          case 'multiChoiceMultiAnswer':
          case 'matching':
            questionData.question = $question.find('input[placeholder="Question"]').val();
            break;
          case 'fillBlank':
            questionData.before = $question.find('input[placeholder="Before"]').val();
            questionData.after = $question.find('input[placeholder="After"]').val();
            break;
        }

        switch(type) {
          case 'questionResponse':
          case 'pictureResponse':
            questionData.answer = $question.find('input[placeholder="Answer"]').val();
            break;
          case 'fillBlank':
            questionData.answer = $question.find('input[placeholder="Answer"]').val();
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

          case 'multiAnswer':
            questionData.answers = [];
            $question.find('.answers input[type="text"]').each(function() {
              questionData.answers.push($(this).val());
            });
            break;

          case 'multiChoiceMultiAnswer':
            questionData.answers = [];
            $question.find('.answers input[type="text"]').each(function(index) {
              questionData.answers.push({
                text: $(this).val(),
                isCorrect: $question.find(`input[name="correct${$question.attr('id').replace('question', '')}"][value="${index}"]`).is(':checked')
              });
            });
            break;

          case 'matching':
            questionData.pairs = [];
            $question.find('.answers > div').each(function() {
              let $inputs = $(this).find('input[type="text"]');
              questionData.pairs.push({
                a: $inputs.eq(0).val(),
                b: $inputs.eq(1).val()
              });
            });
            break;
        }

        if (type === 'pictureResponse') {
          questionData.image = $question.find('input[placeholder="Image URL"]').val();
        }

        quiz.questions.push(questionData);
      });

      return quiz;
    }

    $('#quizForm').submit(function(e) {
      e.preventDefault();
      let quizData = serializeQuiz();

      $.ajax({
        url: 'CreateQuizServlet',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(quizData),
        success: function(response) {
          // Redirect to UserHomePage.jsp upon successful creation
          window.location.href = 'UserHomePage.jsp';
        },
        error: function(xhr, status, error) {
          alert('Error creating quiz: ' + error);
        }
      });
    });


    function setupQuestionButtons(questionId) {
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
    }

  });
</script>
</body>
</html>
<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>