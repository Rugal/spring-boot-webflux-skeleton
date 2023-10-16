package ga.rugal.torrency.raizekusu.springmvc.exception

import graphql.ErrorClassification
import graphql.ErrorType

abstract class GraphqlException(override val message: String) : RuntimeException(message) {

  open fun getErrorType(): ErrorClassification = ErrorType.ValidationError
}
