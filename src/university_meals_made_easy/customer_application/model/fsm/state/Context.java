package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.IState;
import university_meals_made_easy.customer_application.model.fsm.State;

public class Context {
  private final DataManager dataManager;
  private IState state;

  public Context(DataManager dataManager) throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    state = new AuthenticationState(dataManager, this);
  }
  void changeState(State state) throws NullPointerException {
    if (state == null)
      throw new NullPointerException("state cannot be null");
    this.state = state.getState(dataManager, this);
  }
  public boolean login(String username) {
    return state.login(username);
  }
  public boolean logout() {
    return state.logout();
  }
  public boolean changeToMainMenu() {
    return state.changeToMainMenu();
  }
  public boolean changeToMealOrdering() {
    return state.changeToMealOrdering();
  }
  public boolean changeToMenuConsultation() {
    return state.changeToMenuConsultation();
  }
  public boolean changeToReviewal() {
    return state.changeToReviewal();
  }
  public boolean changeToTicketsConsultation() {
    return state.changeToTicketsConsultation();
  }
  public boolean changeToTransactionHistory() {
    return state.changeToTransactionHistory();
  }
}
