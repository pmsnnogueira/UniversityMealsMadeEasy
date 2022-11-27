package university_meals_made_easy.customer_application.model.fsm;

public interface IState {
  State getState();
  boolean login(String username);
  boolean logout();
  boolean changeToMainMenu();
  boolean changeToMealOrdering();
  boolean changeToMenuConsultation();
  boolean changeToReviewal();
  boolean changeToTicketsConsultation();
  boolean changeToTransactionHistory();
}
