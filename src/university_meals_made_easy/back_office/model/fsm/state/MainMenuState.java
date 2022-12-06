package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;

/**
 * MainMenuState class
 * this state is used only for information purposes
 */
public class MainMenuState extends StateAdapter {
  /**
   * MainMenuState Constructor
   * @param dataManager
   * @param context
   */
  public MainMenuState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return state
   */
  @Override
  public State getState() {
    return State.MAIN_MENU;
  }
}
