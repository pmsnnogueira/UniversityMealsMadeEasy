package university_meals_made_easy.database.tables;

import university_meals_made_easy.Logger;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * The TimeSlot Class contains a specific timeslot.
 * Has the methods to return information about the timeslot.
 */
public class TimeSlot {
  private final int id;
  private final int mealId;
  private final LocalTime timeOfStart;
  private final LocalTime timeOfEnd;
  private final int capacity;

  /**
   * Constructor for TimeSlot class that
   * receives some information about the timeslot,
   * and assign them to the local variables.
   * @param id
   * @param mealId
   * @param timeOfStartString
   * @param timeOfEndString
   * @param capacity
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
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

  /**
   * The getId method returns an id for a specific timeSlot.
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * The getMealId method returns an idMeal for a specific timeSlot.
   * @return mealId
   */
  public int getMealId() {
    return mealId;
  }

  /**
   * The getTimeOfStart method returns
   * the time that the time slot starts.
   * @return timeOfStart
   */
  public LocalTime getTimeOfStart() {
    return timeOfStart;
  }

  /**
   * The getTimeOfStartAsString method returns
   * the time that the time slot starts in string type.
   * @return timeOfStart
   */
  public String getTimeOfStartAsString() {
    return timeOfStart.format(Logger.timeFormatter);
  }

  /**
   * The getTimeOfEnd method returns
   * the time that the time slot ends.
   * @return timeOfEnd
   */
  public LocalTime getTimeOfEnd() {
    return timeOfEnd;
  }

  /**
   * The getTimeOfEndAsString method returns
   * the time that the time slot ends in string type.
   * @return timeOfEnd
   */
  public String getTimeOfEndAsString() {
    return timeOfEnd.format(Logger.timeFormatter);
  }

  /**
   * The getCapacity method returns the timeslot capacity.
   * @return capacity
   */
  public int getCapacity() {
    return capacity;
  }


  /**
   * Return the timeOfStart and timeOfEnd in string type.
   * @return
   */
  @Override
  public String toString() {
    return String.format("%s : %s", timeOfStart, timeOfEnd);
  }
}
