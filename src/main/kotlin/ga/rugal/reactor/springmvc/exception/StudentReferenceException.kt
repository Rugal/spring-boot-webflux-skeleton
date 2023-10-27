package ga.rugal.reactor.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Student is already registered in system. Remove them all before removing this course.
 *
 * @author Rugal Bernstein
 */
class StudentReferenceException(id: Int) : GraphqlException("Student id [${id}] is referenced in registration") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
