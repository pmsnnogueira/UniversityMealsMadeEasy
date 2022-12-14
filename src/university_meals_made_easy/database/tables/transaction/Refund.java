package university_meals_made_easy.database.tables.transaction;

import university_meals_made_easy.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 *This class encapsulates the necessary data that define a refund
 *
 */
public class Refund extends Transaction {
  private final int id;
  private final int appUserId;
  private final int ticketId;
  private final LocalDateTime dateTime;

  /**
   * Constructor for Refund class that
   * receives some information about the refund,
   * and assign them to the local variables.
   * @param id
   * @param appUserId
   * @param ticketId
   * @param dateTimeString
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public Refund(int id, int appUserId, int ticketId,
                String dateTimeString)
      throws NullPointerException, IllegalArgumentException {
    if (dateTimeString == null)
      throw new NullPointerException("date time string cannot be null");
    this.id = id;
    this.appUserId = appUserId;
    this.ticketId = ticketId;
    try {
      dateTime = LocalDateTime.parse(dateTimeString,
          Logger.dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "date time string must have valid format");
    }
  }

  /**
   *
   * @return the id of the refund
   */
  public int getId() {
    return id;
  }

  /**
   *
   * @return the id of the user
   */
  public int getAppUserId() {
    return appUserId;
  }

  /**
   *
   * @return the id of the ticker
   */
  public int getTicketId() {
    return ticketId;
  }

  /**
   *
   * @return the date of that the refund was made
   */
  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public String getDateTimeAsString() {
    return dateTime.format(Logger.dateTimeFormatter);
  }

  @Override
  public String toString() {
    return String.format("Refund ticket: %d on %s", ticketId, getDateTimeAsString());
  }
}
