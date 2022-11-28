package university_meals_made_easy.customer_application.model;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.customer_application.model.fsm.state.Context;

public class ModelManager {
  private final DataManager dataManager;
  private final Context context;

  public ModelManager(DataManager dataManager)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    context = new Context(dataManager);
  }
  public State getState() {
    return context.getState();
  }
  public boolean login(String username) {
    return context.login(username);
  }
  public boolean logout() {
    return context.logout();
  }
  public boolean changeToMainMenu() {
    return context.changeToMainMenu();
  }
  public boolean changeToMealOrdering() {
    return context.changeToMealOrdering();
  }
  public boolean changeToMenuConsultation() {
    return context.changeToMenuConsultation();
  }
  public boolean changeToReviewal() {
    return context.changeToReviewal();
  }
  public boolean changeToTicketsConsultation() {
    return context.changeToTicketsConsultation();
  }
  public boolean changeToTransactionHistory() {
    return context.changeToTransactionHistory();
  }
}
