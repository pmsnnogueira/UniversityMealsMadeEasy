package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.IState;
import university_meals_made_easy.back_office.model.fsm.State;

public class Context {
  private final DataManager dataManager;
  private IState state;

  public Context(DataManager dataManager) throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    this.state = new MainMenuState(dataManager, this);
  }
  void changeState(State state) throws NullPointerException {
    if (state == null)
      throw new NullPointerException("state cannot be null");
    this.state = state.getState(dataManager, this);
  }
}
