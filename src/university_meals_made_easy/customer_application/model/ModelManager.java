package university_meals_made_easy.customer_application.model;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.customer_application.model.fsm.state.Context;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Class to make the connection between the View and the data model
 * @version 1.0
 */
public class ModelManager {
  public static final String PROP_STATE = "state";
  private final DataManager dataManager;
  private final Context context;
  private final PropertyChangeSupport pcs;

  /**
   * Constructor for model manager, recieves datamanager and makes appropriate checks before setting the new context
   * @param dataManager
   * @throws NullPointerException
   */
  public ModelManager(DataManager dataManager)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    context = new Context(dataManager);
    pcs = new PropertyChangeSupport(this);
  }

  /**
   * Adds propertyChangeListner to current PropertyChangeSupport object
   * @param property
   * @param listener
   */
  public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(property, listener);
  }

  /**
   * This method is used to return the current state
   * @return state
   */
  public State getState() {
    return context.getState();
  }

  /**
   * This method is used to change the current state of fsm to login
   * @return result
   */
  public boolean login(String username) {
    boolean result = context.login(username);
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to logout
   * @return result
   */
  public boolean logout() {
    boolean result = context.logout();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to MainMenu
   * @return result
   */
  public boolean changeToMainMenu() {
    boolean result =  context.changeToMainMenu();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to MealOrdering
   * @return result
   */
  public boolean changeToMealOrdering() {
    boolean result =  context.changeToMealOrdering();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to MenuConsultation
   * @return result
   */
  public boolean changeToMenuConsultation() {
    boolean result = context.changeToMenuConsultation();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to ToReviewal
   * @return result
   */
  public boolean changeToReviewal() {
    boolean result =  context.changeToReviewal();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to TicketsConsultation
   * @return result
   */
  public boolean changeToTicketsConsultation() {
    boolean result =  context.changeToTicketsConsultation();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to TransactionHistory
   * @return result
   */
  public boolean changeToTransactionHistory() {
    boolean result = context.changeToTransactionHistory();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }
}
