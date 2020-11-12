class CurrentQuestionController {

  int numberOfAllQuestions;
  int currentQuestion;
  List<int> listOfAnsweredQuestions =[];
  bool isBackPossible;

  CurrentQuestionController(this.numberOfAllQuestions, this.isBackPossible) {
    this.currentQuestion = 1;
  }

  void onAnswerSuccess() {
    _addQuestionToAnswered();
    _setNewCurrentQuestion();
  }

  void _addQuestionToAnswered() {
    if(!listOfAnsweredQuestions.contains(currentQuestion)) {
      listOfAnsweredQuestions.add(currentQuestion);
    }
  }

  void _setNewCurrentQuestion() {
    if(!isBackPossible) {
      _setNewCurrentQuestionForBackImpossible();
    } else {
      _setNewCurrentQuestionForBackPossible();
    }
  }

  void _setNewCurrentQuestionForBackPossible() {
    for(int i=currentQuestion+1; i<= numberOfAllQuestions; i++) {
      if(!listOfAnsweredQuestions.contains(i)) {
        currentQuestion = i;
        return;
      }
    }
    for(int i=1; i< currentQuestion; i++) {
      if(!listOfAnsweredQuestions.contains(i)) {
        currentQuestion = i;
        return;
      }
    }
    currentQuestion=1;
  }

  void _setNewCurrentQuestionForBackImpossible() {
    listOfAnsweredQuestions.add(currentQuestion);
    currentQuestion +=1;
  }

  bool questionIsAnswered(int questionNumber) {
    return listOfAnsweredQuestions.contains(questionNumber);
  }

  bool noMoreQuestions() {
    return numberOfAllQuestions < currentQuestion;
  }
}