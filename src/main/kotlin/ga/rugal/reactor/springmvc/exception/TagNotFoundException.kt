package ga.rugal.reactor.springmvc.exception

import ga.rugal.torrency.raizekusu.springmvc.exception.GraphqlException
import graphql.ErrorClassification
import graphql.ErrorType

/**
 * Tag not found in system.
 *
 * @author Rugal Bernstein
 */
class TagNotFoundException(id: Int) : GraphqlException("Tag id [${id}] not found") {

  override fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
