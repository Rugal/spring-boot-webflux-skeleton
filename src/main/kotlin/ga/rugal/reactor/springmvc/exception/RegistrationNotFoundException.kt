package ga.rugal.reactor.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Registration not found in system.
 *
 * @author Rugal Bernstein
 */
class RegistrationNotFoundException(id: Int) : GraphqlException("Registration id [${id}] not found") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
