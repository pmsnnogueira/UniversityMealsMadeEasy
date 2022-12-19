package university_meals_made_easy.back_office.model.data.result;


/**
 * Enum that defines all the possibilities that
 * can occur when validating a ticket
 */
public enum TicketValidationResult {
  TICKET_DOES_NOT_EXIST,
  UNEXPECTED_ERROR,
  TICKET_ALREADY_VALIDATED, TICKET_ALREADY_REFUNDED, WRONG_TIME_SLOT, SUCCESS
}
