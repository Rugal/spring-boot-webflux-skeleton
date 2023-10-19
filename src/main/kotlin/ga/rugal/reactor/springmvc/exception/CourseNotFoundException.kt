package ga.rugal.reactor.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Course not found in system.
 *
 * @author Rugal Bernstein
 */
class CourseNotFoundException(id: Int) : GraphqlException("Course id [${id}] not found") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
