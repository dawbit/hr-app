import 'package:flutter_test/flutter_test.dart';
import 'package:mobile/models/current_question_controller.dart';

void main() {

  test('Given next question call When user is at question 2, solved question 3 and didnt solve question 1'
      'And when is on question 2, solved question 1 and 3',() async {
    // Version one
    CurrentQuestionController currentQuestionController = CurrentQuestionController(3, true, 2);
    currentQuestionController.listOfAnsweredQuestions = [3];

    // ACT
    currentQuestionController.onAnswerSuccess();

    // ASSERT
    expect(currentQuestionController.currentQuestion, 1);


    // Version two
    currentQuestionController = CurrentQuestionController(3, true, 2);
    currentQuestionController.listOfAnsweredQuestions = [1, 3];

    // ACT
    currentQuestionController.onAnswerSuccess();

    // ASSERT
    expect(currentQuestionController.currentQuestion, 1);
  });
}