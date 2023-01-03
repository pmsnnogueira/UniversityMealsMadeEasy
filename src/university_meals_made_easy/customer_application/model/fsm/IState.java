package university_meals_made_easy.customer_application.model.fsm;

import university_meals_made_easy.customer_application.model.data.result.*;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Ticket;

import java.time.LocalDate;
import java.util.List;

/**
 * IState
 * Interface for that declares all possible states
 */
public interface IState {
  State getState();
  LoginResult login(String username);
  boolean logout();
  boolean changeToMainMenu();
  boolean changeToMealOrdering();
  boolean changeToMenuConsultation();
  boolean changeToReviewal();
  boolean changeToTicketsConsultation();
  boolean changeToTransactionHistory();
  BuyResult buy(TimeSlot slot, List<FoodItem> foodItems);
  RefundResult refund(Ticket ticket);
  ReviewResult review(Ticket ticket, int rating, String comment);
  BalanceTopOffResult topOffBalance(float value);
}
