package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.IState;
import university_meals_made_easy.customer_application.model.fsm.State;

public class Context {
  private final DataManager dataManager;
  private IState state;

  public Context(DataManager dataManager) throws NullPointerException {
    this.dataManager = dataManager;
    state = new AuthenticationState(dataManager, this);
  }
  void changeState(State state) throws NullPointerException {
    if (state == null)
      throw new NullPointerException("state cannot be null");
    this.state = state.getState(dataManager, this);
  }
}
