package university_meals_made_easy.database.tables.Meal;

import university_meals_made_easy.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The Meal Class contains a specific meal.
 * Has the methods to return information about the meal
 */
public class Meal {
  private final int id;
  private final MealPeriod period;
  private final LocalDate date;

  /**
   * Constructor for Meal class that receives some information about the meal,
   * and assign them to the local variables,
   * @param id
   * @param period
   * @param dateString
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public Meal(int id, MealPeriod period, String dateString)
      throws NullPointerException, IllegalArgumentException {
    if (period == null)
      throw new NullPointerException("period cannot be null");
    if (dateString == null)
      throw new NullPointerException("date string cannot be null");
    this.id = id;
    this.period = period;
    try {
      date = LocalDate.parse(dateString, Logger.dateFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("date string must have valid format");
    }
  }

  /**
   * The getId method returns an id for a specific meal
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * The getPeriod method returns a meal period for a specific meal
   * @return period
   */
  public MealPeriod getPeriod() {
    return period;
  }

  /**
   * The getDate method returns a date for a specific meal
   * @return date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * The getDateAsString method returns a date in
   * string type for a specific meal
   * @return date
   */
  public String getDateAsString() {
    return date.format(Logger.dateFormatter);
  }

  /**
   * The toString method returns all the class information in string type.
   * @return
   */
  @Override
  public String toString() {
    return String.format("%s - %s", getDateAsString(), period);
  }
}
