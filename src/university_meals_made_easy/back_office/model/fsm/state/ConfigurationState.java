package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.data.result.TimeSlotCapacityConfiguringResult;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.database.tables.TimeSlot;

/**
 * ConfigurationState class
 * In this state it will be possible to change the capacity of the time slots
 */
public class ConfigurationState extends StateAdapter {
  /**
   * ConfigurationState Constructor
   * @param dataManager
   * @param context
   */
  public ConfigurationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return state
   */
  @Override
  public State getState() {
    return State.CONFIGURATION;
  }
  @Override
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot, int capacity) {
    return dataManager.configureCapacity(slot, capacity);
  }
}
