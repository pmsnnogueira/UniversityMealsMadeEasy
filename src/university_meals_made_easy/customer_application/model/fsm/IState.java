package university_meals_made_easy.customer_application.model.fsm;

/**
 * IState
 * Interface for that declares all possible states
 */
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
