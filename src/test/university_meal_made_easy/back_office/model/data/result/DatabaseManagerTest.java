package test.university_meal_made_easy.back_office.model.data.result;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import university_meals_made_easy.back_office.model.data.DatabaseManager;
import university_meals_made_easy.back_office.model.data.result.MealFoodItemInsertionResult;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import java.sql.SQLException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DatabaseManagerTest {

  @Test
  @DisplayName("Should throw 'NullPointerException' if the provided slot is NULL ")
  public void slotIsNull() {
      TimeSlot slot = null;
      assertThrows(NullPointerException.class,()-> {
        DatabaseManager.getInstance().getOrderedMealsFoodItems(slot);
      });
  }

  @Test
  @DisplayName("Should throw 'NullPointerException' if the provided meal period is NULL ")
  public void mealPeriodIsNull(){
    MealPeriod mp = null;
    LocalDate ld = LocalDate.now();
    assertThrows(NullPointerException.class,()-> {
      DatabaseManager.getInstance().insertMeal(mp,ld);
    });
  }

  @Test
  @DisplayName("Should throw 'NullPointerException' if the provided date is NULL ")
  public void dateIsNull() {
    MealPeriod mp = MealPeriod.LUNCH;
    LocalDate ld = null;
    assertThrows(NullPointerException.class,()-> {
      DatabaseManager.getInstance().insertMeal(mp,ld);
    });
  }

  @Test
  @DisplayName("Should throw 'NullPointerException' if the provided meal is NULL ")
  public void  mealIsNull() {
    Meal meal = null;
    assertThrows(NullPointerException.class,()-> {
      DatabaseManager.getInstance().insertFoodItem(meal,1,"aaaa");
    });
  }


  @Test
  @DisplayName("Should return 'SUCCESS' if the food item is correctly inserted ")
  public void successInsertingFoodItem() throws SQLException {
    LocalDate date = LocalDate.parse("2022-12-07");
    DatabaseManager.getInstance().insertMeal(MealPeriod.LUNCH, date);
    Meal meal = DatabaseManager.getInstance().getMeal(date,MealPeriod.LUNCH);

    assertEquals(MealFoodItemInsertionResult.SUCCESS, DatabaseManager.getInstance().insertFoodItem(meal,7,"test"));
  }
}


