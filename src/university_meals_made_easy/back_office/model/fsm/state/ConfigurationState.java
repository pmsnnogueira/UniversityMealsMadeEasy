package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.data.result.TimeSlotCapacityConfiguringResult;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.database.tables.TimeSlot;

public class ConfigurationState extends StateAdapter {
  public ConfigurationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.CONFIGURATION;
  }
  @Override
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot, int capacity) {
    return dataManager.configureCapacity(slot, capacity);
  }
}
