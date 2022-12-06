package university_meals_made_easy.database.tables;

import university_meals_made_easy.Logger;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeSlot {
  private final int id;
  private final int mealId;
  private final LocalTime timeOfStart;
  private final LocalTime timeOfEnd;
  private final int capacity;

  public TimeSlot(int id, int mealId, String timeOfStartString,
                  String timeOfEndString, int capacity)
      throws NullPointerException, IllegalArgumentException {
    if (timeOfStartString == null)
      throw new NullPointerException("time of start string cannot be null");
    if (timeOfEndString == null)
      throw new NullPointerException("time of end string cannot be null");
    this.id = id;
    this.mealId = mealId;
    try {
      this.timeOfStart = LocalTime.parse(timeOfStartString,
          Logger.timeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "time of start string must have valid format");
    }
    try {
      this.timeOfEnd = LocalTime.parse(timeOfEndString, Logger.timeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "time of end string must have valid format");
    }
    this.capacity = capacity;
  }
  public int getId() {
    return id;
  }
  public int getMealId() {
    return mealId;
  }
  public LocalTime getTimeOfStart() {
    return timeOfStart;
  }
  public String getTimeOfStartAsString() {
    return timeOfStart.format(Logger.timeFormatter);
  }
  public LocalTime getTimeOfEnd() {
    return timeOfEnd;
  }
  public String getTimeOfEndAsString() {
    return timeOfEnd.format(Logger.timeFormatter);
  }
  public int getCapacity() {
    return capacity;
  }
}
