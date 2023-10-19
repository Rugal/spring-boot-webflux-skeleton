package ga.rugal.reactor.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

abstract class GraphqlException(override val message: String) : RuntimeException(message) {

  open fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
