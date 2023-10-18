package ga.rugal.reactor.springmvc.exception

import ga.rugal.torrency.raizekusu.springmvc.exception.GraphqlException
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
