package ga.rugal.reactor.springmvc.exception

import ga.rugal.torrency.raizekusu.springmvc.exception.GraphqlException
import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Student not found in system.
 *
 * @author Rugal Bernstein
 */
class StudentNotFoundException(id: Int) : GraphqlException("Student id [${id}] not found") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
