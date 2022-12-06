package university_meals_made_easy.database.tables.Meal;

import university_meals_made_easy.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Meal {
  private final int id;
  private final MealPeriod period;
  private final LocalDate date;

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
  public int getId() {
    return id;
  }
  public MealPeriod getPeriod() {
    return period;
  }
  public LocalDate getDate() {
    return date;
  }
  public String getDateAsString() {
    return date.format(Logger.dateFormatter);
  }
}
