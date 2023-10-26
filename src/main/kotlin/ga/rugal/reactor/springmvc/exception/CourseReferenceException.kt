package ga.rugal.reactor.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Course is already registered in system. Remove them all before removing this course.
 *
 * @author Rugal Bernstein
 */
class CourseReferenceException(id: Int) : GraphqlException("Course id [${id}] is referenced in registration") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
