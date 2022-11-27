package university_meals_made_easy.back_office.model.fsm;

public interface IState {
  State getState();
  void changeToConfiguration();
  void changeToMainMenu();
  void changeToMealInsertion();
  void changeToOrderedMealsConsultation();
  void changeToReviewsConsultation();
  void changeToTicketValidation();
}
