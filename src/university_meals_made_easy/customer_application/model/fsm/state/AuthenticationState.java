package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class AuthenticationState extends StateAdapter {
  public AuthenticationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.AUTHENTICATION;
  }
  @Override
  public boolean login(String username) {
    dataManager.login(username);
    context.changeState(State.AUTHENTICATION);
    return true;
  }
  @Override
  public boolean logout() {
    return false;
  }
  @Override
  public boolean changeToMainMenu() {
    return false;
  }
  @Override
  public boolean changeToMealOrdering() {
    return false;
  }
  @Override
  public boolean changeToMenuConsultation() {
    return false;
  }
  @Override
  public boolean changeToReviewal() {
    return false;
  }
  @Override
  public boolean changeToTicketsConsultation() {
    return false;
  }
  @Override
  public boolean changeToTransactionHistory() {
    return false;
  }
}
