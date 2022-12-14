package test.university_meal_made_easy.customer_application.model.data;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import university_meals_made_easy.customer_application.model.data.DatabaseManager;
import university_meals_made_easy.customer_application.model.data.result.BuyResult;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DatabaseManagerTest {

    /**
     * Tests that a 'NullPointerException' is thrown when the provided TimeSlot is NULL.
     */
    @Test
    @DisplayName("Should throw 'NullPointerException' if the provided TimeSlot is NULL ")
    public void ticketIsNull(){
        TimeSlot ts  = null;
        List<FoodItem> fd = new ArrayList<>();
        assertThrows(NullPointerException.class,()-> {
            DatabaseManager.getInstance().buy(1,ts,fd);
        });
    }

    @Test
    @DisplayName("Should throw 'NullPointerException' if the provided FoodItems are NULL ")
    public void foodItemsAreNull(){
        TimeSlot ts  = new TimeSlot(1,1,"test","12:11:11",1);
        List<FoodItem> fd = null;
        assertThrows(NullPointerException.class,()-> {
            DatabaseManager.getInstance().buy(1,ts,fd);
        });
    }

    @Test
    @DisplayName("Should throw 'IllegalArgumentException' if the provided FoodItems are Empty ")
    public void foodItemsAreEmpty(){
        TimeSlot ts  = new TimeSlot(1,1,"11:11:11","12:11:11",1);
        List<FoodItem> fd = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,()-> {
            DatabaseManager.getInstance().buy(1,ts,fd);
        });
    }

    @Test
    @DisplayName("Should return 'SUCCESS' if the ticket is successfully bought ")
    public void successBuyingTicket() throws SQLException {
        DatabaseManager.getInstance().login("UserTest");
        int userID =  DatabaseManager.getInstance().getId("UserTest");
        DatabaseManager.getInstance().topOffBalance(userID,100);

        LocalDate lc = LocalDate.now();

        university_meals_made_easy.back_office.model.data.DatabaseManager.getInstance().insertMeal(
                MealPeriod.LUNCH, lc);

        Meal meal = university_meals_made_easy.back_office.model.data.DatabaseManager.getInstance().getMeal(
                lc,MealPeriod.LUNCH
        );

        university_meals_made_easy.back_office.model.data.DatabaseManager.getInstance().insertFoodItem(
               meal,1,"test"
        );

        List<FoodItem> foodItems =  DatabaseManager.getInstance().getFoodItems(meal);
        TimeSlot ts = DatabaseManager.getInstance().getAvailableTimeSlots(meal).get(0);
        assertEquals(BuyResult.SUCCESS,DatabaseManager.getInstance().buy(userID,ts,foodItems));
    }
}
