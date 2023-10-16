package ga.rugal.reactor.springmvc.graphql

import ga.rugal.torrency.raizekusu.springmvc.exception.GraphqlException
import graphql.ErrorType
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

@Component
class CustomExceptionResolver : DataFetcherExceptionResolverAdapter() {

  override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError =
    GraphqlErrorBuilder.newError()
      .errorType(if (ex is GraphqlException) ex.getErrorType() else ErrorType.ValidationError)
      .message(ex.message)
      .path(env.executionStepInfo.path)
      .location(env.field.sourceLocation)
      .build()
}
