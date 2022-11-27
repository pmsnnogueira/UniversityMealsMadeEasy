package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.IState;
import university_meals_made_easy.customer_application.model.fsm.State;

public abstract class StateAdapter implements IState {
  protected final DataManager dataManager;
  protected final Context context;

  public StateAdapter(DataManager dataManager, Context context)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    if (context == null)
      throw new NullPointerException("context cannot be null");
    this.dataManager = dataManager;
    this.context = context;
  }
  @Override
  public boolean login(String username) {
    return false;
  }
  @Override
  public boolean logout() {
    context.changeState(State.AUTHENTICATION);
    return true;
  }
  @Override
  public boolean changeToMainMenu() {
    context.changeState(State.MAIN_MENU);
    return true;
  }
  @Override
  public boolean changeToMealOrdering() {
    context.changeState(State.MEAL_ORDERING);
    return true;
  }
  @Override
  public boolean changeToMenuConsultation() {
    context.changeState(State.MENU_CONSULTATION);
    return true;
  }
  @Override
  public boolean changeToReviewal() {
    context.changeState(State.REVIEWAL);
    return true;
  }
  @Override
  public boolean changeToTicketsConsultation() {
    context.changeState(State.TICKETS_CONSULTATION);
    return true;
  }
  @Override
  public boolean changeToTransactionHistory() {
    context.changeState(State.TRANSACTION_HISTORY);
    return true;
  }
}
