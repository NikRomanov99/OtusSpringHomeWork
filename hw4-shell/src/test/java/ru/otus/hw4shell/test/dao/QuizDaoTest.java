package ru.otus.hw4shell.test.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw4shell.dao.QuizDao;
import ru.otus.hw4shell.model.Answer;
import ru.otus.hw4shell.model.Question;
import ru.otus.hw4shell.model.Quiz;

@DisplayName("QuizDao test")
@SpringBootTest
public class QuizDaoTest {
  private static final String TEST_QUESTION_FOR_QUIZ = "Did your spring test work correctly ?";
  @Autowired
  private QuizDao quizDao;

  @DisplayName("Проверка QuizDao.readQuiz с настроенным тестовым бином")
  @Test
  public void testDao() {
    Quiz testQuiz = quizDao.readQuiz();
    Question question = testQuiz.getQuestions().get(0);
    assertThat(Objects.equals(prepareQuestionForEqualsTest(), question)).isTrue();;
  }

  private Question prepareQuestionForEqualsTest(){
    Question question = new Question(TEST_QUESTION_FOR_QUIZ);
    List<Answer> answerList = new ArrayList<>();

    Answer firstAnswer = new Answer("Yes");
    firstAnswer.setRight(true);

    Answer secondAnswer = new Answer("No");
    Answer thirdAnswer = new Answer("I don't know");

    answerList.add(firstAnswer);
    answerList.add(secondAnswer);
    answerList.add(thirdAnswer);
    question.setAnswers(answerList);

    return question;
  }
}
