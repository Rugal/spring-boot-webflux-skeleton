package ga.rugal.reactor.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Registration already exists in system.
 *
 * @author Rugal Bernstein
 */
class RedundantRegistrationException(studentId: Int, courseId: Int)
  : GraphqlException("Student [${studentId}] already register the course [${courseId}]") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
